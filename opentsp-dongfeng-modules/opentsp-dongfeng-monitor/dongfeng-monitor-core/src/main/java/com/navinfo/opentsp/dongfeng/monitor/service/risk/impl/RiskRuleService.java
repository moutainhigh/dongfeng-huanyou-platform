package com.navinfo.opentsp.dongfeng.monitor.service.risk.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lc.core.protocol.common.LCAreaType;
import com.lc.core.protocol.common.LCMessageSign;
import com.lc.core.protocol.dataaccess.common.LCAreaData;
import com.lc.core.protocol.dataaccess.common.LCAreaInfo;
import com.lc.core.protocol.terminal.setting.LCOutRegionToLimitSpeed;
import com.navinfo.opentsp.dongfeng.common.constant.Constants;
import com.navinfo.opentsp.dongfeng.common.constant.TcpCommandEnum;
import com.navinfo.opentsp.dongfeng.common.dto.TamperData;
import com.navinfo.opentsp.dongfeng.common.pojo.TerminalLogPojo;
import com.navinfo.opentsp.dongfeng.common.result.CommonResult;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.PagingInfo;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.common.service.BaseService;
import com.navinfo.opentsp.dongfeng.common.service.ITerminalLogService;
import com.navinfo.opentsp.dongfeng.common.util.CoordinatesUtil;
import com.navinfo.opentsp.dongfeng.common.util.IDGeneralUtil;
import com.navinfo.opentsp.dongfeng.common.util.StringUtil;
import com.navinfo.opentsp.dongfeng.monitor.client.CmdClient;
import com.navinfo.opentsp.dongfeng.monitor.commands.risk.*;
import com.navinfo.opentsp.dongfeng.monitor.constant.RiskConstant;
import com.navinfo.opentsp.dongfeng.monitor.converter.risk.RiskConverter;
import com.navinfo.opentsp.dongfeng.monitor.dto.risk.QueryRisksPoint;
import com.navinfo.opentsp.dongfeng.monitor.dto.risk.RiskRegionDto;
import com.navinfo.opentsp.dongfeng.monitor.entity.HyRiskEntity;
import com.navinfo.opentsp.dongfeng.monitor.model.Gps_2317_Cmd;
import com.navinfo.opentsp.dongfeng.monitor.model.Gps_2318_Cmd;
import com.navinfo.opentsp.dongfeng.monitor.pojo.car.QueryCarInfoPojo;
import com.navinfo.opentsp.dongfeng.monitor.pojo.risk.RiskRulePojo;
import com.navinfo.opentsp.dongfeng.monitor.service.risk.IRiskRegionService;
import com.navinfo.opentsp.dongfeng.monitor.service.risk.IRiskRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author tushenghong
 * @version 1.0
 * @date 2017-03-20
 * @modify
 * @copyright Navi Tsp
 */
@Service
public class RiskRuleService extends BaseService implements IRiskRuleService {
    /**
     * 出区域提醒
     */
    private static final String OUT_REGION_REMIND = "系统判断您的行为存在一定安全风险，您的车辆因此将自动限制转速，请与您的经销商联系进行解锁!";

    /**
     * 潍柴发动机类型
     */
    private static final Integer ENGINE_OF_WEI_CHAI = 45;

    @Autowired
    private ITerminalLogService terminalLogService;

    @Autowired
    private IRiskRegionService regionService;

    @Autowired
    CmdClient cmdClient;

    @Override
    @Transactional
    public HttpCommandResultWithData addRiskRule(AddRiskRuleCommand command) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        boolean isExisted = isExistedRiskRuleOfVehicle(command);
        if (isExisted) {
            result.fillResult(ReturnCode.ALREADY_EXISTED_RISK_RULE);
            return result;
        }
        // 查询车辆信息
        Map<String, Object> parm = new HashMap<String, Object>();
        // 根据车辆ID获取车辆基本信息
        parm.put("id", command.getVehicleId());
        parm.put("accountType", command.getUserInfor().getType());
        parm.put("accountId", command.getUserInfor().getUserId());
        QueryCarInfoPojo carInfoPojo =
                (QueryCarInfoPojo) dao.sqlFindObject("queryCarInfo", parm, QueryCarInfoPojo.class);
        // 保存日志
        String serialNum;
        try {
            String content =
                    command.getRegionId() + "," + TcpCommandEnum.OUT_REGION_LIMIT_SPEED.getMsg() + "," + command.getValue()
                            + "r/min" + "操作";
            serialNum =
                    saveTerminalLog(carInfoPojo,
                            command.getUserInfor().getUsername(),
                            command.getToken(),
                            content,
                            TcpCommandEnum.OUT_REGION_LIMIT_SPEED);
        } catch (JsonProcessingException e) {
            logger.error("send out region limit speed failed", e);
            throw new RuntimeException(e);
        }
        // 下发到位置云
        CommonResult tcpResult = settingOutRegionToLimitSpeed(command, carInfoPojo, serialNum);
        if (tcpResult.getResultCode() != ReturnCode.OK.code()) {
            result.setResultCode(tcpResult.getResultCode());
            result.setMessage(tcpResult.getMessage());
        } else {
            // 入库
            HyRiskEntity entity = RiskConverter.toRiskEntity(command);
            dao.save(entity);
        }
        return result;
    }

    @Override
    @Transactional
    public HttpCommandResultWithData deleteRiskRule(DeleteRiskRuleCommand command) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        // 判断数据是否存在
        RiskRulePojo pojo = queryRiskRuleById(command.getRuleId());
        if (StringUtil.isEmpty(pojo)) {
            result.fillResult(ReturnCode.RISK_RULE_NOT_EXISTED);
            return result;
        }
        // 查询车辆信息
        Map<String, Object> parm = new HashMap<String, Object>();
        // 根据车辆ID获取车辆基本信息
        parm.put("id", pojo.getVehicleId());
        parm.put("accountType", command.getUserInfor().getType());
        parm.put("accountId", command.getUserInfor().getUserId());
        QueryCarInfoPojo carInfoPojo =
                (QueryCarInfoPojo) dao.sqlFindObject("queryCarInfo", parm, QueryCarInfoPojo.class);
        // 添加终端日志
        String serialNum;
        try {
            String content = pojo.getRegionId() + "," + TcpCommandEnum.DEL_OUT_REGION_LIMIT_SPEED.getMsg() + "操作";
            serialNum =
                    saveTerminalLog(carInfoPojo,
                            command.getUserInfor().getUsername(),
                            command.getToken(),
                            content,
                            TcpCommandEnum.DEL_OUT_REGION_LIMIT_SPEED);
        } catch (JsonProcessingException e) {
            logger.error("send del out region limit speed failed,", e);
            throw new RuntimeException(e);
        }
        // 下发到位置云
        CommonResult tcpResult =
                delOutRegionToLimitSpeed(command.getToken(), carInfoPojo.getFkCommNum().toString(), pojo.getRegionId()
                        .toString(), serialNum);
        if (tcpResult.getResultCode() != ReturnCode.OK.code()) {
            result.setResultCode(tcpResult.getResultCode());
            result.setMessage(tcpResult.getMessage());
        } else {
            // 从库中删除
            HyRiskEntity entity = new HyRiskEntity();
            entity.setId(StringUtil.toBigInteger(command.getRuleId()));
            dao.delete(entity);
        }
        return result;
    }

    @Override
    public HttpCommandResultWithData queryRiskRuleDetail(QueryRiskRuleDetailCommand command) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        RiskRulePojo pojo = queryRiskRuleById(command.getRuleId());
        result.setData(pojo);
        return result;
    }

    @Override
    public HttpCommandResultWithData queryRiskRule(QueryRiskRuleCommand command) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        PagingInfo page = new PagingInfo();
        Map<String, Object> param = new HashMap<>();
        param.put("id", command.getRegionId()); // 区域ID
        param.put("accountId", command.getUserInfor().getUserId());
        param.put("accountType", command.getUserInfor().getType());
        PagingInfo<RiskRulePojo> pageInfo =
                dao.sqlPagelFind("queryRiskRuleOfRegion",
                        param,
                        StringUtil.toInteger(command.getPage_number()),
                        StringUtil.toInteger(command.getPage_size()),
                        RiskRulePojo.class);
        page.setList(pageInfo.getList());
        page.setPage_total(pageInfo.getPage_total());
        page.setTotal(pageInfo.getTotal());
        result.setData(page);
        return result;
    }

    @Override
    @Transactional
    public HttpCommandResultWithData updateRiskRule(UpdateRiskRuleCommand command) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        DeleteRiskRuleCommand deleteRiskRuleCommand = new DeleteRiskRuleCommand();
        deleteRiskRuleCommand.setRuleId(command.getRuleId());
        deleteRiskRuleCommand.setUserInfor(command.getUserInfor());
        deleteRiskRuleCommand.setToken(command.getToken());
        //删除防控规则
        HttpCommandResultWithData deleteResult = deleteRiskRule(deleteRiskRuleCommand);
        if (deleteResult.getResultCode() != ReturnCode.OK.code()) {
            result.fillResult(ReturnCode.UPDATE_RISK_RULE_FAILED);
        } else {
            //作为一个新的防控规则新增
            HttpCommandResultWithData addResult = addRiskRule(command);
            if (addResult.getResultCode() != ReturnCode.OK.code()) {
                result.fillResult(ReturnCode.UPDATE_RISK_RULE_FAILED);
            }
        }
        return result;
    }

    /**
     * 车辆在该防控取是否已设置防控规则
     *
     * @param command
     * @return
     */
    private boolean isExistedRiskRuleOfVehicle(AddRiskRuleCommand command) {
        Map<String, Object> param = new HashMap<>();
        param.put("id", command.getRegionId()); // 区域ID
        param.put("vehicleId", command.getVehicleId()); // 车辆ID
        param.put("accountType", command.getUserInfor().getType());
        param.put("accountId", command.getUserInfor().getUserId());
        PagingInfo<RiskRulePojo> pageInfo =
                dao.sqlPagelFind("queryRiskRuleOfRegion",
                        param,
                        StringUtil.toInteger(command.getPage_number()),
                        StringUtil.toInteger(command.getPage_size()),
                        RiskRulePojo.class);
        return !StringUtil.isEmpty(pageInfo) && !StringUtil.isEmpty(pageInfo.getList());
    }

    /**
     * 查询车辆风控规则
     *
     * @param ruleId 规则ID
     * @return
     */
    private RiskRulePojo queryRiskRuleById(String ruleId) {
        Map<String, String> param = new HashMap<>();
        param.put("id", ruleId);
        return (RiskRulePojo) dao.sqlFindObject("queryRiskRuleById", param, RiskRulePojo.class);
    }

    /**
     * 组装TCP信息
     *
     * @param riskRegionDto 防控信息
     * @param carInfoPojo   车辆信息
     * @param command       防控规则
     * @return
     */
    private Gps_2317_Cmd constructRegionInfo(RiskRegionDto riskRegionDto, QueryCarInfoPojo carInfoPojo,
                                             AddRiskRuleCommand command) {
        LCOutRegionToLimitSpeed.OutRegionToLimitSpeed.Builder builder =
                LCOutRegionToLimitSpeed.OutRegionToLimitSpeed.newBuilder();

        builder.setLimitSpeed(StringUtil.toInteger(command.getValue()));
        String content =
                "尊敬的用户" + carInfoPojo.getFkSim() + ",您好！您的车辆已出风控区域" + riskRegionDto.getRegionName() + ","
                        + OUT_REGION_REMIND;
        builder.setBroadcastContent(content);
        // 填充消息显示标示
        LCMessageSign.MessageSign.Builder builders = LCMessageSign.MessageSign.newBuilder();
        builders.setIsBroadcast(true);
        builders.setIsDisplay(false);
        builders.setIsUrgent(false);
        builders.setInfoType(false);
        builders.setIsAdvertiseScreen(false);
        builder.setSigns(builders);
        // 填充区域信息
        LCAreaInfo.AreaInfo.Builder areaInfo = LCAreaInfo.AreaInfo.newBuilder();
        areaInfo.setTerminalId(carInfoPojo.getFkCommNum().longValue());
        areaInfo.setAreaIdentify(getAreaIdentify(command.getRegionId()));
        areaInfo.setCreateDate(riskRegionDto.getCreateTime().longValue());
        if (riskRegionDto.getRegionType() == 1) {
            areaInfo.setTypes(LCAreaType.AreaType.rectangle);
        }
        if (riskRegionDto.getRegionType() == 2) {
            areaInfo.setTypes(LCAreaType.AreaType.polygon);
        }
        if (riskRegionDto.getRegionType() == 3) {
            areaInfo.setTypes(LCAreaType.AreaType.circle);
        }
        if (riskRegionDto.getRegionType() == 4) {
            areaInfo.setTypes(LCAreaType.AreaType.route);
        }
        if (riskRegionDto.getRegionType() == 5) {
            areaInfo.setTypes(LCAreaType.AreaType.point);
        }
        List<QueryRisksPoint> points = riskRegionDto.getPoints();
        for (int i = 0; i < points.size(); i++) {
            LCAreaData.AreaData.Builder datas = LCAreaData.AreaData.newBuilder();
            datas.setDataSN(i + 1);
            if (riskRegionDto.getRegionType() == RiskConstant.RegionTypeEnum.CIRCLE.getCode()) {
                datas.setLatitude(points.get(i).getLat().intValue());
                datas.setLongitude(points.get(i).getLng().intValue());
                datas.setRadiusLength(riskRegionDto.getRadius());
            } else if (riskRegionDto.getRegionType() == RiskConstant.RegionTypeEnum.RECTANGLE.getCode()) { // 矩形只传1,3点
                if (i == 0 || i == 2) {
                    datas.setLatitude(points.get(i).getLat().intValue());
                    datas.setLongitude(points.get(i).getLng().intValue());
                }
            } else {
                datas.setLatitude(points.get(i).getLat().intValue());
                datas.setLongitude(points.get(i).getLng().intValue());

            }
            //火星坐标转换为标准坐标
            Double lat = Integer.valueOf(datas.getLatitude()) * 0.000001;
            Double lon = Integer.valueOf(datas.getLongitude()) * 0.000001;
            double[] temp = CoordinatesUtil.gcj2WGSExactly(lat, lon);
            datas.setLatitude(StringUtil.toInteger(String.valueOf(temp[0] * 1000000)));
            datas.setLongitude(StringUtil.toInteger(String.valueOf(temp[1] * 1000000)));
            areaInfo.addDatas(datas);
        }
        builder.addAreaInfo(areaInfo);
        int controlType;
        if (!StringUtil.isEmpty(carInfoPojo.getEngineType()) && carInfoPojo.getEngineType().equals(ENGINE_OF_WEI_CHAI)) {
            controlType = 1;// 潍柴CAN总线实现（激活ECU）
        } else {
            controlType = 2;// 终端自身实现(OBD)（不激活ECU）
        }
        builder.setControlType(controlType);
        builder.setGpsId(carInfoPojo.getFkTer());

        Gps_2317_Cmd gps_2317_cmd = new Gps_2317_Cmd();
        String contentForBytesStr = "";
        try {
            contentForBytesStr = new String(builder.build().toByteArray(), "ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            logger.info("constructRegionInfo encoding content failed", e);
            e.printStackTrace();
        }
        gps_2317_cmd.setContentForBytes(contentForBytesStr);
        gps_2317_cmd.setUniqueMark(carInfoPojo.getFkCommNum().toString());
        gps_2317_cmd.setCommand(TcpCommandEnum.OUT_REGION_LIMIT_SPEED.getCommand());
        gps_2317_cmd.setToken(command.getToken());
        return gps_2317_cmd;
    }

    /**
     * 保存终端指令日志
     *
     * @param pojo
     * @param userName
     * @param token
     * @param tcpCommandEnum
     * @throws JsonProcessingException
     */
    private String saveTerminalLog(QueryCarInfoPojo pojo, String userName, String token, String logContent,
                                   TcpCommandEnum tcpCommandEnum)
            throws JsonProcessingException {
        TerminalLogPojo terminalLogPojo = new TerminalLogPojo();
        terminalLogPojo.setOperateUser(userName);
        terminalLogPojo.setSimNo(BigInteger.valueOf(Long.valueOf(pojo.getFkSim())));
        terminalLogPojo.setChassisNum(pojo.getChassisNum());
        terminalLogPojo.setCarNum(pojo.getPlateNum());
        terminalLogPojo.setTeam(pojo.getDealer());
        terminalLogPojo.setReCustom(pojo.getCustomer());
        terminalLogPojo.setDes(logContent);
        terminalLogPojo.setType(tcpCommandEnum.getMsg());
        BigInteger logId = terminalLogService.addTerminalCommandLog(terminalLogPojo);
        TamperData data = new TamperData();
        data.setCommanId(tcpCommandEnum.getCommand());
        data.setLogId(logId.toString());
        data.setToken(token);
        // 获取序列号
        String serialNum = IDGeneralUtil.builderID(IDGeneralUtil.IDTypeEnum.SerialNumber).toString();
        // 保存日志
        redisService.saveObjectToJson(Constants.MSG_KEY_PREFIX + "_" + serialNum + "_"
                + tcpCommandEnum.getCommand().substring(0, 4), data);
        return serialNum;
    }

    /**
     * 获取区域标示
     *
     * @param ruleId
     * @return
     */
    private long getAreaIdentify(String ruleId) {
        return StringUtil.toLong("91" + String.format("%07d", StringUtil.toInteger(ruleId)));
    }

    /**
     * 设置区域限速
     *
     * @param command     参数
     * @param carInfoPojo 车辆信息
     * @param serialNum   序列号
     * @return
     */
    private CommonResult settingOutRegionToLimitSpeed(AddRiskRuleCommand command, QueryCarInfoPojo carInfoPojo,
                                                      String serialNum) {
        CommonResult result = new CommonResult();
        // 获取防控区域信息
        RiskRegionDto riskRegionDto = regionService.queryRiskRegionById(command.getRegionId());
        // 组成传输对象
        Gps_2317_Cmd gps_2317_cmd = constructRegionInfo(riskRegionDto, carInfoPojo, command);
        gps_2317_cmd.setSerialNumber(serialNum);
        try {
            result = cmdClient.call_2317(gps_2317_cmd);
        } catch (Exception e) {
            logger.error("设置出区域限速到TCP模块异常,", e);
            result.fillResult(ReturnCode.OUT_REGION_TO_TCP_ERROR);
        }
        return result;

    }

    /**
     * 出区域限速删除
     *
     * @param regionId  区域ID
     * @param serialNum
     * @return
     */
    private CommonResult delOutRegionToLimitSpeed(String token, String communicationId, String regionId,
                                                  String serialNum) {
        CommonResult result = new CommonResult();
        Gps_2318_Cmd gps_2318_cmd = new Gps_2318_Cmd();
        gps_2318_cmd.setCommand(TcpCommandEnum.DEL_OUT_REGION_LIMIT_SPEED.getCommand());
        gps_2318_cmd.setAreaIdentify(getAreaIdentify(regionId));
        gps_2318_cmd.setSerialNumber(serialNum);
        gps_2318_cmd.setUniqueMark(communicationId);
        gps_2318_cmd.setToken(token);
        try {
            result = cmdClient.call_2318(gps_2318_cmd);
        } catch (Exception e) {
            logger.error("删除出区域限速到TCP模块异常,", e);
            result.fillResult(ReturnCode.DEL_OUT_REGION_TO_TCP_ERROR);
        }
        return result;
    }
}
