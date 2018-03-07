package com.navinfo.opentsp.dongfeng.system.commands.car;

import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.result.CommonResult;

import javax.validation.constraints.NotNull;

public class DeleteCarCommand extends BaseCommand<CommonResult>{
	
	@NotNull(message = "carId不能为空")
	private Long carId;

	public Long getCarId() {
		return carId;
	}

	public void setCarId(Long carId) {
		this.carId = carId;
	}

	@Override
	public Class<? extends CommonResult> getResultType() {
		return CommonResult.class;
	}
}
