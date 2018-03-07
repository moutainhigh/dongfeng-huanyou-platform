package com.navinfo.opentsp.dongfeng.system.commands.dto.outDto;

import org.springframework.util.StringUtils;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CarBaseInfoDto {
	
	/**
	 * 车辆底盘号
	 */
	private String chassis;
	
	/**
	 * 车辆VIN码
	 */
	private String vechicleVin;
	
	/**
	 * 车型码
	 */
	private String vechicleCode;
	
	/**
	 * 车辆类型
	 */
	private String cx;
	
	/**
	 * 发动机型号
	 */
	private String fdj;
	
	/**
	 * 下线/制造日期
	 */
	private BigInteger offlineTime;
	
	/**
	 * 发动机号
	 */
	private String engineNum;
	
	/**
	 * 额定功率
	 */
	private String edgl;
	
	/**
	 * 整备质量
	 */
	private String zbzl;
	
	/**
	 * 总质量
	 */
	private String zzl;

	public String getChassis() {
		return chassis;
	}

	public void setChassis(String chassis) {
		this.chassis = chassis;
	}

	public String getVechicleVin() {
		return vechicleVin;
	}

	public void setVechicleVin(String vechicleVin) {
		this.vechicleVin = vechicleVin;
	}

	public String getVechicleCode() {
		return vechicleCode;
	}

	public void setVechicleCode(String vechicleCode) {
		this.vechicleCode = vechicleCode;
	}

	public String getCx() {
		return cx;
	}

	public void setCx(String cx) {
		this.cx = cx;
	}

	public String getOfflineTime() {
		
		if (null != offlineTime && offlineTime.longValue() > 0) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			
			return format.format(new Date(offlineTime.longValue() * 1000));
		}
		return "";
	}

	public void setOfflineTime(BigInteger offlineTime) {
		this.offlineTime = offlineTime;
	}

	public String getEngineNum() {
		return engineNum;
	}

	public void setEngineNum(String engineNum) {
		this.engineNum = engineNum;
	}

	public String getEdgl() {
		return edgl;
	}

	public void setEdgl(String edgl) {
		if (!StringUtils.isEmpty(edgl)) {
			this.edgl = edgl.substring(0 , -1 != edgl.indexOf("KW") ? edgl.indexOf("KW") : edgl.length());
		}
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

	public String getFdj() {
		return fdj;
	}

	public void setFdj(String fdj) {
		this.fdj = fdj;
	}
	
}
