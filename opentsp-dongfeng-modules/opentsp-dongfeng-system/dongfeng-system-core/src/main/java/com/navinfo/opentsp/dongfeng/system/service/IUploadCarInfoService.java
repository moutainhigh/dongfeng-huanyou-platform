package com.navinfo.opentsp.dongfeng.system.service;

import com.navinfo.opentsp.dongfeng.common.exception.BaseServiceException;
import com.navinfo.opentsp.dongfeng.system.commands.car.UploadCarInfoCommand;

public interface IUploadCarInfoService {
	
	/**
	 * 向云端同步车辆信息
	 * @param carId
	 * @return
	 */
	public void uploadCarInfo(UploadCarInfoCommand command) throws BaseServiceException;
}
