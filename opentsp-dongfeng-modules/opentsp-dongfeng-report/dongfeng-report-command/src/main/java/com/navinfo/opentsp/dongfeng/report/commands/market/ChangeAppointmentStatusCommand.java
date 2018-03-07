package com.navinfo.opentsp.dongfeng.report.commands.market;

import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;

@SuppressWarnings("rawtypes")
public class ChangeAppointmentStatusCommand extends BaseCommand<HttpCommandResultWithData> {

	private Integer appointmentNum;
	private Integer appointmentStatus;

	public Integer getAppointmentNum() {
		return appointmentNum;
	}

	public void setAppointmentNum(Integer appointmentNum) {
		this.appointmentNum = appointmentNum;
	}

	public Integer getAppointmentStatus() {
		return appointmentStatus;
	}

	public void setAppointmentStatus(Integer appointmentStatus) {
		this.appointmentStatus = appointmentStatus;
	}

	@Override
	public Class<? extends HttpCommandResultWithData> getResultType() {
		return HttpCommandResultWithData.class;
	}

	@Override
	public String toString() {
		return "ChangeAppointmentStatusCommand{" + "appointmentNum='" + appointmentNum + '\'' + ", appointmentStatus='"
				+ appointmentStatus + '\'' + '}';

	}
}
