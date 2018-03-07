package com.navinfo.dongfeng.terminal.comm.model.system.vehicle;

import java.io.Serializable;

public class HyTerminal implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -5964039903815911702L;

	private Long tId;
	//分区服务编码 
    private Integer district;
    //车载终端SIM卡号
    private String tSim;
    //通讯号
    private Long tCommunicationId;
    //车载终端所属组
    private Long tTeamId;
    //终端ID 
    private String tCode;
    //终端协议 
    private Integer tTypeId;
    //SIM卡提供方
    private Integer tSimSign;
    //逻辑删除标记
    private Integer delFlag;
    
    private Integer alarmHandle;
    //生成永久通信id
    private Long tAutoCommunicationId;
    //是否使用自动生成通信id标识
    private Integer autoFlag;
    //导入时间
    private Long importTime;
    //底盘号
    private String chassisNum;
    //终端类型
    private Integer tStyle;
    //终端型号
    private String tModel;
    //备注
    private String tCommon;
    //所属分组
    private String tteam;
    //所属车辆
    private String carCph;
    //车辆ID
    private Long carId;
    //终端协议描述
    private String terTypeName;
    
    //终端外设--摄像头通道
    private String cameraChannel;
    //终端外设--MIC
    private Integer mic;
    //设备标签id
    private String tlableId;
    /*
     * 适用发动机类型：终端初始化参数设置保存的数据（数据库初始化默认值0）
     * 0:潍坊
     * 1：玉柴
     * 2：锡柴
     * 3：杭发
     * 4：沃能
     * 5：汽研
     */
    private Long applianceEngineType;
    private Long applianceCarType;
	public String getTlableId() {
		return tlableId;
	}

	public void setTlableId(String tlableId) {
		this.tlableId = tlableId;
	}

	public String getCameraChannel() {
		return cameraChannel;
	}

	public void setCameraChannel(String cameraChannel) {
		this.cameraChannel = cameraChannel;
	}

	public Integer getMic() {
		return mic;
	}

	public void setMic(Integer mic) {
		this.mic = mic;
	}

	public Long getCarId() {
		return carId;
	}

	public void setCarId(Long carId) {
		this.carId = carId;
	}

	public String getTerTypeName() {
		return terTypeName;
	}

	public void setTerTypeName(String terTypeName) {
		this.terTypeName = terTypeName;
	}

	public String getCarCph() {
		return carCph;
	}

	public void setCarCph(String carCph) {
		this.carCph = carCph;
	}

	public String getTteam() {
		return tteam;
	}

	public void setTteam(String tteam) {
		this.tteam = tteam;
	}

	public Long gettTeamId() {
		return tTeamId;
	}

	public void settTeamId(Long tTeamId) {
		this.tTeamId = tTeamId;
	}

	public Integer gettTypeId() {
		return tTypeId;
	}

	public void settTypeId(Integer tTypeId) {
		this.tTypeId = tTypeId;
	}

	public Integer gettStyle() {
		return tStyle;
	}

	public void settStyle(Integer tStyle) {
		this.tStyle = tStyle;
	}

	public String gettModel() {
		return tModel;
	}

	public void settModel(String tModel) {
		this.tModel = tModel;
	}

	public String gettCommon() {
		return tCommon;
	}

	public void settCommon(String tCommon) {
		this.tCommon = tCommon;
	}

	public Integer getAlarmHandle() {
		return alarmHandle;
	}

	public void setAlarmHandle(Integer alarmHandle) {
		this.alarmHandle = alarmHandle;
	}

	public Long gettId() {
        return tId;
    }

    public void settId(Long tId) {
        this.tId = tId;
    }

    public Integer getDistrict() {
        return district;
    }

    public void setDistrict(Integer district) {
        this.district = district;
    }

    public String gettSim() {
        return tSim;
    }

    public void settSim(String tSim) {
        this.tSim = tSim == null ? null : tSim.trim();
    }

    public Long gettCommunicationId() {
        return tCommunicationId;
    }

    public void settCommunicationId(Long tCommunicationId) {
        this.tCommunicationId = tCommunicationId;
    }

  

    public String gettCode() {
        return tCode;
    }

    public void settCode(String tCode) {
        this.tCode = tCode == null ? null : tCode.trim();
    }

 

    public Long getTTeamId() {
		return tTeamId;
	}

	public void setTTeamId(Long teamId) {
		tTeamId = teamId;
	}

	public Integer getTTypeId() {
		return tTypeId;
	}

	public void setTTypeId(Integer typeId) {
		tTypeId = typeId;
	}

	public Integer gettSimSign() {
        return tSimSign;
    }

    public void settSimSign(Integer tSimSign) {
        this.tSimSign = tSimSign;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }
	public Long gettAutoCommunicationId() {
		return tAutoCommunicationId;
	}

	public void settAutoCommunicationId(Long tAutoCommunicationId) {
		this.tAutoCommunicationId = tAutoCommunicationId;
	}

	public Integer getAutoFlag() {
		return autoFlag;
	}

	public void setAutoFlag(Integer autoFlag) {
		this.autoFlag = autoFlag;
	}

	public Long getImportTime() {
		return importTime;
	}

	public void setImportTime(Long importTime) {
		this.importTime = importTime;
	}

	public String getChassisNum() {
		return chassisNum;
	}

	public void setChassisNum(String chassisNum) {
		this.chassisNum = chassisNum;
	}

	public Long getCommunctionId() {
		// TODO Auto-generated method stub
		return null;
	}

	public Long getApplianceEngineType() {
		return applianceEngineType;
	}

	public void setApplianceEngineType(Long applianceEngineType) {
		this.applianceEngineType = applianceEngineType;
	}

	public Long getApplianceCarType() {
		return applianceCarType;
	}

	public void setApplianceCarType(Long applianceCarType) {
		this.applianceCarType = applianceCarType;
	}
}