package com.navinfo.opentsp.dongfeng.system.converter;

import com.navinfo.opentsp.dongfeng.system.commands.car.DownloadCarCommand;
import com.navinfo.opentsp.dongfeng.system.commands.car.QueryCarCommand;

public class DownloadCarConverter {
	
	public static QueryCarCommand downloadVechicleCommand2QueryVechicleCommand(DownloadCarCommand command) {
		
		QueryCarCommand queryCarCommand = new QueryCarCommand();
		
		queryCarCommand.setBusinessBean(command.getBusinessBean());
		queryCarCommand.setCarBean(command.getCarBean());
		queryCarCommand.setCarDetailBean(command.getCarDetailBean());
		queryCarCommand.setControllerBean(command.getControllerBean());
		queryCarCommand.setDealerBean(command.getDealerBean());
		queryCarCommand.setTerminalBean(command.getTerminalBean());
		queryCarCommand.setTerminalIndto(command.getTerminalIndto());
		
		queryCarCommand.setAuthentication(command.getAuthentication());
		queryCarCommand.setPage_number(command.getPage_number());
		queryCarCommand.setPage_size(command.getPage_size());
		queryCarCommand.setUserInfor(command.getUserInfor());
		return queryCarCommand;
	}
}
