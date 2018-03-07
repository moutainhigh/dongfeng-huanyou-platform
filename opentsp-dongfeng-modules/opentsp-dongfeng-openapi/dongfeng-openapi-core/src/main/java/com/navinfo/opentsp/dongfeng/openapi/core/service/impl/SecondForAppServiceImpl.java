package com.navinfo.opentsp.dongfeng.openapi.core.service.impl;

import com.navinfo.opentsp.dongfeng.authority.commands.OpenApiLoginCommand;
import com.navinfo.opentsp.dongfeng.common.cache.AreaEnterCache;
import com.navinfo.opentsp.dongfeng.common.client.AuthorityClient;
import com.navinfo.opentsp.dongfeng.common.constant.Constants;
import com.navinfo.opentsp.dongfeng.common.dto.AreaEnterData;
import com.navinfo.opentsp.dongfeng.common.result.CommonResult;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.common.service.BaseService;
import com.navinfo.opentsp.dongfeng.common.util.StringUtil;
import com.navinfo.opentsp.dongfeng.openapi.commands.time.ArriveStationTimeCommand;
import com.navinfo.opentsp.dongfeng.openapi.commands.time.StayTimeInAeraCommand;
import com.navinfo.opentsp.dongfeng.openapi.core.converter.time.SecondStationToDto;
import com.navinfo.opentsp.dongfeng.openapi.core.pojo.SecondStationPoJo;
import com.navinfo.opentsp.dongfeng.openapi.core.service.ISecondForAppService;
import com.navinfo.opentsp.dongfeng.openapi.core.util.OpenApiLoginUtil;
import com.navinfo.opentsp.dongfeng.openapi.dto.time.SecondStationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@SuppressWarnings("rawtypes")
public class SecondForAppServiceImpl extends BaseService implements ISecondForAppService {

	@Autowired
	AuthorityClient authorityClient;;

	@Autowired
	private AreaEnterCache areaEnterCache;

	@Override
	public HttpCommandResultWithData selectSecondInfoByParam(ArriveStationTimeCommand command) {
		HttpCommandResultWithData result = OpenApiLoginUtil.getInstance().resultData();
		// 获取登陆参数
		OpenApiLoginCommand openApiLoginCommand = OpenApiLoginUtil.getInstance().getOpenApiLoginCommand(command);
		// 获取返回结果
		CommonResult openApiloginResult = authorityClient.openApilogin(openApiLoginCommand);
		// 登陆成功
		if (openApiloginResult != null && openApiloginResult.getResultCode() == ReturnCode.OK.code()) {
			return processSelectSecondInfoByParam(command);
		} else {
			// 登陆失败
			result.setMessage(openApiloginResult.getMessage());
			result.setResultCode(openApiloginResult.getResultCode());
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	private HttpCommandResultWithData processSelectSecondInfoByParam(ArriveStationTimeCommand command) {
		HttpCommandResultWithData result = OpenApiLoginUtil.getInstance().resultData();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ssCode", command.getSsCode());
		map.put("secCode", command.getSecCode());
		List<BigInteger> list = dao.sqlFindField("selectSecondStationIdByParam", map);
		if (OpenApiLoginUtil.getInstance().checkList(list)) {
			result.setMessage("没有查询到记录");
			return result;
		}

		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("vin", command.getVin());
		List<BigInteger> cLists = dao.sqlFindField("selectCommunicationIdOfCar", map1);
		if (OpenApiLoginUtil.getInstance().checkList(cLists)) {
			result.setMessage("没有查询到记录");
			return result;
		}

		BigInteger communicationId = cLists.get(0);
		AreaEnterData areaEnterData = areaEnterCache.getAreaEnterData(communicationId.toString(),
				list.get(0).longValue(), Constants.AreaTypeEnum.SUBSTATION.getValue());
		if (areaEnterData == null) {
			result.setMessage("没有查询到记录");
			return result;
		}
		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("arriveTime", areaEnterData.getInTime());
		result.setData(map2);
		return result;
	}

	@Override
	public HttpCommandResultWithData selectStayTimeInAera(StayTimeInAeraCommand command) {
		HttpCommandResultWithData result = OpenApiLoginUtil.getInstance().resultData();
		// 获取登陆参数
		OpenApiLoginCommand openApiLoginCommand = OpenApiLoginUtil.getInstance().getOpenApiLoginCommand(command);
		// 获取返回结果
		CommonResult openApiloginResult = authorityClient.openApilogin(openApiLoginCommand);
		// 登陆成功
		if (openApiloginResult != null && openApiloginResult.getResultCode() == ReturnCode.OK.code()) {
			return processStayTimeInAera(command);
		} else {
			// 登陆失败
			result.setMessage(openApiloginResult.getMessage());
			result.setResultCode(openApiloginResult.getResultCode());
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	private HttpCommandResultWithData processStayTimeInAera(StayTimeInAeraCommand command) {
		HttpCommandResultWithData result = OpenApiLoginUtil.getInstance().resultData();
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("vin", command.getVin());
		parameters.put("aeraId", command.getAeraId());
		// 24小时之内
		long quertime = System.currentTimeMillis() / 1000 - 60 * 60 * 24;
		parameters.put("quertime", quertime);
		parameters.put("currentTime", System.currentTimeMillis() / 1000);
		List<BigInteger> list = dao.sqlFindField("selectSubStationIds", parameters);
		if (StringUtil.isEmpty(list)) {
			result.setMessage("没有查询到记录");
			return result;
		}

		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("vin", command.getVin());
		List<BigInteger> cLists = dao.sqlFindField("selectCommunicationIdOfCar", map1);
		if (OpenApiLoginUtil.getInstance().checkList(cLists)) {
			result.setMessage("没有查询到记录");
			return result;
		}
		BigInteger communicationId = cLists.get(0);
		List<SecondStationDto> datas = new ArrayList<>();
		SecondStationPoJo poJo = converterToPojo(communicationId.toString(), Long.parseLong(command.getAeraId()),
				Constants.AreaTypeEnum.STATION);
		if (poJo != null) {
			datas.add(SecondStationToDto.convert(poJo));
		}
		for (BigInteger subStationId : list) {
			SecondStationPoJo secondStationPoJo = converterToPojo(communicationId.toString(), subStationId.longValue(),
					Constants.AreaTypeEnum.SUBSTATION);
			if (secondStationPoJo != null) {
				datas.add(SecondStationToDto.convert(secondStationPoJo));
			}
		}
		result.setData(datas);
		return result;
	}

	/**
	 * @param communicationId
	 * @param stationId
	 * @param areaTypeEnum
	 * @return
	 */
	private SecondStationPoJo converterToPojo(String communicationId, long stationId,
			Constants.AreaTypeEnum areaTypeEnum) {
		AreaEnterData areaEnterData = areaEnterCache.getAreaEnterData(communicationId, stationId,
				areaTypeEnum.getValue());
		if (areaEnterData == null) {
			return null;
		}
		if (OpenApiLoginUtil.getInstance().isInTimeWindow(areaEnterData.getInTime())
				|| OpenApiLoginUtil.getInstance().isInTimeWindow(areaEnterData.getOutTime())) {
			long enterTime = areaEnterData.getInTime() > 0 ? areaEnterData.getInTime() : 0L;
			long outTime = areaEnterData.getOutTime() > enterTime ? areaEnterData.getOutTime()
					: StringUtil.getCurrentTimeSeconds();
			long stayTime = 0;
			if (enterTime > 0) {
				stayTime = outTime - enterTime;
				SecondStationPoJo poJo2 = new SecondStationPoJo();
				poJo2.setStayTime(StringUtil.toBigInteger(stayTime));
				poJo2.setAeraId(BigInteger.valueOf(areaEnterData.getAreaId()));
				poJo2.setAeraType(areaEnterData.getAreaType());
				poJo2.setEnterTime(StringUtil.toBigInteger(enterTime));
				poJo2.setOutTime(StringUtil.toBigInteger(outTime));
				return poJo2;
			}
		}
		return null;
	}

}
