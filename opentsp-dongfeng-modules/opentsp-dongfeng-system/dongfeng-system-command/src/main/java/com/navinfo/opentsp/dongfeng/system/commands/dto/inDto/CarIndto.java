package com.navinfo.opentsp.dongfeng.system.commands.dto.inDto;

import com.navinfo.opentsp.dongfeng.common.validation.group.GroupCommand;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.math.BigInteger;

/**
 * @author Administrator
 *
 */
/**
 * @author Administrator
 *
 */
public class CarIndto {
	
	private BigInteger id;
	/**
	 * 车辆颜色
	 */
	private Integer color;
	/**
	 * 底盘号
	 */
    @NotNull(message = "底盘号(chassisNum)不能为空", groups = {GroupCommand.class})
    @NotBlank(message = "底盘号(chassisNum)不能为空", groups = {GroupCommand.class})
	private String chassisNum;
    
    private BigInteger terminalId;
    
    /**
     * 逻辑删除
     */
    private Integer delFlag;
	
	/**
	 * 车牌号
	 */
//    @NotNull(message = "carCph不能为空", groups = {GroupCommand.class})
//    @NotBlank(message = "carCph不能为空", groups = {GroupCommand.class})
	private String carCph;
    
    /**
     * 车辆类型
     */
    @NotNull(message = "车辆类型(carType)不能为空", groups = {GroupCommand.class})
    private Integer carType;
    /**
	 * 首次上线时间
	 */
	private String nettingDate;
	
	/**
	 * 注册时间
	 */
	private String registerDate;
	
	/**
	 * 结构号
	 */
//    @NotNull(message = "车架号(structureNum)不能为空", groups = {GroupCommand.class})
//    @NotBlank(message = "车架号(structureNum)不能为空", groups = {GroupCommand.class})
	private String structureNum;
	
	/**
	 * 邮箱容量
	 */
//    @NotNull(message = "邮箱容量(oilCapacity)不能为空", groups = {GroupCommand.class})
//    @NotBlank(message = "邮箱容量(oilCapacity)不能为空", groups = {GroupCommand.class})
	private String oilCapacity;
	
	/**
	 * 出库时间
	 */
	private String removeTime;
	
	/**
	 * 电池类型
	 */
	private Integer batteryType;
	
	/**
	 * 电池批次
	 */
	private Integer batteryBatch;
	
	/**
	 * 
	 */
	private BigInteger payType;
	
	/**
	 * 录入方式
	 */
	private int autoFlag = 9101;
	
	/**
	 * 整车二维码
	 */
	private String qrCode;
	
	/**
	 * 车型吗
	 */
	private String carModelCode;
	
	/**
	 * 车辆VIN
	 */
	private String vechicleVin;
	
	/**
	 * 下线时间
	 */
	private String offlineTime;
	
	/**
	 * 车牌颜色
	 */
	private Integer cphColor;
	
	/**
	 * 终端类型
	 */
	private Integer terminalType;
	
	/**
	 * 安装类型
	 */
	private Integer instalType;
	
	/**
	 * FK控制器
	 */
	private BigInteger fkCode;
	
	private BigInteger assembleTime;
	
	/**
	 * 正确的下线时间
	 */
	private String online;
	
	/**
	 * 载重类型
	 */
	private Integer zzType;
	
	/**
	 * 正确载重类型 
	 */
	private String vfactory;
	
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

	public String getCarCph() {
		return carCph;
	}

	public void setCarCph(String carCph) {
		this.carCph = carCph;
	}

	public String getNettingDate() {
		return nettingDate;
	}

	public void setNettingDate(String nettingDate) {
		this.nettingDate = nettingDate;
	}

	public String getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(String registerDate) {
		this.registerDate = registerDate;
	}

	public Integer getCarType() {
		return carType;
	}

	public void setCarType(Integer carType) {
		this.carType = carType;
	}

	public String getStructureNum() {
		return structureNum;
	}

	public void setStructureNum(String structureNum) {
		this.structureNum = structureNum;
	}

	public String getOilCapacity() {
		return oilCapacity;
	}

	public void setOilCapacity(String oilCapacity) {
		this.oilCapacity = oilCapacity;
	}

	public String getRemoveTime() {
		return removeTime;
	}

	public void setRemoveTime(String removeTime) {
		this.removeTime = removeTime;
	}

	public Integer getBatteryType() {
		return batteryType;
	}

	public void setBatteryType(Integer batteryType) {
		this.batteryType = batteryType;
	}

	public Integer getBatteryBatch() {
		return batteryBatch;
	}

	public void setBatteryBatch(Integer batteryBatch) {
		this.batteryBatch = batteryBatch;
	}

	public Integer getColor() {
		return color;
	}

	public void setColor(Integer color) {
		this.color = color;
	}

	public BigInteger getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(BigInteger terminalId) {
		this.terminalId = terminalId;
	}

	public BigInteger getPayType() {
		return payType;
	}

	public void setPayType(BigInteger payType) {
		this.payType = payType;
	}

	public int getAutoFlag() {
		return autoFlag;
	}

	public void setAutoFlag(int autoFlag) {
		this.autoFlag = autoFlag;
	}

	public String getQrCode() {
		return qrCode;
	}

	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}

	public String getCarModelCode() {
		return carModelCode;
	}

	public void setCarModelCode(String carModelCode) {
		this.carModelCode = carModelCode;
	}

	public String getOfflineTime() {
		return offlineTime;
	}

	public void setOfflineTime(String offlineTime) {
		this.offlineTime = offlineTime;
	}

	public String getVechicleVin() {
		return vechicleVin;
	}

	public void setVechicleVin(String vechicleVin) {
		this.vechicleVin = vechicleVin;
	}

	public Integer getCphColor() {
		return cphColor;
	}

	public void setCphColor(Integer cphColor) {
		this.cphColor = cphColor;
	}

	public Integer getTerminalType() {
		return terminalType;
	}

	public void setTerminalType(Integer terminalType) {
		this.terminalType = terminalType;
	}

	public Integer getInstalType() {
		return instalType;
	}

	public void setInstalType(Integer instalType) {
		this.instalType = instalType;
	}

	public Integer getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}

	public BigInteger getFkCode() {
		return fkCode;
	}

	public void setFkCode(BigInteger fkCode) {
		this.fkCode = fkCode;
	}

	public BigInteger getAssembleTime() {
		return assembleTime;
	}

	public void setAssembleTime(BigInteger assembleTime) {
		this.assembleTime = assembleTime;
	}

	public Integer getZzType() {
		return zzType;
	}

	public void setZzType(Integer zzType) {
		this.zzType = zzType;
	}

	public String getOnline() {
		return online;
	}

	public void setOnline(String online) {
		this.online = online;
	}

	public String getVfactory() {
		return vfactory;
	}

	public void setVfactory(String vfactory) {
		this.vfactory = vfactory;
	}
	
}
