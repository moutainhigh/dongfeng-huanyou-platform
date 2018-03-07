package com.navinfo.opentsp.dongfeng.monitor.service.car.impl;

import com.navinfo.opentsp.dongfeng.common.cache.GpsInfoCache;
import com.navinfo.opentsp.dongfeng.common.constant.Constants;
import com.navinfo.opentsp.dongfeng.common.dto.GpsInfoData;
import com.navinfo.opentsp.dongfeng.common.pojo.TerminalLogPojo;
import com.navinfo.opentsp.dongfeng.common.result.CommonResult;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.common.service.BaseService;
import com.navinfo.opentsp.dongfeng.common.service.ITerminalLogService;
import com.navinfo.opentsp.dongfeng.common.util.ClientUtil;
import com.navinfo.opentsp.dongfeng.common.util.StringUtil;
import com.navinfo.opentsp.dongfeng.monitor.client.CmdClient;
import com.navinfo.opentsp.dongfeng.monitor.commands.car.SendTamperCommand;
import com.navinfo.opentsp.dongfeng.monitor.entity.HyCarEntity;
import com.navinfo.opentsp.dongfeng.monitor.model.Gps_2170_Cmd;
import com.navinfo.opentsp.dongfeng.monitor.model.Gps_2271_Cmd;
import com.navinfo.opentsp.dongfeng.monitor.pojo.car.TamperPojo;
import com.navinfo.opentsp.dongfeng.monitor.service.car.ISendTamperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 发送防控激活指令
 *
 * @wenya
 * @create 2017-03-27 18:22
 **/
@Service
public class SendTamperServiceImpl extends BaseService implements ISendTamperService {

    @Autowired
    private GpsInfoCache gpsInfoCache;

    @Autowired
    private ITerminalLogService terminalLogService;

    @Autowired
    CmdClient cmdClient;

    @Transactional
    @Override
    public HttpCommandResultWithData sendTamper(SendTamperCommand command) {
        logger.info("=====  SendTamper start  =====");
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        try {
            // 密码错误，返回
            if (!command.getPassword().equals(command.getUserInfor().getPassword())) {
                result.setMessage("密码错误");
                result.fillResult(ReturnCode.ERROR_PASSWORD);
                return result;
            }
            logger.info("=====  SendTamper cardetail  =====");
            // 查询车辆信息
            Map<String, Object> parm = new HashMap<String, Object>();
            parm.put("carId", command.getCarId());
            // sql定义在car-QueryCommonCar-dynamic.xml中
            TamperPojo tamperPojo = (TamperPojo) dao.sqlFindObject("queryTamperByCarId", parm, TamperPojo.class);
            // 没有绑定SIM卡，返回
            if (tamperPojo.getTerminalId().longValue() == 0 && tamperPojo.getCar_termianlId().longValue() == 0) {
                result.setMessage("没有绑定sim卡");
                result.fillResult(ReturnCode.OK);
                return result;
            }
            // 获取车辆在线状态
            Integer statue = getOnlineStatue(tamperPojo);
            tamperPojo.setCarState(statue);
            logger.info("=====  SendTamper carsatue  =====");
            // 修改车辆相关信息
            saveCarInfo(command);
            logger.info("=====  SendTamper 入库  =====");
            // 判断离线缓存发送还是直接发送
            CommonResult resposeBD = new CommonResult();
            CommonResult resposeFk = new CommonResult();
            CommonResult resposeFC = new CommonResult();
            // if(statue!=null && (statue.intValue()&1) > 0){//车辆在线，直接发送
            if (tamperPojo.getTerminalId().longValue() != 0) { // 给一体机发送指令
                resposeBD = sendMessage2170(command, tamperPojo, 1);
            }
            if (tamperPojo.getCar_termianlId().longValue() != 0) { // 给FK发送指令
                resposeFk = sendMessage2170(command, tamperPojo, 2);
                if (resposeFk.getResultCode() == ReturnCode.OK.code() && command.getSign().intValue() == 1) {// 激活时发送
                    resposeFC = sendMessage2271(command, tamperPojo);
                }
            }
            if ((statue != null && ((statue.intValue() & 1) < 1)) || statue == null) { // 车辆不在线
                result.setMessage("车辆不在线，指令信息将缓存下发");
                result.fillResult(ReturnCode.OK);
            } else if (resposeBD != null && resposeBD.getResultCode() != ReturnCode.OK.code()) {
                result.setResultCode(resposeBD.getResultCode());
                result.setMessage(resposeBD.getMessage());
            } else if (resposeFk != null && resposeFk.getResultCode() != ReturnCode.OK.code()) {
                result.setResultCode(resposeFk.getResultCode());
                result.setMessage(resposeFk.getMessage());
            } else if (resposeFC != null && resposeFC.getResultCode() != ReturnCode.OK.code()) {
                result.setResultCode(resposeFC.getResultCode());
                result.setMessage(resposeFC.getMessage());
            } else {
                result.setMessage("已发送防控激活信息");
                result.fillResult(ReturnCode.OK);
            }
            // }else{//车辆不在线，缓存发送
            // // saveCommandCache(command);
            // result.setMessage("车辆不在线，指令信息将缓存下发");
            // result.fillResult(ReturnCode.OK);
            // }
        } catch (Exception e) {
            result.fillResult(ReturnCode.SERVER_ERROR);
            result.setMessage("发送防控激活信息异常");
            logger.error("===== SendTamper Exception =====", e);
        }
        logger.info("===== SendTamper end  =====");
        return result;
    }

    private void saveCarInfo(SendTamperCommand command) {
        HyCarEntity carEntity = new HyCarEntity();
        carEntity.setCarId(BigInteger.valueOf(command.getCarId()));
        HyCarEntity car = (HyCarEntity) dao.findOne(carEntity);
        car.setOperateUser(command.getUserInfor().getUsername());
        car.setOperateDate(new BigInteger(StringUtil.getCurrentTimeSeconds() + ""));
        car.setOperateIp(ClientUtil.getClientIp(command.getRequest()));
        car.setCarFkdate(new BigInteger(command.getCarFKDate() / 1000 + ""));
        car.setOperateCommon(command.getContent());
        if (command.getSign().intValue() == 1) { // 激活
            car.setLockStauts(4); // 正在激活
        }
        if (command.getSign().intValue() == 2) { // 关闭激活
            car.setLockStauts(5); // 正在取消激活
        }
        dao.update(car);
    }

    // 缓存入库
    /*
     * private void saveCommandCache(SendTamperCommand command) { HttpCommandResultWithData oldEntity = null; try {
     * oldEntity = commandService.queryCommand(command.getCarId()); HyCommandEntity entity =
     * converterHyCommand(command); if(oldEntity.getData()!=null){ commandService.updateCommand(entity); }else{
     * commandService.addCommand(entity); } } catch (Exception e) { e.printStackTrace(); } }
     * 
     * 
     * // hycommandcache对象转换 private HyCommandEntity converterHyCommand(SendTamperCommand command) { HyCommandEntity
     * entity = new HyCommandEntity(); if(command.getPassWord()!=null){ entity.setPassword(command.getPassWord()); }
     * if(command.getAccountIp()!=null){ entity.setAccountIp(command.getAccountIp()); }
     * if(command.getUserInfor()!=null){ entity.setAccountName(command.getUserInfor().getUsername()); }
     * if(command.getCarId()!=null){ entity.setCarId(new BigInteger(command.getCarId()+"")); }
     * if(command.getContent()!=null){ entity.setContent(command.getContent()); } if(command.getSign()!=null){
     * entity.setSign(Integer.parseInt(command.getSign())); } if(command.getCommon()!=null){
     * entity.setType(Integer.parseInt(command.getCommon())); } return entity; }
     */

    // 转换对象参数2271
    private CommonResult sendMessage2271(SendTamperCommand command, TamperPojo tamperPojo) {
        CommonResult result = new CommonResult();
        Gps_2271_Cmd gps_2271_cmd = new Gps_2271_Cmd();
        gps_2271_cmd.setBaseCode(command.getCommon());
        gps_2271_cmd.setDeviceId(tamperPojo.getFkCode());
        gps_2271_cmd.setUniqueMark(tamperPojo.getFkCommId().toString());
        gps_2271_cmd.setCommand("2271");
        gps_2271_cmd.setStatue(tamperPojo.getCarState());
        gps_2271_cmd.setToken(command.getToken());
        try {
            // //存日志(在线则存，不在线则不存)
            // if(tamperPojo.getCarState()!=null&&(tamperPojo.getCarState().intValue()&1) > 0){//在线
            String logId = saveTerminalLog(command, tamperPojo, "2271", null);
            gps_2271_cmd.setLogId(logId);
            // }else{
            // gps_2271_cmd.setLogId("");
            // }

            result = cmdClient.call_2271(gps_2271_cmd);
            logger.info("response:" + result);
        } catch (Exception e) {
            result.setResultCode(3);
            result.setMessage("发送防拆盒设置失败");
            logger.error(e.getMessage(), e);
        }
        return result;
    }

    // 存放发送指令
    private String saveTerminalLog(SendTamperCommand command, TamperPojo tamperPojo, String type, Integer flag) {
        TerminalLogPojo hyTerminalLog = new TerminalLogPojo();
        String logname = "";
        if (type.equals("2170")) {
            if (command.getSign().intValue() == 1) { // 激活
                logname = Constants.TAMPERON;
            } else {
                logname = Constants.TAMPEROFF;
            }
        } else {
            logname = "8103终端设置指令";
            // hyTerminalLog.setLogCommon(Integer.parseInt(command.getCommon()));
        }
        hyTerminalLog.setDes(command.getContent());// 数据库字段—LOG_CONTENT
        hyTerminalLog.setType(logname);// 数据库字段—LOG_NAME
        hyTerminalLog.setOperateUser(command.getUserInfor().getUsername());// 数据库字段—LOG_ACCOUNT_NAME
        hyTerminalLog.setChassisNum(tamperPojo.getChassisNum());// 数据库字段—CHASSIS_NUM
        hyTerminalLog.setCarNum(tamperPojo.getCarNum());// 数据库字段—CAR_CPH
        hyTerminalLog.setTeam(tamperPojo.getTeam());// 数据库字段—COMPANY_NAME
        hyTerminalLog.setReCustom(tamperPojo.getReCustom());// 数据库字段—CAR_OWNER
        if (flag != null && flag == 1) {
            hyTerminalLog.setSimNo(tamperPojo.getBdCommId());
        } else {
            hyTerminalLog.setSimNo(tamperPojo.getFkCommId());
        }
        hyTerminalLog.setOperateIp(ClientUtil.getClientIp(command.getRequest()));// 数据库字段—LOG_ACCOUNT_IP
        hyTerminalLog.setValue(Constants.ResponseResult.waiting.getValue());// 数据库字段—LOG_STATE
        hyTerminalLog.setOperateTime(new BigInteger(String.valueOf(new Date().getTime() / 1000)));// 数据库字段—LOG_DATE
        hyTerminalLog.setStatus(-3);
        BigInteger logId = terminalLogService.addTerminalCommandLog(hyTerminalLog);
        return logId.toString();
    }

    // 转换对象参数2170
    private CommonResult sendMessage2170(SendTamperCommand command, TamperPojo tamperPojo, int flag) {
        CommonResult result = new CommonResult();
        Gps_2170_Cmd gps_2170_cmd = new Gps_2170_Cmd();
        gps_2170_cmd.setEngineType(tamperPojo.getEngineType());
        gps_2170_cmd.setSign(command.getSign());
        gps_2170_cmd.setFlag(0);
        gps_2170_cmd.setCommand("2170");
        gps_2170_cmd.setLockMethod(0);
        gps_2170_cmd.setLockParam(0);
        gps_2170_cmd.setStatue(tamperPojo.getCarState());
        gps_2170_cmd.setToken(command.getToken());
        if (flag == 1) {// 用一体机终端
            gps_2170_cmd.setTerminalId(tamperPojo.getTerminalId() + "");
            gps_2170_cmd.setUniqueMark(tamperPojo.getBdCommId() + "");
        } else {// 用FK终端
            gps_2170_cmd.setTerminalId(tamperPojo.getCar_termianlId() + "");
            gps_2170_cmd.setUniqueMark(tamperPojo.getFkCommId() + "");
        }
        try {
            // 存日志
            // if(tamperPojo.getCarState()!=null&&(tamperPojo.getCarState().intValue()&1) > 0) {//在线
            String logId = saveTerminalLog(command, tamperPojo, "2170", flag);
            gps_2170_cmd.setLogId(logId);
            // }else{
            // gps_2170_cmd.setLogId("");
            // }
            result = cmdClient.call_2170(gps_2170_cmd);
            logger.info("response:" + result);
        } catch (Exception e) {
            result.setResultCode(3);
            result.setMessage("发送防控激活失败");
            logger.error(e.getMessage(), e);
        }
        return result;
    }

    // 获取车辆在线状态(0离线 1在线)
    private Integer getOnlineStatue(TamperPojo tamperPojo) {
        GpsInfoData gpsInfo = null;
        if (tamperPojo.getFkCommId() != null && tamperPojo.getFkCommId().longValue() != 0) {
            gpsInfo = gpsInfoCache.getGpsInfo(tamperPojo.getFkCommId() + "");
        } else {
            gpsInfo = gpsInfoCache.getGpsInfo(tamperPojo.getBdCommId() + "");
        }
        if (gpsInfo != null && gpsInfo.getCarStatue() != null) {
            return gpsInfo.getCarStatue().intValue() & 1;
        }
        return 0;
    }
}
