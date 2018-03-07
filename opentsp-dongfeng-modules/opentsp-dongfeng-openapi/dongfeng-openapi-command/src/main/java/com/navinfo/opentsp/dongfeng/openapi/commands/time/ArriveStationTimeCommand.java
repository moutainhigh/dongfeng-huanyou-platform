package com.navinfo.opentsp.dongfeng.openapi.commands.time;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.validation.group.GroupCommand;
import com.navinfo.opentsp.dongfeng.openapi.commands.base.BaseOpenApiCommand;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

@SuppressWarnings("rawtypes")
public class ArriveStationTimeCommand extends BaseOpenApiCommand {

	@NotNull(message = "VIN不能为空", groups = {GroupCommand.class})
    @NotBlank(message = "VIN不能为空白", groups = {GroupCommand.class})
	private String vin;
	
	@NotNull(message = "服务站编码不能为空", groups = {GroupCommand.class})
    @NotBlank(message = "服务站编码不能为空白", groups = {GroupCommand.class})
	private String ssCode;
	
	@NotNull(message = "服务站二级网点编码不能为空", groups = {GroupCommand.class})
    @NotBlank(message = "服务站二级网点编码不能为空白", groups = {GroupCommand.class})
	private String secCode;

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public String getSsCode() {
		return ssCode;
	}

	public void setSsCode(String ssCode) {
		this.ssCode = ssCode;
	}

	public String getSecCode() {
		return secCode;
	}

	public void setSecCode(String secCode) {
		this.secCode = secCode;
	}

	@Override
	public String toString() {
		return "ArriveStationTimeCommand{}";
	}

	@Override
	public Class<? extends HttpCommandResultWithData> getResultType() {
		return HttpCommandResultWithData.class;
	}

}
