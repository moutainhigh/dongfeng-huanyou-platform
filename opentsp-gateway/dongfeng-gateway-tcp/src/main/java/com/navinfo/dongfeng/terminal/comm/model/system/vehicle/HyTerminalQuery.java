package com.navinfo.dongfeng.terminal.comm.model.system.vehicle;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

public class HyTerminalQuery implements Serializable{
    /**
	 * 终端查询结果实体类
	 */
	private static final long serialVersionUID = -5964039903815911702L;
	private Long tId;
	// 终端ID
	private String tCode;
    // 终端SIM卡
	private String tSim;
    // 终端类型
	private String tstyle;
    // 终端型号
	private String tModel;
    // 协议类型
    private String tType;
    // 所属经销商---------------------------车辆新建中终端根据经销商选取
    private String tTeam;
    // 所属经销商Str------------------------终端查询
    private String tTeamStr;
	// 所属车辆
    private String car;
    // 底盘号
    private String chassisNum;
    // GPS时间
    private String gpsDate;
    // GPS位置
    private String gps;
    // 备注
    private String tCommon;
    //此终端是否已经被绑定
    private String isTailed;
    //车辆ID
    private Long carId;
    //车辆ID
    private String carIdStr;
    
    //在线状态
    private int carStatue;
    //通信号
    private long communicate;
    
    // 发送机类型
 	private Integer engineType;
 	//设备标签id
    private String tlableId;
   
    public String getTlableId() {
		return tlableId;
	}

	public void setTlableId(String tlableId) {
		this.tlableId = tlableId;
	}
    
    public Integer getEngineType() {
		return engineType;
	}

	public void setEngineType(Integer engineType) {
		this.engineType = engineType;
	}

	public long getCommunicate() {
		return communicate;
	}

	public void setCommunicate(long communicate) {
		this.communicate = communicate;
	}

	public int getCarStatue() {
		return carStatue;
	}

	public void setCarStatue(int carStatue) {
		this.carStatue = carStatue;
	}
    
    @JSONField(name="tTeamStr")
	public String gettTeamStr() {
		return tTeamStr;
	}
    @JSONField(name="tTeamStr")
	public void settTeamStr(String tTeamStr) {
		this.tTeamStr = tTeamStr;
	}
    
	public String getCarIdStr() {
		return carIdStr;
	}
	public void setCarIdStr(String carIdStr) {
		this.carIdStr = carIdStr;
	}
	public Long getCarId() {
		return carId;
	}
	public void setCarId(Long carId) {
		this.carId = carId;
	}
	public String getIsTailed() {
		return isTailed;
	}
	public void setIsTailed(String isTailed) {
		this.isTailed = isTailed;
	}
	public String getGpsDate() {
		return gpsDate;
	}
	public void setGpsDate(String gpsDate) {
		this.gpsDate = gpsDate;
	}
	public String getGps() {
		return gps;
	}
	public void setGps(String gps) {
		this.gps = gps;
	}
	@JSONField(name="tCommon")
	public String gettCommon() {
		return tCommon;
	}
	@JSONField(name="tCommon")
	public void settCommon(String tCommon) {
		this.tCommon = tCommon;
	}
	@JSONField(name="tId")
	public Long gettId() {
		return tId;
	}
	@JSONField(name="tId")
	public void settId(Long tId) {
		this.tId = tId;
	}
	@JSONField(name="tCode")
	public String gettCode() {
		return tCode;
	}
	@JSONField(name="tCode")
	public void settCode(String tCode) {
		this.tCode = tCode;
	}
	@JSONField(name="tSim")
	public String gettSim() {
		return tSim;
	}
	@JSONField(name="tSim")
	public void settSim(String tSim) {
		this.tSim = tSim;
	}
	
	
	public String getTstyle() {
		return tstyle;
	}
	public void setTstyle(String tstyle) {
		this.tstyle = tstyle;
	}
	@JSONField(name="tModel")
	public String gettModel() {
		return tModel;
	}
	@JSONField(name="tModel")
	public void settModel(String tModel) {
		this.tModel = tModel;
	}
	
	@JSONField(name="tType")
	public String gettType() {
		return tType;
	}
	@JSONField(name="tType")
	public void settType(String tType) {
		this.tType = tType;
	}
	@JSONField(name="tTeam")
	public String gettTeam() {
		return tTeam;
	}
	@JSONField(name="tTeam")
	public void settTeam(String tTeam) {
		this.tTeam = tTeam;
	}
	public String getCar() {
		return car;
	}
	public void setCar(String car) {
		this.car = car;
	}
	public String getChassisNum() {
		return chassisNum;
	}
	public void setChassisNum(String chassisNum) {
		this.chassisNum = chassisNum;
	}
    
}