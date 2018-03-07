package com.navinfo.opentsp.dongfeng.kafka.service.impl;

import com.lc.core.protocol.terminal.monitor.LCTerminalStatusControlRes;
import com.navinfo.opentsp.dongfeng.common.cache.AlarmCache;
import com.navinfo.opentsp.dongfeng.common.constant.Constants;
import com.navinfo.opentsp.dongfeng.common.constant.MessagePushConstants;
import com.navinfo.opentsp.dongfeng.common.dto.AlarmInfoData;
import com.navinfo.opentsp.dongfeng.common.dto.Message;
import com.navinfo.opentsp.dongfeng.common.dto.TamperData;
import com.navinfo.opentsp.dongfeng.common.entity.HyTerminalLogEntity;
import com.navinfo.opentsp.dongfeng.common.pojo.TerminalLogPojo;
import com.navinfo.opentsp.dongfeng.common.service.BaseService;
import com.navinfo.opentsp.dongfeng.common.service.ITerminalLogService;
import com.navinfo.opentsp.dongfeng.common.util.CloudUtil;
import com.navinfo.opentsp.dongfeng.common.util.tcp.Convert;
import com.navinfo.opentsp.dongfeng.kafka.client.PushClient;
import com.navinfo.opentsp.dongfeng.kafka.configuration.kafka.KafkaCommand;
import com.navinfo.opentsp.dongfeng.kafka.entity.HyCarEntity;
import com.navinfo.opentsp.dongfeng.kafka.pojo.CarInfoPojo;
import com.navinfo.opentsp.dongfeng.kafka.service.IGps_3170_Service;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.*;

/**
 * @wenya
 * @create 2017-03-31 18:05
 **/
@Service(value = "gps_3170_Service")
@EnableAsync
public class Gps_3170_ServiceImpl extends BaseService implements IGps_3170_Service
{
    @Autowired
    private ITerminalLogService terminalLogService;
    
    @Autowired
    private PushClient pushClient;

    @Autowired
    private AlarmCache alarmCache;
    
    @Override
    @Transactional
    public void businessManage(KafkaCommand kafkaCommand)
        throws Exception
    {
        try
        {
            LCTerminalStatusControlRes.TerminalStatusControlRes res = LCTerminalStatusControlRes.TerminalStatusControlRes.parseFrom(kafkaCommand.getMessage());


            String sim = kafkaCommand.getKey();
            String CommandId = kafkaCommand.getCommandId();
            String serialNumber = Convert.decimalToHexadecimal(res.getSerialNumber(), 4);

            logger.info("3170:"+serialNumber+","+res.getResult().getNumber());
            // 位置云回复非终端离线，则删除redis缓存
            // if (res.getResult().getNumber() != 5) {
            // clearOfflineCache(sim);
            // }
            // 获取车辆信息
            Map<String, Object> conMap = new HashMap<String, Object>();
            conMap.put("commId", new BigInteger(sim));
            CarInfoPojo pojo = (CarInfoPojo)dao.sqlFindObject("queryCarByCommId", conMap, CarInfoPojo.class);
            // 修改日志
            List<TamperData> tamperDatas = new ArrayList<>();
            Set<String> keys = redisService.getKeys("MSGPUSH_*_" + serialNumber + "_" + 2170);
            Iterator<String> it = keys.iterator();
            while (it.hasNext())
            {
                TamperData tamperData = redisService.getJson(it.next(), TamperData.class);
                tamperDatas.add(tamperData);
            }
            Integer logValue = null;
            if (tamperDatas != null && tamperDatas.size() != 0)
            {
                logValue = updateLog(serialNumber, res, tamperDatas.get(0).getLogId(), pojo.getTerminalType(), sim);
            }
            else
            { // FFFF
                logValue = updateLog(serialNumber, res, null, pojo.getTerminalType(), sim);
            }
            // 根据指令回复修改车辆状态
            changeLockState(pojo, tamperDatas.get(0), res, serialNumber, logValue);

            //添加解锁车解锁报警
            addAlarmRemind(serialNumber,res,sim,pojo.getOperateUser());
        }
        catch (Exception e)
        {
            logger.error(e.getMessage(),e);
            throw e;
        }
    }
    
    // 根据指令回复修改车辆状态
    private void changeLockState(CarInfoPojo pojo, TamperData tamperData, LCTerminalStatusControlRes.TerminalStatusControlRes res,
        String serialNumber, Integer logValueInt)
    {
        if (pojo.getTerminalType() != null && pojo.getTerminalType() == 0)
        { // 防拆盒
            if (String.valueOf(serialNumber).equals("FFFF"))
            { // F9发送流水号为ffff的指令
                if (res.getResult().getNumber() == 7)
                { // ecu成功
                    Integer lockStatus = updateLockStatus(pojo, res);
                    // 删除redis
                    clearCache(serialNumber);
                    // 推送
                    pushMessage(tamperData.getToken(), lockStatus, pojo.getCarId());
                }
                else if (res.getResult().getNumber() == 9)
                {// 车辆关机时车辆状态不变
                
                }
                else
                {
                    cancleLockStatus(pojo);
                    // 删除redis
                    clearCache(serialNumber);
                }
            }
            else
            { // F9正常发送
                // HyTerminalLogEntity hyteminallog = terminalLogService.queryTerminalLogById(new
                // BigInteger(tamperData.getLogId()));
                String logValue = String.valueOf(logValueInt);
                if ((logValue.startsWith("2") || logValue.startsWith("3")) && res.getResult().getNumber() == 7)
                { // 第二次返回ECU成功或者第三次返回ECU成，f9返回成功
                    Integer lockStatus = updateLockStatus(pojo, res);
                    // 删除redis
                    clearCache(serialNumber);
                    // 推送
                    pushMessage(tamperData.getToken(), lockStatus, pojo.getCarId());
                }
                else if ((logValue.startsWith("2") && res.getResult().getNumber() == 12)
                    || (logValue.startsWith("1") && res.getResult().getNumber() == 0))
                {// 第一次返回成功，或第二次返回车辆关机，状态不变
                
                }
                else
                {
                    cancleLockStatus(pojo);
                    // 删除redis
                    clearCache(serialNumber);
                }
            }
        }
        else
        {
            // 东风无一体机相应指令协议，暂不处理一体机，后续可添加
        }
    }
    
    // 修改日志状态
    @Transactional
    public Integer updateLog(String serialNumber, LCTerminalStatusControlRes.TerminalStatusControlRes res, String logId, Integer terminalType,
        String sim)
    {
        HyTerminalLogEntity hyteminallog = new HyTerminalLogEntity();
        String state = CloudUtil.statueToHyStatue(res.getResult().getNumber() + "");
        if (logId == null)
        {
            // 数据库里查出最新的一条指令类型相同（即都是激活），回复记录为29
            Map<String, Object> conMap = new HashMap<String, Object>();
            conMap.put("commId", new BigInteger(sim));
            conMap.put("logValue", 29);
            if (res.getTypes() == 1)
            { // 激活
                conMap.put("logName", Constants.TAMPERON);
            }
            else if (res.getTypes() == 2)
            { // 取消激活
                conMap.put("logName", Constants.TAMPEROFF);
            }
            else if (res.getTypes() == 3)
            { // 锁车
                conMap.put("logName", Constants.LOCKCARON);
            }
            else
            {
                conMap.put("logName", Constants.LOCKCAROFF);
            }
            hyteminallog = (HyTerminalLogEntity)dao.sqlFindObject("queryCarByCommId", conMap, CarInfoPojo.class);
            hyteminallog.setLogValue(Integer.parseInt("4" + state)); // 保证从数据库中可以看出是终端自动回复的流水号为ffff的
            if (terminalType != null && terminalType.intValue() == 0)
            {
                hyteminallog.setLogState(getLogState(hyteminallog.getLogValue()));
            }
        }
        else
        {

            hyteminallog = terminalLogService.queryTerminalLogById(new BigInteger(logId));
            if (hyteminallog!=null){
                if (hyteminallog.getLogValue().intValue() == Constants.ResponseResult.waiting.getValue())
                { // 第一次回复
                    hyteminallog.setLogValue(Integer.parseInt("1" + state));
                    hyteminallog.setLogState(getLogState(hyteminallog.getLogValue()));
                }
                else if (String.valueOf(hyteminallog.getLogValue()).startsWith("1"))
                { // 第二次回复
                    hyteminallog.setLogValue(Integer.parseInt("2" + state));
                    hyteminallog.setLogState(getLogState(hyteminallog.getLogValue()));
                }
                else if (String.valueOf(hyteminallog.getLogValue()).startsWith("2"))
                { // 第三次回复
                    hyteminallog.setLogValue(Integer.parseInt("3" + state));
                    hyteminallog.setLogState(getLogState(hyteminallog.getLogValue()));
                }
            }
        }
        // 修改日志
        TerminalLogPojo pojo = entityToPojo(hyteminallog);
        terminalLogService.updateTerminalLog(pojo);
        return hyteminallog.getLogValue();
    }
    
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
    
    // 推送到前端
    @Async
    public void pushMessage(String token, Integer lockStatus, BigInteger carId)
    {
        Message msg = new Message();
        JSONObject json = new JSONObject();
        json.put("type", MessagePushConstants.PushType.CMD_SEND_ANSWER.getCode());
        json.put("message", "成功");
        JSONObject jsonData = new JSONObject();
        jsonData.put("lockStatus", lockStatus);
        jsonData.put("carId", carId);
        json.put("data", jsonData);
        msg.setData(json.toString());
        msg.setToken(token);
        try
        {
            pushClient.messagePush(msg);
        }
        catch (Exception e)
        {
            logger.error(e.getMessage(), e);
        }
    }
    
    // 应答成功，车辆更新
    @Transactional
    private Integer updateLockStatus(CarInfoPojo pojo, LCTerminalStatusControlRes.TerminalStatusControlRes res)
    {
        HyCarEntity carEntity = new HyCarEntity();
        carEntity.setCarId(pojo.getCarId());
        HyCarEntity car = (HyCarEntity)dao.findOne(carEntity);
        // 2.解析protobuffer，根据返回值设置车辆锁定状态值
        logger.error("TerminalStatusControlRes==" + res.getResult().getNumber() + "===" + res.getTypes());
        if ((res.hasResult() && res.getResult().getNumber() == 0)
            || (res.hasResult() && res.getResult().getNumber() == 7))
        {
            if (res.getTypes() == 1
                && (pojo.getLockStatue() == null || pojo.getLockStatue() == 0 || pojo.getLockStatue() == 4))
            {// 激活
                car.setLockStauts(2);
            }
            else if (res.getTypes() == 2 && pojo.getLockStatue() == 2 || pojo.getLockStatue() == 5)
            {// 关闭
                car.setLockStauts(0);
            }
            else if (res.getTypes() == 3 && pojo.getLockStatue() == 2 || pojo.getLockStatue() == 6)
            {// 锁车
                car.setLockStauts(3);
            }
            else if (res.getTypes() == 4 && pojo.getLockStatue() == 3 || pojo.getLockStatue() == 7)
            {// 解锁
                car.setLockStauts(2);
            }
        }
        dao.update(car);
        return car.getLockStauts();
    }
    
    // 指令应答失败，回退车辆状态
    @Transactional
    private void cancleLockStatus(CarInfoPojo pojo)
    {
        HyCarEntity carEntity = new HyCarEntity();
        carEntity.setCarId(pojo.getCarId());
        HyCarEntity car = (HyCarEntity)dao.findOne(carEntity);
        logger.error("cancleLockStatus==锁车状态" + pojo.getLockStatue());
        if (pojo.getLockStatue() == 4 || pojo.getLockStatue() == 5 || pojo.getLockStatue() == 6
            || pojo.getLockStatue() == 7)
        {
            if (pojo.getLockStatue() == 4)
            {
                car.setLockStauts(0);
            }
            else if (pojo.getLockStatue() == 5)
            {
                car.setLockStauts(2);
            }
            else if (pojo.getLockStatue() == 6)
            {
                car.setLockStauts(2);
            }
            else if (pojo.getLockStatue() == 7)
            {
                car.setLockStauts(3);
            }
        }
        dao.update(car);
        
    }
    
    public void clearCache(String serialNumber)
    {
        Set<String> keys = redisService.getKeys("MSGPUSH_*_" + serialNumber + "_" + 2170);
        Iterator<String> it = keys.iterator();
        while (it.hasNext())
        {
            redisService.del(it.next());
        }
    }
    
    // 获取F9日志状态
    public static Integer getLogState(Integer logValue)
    {
        switch (logValue)
        {
            case 10:
                // 失败
                return Constants.ResponseResult.failure.getValue();
            case 11:
                // 成功
                return Constants.ResponseResult.executing.getValue();
            case 12:
                // 消息有误
                return Constants.ResponseResult.messageerror.getValue();
            case 15:
                // 终端离线
                return Constants.ResponseResult.offline.getValue();
            case 22:
                // 消息有误
                return Constants.ResponseResult.messageerror.getValue();
            case 27:
                // 成功
                return Constants.ResponseResult.success.getValue();
            case 28:
                // 失败
                return Constants.ResponseResult.failure.getValue();
            case 29:
                // 终端不支持
                return Constants.ResponseResult.notsupport.getValue();
            case 210:
                // 操作类型无效
                return Constants.ResponseResult.typeError.getValue();
            case 211:
                // 限制类型无效
                return Constants.ResponseResult.limitError.getValue();
            case 212:
                // 车辆关机
                return Constants.ResponseResult.vehicleClose.getValue();
            case 213:
                // 总线通信异常
                return Constants.ResponseResult.communicateError.getValue();
            case 32:
                // 消息有误
                return Constants.ResponseResult.messageerror.getValue();
            case 37:
                // 成功
                return Constants.ResponseResult.success.getValue();
            case 38:
                // 失败
                return Constants.ResponseResult.failure.getValue();
            case 39:
                // 终端不支持
                return Constants.ResponseResult.notsupport.getValue();
            case 310:
                // 操作类型无效
                return Constants.ResponseResult.typeError.getValue();
            case 311:
                // 限制类型出错
                return Constants.ResponseResult.limitError.getValue();
            case 313:
                // 总线通信异常
                return Constants.ResponseResult.communicateError.getValue();
                // 针对终端回复流水号为FFFF处理
            case 41:// 成功，正在执行
                return Constants.ResponseResult.executing.getValue();
            case 40:// 失败
                return Constants.ResponseResult.failure.getValue();
            case 49:// 终端不支持
                return Constants.ResponseResult.typeError.getValue();
            case 47:// ecu成功
                return Constants.ResponseResult.success.getValue();
            case 48:// ecu失败
                return Constants.ResponseResult.failure.getValue();
            case 411:// 限制类型出错
                return Constants.ResponseResult.limitError.getValue();
            case 42:// 消息有误
                return Constants.ResponseResult.messageerror.getValue();
            case 410:// 操作类型无效
                return Constants.ResponseResult.typeError.getValue();
            case 412:// 车辆关机
                return Constants.ResponseResult.vehicleClose.getValue();
            case 413:// 总线通信异常
                return Constants.ResponseResult.communicateError.getValue();
                // 针对终端回复流水号为FFFF处理
            default:
                // 消息有误
                return Constants.ResponseResult.messageerror.getValue();
        }
    }

    /**
     * 添加锁车解锁报警
     * @param res
     * @param sim
     */
    public void addAlarmRemind(String serialNumber,LCTerminalStatusControlRes.TerminalStatusControlRes res,String sim,String operateUser){
        AlarmInfoData alarmremind = new AlarmInfoData();
        alarmremind.setSim(sim);
        alarmremind.setAlarmType(4);
        alarmremind.setAlarmDate(new Date().getTime() / 1000);
        alarmremind.setAlarmOverType(0);
        if (serialNumber.equals("FFFF")) {
            String lockCarReason = operateUser + "人工下发";
            if (res.getTypes() == 3) {
                alarmremind.setLockCarReason(lockCarReason);
                alarmremind.setLockCar("锁车");
                alarmCache.add(alarmremind);
            }else if (res.getTypes() == 4) {
                alarmremind.setLockCarReason(lockCarReason);
                alarmremind.setLockCar("解锁");
                alarmCache.add(alarmremind);
            }
        }else{
            String lockCarReason = "出区域限速";
            if (res.getTypes() == 3) {
                alarmremind.setLockCarReason(lockCarReason);
                alarmremind.setLockCar("锁车");
                alarmCache.add(alarmremind);
            }
        }


    }
}
