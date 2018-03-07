package com.navinfo.opentsp.dongfeng.system.commands.dto.outDto;

import com.navinfo.opentsp.dongfeng.common.annotation.Report;
import com.navinfo.opentsp.dongfeng.common.util.ColumnStyle;
import com.navinfo.opentsp.dongfeng.common.util.FormatStyle;
import com.navinfo.opentsp.dongfeng.common.util.TimePrecision;
import org.springframework.util.StringUtils;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class VechicleOutdto {
	
	/**
	 * 车辆ID
	 */
	private BigInteger carId;
	
	/**
	 * 底盘号
	 */
	@Report(columnNum=1 , header="底盘号")
	private String chassisNum;
	
	/**
	 * 车牌号
	 */
	@Report(columnNum=2 , header="车牌号")
	private String cph;
	
	/**
	 * 注册时间
	 */
	@Report(columnNum=24 , header="注册时间" , style=ColumnStyle.DATE , precision=TimePrecision.SECOND)
	private BigInteger registerTimeStr;
	
	/**
	 * 一体机CODE
	 */
	@Report(columnNum=10 , header="北斗一体机ID")
	private String terminalCode;
	
	/**
	 * 一体机SIM
	 */
	@Report(columnNum=11 , header="北斗一体机SIM卡")
	private String terminalSim;
	
	/**
	 * 控制器CODE
	 */
	@Report(columnNum=12 , header="FK控制器ID")
	private String fkCode;
	
	/**
	 * FK控制器SIM
	 */
	@Report(columnNum=13 , header="FK控制器SIM卡")
	private String fkSim;
	
	private String communicationId;
	
	/**
	 * 经销商名称
	 */
	@Report(columnNum=14 , header="所属经销商")
	private String companyName;
	
	/**
	 * 
	 */
	@Report(columnNum=15,header="经销商代码")
	private String dealerCode;
	
	@Report(columnNum=16 , header="所属客户")
	private String businessName;
	
	@Report(columnNum=19 , header="车辆型号")
	private String car_TYPE_VALUE;
	
	@Report(columnNum=21 , header="发动机类型")
	private String engine_TYPE_VALUE;
	
//	private String ENGINE_NUMBER;

	@Report(columnNum=26 , header="STD销售状态")
	private String salesStatusValue;
	
	@Report(columnNum=25 , header="STD销售时间" , style=ColumnStyle.DATE , precision=TimePrecision.SECOND)
	private BigInteger salesDateStr;
	
	@Report(columnNum=29 , header="录入方式")
	private String autoFlagStr;
	
	/**
	 * 平台录入方式编码
	 */
	private Integer autoFlag;
	
	@Report(columnNum=30 , header="出库时间" , style=ColumnStyle.DATE , precision=TimePrecision.SECOND)
	private BigInteger removalTimeStr;
	
	@Report(columnNum=31 , header="首次有效时间" , style=ColumnStyle.DATE , precision=TimePrecision.SECOND , format=FormatStyle.TIME)
	private BigInteger firstGpsTimeStr;
	
	private String firstGpsLat;
	
	private String firstGpsLng;
	
	@Report(columnNum=18 , header="发票号")
	private String invoiceNumber;
	
	@Report(columnNum=17 , header="客户证件号")
	private String businessCode;
	
	@Report(columnNum=34 , header="末次有效位置")
	private String addressDetail;
	
	/**
	 * 首次有效位置
	 */
	@Report(columnNum=32,header="首次有效位置")
	private String firstAvalidPosition;
	
	/**
	 * 北斗一体机ID
	 */
	private BigInteger tId;
	
	private BigInteger dealerId;
	
	/**
	 * 控制器ID
	 */
	private BigInteger controllerId;
	
	/**
	 * 车辆类型ID
	 */
	private Integer carType;
	
	/**
	 * 发动机号
	 */
	private String engineNumber;
	
	/**
	 * 销售状态ID
	 */
	private Integer saleSatusId;
	
	@Report(columnNum=3 , header="车架号(改码前)")
	private String sturctureNum;
	
	/**
	 * 车牌颜色
	 */
	private Integer color;
	
	/**
	 * 客户ID
	 */
	private BigInteger cid;
	
	/**
	 * 发动机类型
	 */
	private String engineType;
	
	/**
	 * 图片路径
	 */
	private String img_path;
	
	/**
	 * 油箱容量
	 */
	private String oilCapacity;
	
	/**
	 * 购车总价
	 */
	private Double carAmount;
	
	/**
	 * 总贷款数
	 */
	private Double loanAmount;
	
	/**
	 * 末次纬度
	 */
	private String lastGpsLat;
	
	/**
	 * 末次经度
	 */
	private String lastGpsLon;
	
	/**
	 * 末次时间
	 */
	@Report(columnNum=33 , header="末次有效时间" , style=ColumnStyle.DATE , precision=TimePrecision.MILLISECOND , format=FormatStyle.TIME)
	private long lastGpsDate;
	
	/**
	 * 剩余未还款
	 */
	private Double surplus;
	
	/**
	 * 安装类型
	 */
	private BigInteger instalType;
	
	/**
	 * 安装类型说明
	 */
	@Report(columnNum=6 , header="安装类型")
	private String instalTypeDesc;
	
	/**
	 * 安装单位ID
	 */
	private BigInteger createStationId;
	
	/**
	 * 安装单位名称 
	 */
	@Report(columnNum=7 , header="安装单位")
	private String stationName;
	
	/**
	 * 下线时间
	 */
	private BigInteger offlineTime;
	
	/**
	 * 安装时间
	 */
//	@Report(columnNum=9 , header="安装时间" , style=ColumnStyle.DATE , precision=TimePrecision.MILLISECOND)
	private BigInteger assembleTime;
	
	/**
	 * AAK销售时间
	 */
	@Report(columnNum=27 , header="AAK销售日期" , style=ColumnStyle.DATE , precision=TimePrecision.SECOND)
	private BigInteger mbSalesDate;
	
	/**
	 * AAK销售状态描述
	 */
	@Report(columnNum=28 , header="AAK销售状态")
	private String aakStatusValue;
	
	/**
	 * AAK销售状态标识
	 */
	private Integer mbSalesStatus;
	
	/**
	 * 车型码
	 */
	@Report(columnNum=5 , header="车型码")
	private String carModelCode;
	
	/**
	 * 发动机型号
	 */
	@Report(columnNum=20 , header="发动机型号")
	private String engineTypeRear;
	
	/**
	 * 二维码
	 */
	@Report(columnNum=4 , header="二维码")
	private String qrCode;
	
	/**
	 * 额定功率
	 */
	private String edgl;
	
	/**
	 * 最大功率
	 */
	private String zdgl;
	
	/**
	 * 整备质量
	 */
	private String zbzl;
	
	/**
	 * 总质量
	 */
	private String zzl;
	
	/**
	 * 设置入网时间
	 */
	@Report(columnNum=9 , header="安装时间" , style=ColumnStyle.DATE , precision=TimePrecision.SECOND , format=FormatStyle.TIME)
	private BigInteger carDate;
	
	/**
	 * 动力类型
	 */
	private Integer fuel;
	/**
	 * 动力类型
	 */
	@Report(columnNum=23 , header="动力类型")
	private String fuelValue;

	
	/**
	 * 载重类型区分
	 */
	private Integer zztype;
	
	/**
	 * FK的通讯号
	 */
	private BigInteger fkAutoCommunication;
	
	/**
	 * FK永久通信id
	 */
	private BigInteger fkCommunication;
	
	/**
	 * 一体机通讯号
	 */
	private BigInteger autoCommunication;
	
	/**
	 * 一体机永久通信ID
	 */
	private BigInteger communication;
	
	/**
	 * 订单号
	 */
	private String orderNumber;
	
	/**
	 * 上线时间
	 */
	@Report(columnNum=8 , header="下线(制造)日期" , style=ColumnStyle.DATE , precision=TimePrecision.SECOND)
	private BigInteger onlineTime;
	
	/**
	 * 同步时间
	 */
	private Timestamp syncTime;
	
	/**
	 * 工厂代码
	 */
	@Report(columnNum=22 , header="工厂代码")
	private String vfactory;
	
	/**
	 * 改动前VIN
	 */
	private String vinOld;
	
	/**
	 * 改动前底盘号
	 */
	private String chassisOld;
	
	
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

	public String getCph() {
		return cph;
	}

	public void setCph(String cph) {
		this.cph = cph;
	}

	public String getRegisterTimeStr() {
		
		if (null != registerTimeStr && registerTimeStr.longValue() > 0) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return format.format(new Date(registerTimeStr.longValue() * 1000));
		}
		return "";
	}

	public void setRegisterTimeStr(BigInteger registerTimeStr) {
		this.registerTimeStr = registerTimeStr;
	}

	public String getTerminalCode() {
		return terminalCode;
	}

	public void setTerminalCode(String terminalCode) {
		this.terminalCode = terminalCode;
	}

	public String getTerminalSim() {
		return terminalSim;
	}

	public void setTerminalSim(String terminalSim) {
		this.terminalSim = terminalSim;
	}

	public String getFkCode() {
		return fkCode;
	}

	public void setFkCode(String fkCode) {
		this.fkCode = fkCode;
	}

	public String getFkSim() {
		return fkSim;
	}

	public void setFkSim(String fkSim) {
		this.fkSim = fkSim;
	}

	public String getCommunicationId() {
		return communicationId;
	}

	public void setCommunicationId(String communicationId) {
		this.communicationId = communicationId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getDealerCode() {
		return dealerCode;
	}

	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public String getCar_TYPE_VALUE() {
		return car_TYPE_VALUE;
	}

	public void setCar_TYPE_VALUE(String car_TYPE_VALUE) {
		this.car_TYPE_VALUE = car_TYPE_VALUE;
	}
	
	public String getSalesStatusValue() {
		return salesStatusValue;
	}

	public String getEngine_TYPE_VALUE() {
		return engine_TYPE_VALUE;
	}

	public void setEngine_TYPE_VALUE(String engine_TYPE_VALUE) {
		this.engine_TYPE_VALUE = engine_TYPE_VALUE;
	}

	public void setSalesStatusValue(String salesStatusValue) {
		this.salesStatusValue = salesStatusValue;
	}

	public String getSalesDateStr() {
		
		if (null != salesDateStr && salesDateStr.longValue() > 0) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return format.format(new Date(salesDateStr.longValue()*1000));
			
		}
		return "";
	}

	public void setSalesDateStr(BigInteger salesDateStr) {
		this.salesDateStr = salesDateStr;
	}

	public String getAutoFlagStr() {
		return autoFlagStr;
	}

	public void setAutoFlagStr(String autoFlagStr) {
		this.autoFlagStr = autoFlagStr;
	}

	public String getRemovalTimeStr() {
		
		if (null != removalTimeStr && removalTimeStr.longValue() > 0) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return format.format(new Date(removalTimeStr.longValue()*1000));	
		}
		
		return "";
	}

	public void setRemovalTimeStr(BigInteger removalTimeStr) {
		this.removalTimeStr = removalTimeStr;
	}

	public String getFirstGpsTimeStr() {
		if (null != firstGpsTimeStr && firstGpsTimeStr.longValue() > 0) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return format.format(new Date(firstGpsTimeStr.longValue()*1000));
		}
		
		return "";
	}

	public void setFirstGpsTimeStr(BigInteger firstGpsTimeStr) {
		this.firstGpsTimeStr = firstGpsTimeStr;
	}

	public String getFirstGpsLat() {
		return firstGpsLat;
	}

	public void setFirstGpsLat(String firstGpsLat) {
		this.firstGpsLat = firstGpsLat;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public String getBusinessCode() {
		return businessCode;
	}

	public void setBusinessCode(String businessCode) {
		this.businessCode = businessCode;
	}

	public String getFirstGpsLng() {
		return firstGpsLng;
	}

	public void setFirstGpsLng(String firstGpsLng) {
		this.firstGpsLng = firstGpsLng;
	}

	public BigInteger gettId() {
		return tId;
	}

	public void settId(BigInteger tId) {
		this.tId = tId;
	}

	public BigInteger getDealerId() {
		return dealerId;
	}

	public void setDealerId(BigInteger dealerId) {
		this.dealerId = dealerId;
	}

	public BigInteger getControllerId() {
		return controllerId;
	}

	public void setControllerId(BigInteger controllerId) {
		this.controllerId = controllerId;
	}

	public Integer getCarType() {
		return carType;
	}

	public void setCarType(Integer carType) {
		this.carType = carType;
	}

	public String getEngineNumber() {
		return engineNumber;
	}

	public void setEngineNumber(String engineNumber) {
		this.engineNumber = engineNumber;
	}

	public String getSturctureNum() {
		return sturctureNum;
	}

	public void setSturctureNum(String sturctureNum) {
		this.sturctureNum = sturctureNum;
	}

	public Integer getSaleSatusId() {
		return saleSatusId;
	}

	public void setSaleSatusId(Integer saleSatusId) {
		this.saleSatusId = saleSatusId;
	}

	public Integer getColor() {
		return color;
	}

	public void setColor(Integer color) {
		this.color = color;
	}

	public BigInteger getCid() {
		return cid;
	}

	public void setCid(BigInteger cid) {
		this.cid = cid;
	}

	public String getEngineType() {
		return engineType;
	}

	public void setEngineType(String engineType) {
		this.engineType = engineType;
	}

	public String getImg_path() {
		return img_path;
	}

	public void setImg_path(String img_path) {
		this.img_path = img_path;
	}

	public String getOilCapacity() {
		return oilCapacity;
	}

	public void setOilCapacity(String oilCapacity) {
		this.oilCapacity = oilCapacity;
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

	public String getLastGpsLat() {
		return lastGpsLat;
	}

	public void setLastGpsLat(String lastGpsLat) {
		this.lastGpsLat = lastGpsLat;
	}

	public String getLastGpsLon() {
		return lastGpsLon;
	}
	public void setLastGpsLon(String lastGpsLon) {
		this.lastGpsLon = lastGpsLon;
	}

	public String getLastGpsDate() {
		
		if (lastGpsDate > 0) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return format.format(new Date(lastGpsDate));
		}
		
		return "";
	}

	public void setLastGpsDate(long lastGpsDate) {
		this.lastGpsDate = lastGpsDate*1000;
	}

	public String getAddressDetail() {
		return addressDetail;
	}

	public void setAddressDetail(String addressDetail) {
		this.addressDetail = addressDetail;
	}

	public BigInteger getInstalType() {
		return instalType;
	}

	public void setInstalType(BigInteger instalType) {
		this.instalType = instalType;
	}

	public BigInteger getCreateStationId() {
		return createStationId;
	}

	public void setCreateStationId(BigInteger createStationId) {
		this.createStationId = createStationId;
	}

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public String getOfflineTime() {
		if (null != offlineTime && offlineTime.longValue() > 0) {
			
			SimpleDateFormat offLineFormat = new SimpleDateFormat("yyyy-MM-dd");
			return offLineFormat.format(new Date(offlineTime.longValue() * 1000));
		}
		return "";
	}

	public void setOfflineTime(BigInteger offlineTime) {
		this.offlineTime = offlineTime;
	}

	public String getAssembleTime() {
		if (null != assembleTime && assembleTime.longValue() > 0) {
			SimpleDateFormat assembleTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return assembleTimeFormat.format(new Date(assembleTime.longValue()));
		}
		return "";
	}

	public void setAssembleTime(BigInteger assembleTime) {
		this.assembleTime = assembleTime;
	}

	public String getMbSalesDate() {
		if (null != mbSalesDate && mbSalesDate.longValue() > 0) {
			SimpleDateFormat mbSaleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			return mbSaleDateFormat.format(new Date(mbSalesDate.longValue()*1000));
		}
		return "";
	}

	public void setMbSaleDate(BigInteger mbSaleDate) {
		this.mbSalesDate = mbSaleDate;
	}

	public String getAakStatusValue() {
		return aakStatusValue;
	}

	public void setAakStatusValue(String aakStatusValue) {
		this.aakStatusValue = aakStatusValue;
	}

	public String getInstalTypeDesc() {
		return instalTypeDesc;
	}

	public void setInstalTypeDesc(String instalTypeDesc) {
		this.instalTypeDesc = instalTypeDesc;
	}

	public Integer getMbSalesStatus() {
		return mbSalesStatus;
	}

	public void setMbSalesStatus(Integer mbSalesStatus) {
		this.mbSalesStatus = mbSalesStatus;
	}

	public String getCarModelCode() {
		return carModelCode;
	}

	public void setCarModelCode(String carModelCode) {
		this.carModelCode = carModelCode;
	}

	public String getEngineTypeRear() {
		return engineTypeRear;
	}

	public void setEngineTypeRear(String engineTypeRear) {
		this.engineTypeRear = engineTypeRear;
	}

	public String getQrCode() {
		return qrCode;
	}

	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}

	public String getEdgl() {
		return edgl;
	}

	public void setEdgl(String edgl) {
		this.edgl = edgl;
	}

	public String getZdgl() {
		return zdgl;
	}

	public void setZdgl(String zdgl) {
		this.zdgl = zdgl;
	}

	public String getZbzl() {
		return zbzl;
	}

	public void setZbzl(String zbzl) {
		this.zbzl = zbzl;
	}

	public String getZzl() {
		return zzl;
	}

	public void setZzl(String zzl) {
		this.zzl = zzl;
	}

	public String getCarDate() {
		
		if (null != carDate && carDate.longValue() > 0) {
			
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			return format.format(new Date(this.carDate.longValue() * 1000));
		}
		return "";
	}

	public void setCarDate(BigInteger carDate) {
		this.carDate = carDate;
	}

	public Integer getFuel() {
		return fuel;
	}

	public void setFuel(Integer fuel) {
		this.fuel = fuel;
	}

	public String getFuelValue() {
		return fuelValue;
	}

	public void setFuelValue(String fuelValue) {
		this.fuelValue = fuelValue;
	}

	public Integer getZztype() {
		return zztype;
	}

	public void setZztype(Integer zztype) {
		this.zztype = zztype;
	}

	public Integer getAutoFlag() {
		return autoFlag;
	}

	public void setAutoFlag(Integer autoFlag) {
		this.autoFlag = autoFlag;
	}

	public BigInteger getFkAutoCommunication() {
		return fkAutoCommunication;
	}

	public void setFkAutoCommunication(BigInteger fkAutoCommunication) {
		this.fkAutoCommunication = fkAutoCommunication;
	}

	public BigInteger getFkCommunication() {
		return fkCommunication;
	}

	public void setFkCommunication(BigInteger fkCommunication) {
		this.fkCommunication = fkCommunication;
	}

	public BigInteger getAutoCommunication() {
		return autoCommunication;
	}

	public void setAutoCommunication(BigInteger autoCommunication) {
		this.autoCommunication = autoCommunication;
	}

	public BigInteger getCommunication() {
		return communication;
	}

	public void setCommunication(BigInteger communication) {
		this.communication = communication;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getOnlineTime() {
		
		if (null != onlineTime && onlineTime.longValue() > 0) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			return format.format(new Date(this.onlineTime.longValue() * 1000));
		}
		return "";
	}

	public void setOnlineTime(BigInteger onlineTime) {
		this.onlineTime = onlineTime;
	}

	public Timestamp getSyncTime() {
		return syncTime;
	}

	public void setSyncTime(Timestamp syncTime) {
		this.syncTime = syncTime;
	}

	public String getVfactory() {
		return vfactory;
	}

	public void setVfactory(String vfactory) {
		this.vfactory = vfactory;
	}

	public String getVinOld() {
		
		if (!StringUtils.isEmpty(vinOld)) {
			return vinOld;
		}
		return "";
	}

	public void setVinOld(String vinOld) {
		this.vinOld = vinOld;
	}

	public String getChassisOld() {
		
		if (!StringUtils.isEmpty(chassisOld)) {
			return chassisOld;
		}
		return "";
	}

	public void setChassisOld(String chassisOld) {
		this.chassisOld = chassisOld;
	}

	public String getFirstAvalidPosition() {
		return firstAvalidPosition;
	}

	public void setFirstAvalidPosition(String firstAvalidPosition) {
		this.firstAvalidPosition = firstAvalidPosition;
	}
	
}
