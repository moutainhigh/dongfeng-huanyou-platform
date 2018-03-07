package com.navinfo.opentsp.dongfeng.monitor.service.car.impl;

import com.navinfo.opentsp.dongfeng.common.cache.GpsInfoCache;
import com.navinfo.opentsp.dongfeng.common.constant.Constants;
import com.navinfo.opentsp.dongfeng.common.dto.GpsInfoData;
import com.navinfo.opentsp.dongfeng.common.pojo.TerminalLogPojo;
import com.navinfo.opentsp.dongfeng.common.result.CommonResult;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.common.service.BaseService;
import com.navinfo.opentsp.dongfeng.common.service.ITerminalLogService;
import com.navinfo.opentsp.dongfeng.common.util.ClientUtil;
import com.navinfo.opentsp.dongfeng.common.util.StringUtil;
import com.navinfo.opentsp.dongfeng.monitor.client.CmdClient;
import com.navinfo.opentsp.dongfeng.monitor.commands.car.LockCarCommand;
import com.navinfo.opentsp.dongfeng.monitor.entity.HyCarEntity;
import com.navinfo.opentsp.dongfeng.monitor.model.Gps_2170_Cmd;
import com.navinfo.opentsp.dongfeng.monitor.pojo.car.LockCarPojo;
import com.navinfo.opentsp.dongfeng.monitor.service.car.ILockCarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 远程锁车
 * 
 * @Author liusanhu@aerozhonghuan.com
 * @Date 2017/3/30
 */
@Service
public class LockCarServiceImpl extends BaseService implements ILockCarService
{
    
    @Resource
    private GpsInfoCache gpsInfoCache;
    
    @Autowired
    CmdClient cmdClient;
    
    @Autowired
    private ITerminalLogService terminalLogService;
    
    @Transactional
    @Override
    public CommonResult lockCar(LockCarCommand command)
    {
        
        CommonResult result = new CommonResult();
        // 密码错误，返回
        if (!command.getPassword().equals(command.getUserInfor().getPassword()))
        {
            result.setMessage("密码错误");
            result.fillResult(ReturnCode.ERROR_PASSWORD);
            return result;
        }
        // 如果是锁车
        if (command.getSign().intValue() == 1)
        {
            if (command.getLockMethod() == null)
            {
                result.setResultCode(507);
                result.setMessage("锁车方案不能为空");
                return result;
            }
            if (command.getLockParam() == null)
            {
                result.setResultCode(507);
                result.setMessage("设置参数不能为空");
                return result;
            }
        }
        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("carId", command.getCarId());
        LockCarPojo lockCarPojo = (LockCarPojo)dao.sqlFindObject("queryCarByLockCarId", params, LockCarPojo.class);
        setOnlineStatue(lockCarPojo);
        
        HyCarEntity carEntity = new HyCarEntity();
        carEntity.setCarId(BigInteger.valueOf(command.getCarId()));
        HyCarEntity car = (HyCarEntity)dao.findOne(carEntity);
        car.setLockStauts(command.getSign().intValue() == 1 ? 6 : 7);// 6:锁车，7：解锁
        car.setOperateIp(ClientUtil.getClientIp(command.getRequest()));
        car.setOperateCommon(command.getContent());
        car.setOperateDate(BigInteger.valueOf(StringUtil.getCurrentTimeSeconds()));
        // 锁车时，存储锁车方案
        if (command.getSign().intValue() == 1)
        {
            car.setLockMethod(command.getLockMethod() + "");
        }
        else
        {
            command.setLockMethod(Integer.parseInt(lockCarPojo.getLockMethod()));
        }
        
        dao.update(car);
        
        result = sendMessage2170(lockCarPojo, command);
        return result;
    }
    
    // 发送2170指令
    private CommonResult sendMessage2170(LockCarPojo lockCarPojo, LockCarCommand command)
    {
        CommonResult result = new CommonResult();
        Gps_2170_Cmd gps_2170_cmd = new Gps_2170_Cmd();
        gps_2170_cmd.setEngineType(lockCarPojo.getEngineType());
        gps_2170_cmd.setSign(command.getSign());
        gps_2170_cmd.setCommand("2170");
        gps_2170_cmd.setFlag(1);
        gps_2170_cmd.setLockMethod(command.getLockMethod());
        gps_2170_cmd.setLockParam(command.getLockParam());
        gps_2170_cmd.setStatue(lockCarPojo.getCarState());
        int flag = 1;
        if (lockCarPojo.getBdTerId().longValue() != 0)
        { // 给一体机发送指令
            gps_2170_cmd.setTerminalId(lockCarPojo.getBdTerId() + "");
            gps_2170_cmd.setUniqueMark(lockCarPojo.getBdCommId() + "");
        }
        if (lockCarPojo.getFkTerId().longValue() != 0)
        { // 给FK发送指令
            gps_2170_cmd.setTerminalId(lockCarPojo.getFkTerId() + "");
            gps_2170_cmd.setUniqueMark(lockCarPojo.getFkCommId() + "");
            flag = 0;
        }
        String logId = saveTerminalLog(command, lockCarPojo, "2170", flag);
        gps_2170_cmd.setLogId(logId);
        gps_2170_cmd.setToken(command.getToken());
        try
        {
            result = cmdClient.call_2170(gps_2170_cmd);
            logger.info("response:" + result);
        }
        catch (Exception e)
        {
            result.setResultCode(3);
            if (command.getSign().intValue() == 1)
            {
                result.setMessage("发送远程锁车失败");
            }
            else
            {
                result.setMessage("发送远程解锁失败");
            }
        }
        return result;
    }
    
    // 存放发送指令
    private String saveTerminalLog(LockCarCommand command, LockCarPojo lockCarPojo, String type, Integer flag)
    {
        TerminalLogPojo hyTerminalLog = new TerminalLogPojo();
        String logname = "";
        if (type.equals("2170"))
        {
            if (command.getSign().intValue() == 1)
            {
                logname = Constants.LOCKCARF;// 锁车
            }
            else
            {
                logname = Constants.LOCKCARN;// 解锁
            }
        }
        hyTerminalLog.setDes(command.getContent());// 数据库字段—LOG_CONTENT
        hyTerminalLog.setType(logname);// 数据库字段—LOG_NAME
        hyTerminalLog.setOperateUser(command.getUserInfor().getUsername());// 数据库字段—LOG_ACCOUNT_NAME
        hyTerminalLog.setChassisNum(lockCarPojo.getChassisNum());// 数据库字段—CHASSIS_NUM
        hyTerminalLog.setCarNum(lockCarPojo.getPlateNum());// 数据库字段—CAR_CPH
        hyTerminalLog.setTeam(lockCarPojo.getDealer());// 数据库字段—COMPANY_NAME
        hyTerminalLog.setReCustom(lockCarPojo.getCustomer());// 数据库字段—CAR_OWNER
        if (flag.intValue() == 1)
        {
            hyTerminalLog.setSimNo(lockCarPojo.getBdCommId());
        }
        else
        {
            hyTerminalLog.setSimNo(lockCarPojo.getFkCommId());
        }
        hyTerminalLog.setOperateIp(ClientUtil.getClientIp(command.getRequest()));// 数据库字段—LOG_ACCOUNT_IP
        hyTerminalLog.setValue(Constants.ResponseResult.waiting.getValue());// 数据库字段—LOG_STATE
        hyTerminalLog.setOperateTime(new BigInteger(String.valueOf(new Date().getTime() / 1000)));// 数据库字段—LOG_DATE
        hyTerminalLog.setStatus(-3);
        BigInteger logId = terminalLogService.addTerminalCommandLog(hyTerminalLog);
        return logId.toString();
    }
    
    // 获取车辆在线状态(0离线 1在线)
    private void setOnlineStatue(LockCarPojo lockCarPojo)
    {
        lockCarPojo.setCarState(0);
        GpsInfoData gpsInfo = null;
        if (lockCarPojo.getFkCommId() != null && lockCarPojo.getFkCommId().longValue() != 0)
        {
            gpsInfo = gpsInfoCache.getGpsInfo(lockCarPojo.getFkCommId() + "");
        }
        else
        {
            gpsInfo = gpsInfoCache.getGpsInfo(lockCarPojo.getBdCommId() + "");
        }
        if (gpsInfo != null && gpsInfo.getCarStatue() != null)
        {
            lockCarPojo.setCarState(gpsInfo.getCarStatue().intValue() & 1);
        }
    }
    
}
