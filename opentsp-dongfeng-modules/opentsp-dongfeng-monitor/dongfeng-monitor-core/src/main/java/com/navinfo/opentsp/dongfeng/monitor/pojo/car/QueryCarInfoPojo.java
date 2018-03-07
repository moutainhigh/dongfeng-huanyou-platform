package com.navinfo.opentsp.dongfeng.monitor.pojo.car;

import java.math.BigInteger;

/**
 * 车辆详情
 *
 * @wenya
 * @create 2017-03-13 17:58
 **/
public class QueryCarInfoPojo {
    private BigInteger id; //车辆id
    private String chassisNum; //底盘号
    private String plateNum; //车牌号
    private String dealer;//所属经销商
    private String fkTer;  //FK控制器code
    private String fkSim; //FK控制器SIM
    private BigInteger fkCommNum; //FK控制器通信号
    private String carType; //车辆类型
    private String engineType; //发动机类型
    private String engineNum; //发动机号
    private String vin; //车架号
    private String oilCapacity; //油箱容量(单位：L)
    private BigInteger removalTimeL; //出库时间
    private String stdSalesStatus;//STD销售状态
    private String aakSalesStatus;//AAK销售状态
    private BigInteger stdSalesTimeL; //STD销售日期
    private BigInteger aakSalesTimeL; //分渠（AAK）销售日期
    private Double carAmount; //购车总金额
    private Double loanAmount; //贷款总金额
    private Double surplus; //剩余未还款
    private String customer; //客户名称
    private String phone; //联系方式
    private BigInteger maintainTimeL;  //上次保养时间
    private String maintainPlace; //上次保养地点  查库获取
    private String driveForm; //驱动形式
    private String driveVender; //驱动厂家及型号
    private String enginePower; //发动机功率
    private String gearbox; //变速箱
    private String rearAxle; //后桥
    private String wheelbase; //轴距
    private String tyre; //轮胎
    private String gearRatio; //速比 暂无此数据
    private String other; //其他
    private Integer carStauts;//车辆在线状态
    private Long gpstimeVaildL;//有效时间
    private Integer lngVaildI;//有效经度
    private Integer latValidI; //有效纬度
    //天然气or柴油车：0表示燃气，1表示燃油
    private Integer fuel;
    private String vinOld; //老VIN
    private String chassisNumOld; //老底盘号


    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getChassisNum() {
        return chassisNum;
    }

    public void setChassisNum(String chassisNum) {
        this.chassisNum = chassisNum;
    }

    public String getPlateNum() {
        return plateNum;
    }

    public void setPlateNum(String plateNum) {
        this.plateNum = plateNum;
    }

    public String getFkTer() {
        return fkTer;
    }

    public void setFkTer(String fkTer) {
        this.fkTer = fkTer;
    }

    public String getFkSim() {
        return fkSim;
    }

    public void setFkSim(String fkSim) {
        this.fkSim = fkSim;
    }

    public BigInteger getFkCommNum() {
        return fkCommNum;
    }

    public void setFkCommNum(BigInteger fkCommNum) {
        this.fkCommNum = fkCommNum;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getEngineType() {
        return engineType;
    }

    public void setEngineType(String engineType) {
        this.engineType = engineType;
    }

    public String getEngineNum() {
        return engineNum;
    }

    public void setEngineNum(String engineNum) {
        this.engineNum = engineNum;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getOilCapacity() {
        return oilCapacity;
    }

    public void setOilCapacity(String oilCapacity) {
        this.oilCapacity = oilCapacity;
    }

    public String getStdSalesStatus() {
        return stdSalesStatus;
    }

    public void setStdSalesStatus(String stdSalesStatus) {
        this.stdSalesStatus = stdSalesStatus;
    }

    public String getAakSalesStatus() {
        return aakSalesStatus;
    }

    public void setAakSalesStatus(String aakSalesStatus) {
        this.aakSalesStatus = aakSalesStatus;
    }

    public BigInteger getStdSalesTimeL() {
        return stdSalesTimeL;
    }

    public void setStdSalesTimeL(BigInteger stdSalesTimeL) {
        this.stdSalesTimeL = stdSalesTimeL;
    }

    public BigInteger getAakSalesTimeL() {
        return aakSalesTimeL;
    }

    public void setAakSalesTimeL(BigInteger aakSalesTimeL) {
        this.aakSalesTimeL = aakSalesTimeL;
    }

    public Double getCarAmount() {
        return carAmount;
    }

    public void setCarAmount(Double carAmount) {
        this.carAmount = carAmount;
    }

    public Double getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(Double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public Double getSurplus() {
        return surplus;
    }

    public void setSurplus(Double surplus) {
        this.surplus = surplus;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMaintainPlace() {
        return maintainPlace;
    }

    public void setMaintainPlace(String maintainPlace) {
        this.maintainPlace = maintainPlace;
    }

    public String getDriveForm() {
        return driveForm;
    }

    public void setDriveForm(String driveForm) {
        this.driveForm = driveForm;
    }

    public String getDriveVender() {
        return driveVender;
    }

    public void setDriveVender(String driveVender) {
        this.driveVender = driveVender;
    }

    public String getEnginePower() {
        return enginePower;
    }

    public void setEnginePower(String enginePower) {
        this.enginePower = enginePower;
    }

    public String getGearbox() {
        return gearbox;
    }

    public void setGearbox(String gearbox) {
        this.gearbox = gearbox;
    }

    public String getRearAxle() {
        return rearAxle;
    }

    public void setRearAxle(String rearAxle) {
        this.rearAxle = rearAxle;
    }

    public String getWheelbase() {
        return wheelbase;
    }

    public void setWheelbase(String wheelbase) {
        this.wheelbase = wheelbase;
    }

    public String getTyre() {
        return tyre;
    }

    public void setTyre(String tyre) {
        this.tyre = tyre;
    }

    public String getGearRatio() {
        return gearRatio;
    }

    public void setGearRatio(String gearRatio) {
        this.gearRatio = gearRatio;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getDealer() {
        return dealer;
    }

    public void setDealer(String dealer) {
        this.dealer = dealer;
    }

    public Integer getCarStauts() {
        return carStauts;
    }

    public void setCarStauts(Integer carStauts) {
        this.carStauts = carStauts;
    }

    public BigInteger getRemovalTimeL() {
        return removalTimeL;
    }

    public void setRemovalTimeL(BigInteger removalTimeL) {
        this.removalTimeL = removalTimeL;
    }

    public BigInteger getMaintainTimeL() {
        return maintainTimeL;
    }

    public void setMaintainTimeL(BigInteger maintainTimeL) {
        this.maintainTimeL = maintainTimeL;
    }

    public Long getGpstimeVaildL() {
        return gpstimeVaildL;
    }

    public void setGpstimeVaildL(Long gpstimeVaildL) {
        this.gpstimeVaildL = gpstimeVaildL;
    }

    public Integer getLngVaildI() {
        return lngVaildI;
    }

    public void setLngVaildI(Integer lngVaildI) {
        this.lngVaildI = lngVaildI;
    }

    public Integer getLatValidI() {
        return latValidI;
    }

    public Integer getFuel() {
        return fuel;
    }

    public void setFuel(Integer fuel) {
        this.fuel = fuel;
    }

    public void setLatValidI(Integer latValidI) {
        this.latValidI = latValidI;
    }

    public String getVinOld() {
        return vinOld;
    }

    public void setVinOld(String vinOld) {
        this.vinOld = vinOld;
    }

    public String getChassisNumOld() {
        return chassisNumOld;
    }

    public void setChassisNumOld(String chassisNumOld) {
        this.chassisNumOld = chassisNumOld;
    }
}
