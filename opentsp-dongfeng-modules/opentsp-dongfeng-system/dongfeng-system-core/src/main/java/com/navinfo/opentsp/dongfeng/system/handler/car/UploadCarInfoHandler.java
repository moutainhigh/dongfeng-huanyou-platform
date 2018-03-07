package com.navinfo.opentsp.dongfeng.system.handler.car;

import com.navinfo.opentsp.dongfeng.common.exception.BaseServiceException;
import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.system.commands.car.UploadCarInfoCommand;
import com.navinfo.opentsp.dongfeng.system.service.IUploadCarInfoService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class UploadCarInfoHandler extends ValidateTokenAndReSetAbstracHandler<UploadCarInfoCommand, HttpCommandResultWithData> {

	@Resource
	private IUploadCarInfoService uploadService;
	
	public UploadCarInfoHandler() {
		super(UploadCarInfoCommand.class , HttpCommandResultWithData.class);
	}
	
	protected UploadCarInfoHandler(Class<UploadCarInfoCommand> commandType, Class<HttpCommandResultWithData> resultType) {
		super(commandType, resultType);
	}

	@Override
	protected HttpCommandResultWithData businessHandle(UploadCarInfoCommand command) {
		
		HttpCommandResultWithData result = null;
		try {
			uploadService.uploadCarInfo(command);
			result = new HttpCommandResultWithData();
			result.setResultCode(ReturnCode.OK.code());
			
		} catch (BaseServiceException e) {
			result = new HttpCommandResultWithData();
			result.setResultCode(ReturnCode.SERVER_ERROR.code());
			result.setMessage(e.getExceptionMessage());
		}
		return result;
	}
	
	

}
