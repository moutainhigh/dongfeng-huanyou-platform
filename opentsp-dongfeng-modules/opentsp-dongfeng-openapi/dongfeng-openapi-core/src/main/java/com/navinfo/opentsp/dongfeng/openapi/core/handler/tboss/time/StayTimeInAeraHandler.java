package com.navinfo.opentsp.dongfeng.openapi.core.handler.tboss.time;

import com.navinfo.opentsp.dongfeng.common.handler.NotValidateTokenAbstractHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.openapi.commands.time.StayTimeInAeraCommand;
import com.navinfo.opentsp.dongfeng.openapi.core.service.ISecondForAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@SuppressWarnings("rawtypes")
public class StayTimeInAeraHandler
		extends NotValidateTokenAbstractHandler<StayTimeInAeraCommand, HttpCommandResultWithData> {

	@Autowired
	private ISecondForAppService secondForAppService;

	public StayTimeInAeraHandler() {
		super(StayTimeInAeraCommand.class, HttpCommandResultWithData.class);
	}

	protected StayTimeInAeraHandler(Class<StayTimeInAeraCommand> commandType,
			Class<HttpCommandResultWithData> resultType) {
		super(commandType, resultType);
	}

	@Override
	protected HttpCommandResultWithData businessHandle(StayTimeInAeraCommand command) {
		HttpCommandResultWithData result = new HttpCommandResultWithData();
		try {
			result = secondForAppService.selectStayTimeInAera(command);
		} catch (Exception e) {
			logger.error("查询车辆的区域停留时间失败：" , e);
			result.fillResult(ReturnCode.SERVER_ERROR);
		}
		return result;
	}

}
