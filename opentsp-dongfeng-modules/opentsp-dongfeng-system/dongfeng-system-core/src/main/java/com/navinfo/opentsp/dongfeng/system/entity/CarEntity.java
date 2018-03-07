package com.navinfo.opentsp.dongfeng.system.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;

@Entity
@Table(name = "hy_car")
public class CarEntity implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 车辆ID
	 */
	private Long carId;

	/**
	 * 服务分区编码(内蒙：国标码或者自定义编码)
	 */
	private int districtId;

	/**
	 * 车牌号
	 */
	private String carCph;

	/**
	 * 车牌颜色（数据字典）
	 */
	private Integer carColor;

	/**
	 * 北斗一体机ID
	 */
	private Long terminalId;

	/**
	 * 经销商ID
	 */
	private Long dealerId;

	/**
	 * 服务状态（数据字典）
	 */
	private Integer saleStatus;

	/**
	 * 车辆查询密码(要设置默认值：888888；密文MD5加密字符串)
	 */
	private String carPw;

	/**
	 * 注册车辆账户
	 */
	private String carAccountName;

	/**
	 * 车辆自编号(库里都是空值)
	 */
	private String carAutoNumber;
	
	/**
	 * 入网时间
	 */
	private Long carDate;
	
	/**
	 * 车籍地
	 */
	private String carPlace;
	
	/**
	 * 车辆所属公司
	 */
	private String carCompany;
	
	/**
	 * 逻辑删除标记(1:删除，0：正常，default：0)
	 */
	private int delFlag;
	
	/**
	 * 车辆类型（数据字典）
	 */
	private Integer carType;
	
	/**
	 * 运输行业类型(数据字典)
	 */
	private Integer carTrade;
	
	/**
	 * 是否服务到期停止
	 */
	private Integer carServiceStop;
	
	/**
	 * 服务期开始时间
	 */
	private Long serviceBegin;
	
	/**
	 * 服务期结束时间
	 */
	private Long serviceEnd;
	
	/**
	 * 入网时间
	 */
	private Long nettingTime;
	
	/**
	 * 入网位置-经度
	 */
	private Long nettingLog;
	
	/**
	 * 入网位置-纬度
	 */
	private Long nettingLat;
	
	/**
	 * 底盘号
	 */
	private String chassisNum;
	
	/**
	 * 结构号
	 */
	private String structureNum;
	
	/**
	 * 油箱容量
	 */
	private String oilCapacity;
	
	/**
	 * 锁车状态：0（未激活未锁车00）1（未激活锁车01）2（激活未锁车10）3（激活锁车11）
	 */
	private Integer lockStatus = 0;
	
	/**
	 * FK控制器ID
	 */
	private Long controllerId;
	
	/**
	 * 1,平台录入，其他：DMS
	 */
	private Integer autoFlag;
	
	/**
	 * 防拆方案
	 */
	private Integer tamperStatue;
	
	/**
	 * 操作备注
	 */
	private String operateCommon;
	
	/**
	 * 操作人
	 */
	private String operateUser;
	
	/**
	 * 操作时间
	 */
	private Long operateDate;
	
	/**
	 * 位置云防拆通知状态
	 */
	private Integer tamperNoticeStatus;
	
	/**
	 * 下线时间
	 */
	private Long offlineTime;
	
	/**
	 * 出库时间
	 */
	private Long removalTime;
	
	/**
	 * 末次注册时间
	 */
	private Long registerTime;
	
	/**
	 * 操作人IP
	 */
	private String operaterIp;
	
	/**
	 * 防控时效
	 */
	private Long carFkdate;
	
	/**
	 * 电池类型
	 */
	private Integer batteryType;
	
	/**
	 * 电池批次
	 */
	private Integer batteryBatch;
	
	/**
	 * 车型码
	 */
	private String carModelCode;
	
	/**
	 * 上线时间
	 */
	private Long onlineTime;
	
	/**
	 * 车辆型号
	 */
	private String carModel;
	
	/**
	 * 入库位置-经度
	 */
	private Long wareHouseLog;
	
	/**
	 * 入库位置-纬度
	 */
	private Long wareHouseLat;
	
	/**
	 * 入库时间
	 */
	private Long wareHouseTime;
	
	/**
	 * 订单号
	 */
	private String orderNumber;
	
	/**
	 * 同步时间
	 */
	private Timestamp sync_time;
	
	/**
	 * 锁车方案
	 */
	private String lockMethod;
	
	/**
	 * 整车二维码
	 */
	private String qrCode;
	
	/**
	 * 所属金融公司
	 */
	private Long financingCompany;
	
	/**
	 * 付款方式
	 */
	private Long payType;
	
	/**
	 * 创建者ID
	 */
	private Long createrId;
	
	/**
	 * 安装类型（1、前装;2、后装）
	 */
	private BigInteger instalType = new BigInteger("1");
	
	/**
	 * 安装单位ID
	 */
	private Long stationId;
	
	/**
	 * 车辆在库状态
	 */
	private Integer state;
	
	/**
	 * 安装时间
	 */
//	private Long assembleTime;
	
	/**
	 * 载重类型
	 */
//	private Integer zzType;
	
	/**
	 * 正确载重类型
	 */
	private String vfactory;
	
	@Id
	@Column(name = "car_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getCarId() {
		return carId;
	}

	public void setCarId(Long carId) {
		this.carId = carId;
	}

	@Column(name = "DISTRICT_ID")
	public Integer getDistrictId() {
		return districtId;
	}

	public void setDistrictId(Integer districtId) {
		this.districtId = districtId;
	}

	@Column(name = "CAR_CPH")
	public String getCarCph() {
		return carCph;
	}

	public void setCarCph(String carCph) {
		this.carCph = carCph;
	}

	@Column(name = "CAR_COLOR")
	public Integer getCarColor() {
		return carColor;
	}

	public void setCarColor(Integer carColor) {
		this.carColor = carColor;
	}

	@Column(name = "CAR_TERMINAL")
	public Long getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(Long terminalId) {
		this.terminalId = terminalId;
	}

	@Column(name = "CAR_TEAM_ID")
	public Long getDealerId() {
		return dealerId;
	}

	public void setDealerId(Long dealerId) {
		this.dealerId = dealerId;
	}

	@Column(name = "CAR_STATE")
	public Integer getSaleStatus() {
		return saleStatus;
	}

	public void setSaleStatus(Integer saleStatus) {
		this.saleStatus = saleStatus;
	}

	@Column(name = "CAR_PW")
	public String getCarPw() {
		return carPw;
	}

	public void setCarPw(String carPw) {
		this.carPw = carPw;
	}

	@Column(name = "CAR_ACCOUNT_NAME")
	public String getCarAccountName() {
		return carAccountName;
	}

	public void setCarAccountName(String carAccountName) {
		this.carAccountName = carAccountName;
	}

	@Column(name = "CAR_AUTO_NUMBER")
	public String getCarAutoNumber() {
		return carAutoNumber;
	}

	public void setCarAutoNumber(String carAutoNumber) {
		this.carAutoNumber = carAutoNumber;
	}

	@Column(name = "CAR_DATE")
	public Long getCarDate() {
		return carDate;
	}

	public void setCarDate(Long carDate) {
		this.carDate = carDate;
	}

	@Column(name = "CAR_PLACE")
	public String getCarPlace() {
		return carPlace;
	}

	public void setCarPlace(String carPlace) {
		this.carPlace = carPlace;
	}

	@Column(name = "CAR_COMPANY")
	public String getCarCompany() {
		return carCompany;
	}

	public void setCarCompany(String carCompany) {
		this.carCompany = carCompany;
	}

	@Column(name = "DEL_FLAG")
	public Integer getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}

	@Column(name = "CAR_TYPE")
	public Integer getCarType() {
		return carType;
	}

	public void setCarType(Integer carType) {
		this.carType = carType;
	}

	@Column(name = "CAR_TRADE")
	public Integer getCarTrade() {
		return carTrade;
	}

	public void setCarTrade(Integer carTrade) {
		this.carTrade = carTrade;
	}

	@Column(name = "CAR_SERVICE_STOP")
	public Integer getCarServiceStop() {
		return carServiceStop;
	}

	public void setCarServiceStop(Integer carServiceStop) {
		this.carServiceStop = carServiceStop;
	}

	@Column(name = "SERVICE_BEGIN")
	public Long getServiceBegin() {
		return serviceBegin;
	}

	public void setServiceBegin(Long serviceBegin) {
		this.serviceBegin = serviceBegin;
	}

	@Column(name = "SERVICE_END")
	public Long getServiceEnd() {
		return serviceEnd;
	}

	public void setServiceEnd(Long serviceEnd) {
		this.serviceEnd = serviceEnd;
	}

	@Column(name = "NETTING_TIME")
	public Long getNettingTime() {
		return nettingTime;
	}

	public void setNettingTime(Long nettingTime) {
		this.nettingTime = nettingTime;
	}

	@Column(name = "NETTING_LOG")
	public Long getNettingLog() {
		return nettingLog;
	}

	public void setNettingLog(Long nettingLog) {
		this.nettingLog = nettingLog;
	}

	@Column(name = "NETTING_LAT")
	public Long getNettingLat() {
		return nettingLat;
	}

	public void setNettingLat(Long nettingLat) {
		this.nettingLat = nettingLat;
	}

	@Column(name = "CHASSIS_NUM")
	public String getChassisNum() {
		return chassisNum;
	}

	public void setChassisNum(String chassisNum) {
		this.chassisNum = chassisNum;
	}

	@Column(name = "STRUCTURE_NUM")
	public String getStructureNum() {
		return structureNum;
	}

	public void setStructureNum(String structureNum) {
		this.structureNum = structureNum;
	}

	@Column(name = "OIL_CAPACITY")
	public String getOilCapacity() {
		return oilCapacity;
	}

	public void setOilCapacity(String oilCapacity) {
		this.oilCapacity = oilCapacity;
	}

	@Column(name = "LOCK_STAUTS")
	public Integer getLockStatus() {
		return lockStatus;
	}

	public void setLockStatus(Integer lockStatus) {
		this.lockStatus = lockStatus;
	}

	@Column(name = "CAR_TERMINAL_ID")
	public Long getControllerId() {
		return controllerId;
	}

	public void setControllerId(Long controllerId) {
		this.controllerId = controllerId;
	}

	@Column(name = "AUTO_FLAG")
	public Integer getAutoFlag() {
		return autoFlag;
	}

	public void setAutoFlag(Integer autoFlag) {
		
		if (null == autoFlag) {
			this.autoFlag = 7101;
		}
		this.autoFlag = autoFlag;
	}

	@Column(name = "TAMPER_STATUE")
	public Integer getTamperStatue() {
		return tamperStatue;
	}

	public void setTamperStatue(Integer tamperStatue) {
		this.tamperStatue = tamperStatue;
	}

	@Column(name = "OPERATE_COMMON")
	public String getOperateCommon() {
		return operateCommon;
	}

	public void setOperateCommon(String operateCommon) {
		this.operateCommon = operateCommon;
	}

	@Column(name = "OPERATE_USER")
	public String getOperateUser() {
		return operateUser;
	}

	public void setOperateUser(String operateUser) {
		this.operateUser = operateUser;
	}

	@Column(name = "OPERATE_DATE")
	public Long getOperateDate() {
		return operateDate;
	}

	public void setOperateDate(Long operateDate) {
		this.operateDate = operateDate;
	}

	@Column(name = "tamper_notice_status")
	public Integer getTamperNoticeStatus() {
		return tamperNoticeStatus;
	}

	public void setTamperNoticeStatus(Integer tamperNoticeStatus) {
		this.tamperNoticeStatus = tamperNoticeStatus;
	}

	@Column(name = "OFFLINE_TIME")
	public Long getOfflineTime() {
		return offlineTime;
	}

	public void setOfflineTime(Long offlineTime) {
		this.offlineTime = offlineTime;
	}

	@Column(name = "REMOVAL_TIME")
	public Long getRemovalTime() {
		return removalTime;
	}

	public void setRemovalTime(Long removalTime) {
		this.removalTime = removalTime;
	}

	@Column(name = "REGISTER_TIME")
	public Long getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(Long registerTime) {
		this.registerTime = registerTime;
	}

	@Column(name = "OPERATE_IP")
	public String getOperaterIp() {
		return operaterIp;
	}

	public void setOperaterIp(String operaterIp) {
		this.operaterIp = operaterIp;
	}

	@Column(name = "CAR_FKDATE")
	public Long getCarFkdate() {
		return carFkdate;
	}

	public void setCarFkdate(Long carFkdate) {
		this.carFkdate = carFkdate;
	}

	@Column(name = "BATTERY_TYPE")
	public Integer getBatteryType() {
		return batteryType;
	}

	public void setBatteryType(Integer batteryType) {
		this.batteryType = batteryType;
	}

	@Column(name = "BATTERY_BATCHES")
	public Integer getBatteryBatch() {
		return batteryBatch;
	}

	public void setBatteryBatch(Integer batteryBatch) {
		this.batteryBatch = batteryBatch;
	}

	@Column(name = "car_model_code")
	public String getCarModelCode() {
		return carModelCode;
	}

	public void setCarModelCode(String carModelCode) {
		this.carModelCode = carModelCode;
	}

	@Column(name = "online_time")
	public Long getOnlineTime() {
		return onlineTime;
	}

	public void setOnlineTime(Long onlineTime) {
		this.onlineTime = onlineTime;
	}

	@Column(name = "CAR_MODEL")
	public String getCarModel() {
		return carModel;
	}

	public void setCarModel(String carModel) {
		this.carModel = carModel;
	}

	@Column(name = "WAREHOUSE_LOG")
	public Long getWareHouseLog() {
		return wareHouseLog;
	}

	public void setWareHouseLog(Long wareHouseLog) {
		this.wareHouseLog = wareHouseLog;
	}

	@Column(name = "WAREHOUSE_LAT")
	public Long getWareHouseLat() {
		return wareHouseLat;
	}

	public void setWareHouseLat(Long wareHouseLat) {
		this.wareHouseLat = wareHouseLat;
	}

	@Column(name = "WAREHOUSE_TIME")
	public Long getWareHouseTime() {
		return wareHouseTime;
	}

	public void setWareHouseTime(Long wareHouseTime) {
		this.wareHouseTime = wareHouseTime;
	}

	@Column(name = "order_number")
	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	@Column(name = "sync_time")
	public Timestamp getSync_time() {
		return sync_time;
	}

	public void setSync_time(Timestamp sync_time) {
		this.sync_time = sync_time;
	}

	@Column(name = "lock_method")
	public String getLockMethod() {
		return lockMethod;
	}

	public void setLockMethod(String lockMethod) {
		this.lockMethod = lockMethod;
	}

	@Column(name = "qr_code")
	public String getQrCode() {
		return qrCode;
	}

	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}

	@Column(name = "financing_company")
	public Long getFinancingCompany() {
		return financingCompany;
	}

	public void setFinancingCompany(Long financingCompany) {
		this.financingCompany = financingCompany;
	}

	@Column(name = "pay_type")
	public Long getPayType() {
		return payType;
	}

	public void setPayType(Long payType) {
		this.payType = payType;
	}

	@Column(name = "CREATE_ACCOUNT_ID")
	public Long getCreaterId() {
		return createrId;
	}

	public void setCreaterId(Long createrId) {
		this.createrId = createrId;
	}

	@Column(name="INSTAL_TYPE" , columnDefinition="bigint")
	public BigInteger getInstalType() {
		return instalType;
	}

	public void setInstalType(BigInteger instalType) {
		this.instalType = instalType;
	}

	@Column(name="CREATE_STATION_ID")
	public Long getStationId() {
		return stationId;
	}

	public void setStationId(Long stationId) {
		this.stationId = stationId;
	}
//
//	@Column(name="ASSEMBLE_TIME")
//	public Long getAssembleTime() {
//		return assembleTime;
//	}
//
//	public void setAssembleTime(Long assembleTime) {
//		this.assembleTime = assembleTime;
//	}

//	@Column(name="zztype")
//	public Integer getZzType() {
//		return zzType;
//	}
//
//	public void setZzType(Integer zzType) {
//		this.zzType = zzType;
//	}

	@Column(name="vfactory")
	public String getVfactory() {
		return vfactory;
	}

	public void setVfactory(String vfactory) {
		this.vfactory = vfactory;
	}

	@Column(name="STATE")
	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}
	
}
