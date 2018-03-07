package com.navinfo.opentsp.dongfeng.system.service.impl;

import com.lc.core.protocol.common.LCAreaType;
import com.lc.core.protocol.common.LCMessageSign;
import com.lc.core.protocol.dataaccess.common.LCAreaData;
import com.lc.core.protocol.dataaccess.common.LCAreaInfo;
import com.lc.core.protocol.dataaccess.common.LCVehiclePassInArea;
import com.lc.core.protocol.terminal.setting.LCMessageBroadcastInArea;
import com.lc.core.protocol.terminal.setting.LCOvertimeParkingInArea;
import com.lc.core.protocol.webservice.newstatisticsdata.entity.LCInOrOutAreaNotifySet;
import com.lc.core.protocol.webservice.newstatisticsdata.entity.LCInOrOutAreaNotifySetDel;
import com.navinfo.opentsp.dongfeng.common.client.MailClient;
import com.navinfo.opentsp.dongfeng.common.command.BaseTcpCommand;
import com.navinfo.opentsp.dongfeng.common.constant.Constants;
import com.navinfo.opentsp.dongfeng.common.constant.OperateLogConstants;
import com.navinfo.opentsp.dongfeng.common.constant.TcpCommandEnum;
import com.navinfo.opentsp.dongfeng.common.constant.UserTypeConstant;
import com.navinfo.opentsp.dongfeng.common.dto.BaseData;
import com.navinfo.opentsp.dongfeng.common.exception.BaseRuntimeException;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.PagingInfo;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.common.service.BaseService;
import com.navinfo.opentsp.dongfeng.common.service.IBaseDataService;
import com.navinfo.opentsp.dongfeng.common.service.IOperateLogService;
import com.navinfo.opentsp.dongfeng.common.util.*;
import com.navinfo.opentsp.dongfeng.mail.commands.SendEmailCommand;
import com.navinfo.opentsp.dongfeng.system.client.CmdClient;
import com.navinfo.opentsp.dongfeng.system.commands.dto.inDto.station.StationAuditIndto;
import com.navinfo.opentsp.dongfeng.system.commands.dto.inDto.station.StationBasicInfoIndto;
import com.navinfo.opentsp.dongfeng.system.commands.dto.inDto.station.SubStationInfoIndto;
import com.navinfo.opentsp.dongfeng.system.commands.dto.outDto.AuditStationOutdto;
import com.navinfo.opentsp.dongfeng.system.commands.dto.outDto.StationOutdto;
import com.navinfo.opentsp.dongfeng.system.commands.dto.outDto.StationServiceItemOutdto;
import com.navinfo.opentsp.dongfeng.system.commands.station.*;
import com.navinfo.opentsp.dongfeng.system.constant.StationConstant;
import com.navinfo.opentsp.dongfeng.system.converter.StationConverter;
import com.navinfo.opentsp.dongfeng.system.entity.HyAuditStationEntity;
import com.navinfo.opentsp.dongfeng.system.entity.HySecdstationEntity;
import com.navinfo.opentsp.dongfeng.system.entity.HyServiceStationEntity;
import com.navinfo.opentsp.dongfeng.system.pojo.AccountStationMapping;
import com.navinfo.opentsp.dongfeng.system.pojo.AuditStationPojo;
import com.navinfo.opentsp.dongfeng.system.pojo.StationPojo;
import com.navinfo.opentsp.dongfeng.system.pojo.SubStationPojo;
import com.navinfo.opentsp.dongfeng.system.pojo.eletag.SyncSecStationPojo;
import com.navinfo.opentsp.dongfeng.system.pojo.eletag.SyncServiceStationParam;
import com.navinfo.opentsp.dongfeng.system.pojo.eletag.SyncStationPojo;
import com.navinfo.opentsp.dongfeng.system.service.IStationService;
import com.navinfo.opentsp.dongfeng.system.service.ISyncService;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 服务站管理
 *
 * @author tushenghong
 * @version 1.0
 * @date 2017-03-14
 * @modify
 * @copyright Navi Tsp
 */
@Service
public class StationServiceImpl extends BaseService implements IStationService {

    protected static final Logger logger = LoggerFactory.getLogger(StationServiceImpl.class);

    // 审核状态 0：待审核 1：拒绝 2：同意
    private static final String AUDIT_RESULT_NO = "1";

    private static final String AUDIT_RESULT_YES = "2";

    // 服务站类型
    private static final int STATION = 1; // 一级服务站

    private static final int SUB_STATION = 2;// 二级服务站

    private static final int STATION_ENABLE = 1;// 服务站启用标志
    private static final int STATION_DISABLE = 0;// 服务站停用标志


    @Autowired
    private IBaseDataService baseDataService;

    @Autowired
    private CmdClient cmdClient;

    @Resource
    private IOperateLogService operateLogService;


    @Autowired
    private MailClient mailClient;
    @Autowired
    private ISyncService syncService;
    @Value("${sign.server.syn.switch}")
    private String syncSwitch;
    @Value("${cloud.server.sync.stationTeam.switch}")
    private String syncStationTeamSwitch;

    @Override
    public HttpCommandResultWithData queryStationDetail(QueryStationDetailCommand command) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        // 查询服务站信息
        StationPojo stationPojo = queryStationById(command.getStationId());
        if (StringUtil.isEmpty(stationPojo)) {
            result.fillResult(ReturnCode.STATION_NOT_EXISTED);
            return result;
        }
        // 查询服务站二级网点信息
        List<SubStationPojo> subStationList = querySubStationByPid(command.getStationId());
        // 构造服务站服务项目
        List<StationServiceItemOutdto> serviceItem = constructServiceItem(stationPojo);
        // 组装成完整的服务站信息
        StationOutdto station = StationConverter.toStationOutDto(stationPojo, subStationList, serviceItem);
        result.setData(station);
        return result;
    }

    @Override
    public PagingInfo queryStation(QueryStationCommand command, boolean isPaging) {
        // 查询服务站
        PagingInfo page = new PagingInfo();
        Map<String, Object> param = new HashMap<>();
        param.put("accountType", command.getUserInfor().getType());
        param.put("accountId", command.getUserInfor().getUserId());
        param.put("stationName", command.getStationName());
        param.put("stationCode", command.getStationCode());
        param.put("provinceCode", command.getProvinceCode());
        param.put("switchStatus", command.getSwitchStatus());
        param.put("tagSwitchStatus", command.getTagSwitchStatus());
        param.put("tBossSwitchStatus", command.gettBossSwitchStatus());
        param.put("delFlag", Constants.HAVE_DEL);
        PagingInfo<StationPojo> pageInfo = new PagingInfo<>();
        if (isPaging) {
            pageInfo = dao.sqlPagelFind("queryStationByParam",
                    param,
                    StringUtil.toInteger(command.getPage_number()),
                    StringUtil.toInteger(command.getPage_size()),
                    StationPojo.class);
        } else {
            List<StationPojo> list = dao.sqlFind("queryStationByParam", param, StationPojo.class);
            pageInfo.setList(list);
            pageInfo.setPage_total(0);
            pageInfo.setTotal(0);
        }
        page.setList(dataConvert(pageInfo.getList()));
        page.setPage_total(pageInfo.getPage_total());
        page.setTotal(pageInfo.getTotal());
        return page;
    }

    @Override
    @Transactional
    public HttpCommandResultWithData addStation(AddStationCommand command) throws Exception {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        //检查添加条件是否满足
        result = addVerify(command.getBasicInfoBean().getStationName(),
                command.getBasicInfoBean().getStationCode());
        if (result.getResultCode() != ReturnCode.OK.code()) {
            return result;
        }
        List<HySecdstationEntity> ssList = new ArrayList<>();
        // 服务站入库
        HyServiceStationEntity entity = StationConverter.inDtoToEntity(command);
        entity.setStationEnable(STATION_ENABLE);
        entity.setSyncStatus(Constants.SyncOperateEnum.SUCCESS.value());
        entity.setTagSyncStatus(ReturnCode.OK.code());
        entity = (HyServiceStationEntity) dao.save(entity);
        if (!StringUtil.isEmpty(entity.getId())) {
            // 保存二级服务站
            Map<String, List<HySecdstationEntity>> stringListMap = saveOrUpdateSubStation(entity.getId(), entity.getServiceCode(), command.getSubStationInfoBean());
            ssList = stringListMap.get("all");
            // 保存用户服务站映射信息
            if (command.getUserInfor() != null && command.getUserInfor().getType() != UserTypeConstant.SYSTEM_ADMIN.getCode()) {
                saveAccountStationMapping(BigInteger.valueOf(command.getUserInfor().getUserId()), entity.getId());
            }
        } else {
            result.fillResult(ReturnCode.ADD_STATION_FAILED);
        }
        //进行标签系统同步
        if (Boolean.valueOf(syncSwitch)) {
            HttpCommandResultWithData resultWithData = synStationToSignSystem(Arrays.asList(entity), ssList, null, null, Constants.SyncModelEnum.ADD.value());
            if (resultWithData.getResultCode() != ReturnCode.OK.code()) {
                // 更新同步标签库结果到数据库
                entity.setTagSyncStatus(Constants.SyncModelEnum.ADD.value());
                dao.update(entity);

                if (!ListUtil.isEmpty(ssList)) {
                    for (HySecdstationEntity secdstationEntity : ssList) {
                        secdstationEntity.setTagSyncStatus(Constants.SyncModelEnum.ADD.value());
                        dao.update(secdstationEntity);
                    }
                }
            }
        }
        // 同步到位置云
        syncToLocation(command.getBasicInfoBean(), entity.getId().toString(), command.getToken());
        //同步一级服务站到位置云
        inOrOutAreaNotifySet(entity.getId().longValue(), entity.getLongitude().intValue(), entity.getLatitude().intValue(), entity.getRadius(), command.getToken());
        //同步二级服务站到位置云
        if (!StringUtil.isEmpty(ssList)) {
            for (HySecdstationEntity ss : ssList) {
                long areaId = Long.parseLong("95" + String.format("%07d", ss.getId().longValue()));
                inOrOutAreaNotifySet(areaId, ss.getLongitude().intValue(), ss.getLatitude().intValue(), ss.getWorkRadius(), command.getToken());
            }
        }
        //服务站ID暂时塞到返回结果中
        result.setData(entity.getId().toString());
        return result;

    }

    @Override
    @Transactional
    public HttpCommandResultWithData deleteStation(DeleteStationCommand command) throws Exception {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        //删除校验
        result = deleteVerify(command.getStationId(), command.getUserInfor().getUserId(), command.getUserInfor().getType());
        if (result.getResultCode() != ReturnCode.OK.code()) {
            return result;
        }
        HyServiceStationEntity entity = new HyServiceStationEntity();
        entity.setId(StringUtil.toBigInteger(command.getStationId()));
        entity = (HyServiceStationEntity) dao.findOne(entity);
        //同步到标签库
        if (Boolean.valueOf(syncSwitch)) {
            result = synStationToSignSystem(Arrays.asList(entity), null, null, null, Constants.SyncModelEnum.DELETE.value());
            if (result != null && result.getResultCode() != ReturnCode.OK.code() && entity.getTagSyncStatus() != Constants.SyncModelEnum.ADD.value()) {
                throw new BaseRuntimeException(result.getMessage());
            } else {
                result.fillResult(ReturnCode.OK);
            }
        }
        //同步到tboss
        String stationId = entity.getId().toString();
        String province = entity.getPovince().toString();
        String city = entity.getCity().toString();
        result = syncService.syncStation(stationId, province, city, Constants.SyncOperateEnum.DELETE.value());
        if (result.getResultCode() != ReturnCode.OK.code() && entity.getSyncStatus() != Constants.SyncOperateEnum.ADD.value()) {
            throw new BaseRuntimeException(result.getMessage());
        } else {
            result.fillResult(ReturnCode.OK);
        }
        // 删除服务站(逻辑删除)
        entity.setDelFlag(Constants.HAVE_DEL);
        dao.update(entity);
        //如果服务站已停用，无需调用位置云删除服务站接口
        if (entity.getStationEnable() != STATION_DISABLE) {
            inOrOutAreaNotifySetDel(entity.getId().longValue(), command.getToken());
        }
        // 删除用户服务站映射信息
        deleteAccountStationMapping(StringUtil.toBigInteger(command.getUserInfor().getUserId()),
                StringUtil.toBigInteger(command.getStationId()));
        result.setData(entity);
        return result;
    }

    @Override
    @Transactional
    public HttpCommandResultWithData updateStation(UpdateStationCommand command) throws Exception {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        String stationId = command.getStationId();
        //更新校验
        result = updateVerify(stationId, command.getBasicInfoBean().getStationName(),
                command.getBasicInfoBean().getStationCode());
        if (result.getResultCode() != ReturnCode.OK.code()) {
            return result;
        }
        StationPojo stationPojo = queryStationById(stationId);
        // 更新服务站
        HyServiceStationEntity entity = StationConverter.inDtoToEntity(command);
        entity.setId(StringUtil.toBigInteger(stationId));
        entity.setCreatDate(stationPojo.getCreateDate());
        entity.setStationEnable(stationPojo.getSwitchStatus());
        entity.setSyncStatus(stationPojo.gettBossSyncStatus());
        entity.setTagSyncStatus(stationPojo.getTagSyncStatus());
        entity = (HyServiceStationEntity) dao.update(entity);
        // 删除二级二级网点
        List<HySecdstationEntity> deleteSubStation = deleteSubStation(command.getDelSubStation());
        // 更新二级网点信息
//        List<HySecdstationEntity> ssList = saveOrUpdateSubStation(entity.getId(), entity.getServiceCode(), command.getSubStationInfoBean());
        Map<String, List<HySecdstationEntity>> stringListMap = saveOrUpdateSubStation(entity.getId(), entity.getServiceCode(), command.getSubStationInfoBean());
        List<HySecdstationEntity> ssList = stringListMap.get("all");
        //进行标签系统同步
        if (Boolean.valueOf(syncSwitch)) {
            List<HySecdstationEntity> addSubStationList = stringListMap.get("add");
            List<HySecdstationEntity> updateSubStationList = stringListMap.get("update");
            result = synStationToSignSystem(Arrays.asList(entity), addSubStationList, updateSubStationList, deleteSubStation, Constants.SyncModelEnum.UPDATE.value());
            if (result.getResultCode() != ReturnCode.OK.code()) {
                //如果有删除操作同步到标签库失败则抛出异常
                if (!ListUtil.isEmpty(deleteSubStation) && entity.getTagSyncStatus() != Constants.SyncModelEnum.ADD.value()) {
                    throw new BaseRuntimeException(result.getMessage());
                }
            }
            if (result.getResultCode() != ReturnCode.OK.code()) {
                //此处只答应日志，不设置错误的返回值,直接返回成功
                logger.info("sync to tag failed," + result.getMessage());
                result.fillResult(ReturnCode.OK);
                if (entity.getTagSyncStatus() != Constants.SyncModelEnum.ADD.value()) {
                    // 更新同步标签库结果到数据库
                    entity.setTagSyncStatus(Constants.SyncModelEnum.UPDATE.value());
                }
            } else {
                entity.setTagSyncStatus(ReturnCode.OK.code());
            }
            dao.update(entity);
            //更新同步结果到二级服务站
            if (!ListUtil.isEmpty(addSubStationList)) {
                for (HySecdstationEntity secdstationEntity : addSubStationList) {
                    if (result.getResultCode() != ReturnCode.OK.code()) {
                        secdstationEntity.setTagSyncStatus(Constants.SyncModelEnum.ADD.value());
                    } else {
                        secdstationEntity.setTagSyncStatus(ReturnCode.OK.code());
                    }
                    dao.update(secdstationEntity);
                }
            }
            //更新同步结果到二级服务站
            if (!ListUtil.isEmpty(updateSubStationList)) {
                for (HySecdstationEntity secdstationEntity : updateSubStationList) {
                    if (result.getResultCode() != ReturnCode.OK.code()) {
                        if (secdstationEntity.getTagSyncStatus() != Constants.SyncModelEnum.ADD.value()) {
                            secdstationEntity.setTagSyncStatus(Constants.SyncModelEnum.UPDATE.value());
                        }
                    } else {
                        secdstationEntity.setTagSyncStatus(ReturnCode.OK.code());
                    }
                    dao.update(secdstationEntity);
                }
            }
        }

        // 同步到位置云
        syncToLocation(command.getBasicInfoBean(), entity.getId().toString(), command.getToken());
        //同步一级服务站到位置云
        inOrOutAreaNotifySet(entity.getId().longValue(), entity.getLongitude().intValue(), entity.getLatitude().intValue(), entity.getRadius(), command.getToken());
        //同步二级服务站到位置云
        if (!StringUtil.isEmpty(ssList)) {
            for (HySecdstationEntity ss : ssList) {
                long areaId = Long.parseLong("95" + String.format("%07d", ss.getId().longValue()));
                inOrOutAreaNotifySet(areaId, ss.getLongitude().intValue(), ss.getLatitude().intValue(), ss.getWorkRadius().intValue(), command.getToken());
            }
        }
        //从位置云删除二级网点
        if (!StringUtil.isEmpty(command.getDelSubStation())) {
            List<String> delSubStationList = StringUtil.stringToList(command.getDelSubStation(), ",");
            for (String subStation : delSubStationList) {
                long areaId = Long.parseLong("95" + String.format("%07d", Long.valueOf(subStation)));
                inOrOutAreaNotifySetDel(areaId, command.getToken());
            }
        }
        return result;
    }

    @Override
    public HttpCommandResultWithData queryAuditStation(QueryAuditStationCommand command) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("accountType", command.getUserInfor().getType());
        map.put("accountId", command.getUserInfor().getUserId());
        if (!StringUtil.isEmpty(command.getStationName())) {
            map.put("stationName", command.getStationName());
        }
        if (!StringUtil.isEmpty(command.getStationCode())) {
            map.put("stationCode", command.getStationCode());
        }
        PagingInfo<AuditStationPojo> pageInfo = dao.sqlPagelFind("queryAuditStation", map, StringUtil.toInteger(command.getPage_number()),
                StringUtil.toInteger(command.getPage_size()), AuditStationPojo.class);
        List<AuditStationPojo> list = pageInfo.getList();
        List<AuditStationOutdto> dtos = new ArrayList<AuditStationOutdto>();
        AuditStationOutdto dto = null;
        for (AuditStationPojo stationPojo : list) {
            dto = new AuditStationOutdto();
            try {
                CopyPropUtil.copyProp(stationPojo, dto);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
            if (!StringUtil.isEmpty(stationPojo.getNewLatitude()) && !StringUtil.isEmpty(stationPojo.getNewLongitude())) {
                dto.setNewLat(NumberFormatUtil.getLatitudeOrLongitude(stationPojo.getNewLatitude().intValue()));
                dto.setNewLong(NumberFormatUtil.getLatitudeOrLongitude(stationPojo.getNewLongitude().intValue()));
            } else {
                dto.setNewLat(0.0);
                dto.setNewLong(0.0);
            }
            if (!StringUtil.isEmpty(stationPojo.getOldLatitude()) && !StringUtil.isEmpty(stationPojo.getOldLongitude())) {
                dto.setOldLat(NumberFormatUtil.getLatitudeOrLongitude(stationPojo.getOldLatitude().intValue()));
                dto.setOldLong(NumberFormatUtil.getLatitudeOrLongitude(stationPojo.getOldLongitude().intValue()));
            } else {
                dto.setOldLat(0.0);
                dto.setOldLong(0.0);
            }
            if (!StringUtil.isEmpty(stationPojo.getCreateTime())) {
                dto.setCreateTimeStr(DateUtil.timeStr(stationPojo.getCreateTime().longValue()));
            } else {
                dto.setCreateTimeStr(null);
            }
            dtos.add(dto);
        }
        PagingInfo page = new PagingInfo();
        page.setList(dtos);
        page.setPage_total(pageInfo.getPage_total());
        page.setTotal(pageInfo.getTotal());
        result.setData(page);
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchUpdateStationAuditStatus(UpdateStationAuditStatusCommand command) throws Exception {
        ArrayList<StationAuditIndto> stationAuditBean = command.getStationAuditBean();
        List<HyAuditStationEntity> list = new ArrayList<>();
        for (StationAuditIndto stationAuditIndto : stationAuditBean) {
            list.add(updateStationAuditStatus(stationAuditIndto.getAuditId(), command, stationAuditIndto));
        }
        //服务站同步到电子标签系统  add fwm 20170821
        if (Boolean.valueOf(syncSwitch)) {
            String auditResult = stationAuditBean.get(0).getAuditStatus();
            if (auditResult.equals(AUDIT_RESULT_YES)) {
                List<HyServiceStationEntity> stationEntity = new ArrayList<>();
                List<HySecdstationEntity> addSubStationEntity = new ArrayList<>();
                List<HySecdstationEntity> updateSubStationEntity = new ArrayList<>();
                for (HyAuditStationEntity hyAuditStationEntity : list) {
                    // 一级服务站信息
                    if (hyAuditStationEntity.getAeraType() == STATION) {
                        stationEntity.add(queryStationById(hyAuditStationEntity.getAeraId()));
                    }
                    // 二级服务站信息
                    if (hyAuditStationEntity.getAeraType() == SUB_STATION) {
                        HySecdstationEntity entity = querySubStationById(hyAuditStationEntity.getAeraId());
                        if (entity.getTagSyncStatus() == Constants.SyncModelEnum.ADD.value()) {
                            addSubStationEntity.add(entity);
                        } else {
                            updateSubStationEntity.add(entity);
                        }
                    }
                }
                HttpCommandResultWithData result = synStationToSignSystem(stationEntity, addSubStationEntity, updateSubStationEntity, null, Constants.SyncModelEnum.UPDATE.value());
                if (result.getResultCode() != ReturnCode.OK.code()) {
                    //更新一级服务站同步状态为更新失败
                    if (!StringUtil.isEmpty(stationEntity)) {
                        HyServiceStationEntity serviceStationEntity = stationEntity.get(0);
                        if (!StringUtil.isEmpty(serviceStationEntity)) {
                            if (serviceStationEntity.getTagSyncStatus() != Constants.SyncModelEnum.ADD.value()) {
                                serviceStationEntity.setTagSyncStatus(Constants.SyncModelEnum.UPDATE.value());
                                dao.executeUpdate("updateTagSyncStatusOfStation", serviceStationEntity);
                            }
                        }
                    }
                    // 原始状态为新增失败的服务站状态不改变
                    //原始状态为更新失败的二级服务站同步状态为更新失败
                    if (!StringUtil.isEmpty(updateSubStationEntity)) {
                        for (HySecdstationEntity entity : updateSubStationEntity) {
                            //修改一级为更新失败
                            HyServiceStationEntity serviceStationEntity = new HyServiceStationEntity();
                            serviceStationEntity.setId(entity.getStationId());
                            serviceStationEntity.setTagSyncStatus(Constants.SyncModelEnum.UPDATE.value());
                            dao.executeUpdate("updateTagSyncStatusOfStation", serviceStationEntity);
                            //修改二级为更新失败
                            entity.setTagSyncStatus(Constants.SyncModelEnum.UPDATE.value());
                            dao.executeUpdate("updateTagSyncStatusOfSubStation", entity);
                        }
                    }
                } else {//同步成功
                    //不修改同步状态，原因：如果审核的是二级服务站，则只同步了二级服务站的信息到标签库，
                    // 一级服务站的状态未知，不能因此而修改一级服务站的同步状态为成功
                }
            }
        }
        //同步Tboss系统
        for (StationAuditIndto auditIndto : stationAuditBean) {
            HyAuditStationEntity entity = queryAuditEntity(StringUtil.toBigInteger(auditIndto.getAuditId()));
            if (entity.getAeraType() == STATION) {
                HyServiceStationEntity queryEntity = new HyServiceStationEntity();
                queryEntity.setId(entity.getAeraId());
                HyServiceStationEntity serviceStationEntity = (HyServiceStationEntity) dao.findOne(queryEntity);
                syncService.asyncStation(serviceStationEntity.getId().toString(), serviceStationEntity.getPovince().toString(), serviceStationEntity.getCity().toString(), Constants.SyncOperateEnum.UPDATE.value());
            }
        }
    }


    @Override
    @Transactional
    public HttpCommandResultWithData enableOrDisableStation(EnableOrDisableStationCommand command) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        Integer switchStatus = command.getSwitchStatus();
        if (switchStatus != STATION_DISABLE && switchStatus != STATION_ENABLE) {
            result.fillResult(ReturnCode.SWITCH_STATUS_VALUE_EXCEPTION);
            return result;
        }
        // 服务站是否存在
        boolean stationExisted = isStationExisted(command.getStationId());
        if (!stationExisted) {
            result.fillResult(ReturnCode.STATION_NOT_EXISTED);
            return result;
        }
        // 查询服务站
        HyServiceStationEntity entity = new HyServiceStationEntity();
        entity.setId(StringUtil.toBigInteger(command.getStationId()));
        entity = (HyServiceStationEntity) dao.findOne(entity);
        //如果已是要设置的状态，无需设置，返回成功
        if (entity.getStationEnable().equals(switchStatus)) {
            return result;
        }
        // 更新服务站
        entity.setStationEnable(switchStatus);
        entity.setUpdateTime(StringUtil.toBigInteger(StringUtil.getCurrentTimeSeconds()));
        dao.update(entity);
        //从位置云删除
        //停用
        if (switchStatus == STATION_DISABLE) {
            //从位置云删除一级服务站
            inOrOutAreaNotifySetDel(entity.getId().longValue(), command.getToken());
            List<SubStationPojo> subStationPojoList = querySubStationByPid(entity.getId().toString());
            //从位置云删除二级网点
            if (!StringUtil.isEmpty(subStationPojoList)) {
                for (SubStationPojo subStationPojo : subStationPojoList) {
                    long areaId = Long.parseLong("95" + String.format("%07d", subStationPojo.getId().longValue()));
                    inOrOutAreaNotifySetDel(areaId, command.getToken());
                }
            }
        } else {
            //同步一级服务站到位置云
            inOrOutAreaNotifySet(entity.getId().longValue(), entity.getLongitude().intValue(), entity.getLatitude().intValue(), entity.getRadius(), command.getToken());
            List<SubStationPojo> subStationPojoList = querySubStationByPid(entity.getId().toString());
            //同步二级服务站到位置云
            if (!StringUtil.isEmpty(subStationPojoList)) {
                for (SubStationPojo subStationPojo : subStationPojoList) {
                    long areaId = Long.parseLong("95" + String.format("%07d", subStationPojo.getId().longValue()));
                    inOrOutAreaNotifySet(areaId, subStationPojo.getLongitude().intValue(), subStationPojo.getLatitude().intValue(), subStationPojo.getWorkRadius(), command.getToken());
                }
            }
        }
        result.setData(entity);
        return result;
    }

    @Async
    @Override
    public void asyncDownload(String fservice, List<? extends Object> list, ExportStationCommand command,
                              String sourcePath) {
        try {
            JSONObject uploadResult = download(fservice, list, command, sourcePath);
            // 调用邮件接口发送邮件信息
            SendEmailCommand mailCommand = new SendEmailCommand();
            mailCommand.setToEmails(command.getEmail());
            mailCommand.setSubject("服务站报表");
            mailCommand.setContent(uploadResult.getJSONObject("data").getString("fullPath"));
            mailClient.sendMail(mailCommand);
        } catch (Exception e) {
            logger.error("异步导出服务站报表异常：", e);
        }
    }

    @Override
    public JSONObject download(String fservice, List<? extends Object> list, ExportStationCommand command,
                               String sourcePath) {
        ReportConfig config = new ReportConfig("服务站");
        config.setColumn("", "序号", 0);
        config.setColumn("stationName", "服务站名称", 1);
        config.setColumn("stationCode", "服务站编码", 2);
        config.setColumn("stationShortName", "简称", 3);
        config.setColumn("address", "服务站地址", 4);
        config.setColumn("radiusStr", "占地半径(Km)", 5);
        config.setColumn("serviceRadiusStr", "服务半径(Km)", 6);
        config.setColumn("strandedMaxTimeStr", "滞留阈值", 7);
        config.setColumn("serviceManager", "服务经理", 8);
        config.setColumn("telephone", "服务热线", 9);
        config.setColumn("starLever", "服务能力星级", 10);
        config.setColumn("serviceContent", "预约维修项", 11);
        config.setColumn("partsContent", "预约保养项", 12);
        config.setColumn("switchStatusStr", "状态", 13);
        config.setColumn("accountName", "创建人", 14);
        config.setColumn("createDateStr", "创建时间", 15);

        config.setSourcePath(sourcePath);
        File file = ExcelUtil.getExcel(config, list, null, 0);

        MultiValueMap<String, Object> param = new LinkedMultiValueMap<String, Object>();
        param.add("account", command.getUserInfor().getUserId());
        param.add("file", new FileSystemResource(file));
        RestTemplate restTemplate = new RestTemplate();
        String httpResult = restTemplate.postForObject(fservice, param, String.class);
        return JSONObject.fromObject(httpResult);
    }

    @Override
    @Transactional
    public void syncServiceStationToTag(String stationId, int operateType) throws Exception {
        HttpCommandResultWithData resultWithData = new HttpCommandResultWithData();
        HyServiceStationEntity serviceStationEntity = queryStationById(StringUtil.toBigInteger(stationId));
        List<HySecdstationEntity> subStationPojoList = querySubStationEntityByPid(stationId);
        if (operateType == Constants.SyncModelEnum.ADD.value()) {
            resultWithData = synStationToSignSystem(Arrays.asList(serviceStationEntity), subStationPojoList, null, null, Constants.SyncModelEnum.ADD.value());
        } else {
            resultWithData = synStationToSignSystem(Arrays.asList(serviceStationEntity), null, subStationPojoList, null, Constants.SyncModelEnum.UPDATE.value());
        }
        if (resultWithData != null && resultWithData.getResultCode() != ReturnCode.OK.code()) {
            throw new BaseRuntimeException(resultWithData.getMessage());
        }
        if (serviceStationEntity != null) {
            serviceStationEntity.setTagSyncStatus(ReturnCode.OK.code());
            dao.update(serviceStationEntity);
        }
        if (subStationPojoList != null) {
            for (HySecdstationEntity entity : subStationPojoList) {
                entity.setTagSyncStatus(ReturnCode.OK.code());
                dao.update(entity);
            }
        }
    }

    @Override
    @Transactional
    public void syncServiceStationToTBoss(String stationId, int operateType) throws Exception {
        // 更新同步结果到数据库
        HyServiceStationEntity entity = new HyServiceStationEntity();
        entity.setId(StringUtil.toBigInteger(stationId));
        entity = (HyServiceStationEntity) dao.findOne(entity);
        HttpCommandResultWithData httpCommandResultWithData = syncService.syncStation(entity.getId().toString(), entity.getPovince().toString(), entity.getCity().toString(), operateType);
        if (httpCommandResultWithData.getResultCode() != ReturnCode.OK.code()) {
            throw new BaseRuntimeException(httpCommandResultWithData.getMessage());
        }
        if (entity != null) {
            entity.setSyncStatus(Constants.SyncOperateEnum.SUCCESS.value());
            dao.save(entity);
        }
    }

    /**
     * 审核单个服务站
     *
     * @param auditId
     * @param command
     */
    private HyAuditStationEntity updateStationAuditStatus(String auditId, UpdateStationAuditStatusCommand command, StationAuditIndto stationAuditIndto) {
        // 更新审核结果
        HyAuditStationEntity entity = setAuditEntity(auditId, stationAuditIndto.getAuditStatus(), stationAuditIndto.getOpinion(), command.getUserInfor().getUserId());
        entity = (HyAuditStationEntity) dao.update(entity);

        // 审核通过 通过更新服务站位置信息
        String auditResult = stationAuditIndto.getAuditStatus();
        if (auditResult.equals(AUDIT_RESULT_YES)) {
            // 更新一级服务站信息
            if (entity.getAeraType() == STATION) {
                updateStation(command, stationAuditIndto, entity);
            }
            // 更新二级服务站信息
            if (entity.getAeraType() == SUB_STATION) {
                updateSubStation(command, entity);
            }
        }
        return entity;
    }

    /**
     * 更新一级服务站
     *
     * @param command
     * @param stationAuditIndto
     * @param entity
     */
    private void updateStation(UpdateStationAuditStatusCommand command, StationAuditIndto stationAuditIndto, HyAuditStationEntity entity) {
        HyServiceStationEntity serviceStationEntity = setServiceStationEntity(entity.getAeraId(), entity);
        if (StringUtil.isNotEmpty(stationAuditIndto.getProvince())) {
            serviceStationEntity.setPovince(StringUtil.toInteger(stationAuditIndto.getProvince()));
        }
        if (StringUtil.isNotEmpty(stationAuditIndto.getCity())) {
            serviceStationEntity.setCity(StringUtil.toInteger(stationAuditIndto.getCity()));
        }
        dao.executeUpdate("updateLocationOfStation", serviceStationEntity);
        StationPojo stationPojo = queryStationById(StringUtil.valueOf(entity.getAeraId()));
        operateLogService.addOperateLog(command, OperateLogConstants.OperateTypeEnum.UPDATE, OperateLogConstants.OperateContentPrefixEnum.STATION.getValue() + stationPojo.getStationCode());
//        syncService.asyncStation(entity.getAeraId().toString(), stationAuditIndto.getProvince(), stationAuditIndto.getCity(), Constants.SyncOperateEnum.UPDATE.value());
        // 同步到位置云
        StationPojo orgStation = queryStationById(serviceStationEntity.getId().toString());
        StationBasicInfoIndto stationBasicInfoIndto = new StationBasicInfoIndto();
        stationBasicInfoIndto.setLongitude(serviceStationEntity.getLongitude().toString());
        stationBasicInfoIndto.setLatitude(serviceStationEntity.getLatitude().toString());
        stationBasicInfoIndto.setServiceRadius(orgStation.getServiceRadius().toString());
        stationBasicInfoIndto.setStationName(orgStation.getStationName());
        stationBasicInfoIndto.setRadius(orgStation.getRadius().toString());
        stationBasicInfoIndto.setStrandedMaxTime(orgStation.getStrandedMaxTime().toString());
        syncToLocation(stationBasicInfoIndto, serviceStationEntity.getId().toString(), command.getToken());
        //同步一级服务站到位置云
        inOrOutAreaNotifySet(serviceStationEntity.getId().longValue(), serviceStationEntity.getLongitude().intValue(), serviceStationEntity.getLatitude().intValue(), Integer.valueOf(stationBasicInfoIndto.getRadius()), command.getToken());
    }

    /**
     * 更新二级服务站
     *
     * @param command
     * @param entity
     */
    private void updateSubStation(UpdateStationAuditStatusCommand command, HyAuditStationEntity entity) {
        HySecdstationEntity secdstationEntity = setSubStationEntity(entity.getAeraId(), entity);
        dao.executeUpdate("updateLocationOfSubStation", secdstationEntity);
        //更新所属一级服务站更新时间
        HySecdstationEntity subStation = querySubStationById(secdstationEntity.getId());
        HyServiceStationEntity serviceStationEntity = new HyServiceStationEntity();
        serviceStationEntity.setId(subStation.getStationId());
        dao.executeUpdate("updateServiceStationTime", serviceStationEntity);
        operateLogService.addOperateLog(command, OperateLogConstants.OperateTypeEnum.UPDATE, OperateLogConstants.OperateContentPrefixEnum.STATION.getValue() + serviceStationEntity.getServiceCode());
        //同步二级服务站到位置云
        long areaId = Long.parseLong("95" + String.format("%07d", secdstationEntity.getId().longValue()));
        inOrOutAreaNotifySet(areaId, secdstationEntity.getLongitude().intValue(), secdstationEntity.getLatitude().intValue(), subStation.getWorkRadius(), command.getToken());
    }


    private HyAuditStationEntity setAuditEntity(String auditId, String auditStatus, String opinion, Long userId) {
        HyAuditStationEntity entity = queryAuditEntity(StringUtil.toBigInteger(auditId));
        entity.setAuditStatus(StringUtil.toBigInteger(auditStatus));
        entity.setOpinion(opinion);
        entity.setAuditUser(StringUtil.toBigInteger(userId));
        entity.setAuditTime(StringUtil.toBigInteger(StringUtil.getCurrentTimeSeconds()));
        return entity;
    }

    /**
     * 一级服务站网点组装
     *
     * @param stationId
     * @param entity
     * @return
     */
    private HyServiceStationEntity setServiceStationEntity(BigInteger stationId, HyAuditStationEntity entity) {
        HyServiceStationEntity serviceStationEntity = new HyServiceStationEntity();
        serviceStationEntity.setId(stationId);
        serviceStationEntity.setLongitude(entity.getLongItude());
        serviceStationEntity.setLatitude(entity.getLatItude());
        serviceStationEntity.setAddress(entity.getAddress());
        return serviceStationEntity;
    }

    /**
     * 二级服务站网点组装
     *
     * @param stationId
     * @param entity
     * @return
     */
    private HySecdstationEntity setSubStationEntity(BigInteger stationId, HyAuditStationEntity entity) {
        HySecdstationEntity secdstationEntity = new HySecdstationEntity();
        secdstationEntity.setId(stationId);
        secdstationEntity.setLongitude(entity.getLongItude());
        secdstationEntity.setLatitude(entity.getLatItude());
        secdstationEntity.setAddress(entity.getAddress());
        return secdstationEntity;
    }

    /**
     * 审核经销商HyAuditTeamEntity查询
     *
     * @param auditId
     * @return
     */
    private HyAuditStationEntity queryAuditEntity(BigInteger auditId) {
        HyAuditStationEntity queryEntity = new HyAuditStationEntity();
        queryEntity.setId(auditId);
        HyAuditStationEntity entity = (HyAuditStationEntity) dao.findOne(queryEntity);
        return entity;
    }

    /**
     * 查询服务站
     *
     * @param sId 服务站ID
     * @return
     */
    private StationPojo queryStationById(String sId) {
        // 查询终端
        Map<String, Object> param = new HashMap<>();
        param.put("id", sId);
        param.put("delFlag", Constants.HAVE_DEL);
        return (StationPojo) dao.sqlFindObject("queryStationByParam", param, StationPojo.class);
    }

    /**
     * 查询服务站
     *
     * @param sId 服务站ID
     * @return
     */
    private HyServiceStationEntity queryStationById(BigInteger sId) {
        // 查询终端
        HyServiceStationEntity entity = new HyServiceStationEntity();
        entity.setId(sId);
        HyServiceStationEntity one = (HyServiceStationEntity) dao.findOne(entity);
        return one;
    }

    /**
     * 查询子服务站
     *
     * @param pid 父服务站id
     * @return
     */
    private List<SubStationPojo> querySubStationByPid(String pid) {
        // 查询终端
        Map<String, String> param = new HashMap<>();
        param.put("pid", pid);
        return dao.sqlFind("querySubStation", param, SubStationPojo.class);
    }

    /**
     * 查询子服务站
     *
     * @param pid 父服务站id
     * @return
     */
    private List<HySecdstationEntity> querySubStationEntityByPid(String pid) {
        // 查询终端
        Map<String, String> param = new HashMap<>();
        param.put("pid", pid);
        return dao.sqlFind("querySubStationEntity", param, HySecdstationEntity.class);
    }

    /**
     * 查询二级服务站
     *
     * @param id 二级服务站ID
     * @return
     */
    private HySecdstationEntity querySubStationById(BigInteger id) {
        HySecdstationEntity entity = new HySecdstationEntity();
        entity.setId(id);
        HySecdstationEntity one = (HySecdstationEntity) dao.findOne(entity);
        return one;
    }

    /**
     * 构建服务项目
     *
     * @param dataTypeEnum
     * @param valueStr
     * @return
     */
    private List<StationServiceItemOutdto> buildServiceItem(StationConstant.StationBasicDataTypeEnum dataTypeEnum,
                                                            String valueStr) {
        int basicType = dataTypeEnum.getCode();
        List<BaseData> baseDatas = baseDataService.queryList(basicType);
        if (StringUtil.isEmpty(baseDatas)) {
            return null;
        }
        List<String> valueList = StringUtil.stringToList(valueStr, ",");
        if (StringUtil.isEmpty(valueList)) {
            return null;
        }
        List<StationServiceItemOutdto> result = new ArrayList<>(baseDatas.size());
        for (BaseData baseData : baseDatas) {
            if (valueList.contains(baseData.getCode())) {
                result.add(new StationServiceItemOutdto(basicType, true, baseData));
            }
        }
        return result;
    }

    /**
     * 构建服务项目
     *
     * @param pojo
     * @return
     */
    private List<StationServiceItemOutdto> constructServiceItem(StationPojo pojo) {
        Map<StationConstant.StationBasicDataTypeEnum, String> map = new HashMap<>();
        if (!StringUtil.isEmpty(pojo.getServiceType())) {
            // 此处数据库设计的时候是int类型，觉得设计为string 类型更合理，可以与下面逻辑保持一致
            String serviceTypeStr = "";
            if (pojo.getServiceType() == 2) {
                serviceTypeStr = "0,1";
            } else {
                serviceTypeStr = pojo.getServiceType().toString();
            }
            map.put(StationConstant.StationBasicDataTypeEnum.SERVICE_TYPE, serviceTypeStr);
        }
        if (!StringUtil.isEmpty(pojo.getPartsContent())) {
            map.put(StationConstant.StationBasicDataTypeEnum.SPARE_PARTS, pojo.getPartsContent());
        }
        if (!StringUtil.isEmpty(pojo.getServiceContent())) {
            map.put(StationConstant.StationBasicDataTypeEnum.SERVICE_CONTENT, pojo.getServiceContent());
        }
        if (!StringUtil.isEmpty(pojo.getToolContent())) {
            map.put(StationConstant.StationBasicDataTypeEnum.PROMOTE_TOOL, pojo.getToolContent());
        }

        List<StationServiceItemOutdto> result = new ArrayList<>();
        for (StationConstant.StationBasicDataTypeEnum dataTypeEnum : map.keySet()) {
            result.addAll(buildServiceItem(dataTypeEnum, map.get(dataTypeEnum)));
        }
        return result;
    }

    private String buildServiceContentItem(StationConstant.StationBasicDataTypeEnum dataTypeEnum,
                                           String valueStr) {
        int basicType = dataTypeEnum.getCode();
        List<BaseData> baseDatas = baseDataService.queryList(basicType);
        if (StringUtil.isEmpty(baseDatas)) {
            return null;
        }
        List<String> valueList = StringUtil.stringToList(valueStr, ",");
        if (StringUtil.isEmpty(valueList)) {
            return null;
        }
        List<String> list = new ArrayList<>(valueList.size());
        for (BaseData baseData : baseDatas) {
            if (valueList.contains(baseData.getCode())) {
                list.add(baseData.getValue());
            }
        }
        return StringUtil.collectionToDelimitedString(list, ",");
    }

    /**
     * 删除二级服务站网点
     *
     * @param delSubStationStr
     */
    private List<HySecdstationEntity> deleteSubStation(String delSubStationStr) {
        if (StringUtil.isEmpty(delSubStationStr)) {
            return null;
        }
        List<String> delSubStationList = StringUtil.stringToList(delSubStationStr, ",");
        List<HySecdstationEntity> result = new ArrayList<>();
        for (String subId : delSubStationList) {
            result.add(querySubStationById(StringUtil.toBigInteger(subId)));
            HySecdstationEntity entity = new HySecdstationEntity();
            entity.setId(StringUtil.toBigInteger(subId));
            dao.delete(entity);
        }
        return result;
    }

    /**
     * 是否有二级服务站
     *
     * @param pid 服务站id
     * @return
     */
    private boolean hasSubStation(String pid) {
        Map<String, Object> param = new HashMap<>();
        param.put("pid", pid);
        param.put("delFlag", Constants.HAVE_DEL);
        List list = dao.sqlFindField("querySubStationIdByParam", param);
        return !StringUtil.isEmpty(list);
    }

    /**
     * 是否和用户绑定
     *
     * @param stationId 服务站ID
     * @return
     */
    private boolean hasAccountStationMapping(String stationId, Long userId, Integer userType) {
        Map<String, Object> param = new HashMap<>();
        param.put("stationId", stationId);
        param.put("accountType", userType);
        param.put("accountId", userId);
        List list = dao.sqlFindField("queryAccountStationMapping", param);
        return !StringUtil.isEmpty(list);
    }

    /**
     * 是否和车辆绑定
     *
     * @param stationId 服务站ID
     * @return
     */
    private boolean hasVehicleBind(String stationId) {
        Map<String, Object> param = new HashMap<>();
        param.put("stationId", stationId);
        List list = dao.sqlFindField("queryVehicleBind", param);
        return !StringUtil.isEmpty(list);
    }

    /**
     * 保存用户服务站关系
     *
     * @param accountId 用户ID
     * @param stationId 服务站ID
     */
    private void saveAccountStationMapping(BigInteger accountId, BigInteger stationId) {
        AccountStationMapping stationMapping = new AccountStationMapping();
        stationMapping.setAccountId(accountId);
        stationMapping.setStationId(stationId);
        dao.executeUpdate("insertStationMapping", stationMapping);
    }

    /**
     * 删除用户服务站关系
     *
     * @param accountId 用户ID
     * @param stationId 服务站ID
     */
    private void deleteAccountStationMapping(BigInteger accountId, BigInteger stationId) {
        AccountStationMapping stationMapping = new AccountStationMapping();
        stationMapping.setAccountId(accountId);
        stationMapping.setStationId(stationId);
        dao.executeUpdate("deleteAccountStationMapping", stationMapping);
    }

    /**
     * 更新二级服务站
     *
     * @param pid   父服务站
     * @param pCode 父服务站编码
     * @param list  二级服务站信息
     */
    private Map<String, List<HySecdstationEntity>> saveOrUpdateSubStation(BigInteger pid, String pCode, List<SubStationInfoIndto> list) {
        Map<String, List<HySecdstationEntity>> result = new HashMap<>();
        if (StringUtil.isEmpty(list)) {
            result.put("add", null);
            result.put("update", null);
            result.put("all", null);
            return result;
        }
        List<HySecdstationEntity> subStationList = StationConverter.inDtoToSubStationList(pid, pCode, list);
        Map<Boolean, List<HySecdstationEntity>> collect = subStationList.stream().collect(Collectors.groupingBy((HySecdstationEntity x) -> StringUtil.isEmpty(x.getId())));
        result.put("add", collect.get(true));
        result.put("update", collect.get(false));
        List<HySecdstationEntity> all = new ArrayList<>();
        //add list
        if (!StringUtil.isEmpty(collect.get(true))) {
            for (HySecdstationEntity entity : collect.get(true)) {
                HySecdstationEntity temp = (HySecdstationEntity) dao.save(entity);
                all.add(temp);
            }
        }
        //update list
        if (!StringUtil.isEmpty(collect.get(false))) {
            for (HySecdstationEntity entity : collect.get(false)) {
                HySecdstationEntity one = (HySecdstationEntity) dao.findOne(entity);
                entity.setTagSyncStatus(one.getTagSyncStatus());
                HySecdstationEntity temp = (HySecdstationEntity) dao.update(entity);
                all.add(temp);
            }
        }
        result.put("all", all);
        return result;
    }

    /**
     * 服务站是否唯一
     *
     * @param stationName 服务站名称
     * @param stationCode 服务站编码
     * @param stationId   服务站ID
     * @return
     */

    private int isStationUnique(String stationName, String stationCode, String stationId) {
        boolean stationNameUnique = isStationNameUnique(stationName, stationId);
        if (!stationNameUnique) {
            return ReturnCode.EXISTED_STATION_NAME.code();
        }
        boolean stationCodeUnique = isStationCodeUnique(stationCode, stationId);
        if (!stationCodeUnique) {
            return ReturnCode.EXISTED_STATION_CODE.code();
        }
        return ReturnCode.OK.code();
    }

    /**
     * 服务站是否停用
     *
     * @param stationName
     * @param stationCode
     * @param stationId
     * @return
     */
    private boolean isStationDisabled(String stationName, String stationCode, String stationId) {
        Map<String, Object> param = new HashMap<>();
        param.put("id", stationId);
        param.put("stationName", stationName);
        param.put("stationCode", stationCode);
        param.put("delFlag", Constants.HAVE_DEL);
        param.put("disableFlag", 0);
        List list = dao.sqlFindField("isStationDisabled", param);
        return StringUtil.isEmpty(list);
    }

    /**
     * 服务站名称是否唯一
     *
     * @param stationName 服务站名称
     * @param stationId   服务站ID
     * @return
     */
    private boolean isStationNameUnique(String stationName, String stationId) {
        Map<String, Object> param = new HashMap<>();
        param.put("id", stationId);
        param.put("stationName", stationName);
        param.put("delFlag", Constants.HAVE_DEL);
        List list = dao.sqlFindField("isStationExisted", param);
        return StringUtil.isEmpty(list);
    }

    /**
     * 服务站编码是否唯一
     *
     * @param StationCode 服务站编码
     * @param stationId   服务站ID
     * @return
     */
    private boolean isStationCodeUnique(String StationCode, String stationId) {
        Map<String, Object> param = new HashMap<>();
        param.put("id", stationId);
        param.put("stationCode", StationCode);
        param.put("delFlag", Constants.HAVE_DEL);
        List list = dao.sqlFindField("isStationExisted", param);
        return StringUtil.isEmpty(list);
    }

    /**
     * 服务站是否存在
     *
     * @param stationId 服务站ID
     * @return
     */
    private boolean isStationExisted(String stationId) {
        StationPojo stationPojo = queryStationById(stationId);
        return !StringUtil.isEmpty(stationPojo) && StringUtil.isNotEmpty(stationPojo.getStationName());
    }

    /**
     * 服务站信息同步到位置云
     *
     * @param basicInfo
     * @param stationId
     */
    private void syncToLocation(StationBasicInfoIndto basicInfo, String stationId, String token) {
        //火星坐标转换为标准坐标
        Double lat = Integer.valueOf(basicInfo.getLatitude()) * 0.000001;
        Double lon = Integer.valueOf(basicInfo.getLongitude()) * 0.000001;
        double[] temp = CoordinatesUtil.gcj2WGSExactly(lat, lon);
        basicInfo.setLatitude(String.valueOf(temp[0] * 1000000));
        basicInfo.setLongitude(String.valueOf(temp[1] * 1000000));
        messageBroadcastInAreaSetting(basicInfo, stationId, token);
        overtimeParkingInArea(basicInfo, stationId, token);
        vehiclePassInArea(basicInfo, stationId, token);
    }

    /**
     * 发送区域滞留超时
     *
     * @param basicInfo
     * @param stationId
     */
    private void overtimeParkingInArea(StationBasicInfoIndto basicInfo, String stationId, String token) {
        LCAreaInfo.AreaInfo.Builder areaInfoBuilder = LCAreaInfo.AreaInfo.newBuilder();
        areaInfoBuilder.setAreaIdentify(StringUtil.toLong(stationId));   //区域标识
        areaInfoBuilder.setTypes(LCAreaType.AreaType.circle);                   //区域类型

        LCAreaData.AreaData.Builder areaDataBuilder = LCAreaData.AreaData.newBuilder();
        areaDataBuilder.setDataSN(1);
        areaDataBuilder.setLatitude(StringUtil.toInteger(basicInfo.getLatitude()));
        areaDataBuilder.setLongitude(StringUtil.toInteger(basicInfo.getLongitude()));
        areaInfoBuilder.setCreateDate(StringUtil.getCurrentTimeSeconds());
        areaDataBuilder.setRadiusLength(StringUtil.toInteger(basicInfo.getRadius()));//占地半径
        areaInfoBuilder.addDatas(areaDataBuilder.build());           //区域数据

        LCOvertimeParkingInArea.OvertimeParkingInArea.Builder overtimeParkingBuilder = LCOvertimeParkingInArea.OvertimeParkingInArea.newBuilder();
        overtimeParkingBuilder.setAreaInfo(areaInfoBuilder.build());                 //区域信息
        overtimeParkingBuilder.setParkingTime(StringUtil.toInteger(basicInfo.getStrandedMaxTime())); //滞留阀值

        BaseTcpCommand baseTcpCommand = new BaseTcpCommand();
        String contentForBytesStr = "";
        try {
            contentForBytesStr = new String(overtimeParkingBuilder.build().toByteArray(), "ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            logger.info("overtimeParkingInArea encoding content failed", e);
            e.printStackTrace();
        }
        baseTcpCommand.setContentForBytes(contentForBytesStr);
        baseTcpCommand.setCommand(TcpCommandEnum.OVERTIME_PARKING_IN_AREA.getCommand());
        //获取序列号
        String serialNum = IDGeneralUtil.builderID(IDGeneralUtil.IDTypeEnum.SerialNumber).toString();
        baseTcpCommand.setSerialNumber(serialNum);
        baseTcpCommand.setToken(token);
        try {
            cmdClient.call_2502(baseTcpCommand);
        } catch (Exception e) {
            logger.error("发送" + TcpCommandEnum.OVERTIME_PARKING_IN_AREA.getMsg() + "到TCP模块失败");
        }
    }

    /**
     * 发送区域信息播报设置
     *
     * @param basicInfo
     * @param stationId
     */
    private void messageBroadcastInAreaSetting(StationBasicInfoIndto basicInfo, String stationId, String token) {
        LCAreaInfo.AreaInfo.Builder areaInfoBuilder = LCAreaInfo.AreaInfo.newBuilder();
        areaInfoBuilder.setAreaIdentify(StringUtil.toLong(stationId)); // 区域标识
        areaInfoBuilder.setTypes(LCAreaType.AreaType.circle); // 区域类型

        LCAreaData.AreaData.Builder areaDataBuilder = LCAreaData.AreaData.newBuilder();
        areaDataBuilder.setDataSN(1);
        areaDataBuilder.setLatitude(StringUtil.toInteger(basicInfo.getLatitude())); // 经度
        areaDataBuilder.setLongitude(StringUtil.toInteger(basicInfo.getLongitude()));// 维度

        areaInfoBuilder.setCreateDate(StringUtil.getCurrentTimeSeconds());
        areaDataBuilder.setRadiusLength(StringUtil.toInteger(basicInfo.getServiceRadius()));// 暂时使用服务半径，后续添加播报半径后再调整，caozhen，20160426
        areaInfoBuilder.addDatas(areaDataBuilder.build()); // 区域数据

        LCMessageBroadcastInArea.MessageBroadcastInArea.Builder messageBuilder =
                LCMessageBroadcastInArea.MessageBroadcastInArea.newBuilder();
        messageBuilder.setAreaInfo(areaInfoBuilder.build()); // 区域信息
        messageBuilder.setBroadcastContent(basicInfo.getStationName()); // 播报内容

        LCMessageSign.MessageSign.Builder msign = LCMessageSign.MessageSign.newBuilder();
        msign.setIsAdvertiseScreen(true);
        msign.setIsBroadcast(true);
        msign.setIsDisplay(true);
        msign.setIsUrgent(false);
        msign.setInfoType(true);

        BaseTcpCommand baseTcpCommand = new BaseTcpCommand();
        messageBuilder.setSigns(msign);
        String contentForBytesStr = "";
        try {
            contentForBytesStr = new String(messageBuilder.build().toByteArray(), "ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            logger.info("messageBroadcastInAreaSetting encoding content failed", e);
            e.printStackTrace();
        }
        baseTcpCommand.setContentForBytes(contentForBytesStr);
        baseTcpCommand.setCommand(TcpCommandEnum.MESSAGE_BROADCAST_IN_AREA.getCommand());
        // 获取序列号
        String serialNum = IDGeneralUtil.builderID(IDGeneralUtil.IDTypeEnum.SerialNumber).toString();
        baseTcpCommand.setSerialNumber(serialNum);
        baseTcpCommand.setToken(token);

        try {
            cmdClient.call_2500(baseTcpCommand);
        } catch (Exception e) {
            logger.error("发送" + TcpCommandEnum.MESSAGE_BROADCAST_IN_AREA.getMsg() + "到TCP模块失败");
        }
    }

    /**
     * 区域车次统计
     *
     * @param basicInfo
     * @param stationId
     */
    private void vehiclePassInArea(StationBasicInfoIndto basicInfo, String stationId, String token) {
        if (basicInfo == null) {
            return;
        }
        LCAreaInfo.AreaInfo.Builder areaInfoBuilder = LCAreaInfo.AreaInfo.newBuilder();
        areaInfoBuilder.setAreaIdentify(StringUtil.toLong(stationId)); // 区域标识
        areaInfoBuilder.setTypes(LCAreaType.AreaType.circle); // 区域类型
        LCAreaData.AreaData.Builder areaDataBuilder = LCAreaData.AreaData.newBuilder();
        areaDataBuilder.setDataSN(1);

        if (StringUtil.isNotEmpty(basicInfo.getLatitude())) {
            areaDataBuilder.setLatitude(StringUtil.toInteger(basicInfo.getLatitude())); // 维度
        }
        if (StringUtil.isNotEmpty(basicInfo.getLongitude())) {
            areaDataBuilder.setLongitude(StringUtil.toInteger(basicInfo.getLongitude()));// 经度
        }
        if (StringUtil.isNotEmpty(basicInfo.getServiceRadius())) {
            areaDataBuilder.setRadiusLength(StringUtil.toInteger(basicInfo.getServiceRadius()));// 使用服务半径
        }
        areaInfoBuilder.setCreateDate(StringUtil.getCurrentTimeSeconds());
        areaInfoBuilder.addDatas(areaDataBuilder.build()); // 区域数据

        LCVehiclePassInArea.VehiclePassInArea.Builder vehiclePassBuilder =
                LCVehiclePassInArea.VehiclePassInArea.newBuilder();
        vehiclePassBuilder.setAreaInfo(areaInfoBuilder.build());

        BaseTcpCommand baseTcpCommand = new BaseTcpCommand();
        String contentForBytesStr = "";
        try {
            contentForBytesStr = new String(vehiclePassBuilder.build().toByteArray(), "ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            logger.info("vehiclePassInArea encoding content failed", e);
            e.printStackTrace();
        }
        baseTcpCommand.setContentForBytes(contentForBytesStr);
        baseTcpCommand.setCommand(TcpCommandEnum.VEHICLE_PASS_IN_AREA.getCommand());
        String serialNum = IDGeneralUtil.builderID(IDGeneralUtil.IDTypeEnum.SerialNumber).toString();
        baseTcpCommand.setSerialNumber(serialNum);
        baseTcpCommand.setToken(token);

        try {
            cmdClient.call_2505(baseTcpCommand);
        } catch (Exception e) {
            logger.error("发送" + TcpCommandEnum.VEHICLE_PASS_IN_AREA.getMsg() + "到TCP模块失败");
        }
    }

    /**
     * 进出区域通知设置
     *
     * @param areaId    区域ID
     * @param longitude 经度
     * @param latitude  纬度
     * @param radius    半径
     */
    public void inOrOutAreaNotifySet(Long areaId, Integer longitude, Integer latitude, Integer radius, String token) {

        LCAreaInfo.AreaInfo.Builder areaInfoBuilder = LCAreaInfo.AreaInfo.newBuilder();
        LCInOrOutAreaNotifySet.InOrOutAreaNotifySet.Builder inOrOutAreaNotifySet = LCInOrOutAreaNotifySet.InOrOutAreaNotifySet.newBuilder();
        areaInfoBuilder.setAreaIdentify(areaId);
        areaInfoBuilder.setCreateDate(StringUtil.getCurrentTimeSeconds());// 创建时间
        areaInfoBuilder.setTypes(LCAreaType.AreaType.circle);// 创建类型

        LCAreaData.AreaData.Builder areaDataBuilder = LCAreaData.AreaData.newBuilder();
        areaDataBuilder.setDataSN(1);

        if (StringUtil.isEmpty(latitude)) {
            areaDataBuilder.setLatitude(0);// 维度
        } else {
            areaDataBuilder.setLatitude(latitude);// 维度
        }
        if (StringUtil.isEmpty(longitude)) {
            areaDataBuilder.setLongitude(0);// 经度
        } else {
            areaDataBuilder.setLongitude(longitude);// 经度
        }
        //火星坐标转换为标准坐标
        Double lat = Integer.valueOf(areaDataBuilder.getLatitude()) * 0.000001;
        Double lon = Integer.valueOf(areaDataBuilder.getLongitude()) * 0.000001;
        double[] temp = CoordinatesUtil.gcj2WGSExactly(lat, lon);
        areaDataBuilder.setLatitude(StringUtil.toInteger(String.valueOf(temp[0] * 1000000)));
        areaDataBuilder.setLongitude(StringUtil.toInteger(String.valueOf(temp[1] * 1000000)));

        areaDataBuilder.setRadiusLength(radius);// 半径
        areaInfoBuilder.addDatas(areaDataBuilder.build());
        inOrOutAreaNotifySet.setAreaInfo(areaInfoBuilder);
        inOrOutAreaNotifySet.setStatus(0);//0：进出通知；1：进通知；2：出通知


        BaseTcpCommand baseTcpCommand = new BaseTcpCommand();
        String contentForBytesStr = "";
        try {
            contentForBytesStr = new String(inOrOutAreaNotifySet.build().toByteArray(), "ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            logger.info("inOrOutAreaNotifySet encoding content failed", e);
            e.printStackTrace();
        }
        baseTcpCommand.setContentForBytes(contentForBytesStr);
        baseTcpCommand.setCommand(TcpCommandEnum.IN_OR_OUT_AREA_NOTIFY_SETTING.getCommand());
        String serialNum = IDGeneralUtil.builderID(IDGeneralUtil.IDTypeEnum.SerialNumber).toString();
        baseTcpCommand.setSerialNumber(serialNum);
        baseTcpCommand.setToken(token);

        try {
            cmdClient.call_2507(baseTcpCommand);
        } catch (Exception e) {
            logger.error("发送" + TcpCommandEnum.IN_OR_OUT_AREA_NOTIFY_SETTING.getMsg() + "到TCP模块失败");
        }
    }

    /**
     * 进出区域通知设置删除
     *
     * @param areaId 区域ID
     */
    public void inOrOutAreaNotifySetDel(Long areaId, String token) {
        com.lc.core.protocol.webservice.newstatisticsdata.entity.LCInOrOutAreaNotifySetDel.InOrOutAreaNotifySetDel.Builder inOrOutAreaNotifySetDel = LCInOrOutAreaNotifySetDel.InOrOutAreaNotifySetDel.newBuilder();
        inOrOutAreaNotifySetDel.addAreaIdentifys(areaId);
        BaseTcpCommand baseTcpCommand = new BaseTcpCommand();
        String contentForBytesStr = "";
        try {
            contentForBytesStr = new String(inOrOutAreaNotifySetDel.build().toByteArray(), "ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            logger.info("inOrOutAreaNotifySetDel encoding content failed", e);
            e.printStackTrace();
        }
        baseTcpCommand.setContentForBytes(contentForBytesStr);
        baseTcpCommand.setCommand(TcpCommandEnum.IN_OR_OUT_AREA_NOTIFY_SETTING_DEL.getCommand());
        String serialNum = IDGeneralUtil.builderID(IDGeneralUtil.IDTypeEnum.SerialNumber).toString();
        baseTcpCommand.setSerialNumber(serialNum);
        baseTcpCommand.setToken(token);
        try {
            cmdClient.call_2508(baseTcpCommand);
        } catch (Exception e) {
            logger.error("发送" + TcpCommandEnum.IN_OR_OUT_AREA_NOTIFY_SETTING_DEL.getMsg() + "到TCP模块失败");
        }
    }

    /**
     * 数据转换
     *
     * @param list
     * @return
     */
    private List<StationPojo> dataConvert(List<StationPojo> list) {
        if (StringUtil.isEmpty(list)) {
            return null;
        }
        List<StationPojo> result = new ArrayList<>(list.size());
        for (StationPojo stationPojo : list) {
            String serviceContent = stationPojo.getServiceContent();
            if (!StringUtil.isEmpty(serviceContent)) {
                String temp = buildServiceContentItem(StationConstant.StationBasicDataTypeEnum.SERVICE_CONTENT, serviceContent);
                stationPojo.setServiceContent(temp);
            }
            String partsContent = stationPojo.getPartsContent();
            if (!StringUtil.isEmpty(partsContent)) {
                String temp = buildServiceContentItem(StationConstant.StationBasicDataTypeEnum.SPARE_PARTS, partsContent);
                stationPojo.setPartsContent(temp);
            }
            if (!StringUtil.isEmpty(stationPojo.getCreateDate())) {
                stationPojo.setCreateDateStr(DateUtils.formatDate(stationPojo.getCreateDate().longValue() * 1000));
            }
            if (!StringUtil.isEmpty(stationPojo.getRadius())) {
                stationPojo.setRadiusStr(stationPojo.getRadius() * 0.001 + "");
            }
            if (!StringUtil.isEmpty(stationPojo.getServiceRadius())) {
                stationPojo.setServiceRadiusStr(stationPojo.getServiceRadius() * 0.001 + "");
            }
            if (!StringUtil.isEmpty(stationPojo.getStrandedMaxTime())) {
                String temp = "";
                int week = stationPojo.getStrandedMaxTime().intValue() / (60 * 60 * 24 * 7);
                int day = stationPojo.getStrandedMaxTime().intValue() % (60 * 60 * 24 * 7) / (60 * 60 * 24);
                int hour = stationPojo.getStrandedMaxTime().intValue() % (60 * 60 * 24 * 7) % (60 * 60 * 24) / (60 * 60);
                if (week != 0) {
                    temp = week + "周";
                } else if (day != 0) {
                    temp = day + "天";
                } else if (hour != 0) {
                    temp = hour + "小时";
                }
                stationPojo.setStrandedMaxTimeStr(temp);
            }
            result.add(stationPojo);
        }
        return result;
    }

    /**
     * 新增服务站校验
     *
     * @param stationName 服务站名称
     * @param stationCode 服务站编码
     * @return
     */
    private HttpCommandResultWithData addVerify(String stationName, String stationCode) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        //服务站是否已停用
        boolean stationDisabled = isStationDisabled(stationName, stationCode, null);
        if (!stationDisabled) {
            result.fillResult(ReturnCode.STATION_ALREADY_DISABLED);
            return result;
        }
        //服务站是否唯一
        int re = isStationUnique(stationName, stationCode, null);
        if (re != ReturnCode.OK.code()) {
            result.setResultCode(re);
            result.setMessage(ReturnCode.valueOf(re).message());
            return result;
        }
        return result;
    }

    /**
     * 删除校验
     *
     * @param stationId 服务站ID
     * @return
     */
    private HttpCommandResultWithData deleteVerify(String stationId, Long userId, Integer userType) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        // 服务站是否存在
        boolean stationExisted = isStationExisted(stationId);
        if (!stationExisted) {
            result.fillResult(ReturnCode.STATION_NOT_EXISTED);
            return result;
        }
        // 判断服务站下是否有二级服务站
        boolean hasSubStation = hasSubStation(stationId);
        if (hasSubStation) {
            result.fillResult(ReturnCode.HAS_SUBSTATION);
            return result;
        }
        // 判断服务是否绑定到其他用户
        boolean hasUserBind = hasAccountStationMapping(stationId, userId, userType);
        if (hasUserBind) {
            result.fillResult(ReturnCode.HAS_BIND_TO_USER);
            return result;
        }
        //是否和车辆绑定  后装车辆安装单位
        boolean hasVehicleBind = hasVehicleBind(stationId);
        if (hasVehicleBind) {
            result.fillResult(ReturnCode.HAS_VEHICLE_BIND);
            return result;
        }
        return result;
    }

    /**
     * 更新校验
     *
     * @param stationId   服务站ID
     * @param stationName 服务站名称
     * @param stationCode 服务站编码
     */
    private HttpCommandResultWithData updateVerify(String stationId, String stationName, String stationCode) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        //服务站是否已停用
        boolean stationDisabled = isStationDisabled(stationName, stationCode, null);
        if (!stationDisabled) {
            result.fillResult(ReturnCode.STATION_ALREADY_DISABLED);
            return result;
        }
        // 服务站是否存在
        boolean stationExisted = isStationExisted(stationId);
        if (!stationExisted) {
            result.fillResult(ReturnCode.STATION_NOT_EXISTED);
            return result;
        }
        // 服务是否唯一
        int re = isStationUnique(stationName, stationCode, stationId);
        if (re != ReturnCode.OK.code()) {
            result.setResultCode(re);
            result.setMessage(ReturnCode.valueOf(re).message());
            return result;
        }
        return result;
    }

    /**
     * 同步服务站网点到标签系统
     *
     * @param entity        一级服务站新
     * @param addSubList    新增二级服务站信息
     * @param updateSubList 需要更新的二级服务站信息
     * @param delList       删除的二级服务站信息
     * @param editModel     编辑模式
     * @return
     * @throws Exception
     */
    private HttpCommandResultWithData synStationToSignSystem(List<HyServiceStationEntity> entity, List<HySecdstationEntity> addSubList, List<HySecdstationEntity> updateSubList, List<HySecdstationEntity> delList, final int editModel) {
        SyncServiceStationParam param = new SyncServiceStationParam();
        //一级服务站处理
        List<SyncStationPojo> stationCollect = entity.stream().map((HyServiceStationEntity x) -> setSyncStationPojoValue(x, editModel)).collect(Collectors.toList());
        param.setStationList(stationCollect);
        //新增的二级服务站处理
        List<SyncSecStationPojo> collect = new ArrayList<>();
        if (!ListUtil.isEmpty(addSubList)) {
            collect = addSubList.stream().map((HySecdstationEntity x) -> setSyncSecStationPojoValue(x, Constants.SyncModelEnum.ADD.value())).collect(Collectors.toList());
        }
        //更新的二级服务站处理
        if (!ListUtil.isEmpty(updateSubList)) {
            List<SyncSecStationPojo> collect_u = updateSubList.stream().map((HySecdstationEntity x) -> setSyncSecStationPojoValue(x, Constants.SyncModelEnum.UPDATE.value())).collect(Collectors.toList());
            collect.addAll(collect_u);
        }
        //删除的二级服务站
        if (!ListUtil.isEmpty(delList)) {
            List<SyncSecStationPojo> collect_d = delList.stream().map((HySecdstationEntity x) -> setSyncSecStationPojoValue(x, Constants.SyncModelEnum.DELETE.value())).collect(Collectors.toList());
            collect.addAll(collect_d);
        }
        param.setSecStationList(collect);
        try {
            //发起标签系统调用
            return syncService.syncStationToSign(param);
        } catch (Exception e) {
            return new HttpCommandResultWithData().fillResult(ReturnCode.STATION_SYNC_TO_TAG_FAILED);
        }
    }

    /**
     * 组装一级服务站信息
     *
     * @param entity
     * @param editModel
     * @return
     */
    public SyncStationPojo setSyncStationPojoValue(HyServiceStationEntity entity, int editModel) {
        SyncStationPojo station = new SyncStationPojo();
        station.setJf2200(entity.getServiceCode());
        station.setJf2201("X009003");//服务站没有销售大区，填充古井值
        station.setJf2202("2");
        station.setJf2203(entity.getNameForshort());
        station.setJf2204(entity.getStationName());
        station.setJf2205(entity.getAddress());
        station.setJf2206(entity.getServiceManager());
        station.setJf2207(entity.getPhone());
//        station.setJf2208(entity.getLatitude().toString());
//        station.setJf2209(entity.getLongitude().toString());
        station.setEditMode(String.valueOf(editModel));
        return validateStationLength(station);
    }

    public SyncSecStationPojo setSyncSecStationPojoValue(HySecdstationEntity entity, int editModel) {
        SyncSecStationPojo station = new SyncSecStationPojo();
        station.setJf2300(entity.getCode());
        station.setJf2301(entity.getLongitude().toString());
        station.setJf2302(entity.getLatitude().toString());
        station.setJf2303("1");
        station.setJf2304(entity.getWorkRadius());
        station.setEditMode(String.valueOf(editModel));
        return validateSubStationLength(station);
    }


    /**
     * 校验一级服务站传值长度
     *
     * @param pojo
     * @return
     */
    public SyncStationPojo validateStationLength(SyncStationPojo pojo) {
        if (null != pojo) {
            //经销商代码必填 长度20 （
            if (StringUtil.isNotEmpty(pojo.getJf2200()) && StringUtil.length(pojo.getJf2200()) > 20) {
                pojo.setJf2200(StringUtil.subChinseseStr(pojo.getJf2200(), 20));
            }
            //单位简称 选填 长度80
            if (StringUtil.isNotEmpty(pojo.getJf2203()) && StringUtil.length(pojo.getJf2203()) > 80) {
                pojo.setJf2203(StringUtil.subChinseseStr(pojo.getJf2203(), 80));
            }
            //单位名称 选填 长度80
            if (StringUtil.isNotEmpty(pojo.getJf2204()) && StringUtil.length(pojo.getJf2204()) > 80) {
                pojo.setJf2204(StringUtil.subChinseseStr(pojo.getJf2204(), 80));
            }
            //地址 选填 长度100
            if (StringUtil.isNotEmpty(pojo.getJf2205()) && StringUtil.length(pojo.getJf2205()) > 100) {
                pojo.setJf2205(StringUtil.subChinseseStr(pojo.getJf2205(), 100));
            }
            //联系人选填 长度20
            if (StringUtil.isNotEmpty(pojo.getJf2206()) && StringUtil.length(pojo.getJf2206()) > 20) {
                pojo.setJf2206(StringUtil.subChinseseStr(pojo.getJf2206(), 20));
            }
            //联系电话 选填 长度30
            if (StringUtil.isNotEmpty(pojo.getJf2207()) && StringUtil.length(pojo.getJf2207()) > 30) {
                pojo.setJf2207(StringUtil.subChinseseStr(pojo.getJf2207(), 30));
            }
        }
        return pojo;
    }

    /**
     * 校验二级服务站传值长度
     *
     * @param pojo
     * @return
     */
    public SyncSecStationPojo validateSubStationLength(SyncSecStationPojo pojo) {
        if (null != pojo) {
            //代码必填 长度20
            if (StringUtil.isNotEmpty(pojo.getJf2300()) && StringUtil.length(pojo.getJf2300()) > 20) {
                pojo.setJf2300(StringUtil.subChinseseStr(pojo.getJf2300(), 20));
            }
        }
        return pojo;
    }
}
