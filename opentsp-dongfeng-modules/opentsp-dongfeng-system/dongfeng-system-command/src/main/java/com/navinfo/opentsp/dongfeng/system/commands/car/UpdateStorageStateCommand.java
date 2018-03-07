package com.navinfo.opentsp.dongfeng.system.commands.car;

import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.result.CommonResult;

public class UpdateStorageStateCommand extends BaseCommand<CommonResult>{

	/**
	 * 车辆ID
	 */
	private Long carId;
	
	/**
	 * 车辆在库状态
	 */
	private Integer state;
	
	@Override
	public Class<? extends CommonResult> getResultType() {
		return CommonResult.class;
	}

	public Long getCarId() {
		return carId;
	}

	public void setCarId(Long carId) {
		this.carId = carId;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "UpdateStorageStateCommand{" +
				"carId=" + carId +
				", state=" + state +
				'}';
	}
}
