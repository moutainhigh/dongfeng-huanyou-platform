package com.navinfo.opentsp.dongfeng.monitor.dto.risk;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * @author tushenghong
 * @version 1.0
 * @date 2017-05-05
 * @modify
 * @copyright Navi Tsp
 */
public class RiskRegionReportDto implements Serializable {

    private static final long serialVersionUID = -1230244925183687513L;
    private BigInteger carId;
    private String chassisNum;  // 底盘号
    private String structureNum;  // 结构号
    private String plate;// 挂车车牌号
    private BigInteger teamId; //经销商ID
    private String teamName; //经销商名称
    private String customer;//所属客户
    private String customerName;//所属客户
    private String stdSalesDate;//std销售日期
    private String stdSalesStatus;//std销售状态
    private Double totalLoan;//贷款总金额(万)
    private Double restLoan;//剩余未还款(万)
    private String lastLocation;//末次位置信息
    private String lastLocationStr;//末次位置信息
    private String lastLocationDateStr;//末次位置时间
    private String totalMileage;//当前里程
    private String monthMileage;//当月里程
    private Integer preventionTamperCode;//防拆方案
    private String preventionTamper;//防拆方案
    private Integer lockStatus;//防控状态值
    private String preventionStatus;//防控状态
    private String preventionWindows;//防控时限
    private String operateLog;//操作备注
    private String operateUser;//操作人
    private String operateIp;//操作人IP
    private String operateTime;//操作时间
    private BigInteger communicationId;//通讯号
    private String aakSalesDate;//aak销售日期
    private String aakSalesStatus;//aak销售状态
    public BigInteger getCarId() {
        return carId;
    }

    public void setCarId(BigInteger carId) {
        this.carId = carId;
    }

    public String getChassisNum() {
        return chassisNum;
    }

    public void setChassisNum(String chassisNum) {
        this.chassisNum = chassisNum;
    }

    public String getStructureNum() {
        return structureNum;
    }

    public void setStructureNum(String structureNum) {
        this.structureNum = structureNum;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public BigInteger getTeamId() {
        return teamId;
    }

    public void setTeamId(BigInteger teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }


    public Double getTotalLoan() {
        return totalLoan;
    }

    public void setTotalLoan(Double totalLoan) {
        this.totalLoan = totalLoan;
    }

    public Double getRestLoan() {
        return restLoan;
    }

    public void setRestLoan(Double restLoan) {
        this.restLoan = restLoan;
    }

    public String getLastLocation() {
        return lastLocation;
    }

    public void setLastLocation(String lastLocation) {
        this.lastLocation = lastLocation;
    }

    public String getLastLocationDateStr() {
        return lastLocationDateStr;
    }

    public void setLastLocationDateStr(String lastLocationDateStr) {
        this.lastLocationDateStr = lastLocationDateStr;
    }

    public String getTotalMileage() {
        return totalMileage;
    }

    public void setTotalMileage(String totalMileage) {
        this.totalMileage = totalMileage;
    }

    public String getMonthMileage() {
        return monthMileage;
    }

    public void setMonthMileage(String monthMileage) {
        this.monthMileage = monthMileage;
    }

    public Integer getPreventionTamperCode() {
        return preventionTamperCode;
    }

    public void setPreventionTamperCode(Integer preventionTamperCode) {
        this.preventionTamperCode = preventionTamperCode;
    }

    public String getPreventionTamper() {
        return preventionTamper;
    }

    public void setPreventionTamper(String preventionTamper) {
        this.preventionTamper = preventionTamper;
    }

    public Integer getLockStatus() {
        return lockStatus;
    }

    public void setLockStatus(Integer lockStatus) {
        this.lockStatus = lockStatus;
    }

    public String getPreventionStatus() {
        return preventionStatus;
    }

    public void setPreventionStatus(String preventionStatus) {
        this.preventionStatus = preventionStatus;
    }

    public String getOperateLog() {
        return operateLog;
    }

    public void setOperateLog(String operateLog) {
        this.operateLog = operateLog;
    }

    public String getOperateUser() {
        return operateUser;
    }

    public void setOperateUser(String operateUser) {
        this.operateUser = operateUser;
    }

    public String getOperateIp() {
        return operateIp;
    }

    public void setOperateIp(String operateIp) {
        this.operateIp = operateIp;
    }

    public String getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(String operateTime) {
        this.operateTime = operateTime;
    }

    public BigInteger getCommunicationId() {
        return communicationId;
    }

    public void setCommunicationId(BigInteger communicationId) {
        this.communicationId = communicationId;
    }

    public String getPreventionWindows() {
        return preventionWindows;
    }

    public void setPreventionWindows(String preventionWindows) {
        this.preventionWindows = preventionWindows;
    }

    public String getStdSalesDate() {
        return stdSalesDate;
    }

    public void setStdSalesDate(String stdSalesDate) {
        this.stdSalesDate = stdSalesDate;
    }

    public String getStdSalesStatus() {
        return stdSalesStatus;
    }

    public void setStdSalesStatus(String stdSalesStatus) {
        this.stdSalesStatus = stdSalesStatus;
    }

    public String getAakSalesDate() {
        return aakSalesDate;
    }

    public void setAakSalesDate(String aakSalesDate) {
        this.aakSalesDate = aakSalesDate;
    }

    public String getAakSalesStatus() {
        return aakSalesStatus;
    }

    public void setAakSalesStatus(String aakSalesStatus) {
        this.aakSalesStatus = aakSalesStatus;
    }

    public String getLastLocationStr() {
        return lastLocationStr;
    }

    public void setLastLocationStr(String lastLocationStr) {
        this.lastLocationStr = lastLocationStr;
    }
}
