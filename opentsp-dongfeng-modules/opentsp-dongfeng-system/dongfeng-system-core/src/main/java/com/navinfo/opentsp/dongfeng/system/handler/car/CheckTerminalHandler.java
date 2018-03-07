package com.navinfo.opentsp.dongfeng.system.handler.car;

import com.navinfo.opentsp.dongfeng.common.exception.BaseServiceException;
import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.system.commands.car.CheckTerminalCommand;
import com.navinfo.opentsp.dongfeng.system.commands.dto.outDto.AccDataOutDto;
import com.navinfo.opentsp.dongfeng.system.commands.dto.outDto.CanDataOutDto;
import com.navinfo.opentsp.dongfeng.system.commands.dto.outDto.CheckTerminalOutDto;
import com.navinfo.opentsp.dongfeng.system.commands.dto.outDto.GpsDataOutDto;
import com.navinfo.opentsp.dongfeng.system.service.ICarService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class CheckTerminalHandler extends ValidateTokenAndReSetAbstracHandler<CheckTerminalCommand, HttpCommandResultWithData> {

	@Resource
	private ICarService carService;
	
	private static final Log logger = LogFactory.getLog(CheckTerminalHandler.class);
	
	public CheckTerminalHandler() {
		super(CheckTerminalCommand.class , HttpCommandResultWithData.class);
	}
	
	protected CheckTerminalHandler(Class<CheckTerminalCommand> commandType,
			Class<HttpCommandResultWithData> resultType) {
		super(commandType, resultType);
	}

	@Override
	protected HttpCommandResultWithData businessHandle(CheckTerminalCommand command) {
		
		HttpCommandResultWithData result = new HttpCommandResultWithData();;
		try {
			
			CheckTerminalOutDto check = carService.checkTerminal(command);
			
			if (null == check) {
				result.setResultCode(ReturnCode.SERVER_ERROR.code());
				result.setMessage("false");
				return result;
			}
			AccDataOutDto accData = check.getAccData();
			CanDataOutDto canData = check.getCanData();
			GpsDataOutDto gpsData = check.getGpsData();
			
			boolean accFlag = null == accData || null == accData.getFlag() || !(1 == accData.getFlag()) ? true : false;
			boolean canFlag = null == canData || null == canData.getFlag() || !(1 == canData.getFlag()) ? true : false;
			boolean gpsFlag = null == gpsData || null == gpsData.getFlag() || !(1 == gpsData.getFlag()) ? true : false;
			
			if (accFlag || canFlag || gpsFlag) {
				result.setResultCode(ReturnCode.OK.code());
				result.setMessage("ACC状态:"+accData.getAcc() + " , CAN状态:"+canData.getCan() + " , GPS状态" + gpsData.getGps());
			} else {
				result.setResultCode(ReturnCode.OK.code());
				result.setMessage("ACC状态:"+accData.getAcc() + " , CAN状态:"+canData.getCan() + " , GPS状态" + gpsData.getGps());
			}
			result.setData(check);
			
		} catch (BaseServiceException e) {
			result.setResultCode(ReturnCode.SERVER_ERROR.code());
			result.setMessage(e.getExceptionMessage());
		}
		return result;
	}

}
