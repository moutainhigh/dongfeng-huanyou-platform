package com.navinfo.opentsp.dongfeng.monitor.service.car.impl;

import com.navinfo.opentsp.dongfeng.common.constant.Constants;
import com.navinfo.opentsp.dongfeng.common.constant.TcpCommandEnum;
import com.navinfo.opentsp.dongfeng.common.pojo.TerminalLogPojo;
import com.navinfo.opentsp.dongfeng.common.result.CommonResult;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.common.service.BaseService;
import com.navinfo.opentsp.dongfeng.common.service.IRedisService;
import com.navinfo.opentsp.dongfeng.common.service.ITerminalLogService;
import com.navinfo.opentsp.dongfeng.common.util.ClientUtil;
import com.navinfo.opentsp.dongfeng.common.util.tcp.Convert;
import com.navinfo.opentsp.dongfeng.monitor.client.CmdClient;
import com.navinfo.opentsp.dongfeng.monitor.commands.car.VoiceMonitoringCommand;
import com.navinfo.opentsp.dongfeng.monitor.model.Gps_2152_Cmd;
import com.navinfo.opentsp.dongfeng.monitor.pojo.car.VoiceMonitoringPojo;
import com.navinfo.opentsp.dongfeng.monitor.service.car.IVoiceMonitoringService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 语音监控
 *
 * @author fengwm
 * @version 1.0
 * @date 2017-03-09
 * @modify
 * @copyright Navi Tsp
 */
@Service
public class VoiceMonitoringServiceImpl extends BaseService implements IVoiceMonitoringService
{
    
    @Autowired
    ITerminalLogService iTerminalLogService;
    
    @Autowired
    private IRedisService redisService;
    
    @Autowired
    CmdClient cmdClient;
    
    /**
     * VoiceMonitoringServiceImpl LOG
     */
    protected static final Logger logger = LoggerFactory.getLogger(VoiceMonitoringServiceImpl.class);
    
    @Override
    @Transactional
    public CommonResult voiceMonitoring(VoiceMonitoringCommand command)
        throws Exception
    {
        
        CommonResult result = new CommonResult();
        CommonResult bdSendResult = new CommonResult();
        CommonResult fkSendResult = new CommonResult();
        Long bdCom = 0l;// 一体机
        Long fkCom = 0l;// 防拆盒
        // 车辆ID
        String carId = command.getCarId();
        // 获取通讯号（北斗and防拆盒）
        Map<String, Object> conMap = new HashMap<String, Object>();
        conMap.put("carId", new BigInteger(carId));
        VoiceMonitoringPojo pojo =
            (VoiceMonitoringPojo)dao.sqlFindObject("queryCommIdByCarId", conMap, VoiceMonitoringPojo.class);
        // 判断绑定通讯号
        if (null != pojo.getBdCommId())
        {
            bdCom = pojo.getBdCommId().longValue();
        }
        if (null != pojo.getFkCommId())
        {
            fkCom = pojo.getFkCommId().longValue();
        }
        if ((null == bdCom || bdCom == 0l) && (null == fkCom || fkCom == 0l))
        {
            result.setResultCode(ReturnCode.SERVER_ERROR.code());
            result.setMessage(pojo.getCarNum() + ",没有绑定终端");
            return result;
        }
        Gps_2152_Cmd gps_2152_cmd = new Gps_2152_Cmd();
        // 北斗发送语音监控
        if (bdCom != null && bdCom != 0)
        {
            TerminalLogPojo hyTerminalLog = setLog(command, pojo);
            // 赋值北斗通讯号
            hyTerminalLog.setSimNo(new BigInteger(String.valueOf(bdCom)));
            // 保存日志
            BigInteger logId = iTerminalLogService.addTerminalCommandLog(hyTerminalLog);
            if (logId.intValue() > 0)
            {
                // 组装package
                gps_2152_cmd = setPackage(command, bdCom, logId);
                bdSendResult = cmdClient.call_2152(gps_2152_cmd);
            }
        }
        // 防拆盒发送语音监控
        if (fkCom != null && fkCom != 0)
        {
            TerminalLogPojo hyTerminalLog = setLog(command, pojo);
            // 赋值防拆盒通讯号
            hyTerminalLog.setSimNo(new BigInteger(String.valueOf(fkCom)));
            // 保存日志
            BigInteger logId = iTerminalLogService.addTerminalCommandLog(hyTerminalLog);
            if (logId.intValue() > 0)
            {
                // 组装package
                gps_2152_cmd = setPackage(command, fkCom, logId);
                bdSendResult = cmdClient.call_2152(gps_2152_cmd);
            }
        }
        if (!(bdSendResult.getResultCode() == 200 && fkSendResult.getResultCode() == 200))
        {
            result.setMessage(pojo.getCarNum() + "发送失败");
        }
        else
        {
            result.setMessage(pojo.getCarNum() + "发送成功");
        }
        return result;
    }
    
    /**
     * 组装日志类
     *
     * @param command
     * @param pojo
     * @return
     */
    public TerminalLogPojo setLog(VoiceMonitoringCommand command, VoiceMonitoringPojo pojo)
    {
        TerminalLogPojo hyTerminalLog = new TerminalLogPojo();
        String content = "";
        if (command.getType().equals("0"))
        {
            content = TcpCommandEnum.VOICE_MONITORING_LISTENING.getMsg();
        }
        else
        {
            content = TcpCommandEnum.VOICE_MONITORING_CALLING.getMsg();
        }
        String logname = TcpCommandEnum.VOICE_MONITORING.getMsg();
        hyTerminalLog.setValue(Constants.ResponseResult.waiting.getValue());// 数据库字段—LOG_VALUE
        hyTerminalLog.setDes(content);// 数据库字段—LOG_CONTENT
        hyTerminalLog.setType(logname);// 数据库字段—LOG_NAME
        hyTerminalLog.setOperateUser(command.getUserInfor().getUsername());// 数据库字段—LOG_ACCOUNT_NAME
        hyTerminalLog.setChassisNum(pojo.getChassisNum());// 数据库字段—CHASSIS_NUM
        hyTerminalLog.setCarNum(pojo.getCarNum());// 数据库字段—CAR_CPH
        hyTerminalLog.setTeam(pojo.getTeam());// 数据库字段—COMPANY_NAME
        hyTerminalLog.setReCustom(pojo.getReCustom());// 数据库字段—CAR_OWNER
        // 注意：数据库字段—MODULE_TYPE 只有防控激活使用：默认是NUll，防控激活是1
        hyTerminalLog.setOperateIp(ClientUtil.getClientIp(command.getRequest()));// 数据库字段—LOG_ACCOUNT_IP
        hyTerminalLog.setStatus(Constants.ResponseResult.waiting.getValue());// 数据库字段—LOG_STATE
        hyTerminalLog.setOperateTime(new BigInteger(String.valueOf(new Date().getTime() / 1000)));// 数据库字段—LOG_DATE
        return hyTerminalLog;
    }
    
    /**
     * 组装package
     *
     * @param command
     * @param comm
     * @return
     */
    public Gps_2152_Cmd setPackage(VoiceMonitoringCommand command, long comm, BigInteger logId)
    {
        Gps_2152_Cmd gps_2152_cmd = new Gps_2152_Cmd();
        gps_2152_cmd.setType(command.getType());
        gps_2152_cmd.setPhone(command.getPhone());
        gps_2152_cmd.setUniqueMark(Convert.fillZero(comm, 12));
        gps_2152_cmd.setCommand(TcpCommandEnum.VOICE_MONITORING.getCommand());
        gps_2152_cmd.setLogId(logId.toString());
        gps_2152_cmd.setToken(command.getToken());
        return gps_2152_cmd;
    }
}