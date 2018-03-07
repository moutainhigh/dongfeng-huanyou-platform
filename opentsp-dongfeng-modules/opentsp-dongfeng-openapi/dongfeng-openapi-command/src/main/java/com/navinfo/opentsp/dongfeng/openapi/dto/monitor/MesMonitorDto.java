package com.navinfo.opentsp.dongfeng.openapi.dto.monitor;

/**
 * 有关一汽对应的车型数据类
 * 
 * @author xltianc.zh
 *
 */
public class MesMonitorDto {

	private String logDate; // 日期
	private String transName;// 转换名
	private String stepName;// 步骤名
	private Integer linesRead;// 读取记录数
	private Integer linesWritten;// 处理记录数
	private Integer linesUpdated;// 更新记录数
	private Integer linesOutput;// 插入记录数
	private Integer errors;// 变速箱

	public String getLogDate() {
		return logDate;
	}

	public String getTransName() {
		return transName;
	}

	public String getStepName() {
		return stepName;
	}

	public Integer getLinesRead() {
		return linesRead;
	}

	public Integer getLinesWritten() {
		return linesWritten;
	}

	public Integer getLinesUpdated() {
		return linesUpdated;
	}

	public Integer getLinesOutput() {
		return linesOutput;
	}

	public Integer getErrors() {
		return errors;
	}

	public void setLogDate(String logDate) {
		this.logDate = logDate;
	}

	public void setTransName(String transName) {
		this.transName = transName;
	}

	public void setStepName(String stepName) {
		this.stepName = stepName;
	}

	public void setLinesRead(Integer linesRead) {
		this.linesRead = linesRead;
	}

	public void setLinesWritten(Integer linesWritten) {
		this.linesWritten = linesWritten;
	}

	public void setLinesUpdated(Integer linesUpdated) {
		this.linesUpdated = linesUpdated;
	}

	public void setLinesOutput(Integer linesOutput) {
		this.linesOutput = linesOutput;
	}

	public void setErrors(Integer errors) {
		this.errors = errors;
	}

}
