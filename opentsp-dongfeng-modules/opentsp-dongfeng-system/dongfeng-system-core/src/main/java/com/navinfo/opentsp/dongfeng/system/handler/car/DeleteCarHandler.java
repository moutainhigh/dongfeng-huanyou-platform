package com.navinfo.opentsp.dongfeng.system.handler.car;

import com.navinfo.opentsp.dongfeng.common.constant.UserTypeConstant;
import com.navinfo.opentsp.dongfeng.common.exception.BaseServiceException;
import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.CommonResult;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.common.service.IOperateLogService;
import com.navinfo.opentsp.dongfeng.system.commands.car.DeleteCarCommand;
import com.navinfo.opentsp.dongfeng.system.constant.OperatorType;
import com.navinfo.opentsp.dongfeng.system.service.ICarService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class DeleteCarHandler extends ValidateTokenAndReSetAbstracHandler<DeleteCarCommand, CommonResult> {

	@Resource
	private ICarService carService;
	
    @Resource
    private IOperateLogService operateLogService;

	public DeleteCarHandler() {
		super(DeleteCarCommand.class, CommonResult.class);
	}

	protected DeleteCarHandler(Class<DeleteCarCommand> commandType, Class<CommonResult> resultType) {
		super(commandType, resultType);
	}

	@Override
	protected CommonResult businessHandle(DeleteCarCommand command) {
		CommonResult result = new CommonResult();
		try {
			
			if (UserTypeConstant.SYSTEM_ADMIN.getCode() == command.getUserInfor().getType()) {
				result = new CommonResult();
				carService.deleteCar(command , UserTypeConstant.SYSTEM_ADMIN);
				result.setResultCode(ReturnCode.OK.code());
			}

			if (UserTypeConstant.CAR_FACTORY.getCode() == command.getUserInfor().getType()) {
				result = new CommonResult();
				carService.deleteCar(command , UserTypeConstant.CAR_FACTORY);
				result.setResultCode(ReturnCode.OK.code());
			}

			if (UserTypeConstant.BUSINESS.getCode() == command.getUserInfor().getType()) {
				result = new CommonResult();
				carService.deleteCar(command , UserTypeConstant.BUSINESS);
				result.setResultCode(ReturnCode.OK.code());
			}

	        final String operator = OperatorType.DELETE.getValue() + ":" + command.getCarId();
//	        operateLogService.addOperateLog(operator, command.getUserInfor().getUsername(), result);
//			carService.deleteCar(command);
		} catch (BaseServiceException e) {
			result.fillResult(ReturnCode.SERVER_ERROR);
			logger.error("#DeleteCarHandler#businessHandle#error...", e);
	        final String operator = OperatorType.DELETE.getValue() + ":" + command.getCarId();
//	        operateLogService.addOperateLog(operator, command.getUserInfor().getUsername(), result);
		}
		return result;
	}

}
