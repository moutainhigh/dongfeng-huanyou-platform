package com.navinfo.opentsp.dongfeng.report.service.market.impl;

import com.navinfo.opentsp.dongfeng.common.constant.ReportConfigConstants;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.common.service.BaseService;
import com.navinfo.opentsp.dongfeng.common.util.ExcelUtil;
import com.navinfo.opentsp.dongfeng.common.util.ReportConfig;
import com.navinfo.opentsp.dongfeng.common.util.StringUtil;
import com.navinfo.opentsp.dongfeng.common.validation.ValidateUtil;
import com.navinfo.opentsp.dongfeng.report.client.VehicleClient;
import com.navinfo.opentsp.dongfeng.report.commands.market.ImportScanCodeCommand;
import com.navinfo.opentsp.dongfeng.report.constant.Constant;
import com.navinfo.opentsp.dongfeng.report.entity.market.ScanCodeCommonEntity;
import com.navinfo.opentsp.dongfeng.report.entity.market.ScanCodeEntity;
import com.navinfo.opentsp.dongfeng.report.entity.market.ScanCodeRecordEntity;
import com.navinfo.opentsp.dongfeng.report.pojo.market.ImportScanCodePojo;
import com.navinfo.opentsp.dongfeng.report.pojo.market.ScanCodeEmptyInVehiclePojo;
import com.navinfo.opentsp.dongfeng.report.pojo.market.ScanCodeVehiclePojo;
import com.navinfo.opentsp.dongfeng.report.service.market.ImportScanCodeService;
import com.navinfo.opentsp.dongfeng.system.commands.car.UpdateStorageStateCommand;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.*;

/**
 * ${DESCRIPTION}
 *
 * @author wt
 * @version 1.0
 * @date 2017-06-07
 * @modify
 * @copyright Navi Tsp
 */
@Service
public class ImportScanCodeServiceImpl extends BaseService implements ImportScanCodeService {

    protected static final Logger logger = LoggerFactory.getLogger(ImportScanCodeServiceImpl.class);

    private static final String[] EXCLUDE_CHECK_IMPORT_SCANCODE_FIELD = new String[]{"id", "vinCode", "operatorNum"};

    private static final String UNOFF_LINE_EMPTY_IN_PROCESS = "00041";

    private static final String OFF_LINE_EMPTY_IN_PROCESS = "00042";

    private static final String OPERATOR_MESSAGE = "空入车辆批量导入";

    @Autowired
    private VehicleClient vehicleClient;


    @Transactional
    @Override
    public HttpCommandResultWithData importScanCode(final ImportScanCodeCommand command)
    {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        List<Object> scanCodes = parseReport(command);
        if (CollectionUtils.isEmpty(scanCodes)) {
            result.fillResult(ReturnCode.IMPORT_SCANREPORT_FAILED);
            return result;
        }

        final String checkResult = checkValid(scanCodes);
        if (StringUtil.isNotEmpty(checkResult)) {
            result.setResultCode(ReturnCode.IMPORT_SCANREPORT_FAILED.code());
            result.setMessage(checkResult);
            return result;
        }

        ScanCodeCommonEntity commonEntity = cvtDbRecords(scanCodes, command.getToken());
        batchSaveRecords(commonEntity);
        return result;
    }

    private void batchSaveRecords(final ScanCodeCommonEntity commonEntity) {
        dao.batchSave(commonEntity.getOffLineRecords(), true);
        dao.batchSave(commonEntity.getUnOffLineRecords(), true);
        dao.batchSave(commonEntity.getScanRecords(), true);
        logger.info("update vehicle {} storage state start", commonEntity.getVehicleStorageStates());
        for (UpdateStorageStateCommand command : commonEntity.getVehicleStorageStates()) {
            vehicleClient.update(command);
        }
        logger.info("update vehicle storage state end");
    }

    private ScanCodeCommonEntity cvtDbRecords(final List<Object> scanCodes, final String token) {
        ScanCodeCommonEntity commonEntity = new ScanCodeCommonEntity();
        List<UpdateStorageStateCommand> vehicleStorageStates = new ArrayList<>();
        List<ScanCodeEntity> offLineRecords = new ArrayList<>();
        List<ScanCodeEntity> unOffLineRecords = new ArrayList<>();
        List<ScanCodeRecordEntity> scanRecords = new ArrayList<>();

        List<ScanCodeVehiclePojo> vehicles = getDbExistVehicles(scanCodes);
        for (Object obj : scanCodes) {
            ImportScanCodePojo scanCode = (ImportScanCodePojo) obj;
            final String classisNum = cvtVin2ClassisNum(scanCode.getVinCode());
            ScanCodeEntity entity = new ScanCodeEntity();
            entity.setChassisNum(classisNum);
            entity.setCreateTime(new Date());
            entity.setOperateUser(scanCode.getOperatorNum());
            entity.setSyncState(BigInteger.valueOf(Constant.SyncStatus.UN_SYNC.getIndex()));

            boolean isOffLine = false;
            for (ScanCodeVehiclePojo vehicle : vehicles) {
                if (vehicle.getChassisNum().equals(classisNum)) {
                    entity.setPlateNum(vehicle.getPlateNum());
                    entity.setCode(vehicle.getCode());
                    entity.setSim(vehicle.getSim());
                    entity.setTeamId(vehicle.getCarTeamId());
                    entity.setState(Constant.ScanCodeStatus.OFFLINE.getIndex());
                    entity.setCurrProcess(OFF_LINE_EMPTY_IN_PROCESS);
                    offLineRecords.add(entity);

                    UpdateStorageStateCommand storageStateCommand = new UpdateStorageStateCommand();
                    storageStateCommand.setCarId(vehicle.getCarId().longValue());
                    storageStateCommand.setState(Constant.ScanCodeStatus.OFFLINE.getIndex());
                    storageStateCommand.setToken(token);
                    vehicleStorageStates.add(storageStateCommand);
                    isOffLine = true;
                    break;
                }
            }
            if (!isOffLine) {
                entity.setState(Constant.ScanCodeStatus.UN_OFFLINE.getIndex());
                entity.setCurrProcess(UNOFF_LINE_EMPTY_IN_PROCESS);
                unOffLineRecords.add(entity);
            }

            ScanCodeRecordEntity scanCodeRecordEntity = new ScanCodeRecordEntity();
            scanCodeRecordEntity.setVin(scanCode.getVinCode());
            scanCodeRecordEntity.setCompany(scanCode.getCompanyCode());
            scanCodeRecordEntity.setFactory(scanCode.getProductionFactoryCode());
            scanCodeRecordEntity.setTeam(scanCode.getShiftCode());
            scanCodeRecordEntity.setPropertywh(scanCode.getSellRepositCode());
            scanCodeRecordEntity.setPhysicalwh(scanCode.getPhysicalLibraryCode());
            scanCodeRecordEntity.setArea(scanCode.getWarehouseCode());
            scanCodeRecordEntity.setScrutator(scanCode.getQualityCheckerCode());
            scanCodeRecordEntity.setRelateman(scanCode.getReverseDriverCode());
            scanCodeRecordEntity.setOperator(scanCode.getOperatorNum());
            scanRecords.add(scanCodeRecordEntity);

        }

        commonEntity.setVehicleStorageStates(vehicleStorageStates);
        commonEntity.setOffLineRecords(offLineRecords);
        commonEntity.setUnOffLineRecords(unOffLineRecords);
        commonEntity.setScanRecords(scanRecords);
        return commonEntity;
    }

    private String cvtVin2ClassisNum(final String vinCode) {
        return vinCode.substring(vinCode.length() - 8, vinCode.length());
    }

    private String checkValid(final List<Object> scanCodes) {
        Map<String, Object> compares = getDbScanCompareCodes();
        List<ScanCodeEmptyInVehiclePojo> emptyInVehicles = getDbExistEmptyInVehicles(scanCodes);
        try {
            for (int i = 0; i < scanCodes.size(); i++) {
                ImportScanCodePojo scanCode = (ImportScanCodePojo) scanCodes.get(i);
                // 校验表格空数据
                final String validateValue = ValidateUtil.validateBean(scanCode);
                if (StringUtil.isNotEmpty(validateValue)) {
                    return "第" + (i + 1) + "行数据有误，" + validateValue;
                }

                // 校验重复导入数据
                for (ScanCodeEmptyInVehiclePojo vehicle : emptyInVehicles) {
                    if (vehicle.getChassisNum().equals(cvtVin2ClassisNum(scanCode.getVinCode()))) {
                        return "第" + (i + 1) + "行数据有误，" + scanCode.getVinCode() + "重复导入";
                    }
                }

                // 校验枚举值是否存在
                final Field[] fields = scanCode.getClass().getDeclaredFields();
                for (Field field : fields) {
                    field.setAccessible(true);
                    if (isExcludeField(field.getName())) {
                        continue;
                    }

                    List<String> compValues = (List<String>) compares.get(field.getName());
                    if (CollectionUtils.isEmpty(compValues) || !compValues.contains(StringUtil.nvl(field.get(scanCode)))) {
                        return "第" + (i + 1) + "行数据有误，" + StringUtil.nvl(field.get(scanCode)) + "枚举值不存在";
                    }
                }
            }
        } catch (IllegalAccessException e) {
            logger.error("Check validation for import data is error:", e);
        }
        return checkImportReportDuplicatData(scanCodes);
    }

    private String checkImportReportDuplicatData(final List<Object> scanCodes) {
        String checkResult = "";
        if (CollectionUtils.isEqualCollection(scanCodes, new HashSet<>(scanCodes))) {
            return checkResult;
        }

        Set<ImportScanCodePojo> uniqueSet = new HashSet<>();
        for (int i = 0; i < scanCodes.size(); i++) {
            ImportScanCodePojo scanCode = (ImportScanCodePojo) scanCodes.get(i);
            if (!uniqueSet.contains(scanCode)) {
                uniqueSet.add(scanCode);
            } else {
                checkResult = "第" + (i + 1) + "行数据有误，" + scanCode.getVinCode() + "在导入报表中存在重复数据";
                break;
            }
        }
        return checkResult;
    }

    private List<ScanCodeVehiclePojo> getDbExistVehicles(final List<Object> scanCodes) {
        logger.info("Query DbExistVehicles start");
        Map<String, Object> parameters = cvtQueryParas(scanCodes);
        List<ScanCodeVehiclePojo> vehicles = new ArrayList<>();
        try {
            vehicles = (List<ScanCodeVehiclePojo>) dao.sqlFind("queryScanVehicles", parameters, ScanCodeVehiclePojo.class);
        } catch (Exception e) {
            logger.error("Query DbExistVehicles is error", e);
        }
        logger.info("Query DbExistVehicles end");
        return vehicles;
    }

    private List<ScanCodeEmptyInVehiclePojo> getDbExistEmptyInVehicles(final List<Object> scanCodes) {
        logger.info("Query DbExistEmptyInVehicles start");
        Map<String, Object> parameters = cvtQueryParas(scanCodes);
        List<ScanCodeEmptyInVehiclePojo> vehicles = new ArrayList<>();
        try {
            vehicles = (List<ScanCodeEmptyInVehiclePojo>) dao.sqlFind("queryScanEmptyInVehicles", parameters, ScanCodeEmptyInVehiclePojo.class);
        } catch (Exception e) {
            logger.error("Query DbExistEmptyInVehicles is error", e);
        }
        logger.info("Query DbExistEmptyInVehicles end");
        return vehicles;
    }

    private Map<String, Object> cvtQueryParas(final List<Object> scanCodes) {
        List<String> classisNums = new ArrayList<>();
        for (Object obj : scanCodes) {
            ImportScanCodePojo scanCode = (ImportScanCodePojo) obj;
            if (StringUtil.isEmpty(scanCode.getVinCode())) {
                continue;
            }
            classisNums.add(cvtVin2ClassisNum(scanCode.getVinCode()));
        }
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("classisNums", classisNums);
        return parameters;
    }

    private Map<String, Object> getDbScanCompareCodes() {
        logger.info("Query scanCode for compare datas start");
        Map<String, Object> results = new HashMap<String, Object>();
        try {
            final ImportScanCodePojo compare = (ImportScanCodePojo) dao.sqlFindObject("queryScanCompareCodes",
                    new HashMap<String, Object>(), ImportScanCodePojo.class);
            final Field[] fields = compare.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                if (isExcludeField(field.getName())) {
                    continue;
                }

                final String value = StringUtil.nvl(field.get(compare));
                results.put(field.getName(), Arrays.asList(StringUtil.split(value, ",")));
            }
        } catch (IllegalAccessException ie) {
            logger.error("Query scanCode compare datas error:", ie);
        }
        logger.info("Query scanCode for compare datas end");
        return results;
    }

    private boolean isExcludeField(final String fieldName) {
        for (String field : EXCLUDE_CHECK_IMPORT_SCANCODE_FIELD) {
            if (fieldName.equals(field)) {
                return true;
            }
        }
        return false;
    }

    private List<Object> parseReport(final ImportScanCodeCommand command) {
        logger.info("Parse report start, command is {}", command.toString());
        List<Object> scanCodes = null;
        try {
            ReportConfig config = ReportConfigConstants.scanCodeImportConfig;
            config.setSourcePath(command.getFilePath());
            scanCodes = ExcelUtil.readExcel(config, 0, 1, ImportScanCodePojo.class);
        } catch (Exception e) {
            logger.error("Parse excel for scan code emptyin is failed:", e);
            scanCodes = Collections.emptyList();
        }
        logger.info("Parse report end");
        return scanCodes;
    }
}
