package com.navinfo.opentsp.dongfeng.monitor.entity;

import javax.persistence.*;
import java.math.BigInteger;
import java.sql.Timestamp;

/**
 * @Author liusanhu@aerozhonghuan.com
 * @Date 2017/3/8
 */
@Entity
@Table(name = "hy_car")
public class HyCarEntity {
    private BigInteger carId;
    private int districtId;
    private String carCph;
    private Integer carColor;
    private BigInteger carTerminal;
    private BigInteger carTeamId;
    private Integer carState;
    private String carPw;
    private String carAccountName;
    private String carAutoNumber;
    private BigInteger carDate;
    private String carPlace;
    private String carCompany;
    private Integer delFlag;
    private Integer carType;
    private Integer carTrade;
    private Integer carServiceStop;
    private BigInteger serviceBegin;
    private BigInteger serviceEnd;
    private BigInteger nettingTime;
    private BigInteger nettingLog;
    private BigInteger nettingLat;
    private String chassisNum;
    private String structureNum;
    private String oilCapacity;
    private Integer lockStauts;
    private BigInteger carTerminalId;
    private Integer autoFlag;
    private Integer tamperStatue;
    private String operateCommon;
    private String operateUser;
    private BigInteger operateDate;
    private Integer tamperNoticeStatus;
    private BigInteger offlineTime;
    private BigInteger removalTime;
    private BigInteger registerTime;
    private String operateIp;
    private BigInteger carFkdate;
    private Integer batteryType;
    private Integer batteryBatches;
    private String carModelCode;
    private BigInteger onlineTime;
    private String carModel;
    private BigInteger warehouseLog;
    private BigInteger warehouseLat;
    private BigInteger warehouseTime;
    private String orderNumber;
    private Timestamp syncTime;
    private String lockMethod;
    private String qrCode;
    private BigInteger financingCompany;
    private BigInteger payType;
    private BigInteger createAccountId;

    @Id
    @Column(name = "CAR_ID", nullable = false, columnDefinition="bigint")
    public BigInteger getCarId() {
        return carId;
    }

    public void setCarId(BigInteger carId) {
        this.carId = carId;
    }

    @Basic
    @Column(name = "DISTRICT_ID", nullable = false)
    public int getDistrictId() {
        return districtId;
    }

    public void setDistrictId(int districtId) {
        this.districtId = districtId;
    }

    @Basic
    @Column(name = "CAR_CPH", nullable = true, length = 50)
    public String getCarCph() {
        return carCph;
    }

    public void setCarCph(String carCph) {
        this.carCph = carCph;
    }

    @Basic
    @Column(name = "CAR_COLOR", nullable = true)
    public Integer getCarColor() {
        return carColor;
    }

    public void setCarColor(Integer carColor) {
        this.carColor = carColor;
    }

    @Basic
    @Column(name = "CAR_TERMINAL", nullable = true, columnDefinition="bigint")
    public BigInteger getCarTerminal() {
        return carTerminal;
    }

    public void setCarTerminal(BigInteger carTerminal) {
        this.carTerminal = carTerminal;
    }

    @Basic
    @Column(name = "CAR_TEAM_ID", nullable = true, columnDefinition="bigint")
    public BigInteger getCarTeamId() {
        return carTeamId;
    }

    public void setCarTeamId(BigInteger carTeamId) {
        this.carTeamId = carTeamId;
    }

    @Basic
    @Column(name = "CAR_STATE", nullable = true)
    public Integer getCarState() {
        return carState;
    }

    public void setCarState(Integer carState) {
        this.carState = carState;
    }

    @Basic
    @Column(name = "CAR_PW", nullable = true, length = 50)
    public String getCarPw() {
        return carPw;
    }

    public void setCarPw(String carPw) {
        this.carPw = carPw;
    }

    @Basic
    @Column(name = "CAR_ACCOUNT_NAME", nullable = true, length = 50)
    public String getCarAccountName() {
        return carAccountName;
    }

    public void setCarAccountName(String carAccountName) {
        this.carAccountName = carAccountName;
    }

    @Basic
    @Column(name = "CAR_AUTO_NUMBER", nullable = true, length = 50)
    public String getCarAutoNumber() {
        return carAutoNumber;
    }

    public void setCarAutoNumber(String carAutoNumber) {
        this.carAutoNumber = carAutoNumber;
    }

    @Basic
    @Column(name = "CAR_DATE", nullable = true, columnDefinition="bigint")
    public BigInteger getCarDate() {
        return carDate;
    }

    public void setCarDate(BigInteger carDate) {
        this.carDate = carDate;
    }

    @Basic
    @Column(name = "CAR_PLACE", nullable = true, length = 200)
    public String getCarPlace() {
        return carPlace;
    }

    public void setCarPlace(String carPlace) {
        this.carPlace = carPlace;
    }

    @Basic
    @Column(name = "CAR_COMPANY", nullable = true, length = 200)
    public String getCarCompany() {
        return carCompany;
    }

    public void setCarCompany(String carCompany) {
        this.carCompany = carCompany;
    }

    @Basic
    @Column(name = "DEL_FLAG", nullable = true)
    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    @Basic
    @Column(name = "CAR_TYPE", nullable = true)
    public Integer getCarType() {
        return carType;
    }

    public void setCarType(Integer carType) {
        this.carType = carType;
    }

    @Basic
    @Column(name = "CAR_TRADE", nullable = true)
    public Integer getCarTrade() {
        return carTrade;
    }

    public void setCarTrade(Integer carTrade) {
        this.carTrade = carTrade;
    }

    @Basic
    @Column(name = "CAR_SERVICE_STOP", nullable = true)
    public Integer getCarServiceStop() {
        return carServiceStop;
    }

    public void setCarServiceStop(Integer carServiceStop) {
        this.carServiceStop = carServiceStop;
    }

    @Basic
    @Column(name = "SERVICE_BEGIN", nullable = true, columnDefinition="bigint")
    public BigInteger getServiceBegin() {
        return serviceBegin;
    }

    public void setServiceBegin(BigInteger serviceBegin) {
        this.serviceBegin = serviceBegin;
    }

    @Basic
    @Column(name = "SERVICE_END", nullable = true, columnDefinition="bigint")
    public BigInteger getServiceEnd() {
        return serviceEnd;
    }

    public void setServiceEnd(BigInteger serviceEnd) {
        this.serviceEnd = serviceEnd;
    }

    @Basic
    @Column(name = "NETTING_TIME", nullable = true, columnDefinition="bigint")
    public BigInteger getNettingTime() {
        return nettingTime;
    }

    public void setNettingTime(BigInteger nettingTime) {
        this.nettingTime = nettingTime;
    }

    @Basic
    @Column(name = "NETTING_LOG", nullable = true, columnDefinition="bigint")
    public BigInteger getNettingLog() {
        return nettingLog;
    }

    public void setNettingLog(BigInteger nettingLog) {
        this.nettingLog = nettingLog;
    }

    @Basic
    @Column(name = "NETTING_LAT", nullable = true, columnDefinition="bigint")
    public BigInteger getNettingLat() {
        return nettingLat;
    }

    public void setNettingLat(BigInteger nettingLat) {
        this.nettingLat = nettingLat;
    }

    @Basic
    @Column(name = "CHASSIS_NUM", nullable = true, length = 100)
    public String getChassisNum() {
        return chassisNum;
    }

    public void setChassisNum(String chassisNum) {
        this.chassisNum = chassisNum;
    }

    @Basic
    @Column(name = "STRUCTURE_NUM", nullable = true, length = 100)
    public String getStructureNum() {
        return structureNum;
    }

    public void setStructureNum(String structureNum) {
        this.structureNum = structureNum;
    }

    @Basic
    @Column(name = "OIL_CAPACITY", nullable = true, length = 100)
    public String getOilCapacity() {
        return oilCapacity;
    }

    public void setOilCapacity(String oilCapacity) {
        this.oilCapacity = oilCapacity;
    }

    @Basic
    @Column(name = "LOCK_STAUTS", nullable = true, columnDefinition="int")
    public Integer getLockStauts() {
        return lockStauts==null?0:lockStauts;
    }

    public void setLockStauts(Integer lockStauts) {
        this.lockStauts = lockStauts;
    }

    @Basic
    @Column(name = "CAR_TERMINAL_ID", nullable = true, columnDefinition="bigint")
    public BigInteger getCarTerminalId() {
        return carTerminalId;
    }

    public void setCarTerminalId(BigInteger carTerminalId) {
        this.carTerminalId = carTerminalId;
    }

    @Basic
    @Column(name = "AUTO_FLAG", nullable = true)
    public Integer getAutoFlag() {
        return autoFlag;
    }

    public void setAutoFlag(Integer autoFlag) {
        this.autoFlag = autoFlag;
    }

    @Basic
    @Column(name = "TAMPER_STATUE", nullable = true)
    public Integer getTamperStatue() {
        return tamperStatue;
    }

    public void setTamperStatue(Integer tamperStatue) {
        this.tamperStatue = tamperStatue;
    }

    @Basic
    @Column(name = "OPERATE_COMMON", nullable = true, length = 200)
    public String getOperateCommon() {
        return operateCommon;
    }

    public void setOperateCommon(String operateCommon) {
        this.operateCommon = operateCommon;
    }

    @Basic
    @Column(name = "OPERATE_USER", nullable = true, length = 200)
    public String getOperateUser() {
        return operateUser;
    }

    public void setOperateUser(String operateUser) {
        this.operateUser = operateUser;
    }

    @Basic
    @Column(name = "OPERATE_DATE", nullable = true, columnDefinition="bigint")
    public BigInteger getOperateDate() {
        return operateDate;
    }

    public void setOperateDate(BigInteger operateDate) {
        this.operateDate = operateDate;
    }

    @Basic
    @Column(name = "tamper_notice_status", nullable = true)
    public Integer getTamperNoticeStatus() {
        return tamperNoticeStatus;
    }

    public void setTamperNoticeStatus(Integer tamperNoticeStatus) {
        this.tamperNoticeStatus = tamperNoticeStatus;
    }

    @Basic
    @Column(name = "OFFLINE_TIME", nullable = true, columnDefinition="bigint")
    public BigInteger getOfflineTime() {
        return offlineTime;
    }

    public void setOfflineTime(BigInteger offlineTime) {
        this.offlineTime = offlineTime;
    }

    @Basic
    @Column(name = "REMOVAL_TIME", nullable = true, columnDefinition="bigint")
    public BigInteger getRemovalTime() {
        return removalTime;
    }

    public void setRemovalTime(BigInteger removalTime) {
        this.removalTime = removalTime;
    }

    @Basic
    @Column(name = "REGISTER_TIME", nullable = true, columnDefinition="bigint")
    public BigInteger getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(BigInteger registerTime) {
        this.registerTime = registerTime;
    }

    @Basic
    @Column(name = "OPERATE_IP", nullable = true, length = 100)
    public String getOperateIp() {
        return operateIp;
    }

    public void setOperateIp(String operateIp) {
        this.operateIp = operateIp;
    }

    @Basic
    @Column(name = "CAR_FKDATE", nullable = true, columnDefinition="bigint")
    public BigInteger getCarFkdate() {
        return carFkdate;
    }

    public void setCarFkdate(BigInteger carFkdate) {
        this.carFkdate = carFkdate;
    }

    @Basic
    @Column(name = "BATTERY_TYPE", nullable = true)
    public Integer getBatteryType() {
        return batteryType;
    }

    public void setBatteryType(Integer batteryType) {
        this.batteryType = batteryType;
    }

    @Basic
    @Column(name = "BATTERY_BATCHES", nullable = true)
    public Integer getBatteryBatches() {
        return batteryBatches;
    }

    public void setBatteryBatches(Integer batteryBatches) {
        this.batteryBatches = batteryBatches;
    }

    @Basic
    @Column(name = "car_model_code", nullable = true, length = 20)
    public String getCarModelCode() {
        return carModelCode;
    }

    public void setCarModelCode(String carModelCode) {
        this.carModelCode = carModelCode;
    }

    @Basic
    @Column(name = "online_time", nullable = true, columnDefinition="bigint")
    public BigInteger getOnlineTime() {
        return onlineTime;
    }

    public void setOnlineTime(BigInteger onlineTime) {
        this.onlineTime = onlineTime;
    }

    @Basic
    @Column(name = "CAR_MODEL", nullable = true, length = 100)
    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    @Basic
    @Column(name = "WAREHOUSE_LOG", nullable = true, columnDefinition="bigint")
    public BigInteger getWarehouseLog() {
        return warehouseLog;
    }

    public void setWarehouseLog(BigInteger warehouseLog) {
        this.warehouseLog = warehouseLog;
    }

    @Basic
    @Column(name = "WAREHOUSE_LAT", nullable = true, columnDefinition="bigint")
    public BigInteger getWarehouseLat() {
        return warehouseLat;
    }

    public void setWarehouseLat(BigInteger warehouseLat) {
        this.warehouseLat = warehouseLat;
    }

    @Basic
    @Column(name = "WAREHOUSE_TIME", nullable = true, columnDefinition="bigint")
    public BigInteger getWarehouseTime() {
        return warehouseTime;
    }

    public void setWarehouseTime(BigInteger warehouseTime) {
        this.warehouseTime = warehouseTime;
    }

    @Basic
    @Column(name = "order_number", nullable = true, length = 60)
    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    @Basic
    @Column(name = "sync_time", nullable = true)
    public Timestamp getSyncTime() {
        return syncTime;
    }

    public void setSyncTime(Timestamp syncTime) {
        this.syncTime = syncTime;
    }

    @Basic
    @Column(name = "lock_method", nullable = true, length = 20)
    public String getLockMethod() {
        return lockMethod;
    }

    public void setLockMethod(String lockMethod) {
        this.lockMethod = lockMethod;
    }

    @Basic
    @Column(name = "qr_code", nullable = true, length = 50)
    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    @Basic
    @Column(name = "financing_company", nullable = true, columnDefinition="bigint")
    public BigInteger getFinancingCompany() {
        return financingCompany;
    }

    public void setFinancingCompany(BigInteger financingCompany) {
        this.financingCompany = financingCompany;
    }

    @Basic
    @Column(name = "pay_type", nullable = true, columnDefinition="bigint")
    public BigInteger getPayType() {
        return payType;
    }

    public void setPayType(BigInteger payType) {
        this.payType = payType;
    }

    @Basic
    @Column(name = "CREATE_ACCOUNT_ID", nullable = true, columnDefinition="bigint")
    public BigInteger getCreateAccountId() {
        return createAccountId;
    }

    public void setCreateAccountId(BigInteger createAccountId) {
        this.createAccountId = createAccountId;
    }



}
