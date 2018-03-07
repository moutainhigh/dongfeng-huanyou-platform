package com.navinfo.dongfeng.terminal.comm.model.system.vehicle;



public class HyCar implements java.io.Serializable{

	private static final long serialVersionUID = 1L;

	private Long carId;

    private Integer districtId;

    private String carCph;

    private Integer carColor;

    private Long carTerminal;

    private Long carTeamId;

    private Integer carState;

    private String carPw;

    private String carAccountName;

    private String carAutoNumber;

    private Long carDate;

    private String carPlace;

    private String carCompany;

    private Integer delFlag;

    private Integer carType;

    private Integer carTrade;

    private Integer carServiceStop;

    private Long serviceBegin;

    private Long serviceEnd;

    private Long nettingTime;

    private Long nettingLog;

    private Long nettingLat;
    
    private String chassisNum;
    
    private String structureNum;
    
    private String oilCapacity;
    
    private Long carTerminalId;
    
    private Integer lockStauts;
    
    private Integer autoFlag;//录入方式
    
    private Integer tamperStatue;//防拆方案
    
    private String operateCommon;//操作备注
    
    private String operateUser;//操作人
    
    private String operateIp;//操作人IP
    
    private Long carFKDate;//防控时效
    
    private Long operateDate;//操作时间
    
    private Integer tamperNoticeStatus;//位置云防拆通知状态
    
    private Long offlineTime; //下线时间
    
    private Long removalTime; //出库时间
    
    private Long registerTime;//末次注册时间
    
    private Integer batteryType;//电池类型
    
    private Integer batteryBatches;//电池批次
    
    private Long warehouseLog;//未售车辆入库位置-经度
    
    private Long warehouseLat;//未售车辆入库位置-纬度
    
    private Long warehouseTime;//未售车辆入库时间
    
    private String carModel;//车辆型号
    
    private String lockMethod;//锁车方案
    
    public String getLockMethod() {
		return lockMethod;
	}

	public void setLockMethod(String lockMethod) {
		this.lockMethod = lockMethod;
	}

	public String getCarModel() {
		return carModel;
	}

	public void setCarModel(String carModel) {
		this.carModel = carModel;
	}

	public Long getWarehouseLog() {
		return warehouseLog;
	}

	public void setWarehouseLog(Long warehouseLog) {
		this.warehouseLog = warehouseLog;
	}

	public Long getWarehouseLat() {
		return warehouseLat;
	}

	public void setWarehouseLat(Long warehouseLat) {
		this.warehouseLat = warehouseLat;
	}

	public Long getWarehouseTime() {
		return warehouseTime;
	}

	public void setWarehouseTime(Long warehouseTime) {
		this.warehouseTime = warehouseTime;
	}

	public Long getCarFKDate() {
		return carFKDate;
	}

	public void setCarFKDate(Long carFKDate) {
		this.carFKDate = carFKDate;
	}

	public String getOperateIp() {
		return operateIp;
	}

	public void setOperateIp(String operateIp) {
		this.operateIp = operateIp;
	}



    
    
    public Integer getTamperNoticeStatus() {

		return tamperNoticeStatus;
	}

	public void setTamperNoticeStatus(Integer tamperNoticeStatus) {
		this.tamperNoticeStatus = tamperNoticeStatus;
	}

	//当月行驶里程
  	private String totalMilleage;
    
	public String getTotalMilleage() {
		return totalMilleage;
	}

	public void setTotalMilleage(String totalMilleage) {
		this.totalMilleage = totalMilleage;
	}

	public Integer getTamperStatue() {
		return tamperStatue;
	}

	public void setTamperStatue(Integer tamperStatue) {
		this.tamperStatue = tamperStatue;
	}

	public String getOperateCommon() {
		return operateCommon;
	}

	public void setOperateCommon(String operateCommon) {
		this.operateCommon = operateCommon;
	}

	public String getOperateUser() {
		return operateUser;
	}

	public void setOperateUser(String operateUser) {
		this.operateUser = operateUser;
	}

	public Long getOperateDate() {
		return operateDate;
	}

	public void setOperateDate(Long operateDate) {
		this.operateDate = operateDate;
	}

	public Integer getAutoFlag() {
		return autoFlag;
	}

	public void setAutoFlag(Integer autoFlag) {
		this.autoFlag = autoFlag;
	}

	public Long getCarTerminalId() {
		return carTerminalId;
	}

	public void setCarTerminalId(Long carTerminalId) {
		this.carTerminalId = carTerminalId;
	}

	public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public Integer getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Integer districtId) {
        this.districtId = districtId;
    }

    public String getCarCph() {
        return carCph;
    }

    public void setCarCph(String carCph) {
        this.carCph = carCph == null ? null : carCph.trim();
    }

    public Integer getCarColor() {
        return carColor;
    }

    public void setCarColor(Integer carColor) {
        this.carColor = carColor;
    }

 

    public Long getCarTerminal() {
		return carTerminal;
	}

	public void setCarTerminal(Long carTerminal) {
		this.carTerminal = carTerminal;
	}

	public Long getCarTeamId() {
		return carTeamId;
	}

	public void setCarTeamId(Long carTeamId) {
		this.carTeamId = carTeamId;
	}

	public Integer getCarState() {
        return carState;
    }

    public void setCarState(Integer carState) {
        this.carState = carState;
    }

    public String getCarPw() {
        return carPw;
    }

    public void setCarPw(String carPw) {
        this.carPw = carPw == null ? null : carPw.trim();
    }

    public String getCarAccountName() {
        return carAccountName;
    }

    public void setCarAccountName(String carAccountName) {
        this.carAccountName = carAccountName == null ? null : carAccountName.trim();
    }

    public String getCarAutoNumber() {
        return carAutoNumber;
    }

    public void setCarAutoNumber(String carAutoNumber) {
        this.carAutoNumber = carAutoNumber == null ? null : carAutoNumber.trim();
    }

    public Long getCarDate() {
        return carDate;
    }

    public void setCarDate(Long carDate) {
        this.carDate = carDate;
    }

    public String getCarPlace() {
        return carPlace;
    }

    public void setCarPlace(String carPlace) {
        this.carPlace = carPlace == null ? null : carPlace.trim();
    }

    public String getCarCompany() {
        return carCompany;
    }

    public void setCarCompany(String carCompany) {
        this.carCompany = carCompany == null ? null : carCompany.trim();
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public Integer getCarType() {
        return carType;
    }

    public void setCarType(Integer carType) {
        this.carType = carType;
    }

    public Integer getCarTrade() {
        return carTrade;
    }

    public void setCarTrade(Integer carTrade) {
        this.carTrade = carTrade;
    }

    public Integer getCarServiceStop() {
        return carServiceStop;
    }

    public void setCarServiceStop(Integer carServiceStop) {
        this.carServiceStop = carServiceStop;
    }

    public Long getServiceBegin() {
        return serviceBegin;
    }

    public void setServiceBegin(Long serviceBegin) {
        this.serviceBegin = serviceBegin;
    }

    public Long getServiceEnd() {
        return serviceEnd;
    }

    public void setServiceEnd(Long serviceEnd) {
        this.serviceEnd = serviceEnd;
    }

	public Long getNettingTime() {
		return nettingTime;
	}

	public void setNettingTime(Long nettingTime) {
		this.nettingTime = nettingTime;
	}

	public Long getNettingLog() {
		return nettingLog;
	}

	public void setNettingLog(Long nettingLog) {
		this.nettingLog = nettingLog;
	}

	public Long getNettingLat() {
		return nettingLat;
	}

	public void setNettingLat(Long nettingLat) {
		this.nettingLat = nettingLat;
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

	public String getOilCapacity() {
		return oilCapacity;
	}

	public void setOilCapacity(String oilCapacity) {
		this.oilCapacity = oilCapacity;
	}

	public Integer getLockStauts() {
		return lockStauts;
	}

	public void setLockStauts(Integer lockStauts) {
		this.lockStauts = lockStauts;
	}

	public Long getOfflineTime() {
		return offlineTime;
	}

	public void setOfflineTime(Long offlineTime) {
		this.offlineTime = offlineTime;
	}

	public Long getRemovelTime() {
		return removalTime;
	}

	public void setRemovelTime(Long removalTime) {
		this.removalTime = removalTime;
	}

	public Long getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(Long registerTime) {
		this.registerTime = registerTime;
	}

	public Integer getBatteryType() {
		return batteryType;
	}

	public void setBatteryType(Integer batteryType) {
		this.batteryType = batteryType;
	}

	public Integer getBatteryBatches() {
		return batteryBatches;
	}

	public void setBatteryBatches(Integer batteryBatches) {
		this.batteryBatches = batteryBatches;
	}
	
	
	
}