package com.navinfo.opentsp.dongfeng.system.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.navinfo.opentsp.dongfeng.common.constant.Constants;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.common.service.BaseService;
import com.navinfo.opentsp.dongfeng.common.service.IRestTemplateService;
import com.navinfo.opentsp.dongfeng.common.util.JsonUtil;
import com.navinfo.opentsp.dongfeng.common.util.StringUtil;
import com.navinfo.opentsp.dongfeng.system.entity.HyServiceStationEntity;
import com.navinfo.opentsp.dongfeng.system.entity.TeamEntity;
import com.navinfo.opentsp.dongfeng.system.pojo.eletag.SyncServiceStationParam;
import com.navinfo.opentsp.dongfeng.system.service.ISyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;

/**
 * @author tushenghong
 * @version 1.0
 * @date 2017-07-26
 * @modify
 * @copyright Navi Tsp
 */
@Service
public class SyncService extends BaseService implements ISyncService {

    @Value("${cloud.server.serviceUrl.syncHyStationTeam}")
    private String syncHyStationTeamUri;
    @Value("${sign.server.serviceUrl.syncDealer}")
    private String syncDealer;
    @Value("${sign.server.serviceUrl.syncServiceStation}")
    private String syncServiceStation;
    @Value("${cloud.server.sync.stationTeam.switch}")
    private String syncStationTeamSwitch;

    @Autowired
    private IRestTemplateService restTemplateService;

    @Override
    @Transactional
    public HttpCommandResultWithData syncStation(String stationId, String provinceCode, String cityCode, int operateType) {
        HttpCommandResultWithData sResult = new HttpCommandResultWithData();
        int status = 0;
        if (!Boolean.valueOf(syncStationTeamSwitch)) {
            logger.info("the switch of sync to tboss is off");
            return sResult;
        }
        HyServiceStationEntity entity = new HyServiceStationEntity();
        entity.setId(StringUtil.toBigInteger(stationId));
        entity = (HyServiceStationEntity) dao.findOne(entity);
        try {
            ResponseEntity<HttpCommandResultWithData> re =
                    restTemplateService.getForEntity(syncHyStationTeamUri,
                            HttpCommandResultWithData.class,
                            stationId,
                            2,
                            provinceCode,
                            cityCode,
                            String.valueOf(operateType));
            // 更新同步结果到数据库
            HttpCommandResultWithData result = re.getBody();
            if (re.getStatusCode().is2xxSuccessful() && result != null && result.getResultCode() == ReturnCode.OK.code()) {
                status = Constants.SyncOperateEnum.SUCCESS.value();
            } else {
                //如果之前为新增且同步到tboss失败，则不改变同步状态,，否则改为当前操作状态
                if (entity.getSyncStatus() == Constants.SyncOperateEnum.ADD.value()) {
                    status = entity.getSyncStatus();
                } else {
                    status = operateType;
                }
                sResult.fillResult(ReturnCode.STATION_SYNC_TO_TBOSS_FAILED);
            }
        } catch (Exception e) {
            logger.error("sync station failed,", e, e.getMessage());
            //如果之前为新增且同步到tboss失败，则不改变同步状态,，否则改为当前操作状态
            if (entity.getSyncStatus() == Constants.SyncOperateEnum.ADD.value()) {
                status = entity.getSyncStatus();
            } else {
                status = operateType;
            }
            sResult.fillResult(ReturnCode.STATION_SYNC_TO_TBOSS_FAILED);
        }
        // 更新同步结果到数据库
        entity.setSyncStatus(status);
        dao.executeUpdate("updateSyncStatusOfStation", entity);
        return sResult;
    }

    @Async
    @Override
    @Transactional
    public HttpCommandResultWithData asyncStation(String stationId, String provinceCode, String cityCode, int operateType) {
        return syncStation(stationId, provinceCode, cityCode, operateType);
    }

    @Transactional
    @Override
    public HttpCommandResultWithData syncDealer(String tId, String provinceCode, String cityCode, int operateType) {
        HttpCommandResultWithData sResult = new HttpCommandResultWithData();
        int status = 0;
        if (!Boolean.valueOf(syncStationTeamSwitch)) {
            logger.info("the switch of sync to tboss is off");
            return sResult;
        }
        TeamEntity paramEntity = new TeamEntity();
        paramEntity.setId(new BigInteger(tId));
        TeamEntity teamEntity = (TeamEntity) dao.findOne(paramEntity);
        try {
            ResponseEntity<HttpCommandResultWithData> re =
                    restTemplateService.getForEntity(syncHyStationTeamUri,
                            HttpCommandResultWithData.class,
                            tId,
                            1,
                            provinceCode,
                            cityCode,
                            String.valueOf(operateType));
            HttpCommandResultWithData result = re.getBody();
            // 更新同步结果到数据库
            if (re.getStatusCode().is2xxSuccessful() && result != null && result.getResultCode() == ReturnCode.OK.code()) {
                status = Constants.SyncOperateEnum.SUCCESS.value();
            } else {
                //如果之前为新增且同步到tboss失败，则不改变同步状态,，否则改为当前操作状态
                if (teamEntity.getSyncStatus() == Constants.SyncOperateEnum.ADD.value()) {
                    status = teamEntity.getSyncStatus();
                } else {
                    status = operateType;
                }
                sResult.fillResult(ReturnCode.STATION_SYNC_TO_TBOSS_FAILED);
            }
        } catch (Exception e) {
            logger.error("sync dealer failed,", e, e.getMessage());
            //如果之前为新增且同步到tboss失败，则不改变同步状态,，否则改为当前操作状态
            if (teamEntity.getSyncStatus() == Constants.SyncOperateEnum.ADD.value()) {
                status = teamEntity.getSyncStatus();
            } else {
                status = operateType;
            }
            sResult.fillResult(ReturnCode.STATION_SYNC_TO_TBOSS_FAILED);
        }
        // 更新同步结果到数据库
//        entity.setId(StringUtil.toBigInteger(tId));
//        entity = (TeamEntity) dao.findOne(entity);
//        entity.setUpdateTime(StringUtil.toBigInteger(StringUtil.getCurrentTimeSeconds()));
//        entity.setSyncStatus(status);
//        dao.update(entity);
        // 更新同步结果到数据库
        TeamEntity upteamEntity = new TeamEntity();
        upteamEntity.setId(teamEntity.getId());
        upteamEntity.setSyncStatus(status);
        dao.executeUpdate("updateSyncTossStatusOfDealer", upteamEntity);
        return sResult;
    }

    @Async
    @Override
    @Transactional
    public HttpCommandResultWithData asyncDealer(String tId, String provinceCode, String cityCode, int operateType) {
        return syncDealer(tId, provinceCode, cityCode, operateType);
    }

    @Override
    public HttpCommandResultWithData syncStationToSign(SyncServiceStationParam param) throws JsonProcessingException {
        String paramJsonStr = JsonUtil.toJson(param);
        ResponseEntity<HttpCommandResultWithData> re = restTemplateService.postForEntity(syncServiceStation, paramJsonStr, HttpCommandResultWithData.class);
        HttpCommandResultWithData result = re.getBody();
        logger.info("服务站同步标签系统响应结果：" + result.getResultCode() + "--------" + result.getMessage());
        return result;
    }

    @Override
    public HttpCommandResultWithData syncDealerToSign(Object param) {
        ResponseEntity<HttpCommandResultWithData> re = restTemplateService.postForEntity(syncDealer, param, HttpCommandResultWithData.class);
        HttpCommandResultWithData result = re.getBody();
        logger.info("经销商同步标签系统响应结果：" + result.getResultCode() + "--------" + result.getMessage());
        return result;
    }
}
