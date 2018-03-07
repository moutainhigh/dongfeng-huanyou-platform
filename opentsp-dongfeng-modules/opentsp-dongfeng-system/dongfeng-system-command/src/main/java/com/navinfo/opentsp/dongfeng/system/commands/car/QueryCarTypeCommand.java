package com.navinfo.opentsp.dongfeng.system.commands.car;

import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;

public class QueryCarTypeCommand extends BaseCommand<HttpCommandResultWithData>{

	private String carTypeRear;
	
	@Override
	public Class<? extends HttpCommandResultWithData> getResultType() {
		return HttpCommandResultWithData.class;
	}
	public String getCarTypeRear() {
		return carTypeRear;
	}

	public void setCarTypeRear(String carTypeRear) {
		this.carTypeRear = carTypeRear;
	}

}
