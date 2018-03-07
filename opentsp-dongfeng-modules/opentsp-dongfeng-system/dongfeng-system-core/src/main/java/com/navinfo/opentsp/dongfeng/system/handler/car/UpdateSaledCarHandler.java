package com.navinfo.opentsp.dongfeng.system.handler.car;

import com.navinfo.opentsp.dongfeng.common.exception.BaseServiceException;
import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.CommonResult;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.system.commands.car.UpdateSaledCarCommand;
import com.navinfo.opentsp.dongfeng.system.service.ICarService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class UpdateSaledCarHandler extends ValidateTokenAndReSetAbstracHandler<UpdateSaledCarCommand, CommonResult> {

	@Resource
	private ICarService carService;
	
	public UpdateSaledCarHandler() {
		super(UpdateSaledCarCommand.class , CommonResult.class);
	}
	protected UpdateSaledCarHandler(Class<UpdateSaledCarCommand> commandType, Class<CommonResult> resultType) {
		super(commandType, resultType);
	}

	@Override
	protected CommonResult businessHandle(UpdateSaledCarCommand command) {
		CommonResult result = null;
		try {
			
			result = new CommonResult();
			carService.updateSaledCar(command);
			
			result.setResultCode(ReturnCode.OK.code());
			result.setMessage("车辆保存成功.");
//	        final String operator = OperatorType.UPDATE.getValue() + ":" + command.getCarIndto().getChassisNum();
//			operateLogService.addOperateLog(command, OperateLogConstants.OperateTypeEnum.UPDATE, OperateLogConstants.OperateContentPrefixEnm.VEHICLE.getValue() + command.getCarIndto().getChassisNum(), result);
		} catch (BaseServiceException e) {
			result = new CommonResult();
			result.fillResult(ReturnCode.SERVER_ERROR);
			logger.error("#UpdateCarHandler#businessHandle#error...", e.getExceptionMessage());
			System.out.println(e.getExceptionMessage());
			result.setMessage(e.getExceptionMessage());
//	        final String operator = OperatorType.UPDATE.getValue() + ":" + command.getCarIndto().getChassisNum();
		}
		
		return result;
	}

}
