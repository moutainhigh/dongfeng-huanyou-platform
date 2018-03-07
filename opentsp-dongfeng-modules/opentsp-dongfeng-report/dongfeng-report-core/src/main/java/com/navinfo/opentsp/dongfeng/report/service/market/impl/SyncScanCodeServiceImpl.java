package com.navinfo.opentsp.dongfeng.report.service.market.impl;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.common.service.BaseService;
import com.navinfo.opentsp.dongfeng.common.service.IRestTemplateService;
import com.navinfo.opentsp.dongfeng.common.util.JsonUtil;
import com.navinfo.opentsp.dongfeng.common.util.StringUtil;
import com.navinfo.opentsp.dongfeng.report.commands.market.SyncScanCodeCommand;
import com.navinfo.opentsp.dongfeng.report.constant.Constant;
import com.navinfo.opentsp.dongfeng.report.dto.market.SyncScanCode;
import com.navinfo.opentsp.dongfeng.report.entity.market.ScanCodeEntity;
import com.navinfo.opentsp.dongfeng.report.entity.market.ScanCodeRecordEntity;
import com.navinfo.opentsp.dongfeng.report.repository.ScanCodeRepository;
import com.navinfo.opentsp.dongfeng.report.service.market.ISyncScanCodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * SyncScanCodeServiceImpl
 *
 * @author wt
 * @version 1.0
 * @date 2017/8/23
 * @modify
 * @copyright
 */
@Service
public class SyncScanCodeServiceImpl extends BaseService implements ISyncScanCodeService {
    protected static final Logger logger = LoggerFactory.getLogger(SyncScanCodeServiceImpl.class);

    @Value("${sync.emptyInVehicle2TDS.serviceUrl}")
    private String syncServiceUrl;

    @Value("${sync.enable:false}")
    private boolean syncSwitch;

    @Autowired
    private IRestTemplateService restTemplateService;

    @Autowired
    private ScanCodeRepository repository;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public HttpCommandResultWithData sync2Tds(SyncScanCodeCommand command) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        if (!syncSwitch) {
            result.fillResult(ReturnCode.IMPORT_SCANCODE_SYNC_SWITCH_OFF);
            return result;
        }

        StringBuffer errors = new StringBuffer();
        for (SyncScanCode code : command.getScanCodeBean()) {
            HttpCommandResultWithData syncResult = sync(code);
            if (syncResult.getResultCode() != ReturnCode.OK.code()) {
                errors.append(code.getChassisNum()).append(syncResult.getMessage()).append(",");
            }
        }
        if (StringUtil.isNotEmpty(errors.toString())) {
            result.setResultCode(ReturnCode.IMPORT_SCANCODE_SYNC_DTS_FAILED.code());
            result.setMessage(errors.toString());
        }
        return result;
    }

    private HttpCommandResultWithData sync(SyncScanCode command) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        Map<String, Object> params = new HashMap<>(16);
        params.put("chassisNum", command.getChassisNum());
        ScanCodeRecordEntity entity = (ScanCodeRecordEntity) dao.sqlFindObject("querySyncScanCodeInfos", params, ScanCodeRecordEntity.class);
        if (StringUtil.isEmpty(entity)) {
            logger.warn("sync vehicle {} is not exist... ", command.getChassisNum());
            result.fillResult(ReturnCode.IMPORT_SCANCODE_SYNC_DTS_FAILED_DATA_EMPTY);
            return result;
        }

        // 封装Url
        StringBuffer url = new StringBuffer(syncServiceUrl);
        url.append("?vvin=").append(entity.getVin());
        url.append("&vcompany=").append(entity.getCompany());
        url.append("&vfactory=").append(entity.getFactory());
        url.append("&vteam=").append(entity.getTeam());
        url.append("&vpropertywh=").append(entity.getPropertywh());
        url.append("&vphysicalwh=").append(entity.getPhysicalwh());
        url.append("&varea=").append(entity.getArea());
        url.append("&vscrutator=").append(entity.getScrutator());
        url.append("&vrelateman=").append(entity.getRelateman());
        url.append("&voperator=").append(entity.getOperator());

        try {
            ResponseEntity<String> response = restTemplateService.getForEntity(url.toString(), String.class);
            Map m = JsonUtil.toMap(response.getBody());
            logger.info("Sync Vehicle {} To TDS resultCdoe:{} ,Message:{}", command.getChassisNum(), m.get("Code"), m.get("Errmsg"));
            if (Integer.parseInt(Objects.toString(m.get("Code"))) == ReturnCode.OK.code()) {
                ScanCodeEntity codeEntity = repository.findByChassisNum(command.getChassisNum());
                codeEntity.setSyncState(BigInteger.valueOf(Constant.SyncStatus.SYNC.getIndex()));
                dao.update(codeEntity);
            } else {
                result.fillResult(ReturnCode.IMPORT_SCANCODE_SYNC_DTS_FAILED);
            }
        } catch (Exception e) {
            logger.error("Sync Vehicle " + command.getChassisNum() + " To TDS error ", e);
            result.fillResult(ReturnCode.IMPORT_SCANCODE_SYNC_DTS_FAILED);
        }

        return result;
    }
}
