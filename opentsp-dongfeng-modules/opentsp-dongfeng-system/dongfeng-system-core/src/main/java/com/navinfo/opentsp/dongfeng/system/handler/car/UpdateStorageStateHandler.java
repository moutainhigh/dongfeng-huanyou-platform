package com.navinfo.opentsp.dongfeng.system.handler.car;

import com.navinfo.opentsp.dongfeng.common.exception.BaseServiceException;
import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.CommonResult;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.system.commands.car.UpdateStorageStateCommand;
import com.navinfo.opentsp.dongfeng.system.service.ICarService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class UpdateStorageStateHandler extends ValidateTokenAndReSetAbstracHandler<UpdateStorageStateCommand, CommonResult> {

	@Resource
	private ICarService carService;
	
	public UpdateStorageStateHandler() {
		super(UpdateStorageStateCommand.class , CommonResult.class);
	}
	
	protected UpdateStorageStateHandler(Class<UpdateStorageStateCommand> commandType, Class<CommonResult> resultType) {
		super(commandType, resultType);
	}

	@Override
	protected CommonResult businessHandle(UpdateStorageStateCommand command) {
		
		CommonResult result = null;
		try {
			if (null == command.getCarId()) {
				result = new CommonResult();
				result.setResultCode(ReturnCode.SERVER_ERROR.code());
				result.setMessage("车辆ID不允许为空 .");
				return result;
			}
			carService.updateInState(command.getCarId(), command.getState());
			result = new CommonResult();
			result.setResultCode(ReturnCode.OK.code());
			result.setMessage("车辆在库状态修改成功 .");
		} catch (BaseServiceException e) {
			result = new CommonResult();
			result.setResultCode(ReturnCode.SERVER_ERROR.code());
			result.setMessage(e.getExceptionMessage());
		}
		return result;
	}

}
