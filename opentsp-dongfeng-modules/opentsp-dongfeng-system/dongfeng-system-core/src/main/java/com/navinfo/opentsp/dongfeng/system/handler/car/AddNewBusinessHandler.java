package com.navinfo.opentsp.dongfeng.system.handler.car;

import com.navinfo.opentsp.dongfeng.common.exception.BaseServiceException;
import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.system.commands.car.AddNewBusinessCommand;
import com.navinfo.opentsp.dongfeng.system.entity.BusinessEntity;
import com.navinfo.opentsp.dongfeng.system.service.ICarService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class AddNewBusinessHandler extends ValidateTokenAndReSetAbstracHandler<AddNewBusinessCommand, HttpCommandResultWithData> {

	@Resource
	private ICarService carService;
	
	public AddNewBusinessHandler() {
		super(AddNewBusinessCommand.class , HttpCommandResultWithData.class);
	}
	
	protected AddNewBusinessHandler(Class<AddNewBusinessCommand> commandType, Class<HttpCommandResultWithData> resultType) {
		super(commandType, resultType);
	}

	@Override
	protected HttpCommandResultWithData businessHandle(AddNewBusinessCommand command) {
		HttpCommandResultWithData result = null;
		try {
			
			result = new HttpCommandResultWithData();
			BusinessEntity entity = (BusinessEntity)carService.addBusiness(command);
			
			result.setResultCode(ReturnCode.OK.code());
			result.setMessage("车辆保存成功.");
			result.setData(entity);
			
//	        final String operator = OperatorType.UPDATE.getValue() + ":" + command.getCarIndto().getChassisNum();
//			operateLogService.addOperateLog(command, OperateLogConstants.OperateTypeEnum.UPDATE, OperateLogConstants.OperateContentPrefixEnum.VEHICLE.getValue() + command.getCarIndto().getChassisNum(), result);
		} catch (BaseServiceException e) {
			result = new HttpCommandResultWithData();
			result.fillResult(ReturnCode.SERVER_ERROR);
			logger.error("#UpdateCarHandler#businessHandle#error...", e.getExceptionMessage());
			result.setMessage(e.getExceptionMessage());
//	        final String operator = OperatorType.UPDATE.getValue() + ":" + command.getCarIndto().getChassisNum();
		}
		
		return result;
	}
}
