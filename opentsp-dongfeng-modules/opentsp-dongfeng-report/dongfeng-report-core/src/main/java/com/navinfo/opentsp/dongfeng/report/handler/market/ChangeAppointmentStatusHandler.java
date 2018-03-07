package com.navinfo.opentsp.dongfeng.report.handler.market;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.CommonResult;
import com.navinfo.opentsp.dongfeng.report.commands.market.ChangeAppointmentStatusCommand;
import com.navinfo.opentsp.dongfeng.report.service.market.IChangeAppointmentStatusService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 更新预约状态
 * 
 * @author xltianc.zh
 *
 */
@Component
public class ChangeAppointmentStatusHandler extends ValidateTokenAndReSetAbstracHandler<ChangeAppointmentStatusCommand, CommonResult> {
	protected static final Logger logger = LoggerFactory.getLogger(ChangeAppointmentStatusHandler.class);

	@Autowired
	private IChangeAppointmentStatusService statusService;

	public ChangeAppointmentStatusHandler() {
		super(ChangeAppointmentStatusCommand.class, CommonResult.class);
	}

	protected ChangeAppointmentStatusHandler(Class<ChangeAppointmentStatusCommand> commandType,
			Class<CommonResult> resultType) {
		super(commandType, resultType);
	}

	@Override
	protected CommonResult businessHandle(ChangeAppointmentStatusCommand command) {
		return statusService.changeStatus(command);
	}

}
