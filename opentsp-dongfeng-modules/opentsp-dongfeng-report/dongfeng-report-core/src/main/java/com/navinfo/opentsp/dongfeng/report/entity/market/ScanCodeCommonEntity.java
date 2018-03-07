package com.navinfo.opentsp.dongfeng.report.entity.market;

import com.navinfo.opentsp.dongfeng.system.commands.car.UpdateStorageStateCommand;

import java.util.ArrayList;
import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author wt
 * @version 1.0
 * @date 2017-07-07
 * @modify
 * @copyright Navi Tsp
 */
public class ScanCodeCommonEntity {
    List<UpdateStorageStateCommand> vehicleStorageStates = new ArrayList<>();
    List<ScanCodeEntity> offLineRecords = new ArrayList<>();
    List<ScanCodeEntity> unOffLineRecords = new ArrayList<>();
    List<ScanCodeRecordEntity> scanRecords = new ArrayList<>();

    public List<UpdateStorageStateCommand> getVehicleStorageStates() {
        return vehicleStorageStates;
    }

    public void setVehicleStorageStates(List<UpdateStorageStateCommand> vehicleStorageStates) {
        this.vehicleStorageStates = vehicleStorageStates;
    }

    public List<ScanCodeEntity> getOffLineRecords() {
        return offLineRecords;
    }

    public void setOffLineRecords(List<ScanCodeEntity> offLineRecords) {
        this.offLineRecords = offLineRecords;
    }

    public List<ScanCodeEntity> getUnOffLineRecords() {
        return unOffLineRecords;
    }

    public void setUnOffLineRecords(List<ScanCodeEntity> unOffLineRecords) {
        this.unOffLineRecords = unOffLineRecords;
    }

    public List<ScanCodeRecordEntity> getScanRecords() {
        return scanRecords;
    }

    public void setScanRecords(List<ScanCodeRecordEntity> scanRecords) {
        this.scanRecords = scanRecords;
    }
}
