package com.navinfo.opentsp.dongfeng.openapi.core.service;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.openapi.commands.car.UpdatePlateNumCommand;
import com.navinfo.opentsp.dongfeng.openapi.commands.day.CarSyncInfoByTimeCommand;
import com.navinfo.opentsp.dongfeng.openapi.commands.day.GetCarInfoCommand;
import com.navinfo.opentsp.dongfeng.openapi.commands.param.GetCarParamCommand;

/**
 * 
 * @author xltianc.zh
 *
 */
@SuppressWarnings("rawtypes")
public interface ICarDataBusinessService {

	/**
	 * 车辆同步:同步进N天修改的车辆
	 * 
	 * @param command
	 * @return
	 */
	public HttpCommandResultWithData queryAddOrUpdateCarInfo(GetCarInfoCommand command);

	/**
	 *  车型同步:同步所有车型
	 * @param command
	 * @return
	 */
	public HttpCommandResultWithData queryCarParam(GetCarParamCommand command);
	
	/**
	 * 同步车辆信息查询接口
	 * @param command
	 * @return
	 */
	public HttpCommandResultWithData selectCarSyncInfoByNum(CarSyncInfoByTimeCommand command);
	
	/**
	 * 同步车牌号
	 * @param command
	 * @return
	 */
	public HttpCommandResultWithData updatePlateNum(UpdatePlateNumCommand command);
}
