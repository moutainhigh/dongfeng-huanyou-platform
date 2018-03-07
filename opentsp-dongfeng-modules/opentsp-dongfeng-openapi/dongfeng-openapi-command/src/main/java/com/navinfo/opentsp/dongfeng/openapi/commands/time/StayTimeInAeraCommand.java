package com.navinfo.opentsp.dongfeng.openapi.commands.time;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.validation.group.GroupCommand;
import com.navinfo.opentsp.dongfeng.openapi.commands.base.BaseOpenApiCommand;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

@SuppressWarnings("rawtypes")
public class StayTimeInAeraCommand extends BaseOpenApiCommand {
	
	@NotNull(message = "VIN不能为空", groups = {GroupCommand.class})
    @NotBlank(message = "VIN不能为空白", groups = {GroupCommand.class})
	private String vin;
	@NotNull(message = "区域ID不能为空", groups = {GroupCommand.class})
    @NotBlank(message = "区域ID不能为空白", groups = {GroupCommand.class})
	private String aeraId;

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public String getAeraId() {
		return aeraId;
	}

	public void setAeraId(String aeraId) {
		this.aeraId = aeraId;
	}

	@Override
	public String toString() {
		return "StayTimeInAeraCommand{}";
	}

	@Override
	public Class<? extends HttpCommandResultWithData> getResultType() {
		return HttpCommandResultWithData.class;
	}

}