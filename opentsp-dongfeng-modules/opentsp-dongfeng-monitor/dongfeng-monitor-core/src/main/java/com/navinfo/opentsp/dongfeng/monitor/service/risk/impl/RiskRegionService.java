package com.navinfo.opentsp.dongfeng.monitor.service.risk.impl;

import com.lc.core.protocol.common.LCLocationData;
import com.lc.core.protocol.webservice.newstatisticsdata.LCMileageConsumptionRecords;
import com.lc.core.protocol.webservice.newstatisticsdata.entity.LCMileageConsumptionRes;
import com.navinfo.opentsp.dongfeng.common.cache.GpsCache;
import com.navinfo.opentsp.dongfeng.common.cache.GpsInfoCache;
import com.navinfo.opentsp.dongfeng.common.client.MailClient;
import com.navinfo.opentsp.dongfeng.common.configuration.CloudDataRmiClientConfiguration;
import com.navinfo.opentsp.dongfeng.common.constant.Constants;
import com.navinfo.opentsp.dongfeng.common.constant.UserTypeConstant;
import com.navinfo.opentsp.dongfeng.common.dto.GpsInfoData;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.PagingInfo;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.common.rmi.DataAnalysisWebService;
import com.navinfo.opentsp.dongfeng.common.rmi.module.CommonParameterSerializer;
import com.navinfo.opentsp.dongfeng.common.service.BaseService;
import com.navinfo.opentsp.dongfeng.common.util.DateUtils;
import com.navinfo.opentsp.dongfeng.common.util.ExcelUtil;
import com.navinfo.opentsp.dongfeng.common.util.ReportConfig;
import com.navinfo.opentsp.dongfeng.common.util.StringUtil;
import com.navinfo.opentsp.dongfeng.mail.commands.SendEmailCommand;
import com.navinfo.opentsp.dongfeng.monitor.commands.risk.AddRiskRegionCommand;
import com.navinfo.opentsp.dongfeng.monitor.commands.risk.ExportRiskRegionReportCommand;
import com.navinfo.opentsp.dongfeng.monitor.commands.risk.QueryRiskRegionCommand;
import com.navinfo.opentsp.dongfeng.monitor.commands.risk.RiskRegionReportCommand;
import com.navinfo.opentsp.dongfeng.monitor.converter.risk.RiskConverter;
import com.navinfo.opentsp.dongfeng.monitor.dto.risk.RiskRegionDto;
import com.navinfo.opentsp.dongfeng.monitor.dto.risk.RiskRegionReportDto;
import com.navinfo.opentsp.dongfeng.monitor.entity.HyDrawMapDataEntity;
import com.navinfo.opentsp.dongfeng.monitor.entity.HyDrawMapEntity;
import com.navinfo.opentsp.dongfeng.monitor.pojo.risk.RiskRegionPojo;
import com.navinfo.opentsp.dongfeng.monitor.service.risk.IRiskRegionService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.File;
import java.util.*;

/**
 * @author tushenghong
 * @version 1.0
 * @date 2017-03-20
 * @modify
 * @copyright Navi Tsp
 */
@Service
@EnableAsync
public class RiskRegionService extends BaseService implements IRiskRegionService {
    @Autowired
    private GpsCache gpsCache;

    @Autowired
    private GpsInfoCache gpsInfoCache;

    @Autowired
    private MailClient mailClient;

    @Resource
    private CloudDataRmiClientConfiguration cloudDataRmiClientConfiguration;

    @Override
    @Transactional
    public HttpCommandResultWithData addRiskRegion(AddRiskRegionCommand command) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        //校验唯一性
        boolean isUnique = isRiskRegionUnique(command.getRegionName(), null);
        if (!isUnique) {
            result.fillResult(ReturnCode.RISK_REGION_EXISTED);
            return result;
        }
        //风险防控区域入库
        HyDrawMapEntity mapEntity = RiskConverter.commandToDrawMapEntity(command);
        mapEntity = (HyDrawMapEntity) dao.save(mapEntity);
        if (StringUtil.isEmpty(mapEntity.getDmId().longValue())) {
            logger.error("add risk region failed");
            result.fillResult(ReturnCode.ADD_RISK_REGION_FAILED);
            return result;
        }
        //风险防控参数入库
        List<HyDrawMapDataEntity> mapDataEntityList = RiskConverter.commandToDrawMapDataEntity(command, mapEntity.getDmId().longValue());
        dao.batchSave(mapDataEntityList, false);
        return result;
    }


    @Override
    public HttpCommandResultWithData queryRiskRegion(QueryRiskRegionCommand command) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        //检查访问权限
        ReturnCode returnCode = verifyAuthToAccess(command.getUserInfor().getUserId(), command.getUserInfor().getType(), command.getRegionId());
        if (returnCode != ReturnCode.OK) {
            result.fillResult(returnCode);
            return result;
        }
        RiskRegionDto riskRegionDto = queryRiskRegionById(command.getRegionId());
        result.setData(riskRegionDto);
        return result;
    }

    @Override
    public PagingInfo queryReportOfRiskRegion(RiskRegionReportCommand command, boolean isPaging) {
        // 查询服务站
        PagingInfo page = new PagingInfo();
        Map<String, Object> param = new HashMap<>();
        if (!StringUtil.isEmpty(command.getPreventionStatus()) && !command.getPreventionStatus().equals("-1")) {
            param.put("preventionStatus", command.getPreventionStatus());
        }
        param.put("stdSalesStatus", command.getStdSalesStatus());
        param.put("aakSalesStatus", command.getAakSalesStatus());
        param.put("chassisNum", command.getChassisNum());
        param.put("carIds", command.getCarIds());
        param.put("teamIds", command.getTeamIds());
        param.put("teamName", command.getTeamName());
        param.put("accountId", command.getUserInfor().getUserId());
        param.put("accountType", command.getUserInfor().getType());
        PagingInfo<RiskRegionReportDto> pageInfo = new PagingInfo<>();
        if (isPaging) {
            pageInfo =
                    dao.sqlPagelFind("queryReportOfRiskRegion",
                            param,
                            StringUtil.toInteger(command.getPage_number()),
                            StringUtil.toInteger(command.getPage_size()),
                            RiskRegionReportDto.class);
        } else {
            List<RiskRegionReportDto> list = dao.sqlFind("queryReportOfRiskRegion", param, RiskRegionReportDto.class);
            pageInfo.setList(list);
            pageInfo.setPage_total(0);
            pageInfo.setTotal(0);
        }
        if (!StringUtil.isEmpty(pageInfo.getList())) {
            List<RiskRegionReportDto> list = pageInfo.getList();
            List<Long> communications = new ArrayList<>();
            Map<String, LCLocationData.LocationData> map = gpsCache.getAllLastGpsMap();
            for (RiskRegionReportDto entity : list) {
                //防控状态
                if (StringUtil.isEmpty(entity.getLockStatus())) {
                    entity.setPreventionStatus(Constants.PreventionStatusEnum.NOT_ACTIVE.getMsg());
                } else {
                    entity.setPreventionStatus(Constants.PreventionStatusEnum.valueOf(entity.getLockStatus().intValue()).getMsg());
                }
                //末次有效位置
//                LCLocationData.LocationData locationDataValid = gpsCache.getLastGps(entity.getCommunicationId().toString());
                LCLocationData.LocationData locationDataValid = map.get(entity.getCommunicationId().toString());

                if (locationDataValid != null) {
                    entity.setLastLocation(locationDataValid.getLongitude() + ";" + locationDataValid.getLatitude());
                    entity.setLastLocationStr(locationDataValid.getLongitude() * 0.000001 + ";" + locationDataValid.getLatitude() * 0.000001);
                    entity.setLastLocationDateStr(DateUtils.formatDate(locationDataValid.getGpsDate() * 1000));
                    GpsInfoData gpsInfo = gpsInfoCache.getGpsInfo(entity.getCommunicationId().toString());
                    double ttMile = 0;
                    if(gpsInfo!=null){
                        ttMile = gpsInfo.getTempMileage();
                    }
                    entity.setTotalMileage(ttMile + "");

                }

                communications.add(StringUtil.toLong(entity.getCommunicationId().toString()));
            }
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.DAY_OF_MONTH, 1);
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            long startTime = cal.getTimeInMillis() / 1000;
            long endTime = Calendar.getInstance().getTimeInMillis() / 1000;
            Map<Long, String> maps = new HashMap<Long, String>();
            List<LCMileageConsumptionRes.MileageConsumptionRes> mileageConsumptionRecords = getMileageConsumptionRecords(communications, startTime, endTime);
            for (LCMileageConsumptionRes.MileageConsumptionRes mileageConsumptionRecord : mileageConsumptionRecords) {
                maps.put(mileageConsumptionRecord.getTerminalID(), mileageConsumptionRecord.getMileage() * 0.001 + "");//页面单位为km，位置云单位为m
            }
            for (RiskRegionReportDto entity : list) {
                entity.setMonthMileage(maps.get(entity.getCommunicationId().longValue()));
            }
            pageInfo.setList(list);
        }
        page.setList(pageInfo.getList());
        page.setPage_total(pageInfo.getPage_total());
        page.setTotal(pageInfo.getTotal());
        return page;
    }

    @Async
    @Override
    public void asyncDownload(String fservice, List<? extends Object> list, ExportRiskRegionReportCommand command,
                              String sourcePath) {
        try {
            JSONObject uploadResult = download(fservice, list, command, sourcePath);
            // 调用邮件接口发送邮件信息
            SendEmailCommand mailCommand = new SendEmailCommand();
            mailCommand.setToEmails(command.getEmail());
            mailCommand.setSubject("防控报表");
            mailCommand.setContent(uploadResult.getJSONObject("data").getString("fullPath"));
//            String jsonStr = JSONObject.fromObject(mailCommand).toString();
            mailClient.sendMail(mailCommand);
        } catch (Exception e) {
            logger.error("异步导出防控报表异常：" , e);
        }
    }

    @Override
    public JSONObject download(String fservice, List<? extends Object> list, ExportRiskRegionReportCommand command,
                               String sourcePath) {
        ReportConfig config = new ReportConfig("车辆轨迹");
        config.setColumn("", "序号", 0);
        config.setColumn("chassisNum", "底盘号", 1);
        config.setColumn("plate", "车牌号", 2);
        config.setColumn("teamName", "所属经销商", 3);
        config.setColumn("customerName", "所属客户", 4);
        config.setColumn("stdSalesDate", "STD销售日期", 5);
        config.setColumn("stdSalesStatus", "STD销售状态", 6);
        config.setColumn("aakSalesDate", "AAK销售日期", 7);
        config.setColumn("aakSalesStatus", "AAK销售状态", 8);
        config.setColumn("totalLoan", "贷款总金额(万)", 9);
        config.setColumn("restLoan", "剩余未还款(万)", 10);
        config.setColumn("lastLocationDateStr", "末次有效位置时间", 11);
        config.setColumn("lastLocationStr", "末次有效位置", 12);
        config.setColumn("totalMileage", "当前里程(KM)", 13);
        config.setColumn("monthMileage", "当月行驶里程(KM)", 14);
        config.setColumn("preventionStatus", "防控状态", 15);
        config.setColumn("preventionWindows", "防控时限", 16);
        config.setColumn("operateLog", "操作备注", 17);
        config.setColumn("operateUser", "操作人", 18);
        config.setColumn("operateIp", "操作人IP", 19);
        config.setColumn("operateTime", "操作时间", 20);

        config.setSourcePath(sourcePath);
        File file = ExcelUtil.getExcel(config, list, null, 0);

        MultiValueMap<String, Object> param = new LinkedMultiValueMap<String, Object>();
        param.add("account", command.getUserInfor().getUserId());
        param.add("file", new FileSystemResource(file));
        RestTemplate restTemplate = new RestTemplate();
        String httpResult = restTemplate.postForObject(fservice, param, String.class);
        return JSONObject.fromObject(httpResult);
    }

    /**
     * 防控区域是否唯一
     *
     * @param regionName 区域名称
     * @param regionId   区域ID
     * @return
     */
    private boolean isRiskRegionUnique(String regionName, Long regionId) {
        Map<String, Object> param = new HashMap<>();
        param.put("regionName", regionName);
        param.put("regionId", regionId);
        List list = dao.sqlFindField("queryRiskRegionIdByParam", param);
        return StringUtil.isEmpty(list);
    }

    /**
     * 查询风控区域信息
     *
     * @param regionId 风控ID
     * @return
     */
    public RiskRegionDto queryRiskRegionById(String regionId) {
        Map<String, Object> param = new HashMap<>();
        param.put("id", regionId);
        List<RiskRegionPojo> list = dao.sqlFind("queryRiskRegionById", param, RiskRegionPojo.class);
        return RiskConverter.toRiskRegionDto(list);
    }

    /**
     * 是否有访问权限
     *
     * @param regionId
     * @param accountId
     * @return
     */
    private boolean hasPermission(String regionId, Long accountId) {
        Map<String, Object> param = new HashMap<>();
        param.put("id", regionId);
        param.put("accountId", accountId);
        List list = dao.sqlFindField("queryPermissionOfRiskRegion", param);
        return !StringUtil.isEmpty(list);
    }

    /**
     * 校验用户是否有访问防控区域的权限
     *
     * @param userId   用户ID
     * @param userType 用户类型
     * @param regionId 区域ID
     * @return
     */
    private ReturnCode verifyAuthToAccess(Long userId, int userType, String regionId) {
        if (StringUtil.isEmpty(regionId)) {
            return ReturnCode.NOT_EXISTED_REGION;
        }
        if (userType == UserTypeConstant.SYSTEM_ADMIN.getCode()) {
            return ReturnCode.OK;
        }
        //查询防控区域
        RiskRegionDto riskRegionDto = queryRiskRegionById(regionId);
        if (StringUtil.isEmpty(riskRegionDto)) {
            return ReturnCode.NOT_EXISTED_REGION;
        }
        //是否有访问权限
        boolean hasPermission = hasPermission(regionId, userId);
        if (!hasPermission) {
            return ReturnCode.NO_TEAM_AUTH_OF_RISK_REGION;
        }
        return ReturnCode.OK;
    }

    /**
     * 获取里程油耗信息
     *
     * @param communicationIds 通讯号
     * @param startDate        起始时间
     * @param endDate          结束时间
     * @return
     */
    private List<LCMileageConsumptionRes.MileageConsumptionRes> getMileageConsumptionRecords(List<Long> communicationIds, long startDate, long endDate) {
        List<LCMileageConsumptionRes.MileageConsumptionRes> list = new ArrayList<>();
        try {
            CommonParameterSerializer commonParameterSerializer = new CommonParameterSerializer();
            commonParameterSerializer.setMultipage(false);
            commonParameterSerializer.setCode(0);
            commonParameterSerializer.setSort(true);
            commonParameterSerializer.setSortTerminal(true);
            commonParameterSerializer.setQueryKey("");
            DataAnalysisWebService dataAnalysisWebService = cloudDataRmiClientConfiguration.getDataAnalysisWebService();
            byte[] response = dataAnalysisWebService.getMileageConsumptionRecords(communicationIds, startDate, endDate, commonParameterSerializer);
            if (!StringUtil.isEmpty(response)) {
                LCMileageConsumptionRecords.MileageConsumptionRecords mileageConsumptionRecords = LCMileageConsumptionRecords.MileageConsumptionRecords.parseFrom(response);
                list = mileageConsumptionRecords.getDataListList();
                logger.info("----位置云getMileageConsumptionRecords，返回总记录数" + list.size());
            } else {
                logger.info("----位置云getMileageConsumptionRecords，返回 null");
            }
        } catch (Exception e) {
            logger.error("调用RMI接口获取油耗失败" , e);
        }
        return list;
    }


}
