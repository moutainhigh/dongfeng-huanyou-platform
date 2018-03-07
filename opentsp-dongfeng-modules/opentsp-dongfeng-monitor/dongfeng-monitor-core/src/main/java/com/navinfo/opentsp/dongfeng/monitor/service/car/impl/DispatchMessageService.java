package com.navinfo.opentsp.dongfeng.monitor.service.car.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.navinfo.opentsp.dongfeng.common.constant.Constants;
import com.navinfo.opentsp.dongfeng.common.constant.TcpCommandEnum;
import com.navinfo.opentsp.dongfeng.common.dto.TamperData;
import com.navinfo.opentsp.dongfeng.common.pojo.TerminalLogPojo;
import com.navinfo.opentsp.dongfeng.common.result.CommonResult;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.common.service.BaseService;
import com.navinfo.opentsp.dongfeng.common.service.IRedisService;
import com.navinfo.opentsp.dongfeng.common.service.ITerminalLogService;
import com.navinfo.opentsp.dongfeng.common.util.ClientUtil;
import com.navinfo.opentsp.dongfeng.common.util.IDGeneralUtil;
import com.navinfo.opentsp.dongfeng.monitor.client.CmdClient;
import com.navinfo.opentsp.dongfeng.monitor.commands.car.DispatchMessageCommand;
import com.navinfo.opentsp.dongfeng.monitor.model.Gps_2151_Cmd;
import com.navinfo.opentsp.dongfeng.monitor.pojo.car.QueryCarInfoPojo;
import com.navinfo.opentsp.dongfeng.monitor.service.car.IDispatchMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

/**
 * @author tushenghong
 * @version 1.0
 * @date 2017-03-28
 * @modify
 * @copyright Navi Tsp
 */
@Service
public class DispatchMessageService extends BaseService implements IDispatchMessageService
{
    
    @Autowired
    private ITerminalLogService terminalLogService;
    
    @Autowired
    private IRedisService redisService;
    
    @Autowired
    CmdClient cmdClient;
    
    @Override
    @Transactional
    public HttpCommandResultWithData sendMessage(DispatchMessageCommand command)
    {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        // 查询车辆信息
        Map<String, Object> parm = new HashMap<String, Object>();
        // 根据车辆ID获取车辆基本信息
        parm.put("id", command.getCarId());
        parm.put("accountType", command.getUserInfor().getType());
        parm.put("accountId", command.getUserInfor().getUserId());
        QueryCarInfoPojo carInfoPojo =
            (QueryCarInfoPojo)dao.sqlFindObject("queryCarInfo", parm, QueryCarInfoPojo.class);
        String communicationIds = carInfoPojo.getFkCommNum().toString();
        // 保存日志
        String serialNum = null;
        try
        {
            serialNum =
                saveTerminalLog(carInfoPojo,
                    command.getUserInfor().getUsername(),
                    command.getToken(),
                    TcpCommandEnum.DISPATCH_MESSAGE,
                    ClientUtil.getClientIp(command.getRequest()));
        }
        catch (JsonProcessingException e)
        {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
        // 发送消息到TCP模块
        CommonResult tcpResult = sendDispatchMessageToTCPModel(communicationIds, serialNum, command);
        if (tcpResult.getResultCode() != ReturnCode.OK.code())
        {
            result.setResultCode(tcpResult.getResultCode());
            result.setMessage(tcpResult.getMessage());
        }
        return result;
    }
    
    /**
     * 发送消息到TCP模块
     *
     * @param communicationIds 通讯号
     * @param command 消息内容
     * @return
     */
    private CommonResult sendDispatchMessageToTCPModel(String communicationIds, String serialNum,
        DispatchMessageCommand command)
    {
        CommonResult result = new CommonResult();
        Gps_2151_Cmd gps_2151_cmd = new Gps_2151_Cmd();
        gps_2151_cmd.setToken(command.getToken());
        gps_2151_cmd.setCommand(TcpCommandEnum.DISPATCH_MESSAGE.getCommand());
        gps_2151_cmd.setContent(command.getMessageContent());
        gps_2151_cmd.setType(command.getShowType());
        gps_2151_cmd.setCommunicationIds(communicationIds);
        gps_2151_cmd.setSerialNumber(serialNum);
        try
        {
            result = cmdClient.call_2151(gps_2151_cmd);
        }
        catch (Exception e)
        {
            logger.error(e.getMessage(), e);
            result.fillResult(ReturnCode.DISPATCH_MESSAGE_TO_TCP_ERROR);
        }
        return result;
    }
    
    /**
     * 保存终端指令日志
     *
     * @param pojo
     * @param userName
     * @param token
     * @param tcpCommandEnum
     * @param operateIp 操作人ip
     * @throws JsonProcessingException
     */
    private String saveTerminalLog(QueryCarInfoPojo pojo, String userName, String token, TcpCommandEnum tcpCommandEnum,
        String operateIp)
        throws JsonProcessingException
    {
        TerminalLogPojo terminalLogPojo = new TerminalLogPojo();
        terminalLogPojo.setOperateUser(userName);
        terminalLogPojo.setSimNo(BigInteger.valueOf(Long.valueOf(pojo.getFkSim())));
        terminalLogPojo.setChassisNum(pojo.getChassisNum());
        terminalLogPojo.setCarNum(pojo.getPlateNum());
        terminalLogPojo.setTeam(pojo.getDealer());
        terminalLogPojo.setReCustom(pojo.getCustomer());
        terminalLogPojo.setDes(tcpCommandEnum.getMsg() + "操作");
        terminalLogPojo.setType(tcpCommandEnum.getMsg());
        terminalLogPojo.setOperateIp(operateIp);
        BigInteger logId = terminalLogService.addTerminalCommandLog(terminalLogPojo);
        TamperData data = new TamperData();
        data.setCommanId(tcpCommandEnum.getCommand());
        data.setLogId(logId.toString());
        data.setToken(token);
        // 获取序列号
        String serialNum = IDGeneralUtil.builderID(IDGeneralUtil.IDTypeEnum.SerialNumber).toString();
        // 保存日志
        redisService.saveObjectToJson(Constants.MSG_KEY_PREFIX  + "_" + serialNum + "_"
            + tcpCommandEnum.getCommand().substring(0, 4), data);
        return serialNum;
    }
    
}
