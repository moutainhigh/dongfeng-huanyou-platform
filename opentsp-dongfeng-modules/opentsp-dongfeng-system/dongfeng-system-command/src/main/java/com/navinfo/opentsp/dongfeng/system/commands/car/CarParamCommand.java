package com.navinfo.opentsp.dongfeng.system.commands.car;

import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;

public class CarParamCommand extends BaseCommand<HttpCommandResultWithData>{

	/**
	 * 最大功率
	 */
	private String carModel;
	

	public String getCarModel() {
		return carModel;
	}

	public void setCarModel(String carModel) {
		this.carModel = carModel;
	}

	@Override
	public Class<? extends HttpCommandResultWithData> getResultType() {
		return HttpCommandResultWithData.class;
	}

}
