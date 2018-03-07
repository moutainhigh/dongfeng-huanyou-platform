package com.navinfo.opentsp.dongfeng.kafka.service.impl;

import com.google.protobuf.InvalidProtocolBufferException;
import com.lc.core.protocol.terminal.LCDownCommonRes;
import com.navinfo.opentsp.dongfeng.common.constant.MessagePushConstants;
import com.navinfo.opentsp.dongfeng.common.dto.Message;
import com.navinfo.opentsp.dongfeng.common.dto.TamperData;
import com.navinfo.opentsp.dongfeng.common.entity.HyTerminalLogEntity;
import com.navinfo.opentsp.dongfeng.common.pojo.TerminalLogPojo;
import com.navinfo.opentsp.dongfeng.common.service.BaseService;
import com.navinfo.opentsp.dongfeng.common.service.IRedisService;
import com.navinfo.opentsp.dongfeng.common.service.ITerminalLogService;
import com.navinfo.opentsp.dongfeng.common.util.CloudUtil;
import com.navinfo.opentsp.dongfeng.common.util.StringUtil;
import com.navinfo.opentsp.dongfeng.common.util.tcp.Convert;
import com.navinfo.opentsp.dongfeng.kafka.client.PushClient;
import com.navinfo.opentsp.dongfeng.kafka.configuration.kafka.KafkaCommand;
import com.navinfo.opentsp.dongfeng.kafka.service.IGps_3001_Service;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.Date;
import java.util.Set;

/**
 * @author fengwm
 * @version 1.0
 * @date 2017-03-29
 * @modify
 * @copyright Navi Tsp
 */
@Service(value = "gps_3001_Service")
@EnableAsync
public class Gps_3001_ServiceImpl extends BaseService implements IGps_3001_Service
{
    
    @Autowired
    ITerminalLogService iTerminalLogService;
    
    @Autowired
    private IRedisService redisService;
    
    @Autowired
    private PushClient pushClient;

    @Override
    @Transactional
    public void businessManage(KafkaCommand kafkaCommand)
            throws InvalidProtocolBufferException
    {
        LCDownCommonRes.DownCommonRes res = LCDownCommonRes.DownCommonRes.parseFrom(kafkaCommand.getMessage());
        //下发指令的指令号
        String responseId = Convert.decimalToHexadecimal(res.getResponseId(), 4);
        // 下发指令的流水号
        String SeriaNumber = Convert.decimalToHexadecimal(res.getSerialNumber(), 4);
        // 3001是通用应答，所以用模糊查询
        Set<String> keys = redisService.getKeys("MSGPUSH_*_" + SeriaNumber + "_*");
        TamperData tamperData = null;
        for (String key : keys)
        {
            // 获取下发指令redis信息
            tamperData = redisService.getJson(key, TamperData.class);
            // 删除redis
            redisService.del(key);
        }
        // 修改日志状态
        if (null != tamperData && StringUtil.isNotEmpty(tamperData.getLogId()))
        {
            logger.debug("指令号:3001 返回结果: " + res.getResult().getNumber() + "");

            String logId = tamperData.getLogId();
            // 获取到下发保存日志
            HyTerminalLogEntity logEntity = iTerminalLogService.queryTerminalLogById(new BigInteger(logId));
            // 位置云返回状态和平台状态进行对应更改
            String state = CloudUtil.statueToHyStatue(res.getResult().getNumber() + "");
            
            logger.debug("指令号:3001 state: " + res.getResult().getNumber() + "");

            // 设置状态
            logEntity.setLogState(Integer.valueOf(state));
            // 设置logValue
            logEntity.setLogValue(Integer.valueOf(state));
            TerminalLogPojo pojo = entityToPojo(logEntity);
            // 修改日志状态
            iTerminalLogService.updateTerminalLog(pojo);
            // push到前段
             pushMessage(tamperData.getToken(),responseId);
        }
    }
    
    /**
     * entityToPojo>>TerminalLogPojo
     *
     * @param hyteminallog
     * @return
     */
    private TerminalLogPojo entityToPojo(HyTerminalLogEntity hyteminallog)
    {
        TerminalLogPojo pojo = new TerminalLogPojo();
        pojo.setSimNo(hyteminallog.getLogSim());
        pojo.setStatus(hyteminallog.getLogState());
        pojo.setCarNum(hyteminallog.getCarCph());
        pojo.setChassisNum(hyteminallog.getChassisNum());
        pojo.setDes(hyteminallog.getLogContent());
        pojo.setLogId(hyteminallog.getLogId());
        pojo.setLockCommon(hyteminallog.getLogLockCommon());
        pojo.setLogCommon(hyteminallog.getLogCommon());
        pojo.setReCustom(hyteminallog.getCarOwner());
        pojo.setOperateIp(hyteminallog.getLogAccountIp());
        pojo.setOperateUser(hyteminallog.getLogAccountName());
        pojo.setTeam(hyteminallog.getCompanyName());
        pojo.setType(hyteminallog.getLogName());
        pojo.setValue(hyteminallog.getLogValue());
        return pojo;
    }
    
    /**
     * 推送到前端
     *
     * @param token
     * @param responseId
     */
    @Async
    public void pushMessage(String token, String responseId)
    {
        Message msg = new Message();
        JSONObject json = new JSONObject();
        json.put("type", MessagePushConstants.PushType.CMD_SEND_ANSWER.getCode());
        json.put("message", MessagePushConstants.reponseMessage(responseId));
        json.put("data", "");
        msg.setData(json.toString());
        msg.setToken(token);
        try
        {
            pushClient.messagePush(msg);
            logger.info((new Date()).toString() + ">>>3001: Gps_3001_ServiceImpl:" + responseId + "--推送至前段成功！");
        }
        catch (Exception e)
        {
            logger.error(e.getMessage(), e);
        }
    }
}