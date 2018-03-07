package com.navinfo.opentsp.dongfeng.report.service.market;

import com.navinfo.opentsp.dongfeng.common.result.CommonResult;
import com.navinfo.opentsp.dongfeng.report.commands.market.ChangeAppointmentStatusCommand;

public interface IChangeAppointmentStatusService {
	public CommonResult changeStatus(ChangeAppointmentStatusCommand command);
}
