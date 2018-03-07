package com.navinfo.opentsp.dongfeng.report.pojo.car;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * 
 * @author xltianc.zh
 *
 */
public class HyAppointmentPojo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2286229814680201612L;

	//预约流水号
	private String appointmentNum;
	
	//预约id
	private BigInteger appointmentId;
	
	//服务站id
	private BigInteger stationId;
	
	//服务站名称
	private String stationName;
	
	//车辆id
	private BigInteger carId;
	
	//预约类型
	private Integer appointmentType;
	
	//服务内容
	private String serviceContent;
	
	//配件明细
	private String partsContent;
	
	// 提升工具明细
    protected String toolContent = "";
	
	//补充说明
	private String appointmentComment;
	
	//预约人
	private String appointmentName;
	
	//预约时间
	private BigInteger appointmentTime;
	
	//联系方式 
	private String appointmentPhone;
	
	//预约提交时间
	private BigInteger appoSubmitTime;
	
	//预约状态  0:预约待确认，1:已确认未服务，2:已取消，3:正在服务,4:完成服务待评价,5:完成评价,6:已过期
	private Integer appointmentStatus;
		
    public String getToolContent() {
		return toolContent;
	}

	public void setToolContent(String toolContent) {
		this.toolContent = toolContent;
	}

	public BigInteger getAppoSubmitTime() {
		return appoSubmitTime;
	}

	public void setAppoSubmitTime(BigInteger appoSubmitTime) {
		this.appoSubmitTime = appoSubmitTime;
	}

	public BigInteger getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(BigInteger appointmentId) {
        this.appointmentId = appointmentId;
    }

    public BigInteger getStationId() {
        return stationId;
    }

    public void setStationId(BigInteger stationId) {
        this.stationId = stationId;
    }

    public Integer getAppointmentType() {
        return appointmentType;
    }

    public void setAppointmentType(Integer appointmentType) {
        this.appointmentType = appointmentType;
    }

    public BigInteger getCarId() {
        return carId;
    }

    public void setCarId(BigInteger carId) {
        this.carId = carId;
    }

    public String getServiceContent() {
        return serviceContent;
    }

    public void setServiceContent(String serviceContent) {
        this.serviceContent = serviceContent == null ? null : serviceContent.trim();
    }

    public String getPartsContent() {
        return partsContent;
    }

    public void setPartsContent(String partsContent) {
        this.partsContent = partsContent == null ? null : partsContent.trim();
    }

    public String getAppointmentComment() {
        return appointmentComment;
    }

    public void setAppointmentComment(String appointmentComment) {
        this.appointmentComment = appointmentComment == null ? null : appointmentComment.trim();
    }

    public String getAppointmentName() {
        return appointmentName;
    }

    public void setAppointmentName(String appointmentName) {
        this.appointmentName = appointmentName == null ? null : appointmentName.trim();
    }

    public BigInteger getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(BigInteger appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public String getAppointmentPhone() {
        return appointmentPhone;
    }

    public void setAppointmentPhone(String appointmentPhone) {
        this.appointmentPhone = appointmentPhone == null ? null : appointmentPhone.trim();
    }

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public String getAppointmentNum() {
		return appointmentNum;
	}

	public void setAppointmentNum(String appointmentNum) {
		this.appointmentNum = appointmentNum;
	}

	public Integer getAppointmentStatus() {
		return appointmentStatus;
	}

	public void setAppointmentStatus(Integer appointmentStatus) {
		this.appointmentStatus = appointmentStatus;
	}
    
}