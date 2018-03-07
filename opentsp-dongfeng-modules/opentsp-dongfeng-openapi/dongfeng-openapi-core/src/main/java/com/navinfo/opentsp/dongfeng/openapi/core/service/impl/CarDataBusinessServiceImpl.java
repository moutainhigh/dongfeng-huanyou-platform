package com.navinfo.opentsp.dongfeng.openapi.core.service.impl;

import com.navinfo.opentsp.dongfeng.authority.commands.OpenApiLoginCommand;
import com.navinfo.opentsp.dongfeng.common.client.AuthorityClient;
import com.navinfo.opentsp.dongfeng.common.result.CommonResult;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.common.service.BaseService;
import com.navinfo.opentsp.dongfeng.openapi.commands.car.UpdatePlateNumCommand;
import com.navinfo.opentsp.dongfeng.openapi.commands.day.CarSyncInfoByTimeCommand;
import com.navinfo.opentsp.dongfeng.openapi.commands.day.GetCarInfoCommand;
import com.navinfo.opentsp.dongfeng.openapi.commands.param.GetCarParamCommand;
import com.navinfo.opentsp.dongfeng.openapi.core.converter.day.CarInfoYqToDto;
import com.navinfo.opentsp.dongfeng.openapi.core.converter.day.CarSyncInfoToDto;
import com.navinfo.opentsp.dongfeng.openapi.core.converter.param.CarParamToDto;
import com.navinfo.opentsp.dongfeng.openapi.core.pojo.CarInfoYqPoJo;
import com.navinfo.opentsp.dongfeng.openapi.core.pojo.CarParamPoJo;
import com.navinfo.opentsp.dongfeng.openapi.core.pojo.CarSyncInfoPoJo;
import com.navinfo.opentsp.dongfeng.openapi.core.service.ICarDataBusinessService;
import com.navinfo.opentsp.dongfeng.openapi.core.util.OpenApiLoginUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author xltianc.zh
 *
 */
@Service
@SuppressWarnings("rawtypes")
public class CarDataBusinessServiceImpl extends BaseService implements ICarDataBusinessService {
	@Autowired
	AuthorityClient authorityClient;;

	@SuppressWarnings("unchecked")
	@Override
	public HttpCommandResultWithData queryAddOrUpdateCarInfo(GetCarInfoCommand command) {
		HttpCommandResultWithData result = OpenApiLoginUtil.getInstance().resultData();
		// 获取登陆参数
		OpenApiLoginCommand openApiLoginCommand = OpenApiLoginUtil.getInstance().getOpenApiLoginCommand(command);
		// 获取返回结果
		CommonResult openApiloginResult = authorityClient.openApilogin(openApiLoginCommand);
		// 登陆成功
		if (openApiloginResult != null && openApiloginResult.getResultCode() == ReturnCode.OK.code()) {
			Map<String, Object> parameters = new HashMap<>();
			parameters.put("day", Integer.parseInt(command.getDay()));
			List<CarInfoYqPoJo> pageInfo = dao.sqlFind("queryAddOrUpdateCarInfo", parameters, CarInfoYqPoJo.class);
			result.setData(CarInfoYqToDto.convert(pageInfo));
		} else {
			// 登陆失败
			result.setMessage(openApiloginResult.getMessage());
			result.setResultCode(openApiloginResult.getResultCode());
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public HttpCommandResultWithData queryCarParam(GetCarParamCommand command) {
		HttpCommandResultWithData result = OpenApiLoginUtil.getInstance().resultData();
		// 获取登陆参数
		OpenApiLoginCommand openApiLoginCommand = OpenApiLoginUtil.getInstance().getOpenApiLoginCommand(command);
		// 获取返回结果
		CommonResult openApiloginResult = authorityClient.openApilogin(openApiLoginCommand);

		// 登陆成功
		if (openApiloginResult != null && openApiloginResult.getResultCode() == ReturnCode.OK.code()) {
			Map<String, Object> parameters = new HashMap<>();
			parameters.put("minute", Integer.parseInt(command.getMinute()));
			List<CarParamPoJo> pageInfo = dao.sqlFind("queryCarParamInfo", parameters, CarParamPoJo.class);
			result.setData(CarParamToDto.convert(pageInfo));
		} else {
			// 登陆失败
			result.setMessage(openApiloginResult.getMessage());
			result.setResultCode(openApiloginResult.getResultCode());
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public HttpCommandResultWithData selectCarSyncInfoByNum(CarSyncInfoByTimeCommand command) {
		HttpCommandResultWithData result = OpenApiLoginUtil.getInstance().resultData();
		// 获取登陆参数
		OpenApiLoginCommand openApiLoginCommand = OpenApiLoginUtil.getInstance().getOpenApiLoginCommand(command);
		// 获取返回结果
		CommonResult openApiloginResult = authorityClient.openApilogin(openApiLoginCommand);
		// 登陆成功
		if (openApiloginResult != null && openApiloginResult.getResultCode() == ReturnCode.OK.code()) {
			int day = Integer.parseInt(command.getDay());
			long endTime = System.currentTimeMillis() / 1000;
			long beginTime = endTime - day * 24 * 60 * 60;
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("beginTime", beginTime);
			parameters.put("endTime", endTime);
			parameters.put("searchFlag", day);
			List<CarSyncInfoPoJo> pageInfo = dao.sqlFind("selectCarSyncInfoByTime", parameters, CarSyncInfoPoJo.class);
			if (pageInfo == null || pageInfo.isEmpty()) {
				result.setMessage("没查到信息");
				return result;
			}
			result.setData(CarSyncInfoToDto.convert(pageInfo));
		} else {
			// 登陆失败
			result.setMessage(openApiloginResult.getMessage());
			result.setResultCode(openApiloginResult.getResultCode());
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public HttpCommandResultWithData updatePlateNum(UpdatePlateNumCommand command) {
		HttpCommandResultWithData result = OpenApiLoginUtil.getInstance().resultData();
		// 获取登陆参数
		OpenApiLoginCommand openApiLoginCommand = OpenApiLoginUtil.getInstance().getOpenApiLoginCommand(command);
		// 获取返回结果
		CommonResult openApiloginResult = authorityClient.openApilogin(openApiLoginCommand);
		// 登陆成功
		if (openApiloginResult != null && openApiloginResult.getResultCode() == ReturnCode.OK.code()) {
			try {
				if (command.getPlateNum() == null || command.getPlateNum().isEmpty()) {
					command.setPlateNum("");
				}
				dao.executeUpdate("updatePlateNum", command);
			} catch (Exception e) {
				logger.error("更新车牌号异常", e);
				throw e;
			}
		} else {
			// 登陆失败
			result.setMessage(openApiloginResult.getMessage());
			result.setResultCode(openApiloginResult.getResultCode());
		}
		return result;
	}

}
