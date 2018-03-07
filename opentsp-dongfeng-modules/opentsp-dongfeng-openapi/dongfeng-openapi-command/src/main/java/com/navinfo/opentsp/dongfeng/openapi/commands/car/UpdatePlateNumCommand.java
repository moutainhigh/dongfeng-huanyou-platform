package com.navinfo.opentsp.dongfeng.openapi.commands.car;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.validation.group.GroupCommand;
import com.navinfo.opentsp.dongfeng.openapi.commands.base.BaseOpenApiCommand;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

public class UpdatePlateNumCommand extends BaseOpenApiCommand {

	@NotNull(message = "VIN不能为空", groups = {GroupCommand.class})
	@NotBlank(message = "VIN不能为空白", groups = {GroupCommand.class})
	private String vin;

	private String plateNum;

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public String getPlateNum() {
		return plateNum;
	}

	public void setPlateNum(String plateNum) {
		this.plateNum = plateNum;
	}

	@Override
	public String toString() {
		return "UpdatePlateNumCommand{}";
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Class<? extends HttpCommandResultWithData> getResultType() {
		return HttpCommandResultWithData.class;
	}

}
