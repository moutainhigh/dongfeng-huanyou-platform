package com.navinfo.opentsp.dongfeng.system.handler.car;

import com.navinfo.opentsp.dongfeng.common.exception.BaseServiceException;
import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.CommonResult;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.common.service.IOperateLogService;
import com.navinfo.opentsp.dongfeng.system.commands.car.UpdateCarCommand;
import com.navinfo.opentsp.dongfeng.system.service.ICarService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class UpdateCarHandler extends ValidateTokenAndReSetAbstracHandler<UpdateCarCommand, CommonResult> {

	@Resource
	private ICarService carService;

    @Resource
    private IOperateLogService operateLogService;

	public UpdateCarHandler() {
		super(UpdateCarCommand.class, CommonResult.class);
	}

	protected UpdateCarHandler(Class<UpdateCarCommand> commandType, Class<CommonResult> resultType) {
		super(commandType, resultType);
	}

	@Override
	protected CommonResult businessHandle(UpdateCarCommand command) {
		CommonResult result = null;
		try {
			
			result = new CommonResult();
			carService.updateCar(command);
			
			result.setResultCode(ReturnCode.OK.code());
			result.setMessage("车辆保存成功.");
//	        final String operator = OperatorType.UPDATE.getValue() + ":" + command.getCarIndto().getChassisNum();
//			operateLogService.addOperateLog(command, OperateLogConstants.OperateTypeEnum.UPDATE, OperateLogConstants.OperateContentPrefixEnum.VEHICLE.getValue() + command.getCarIndto().getChassisNum(), result);
		} catch (BaseServiceException e) {
			result = new CommonResult();
			result.fillResult(ReturnCode.SERVER_ERROR);
			logger.error("#UpdateCarHandler#businessHandle#error...", e.getExceptionMessage());
			result.setMessage(e.getExceptionMessage());
//	        final String operator = OperatorType.UPDATE.getValue() + ":" + command.getCarIndto().getChassisNum();
		}
		
		return result;
	}

}
