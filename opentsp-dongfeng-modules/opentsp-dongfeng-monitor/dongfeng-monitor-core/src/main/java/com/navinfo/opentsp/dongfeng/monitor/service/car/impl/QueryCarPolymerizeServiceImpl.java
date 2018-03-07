package com.navinfo.opentsp.dongfeng.monitor.service.car.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lc.core.protocol.common.LCLocationData;
import com.navinfo.opentsp.dongfeng.common.cache.GpsCache;
import com.navinfo.opentsp.dongfeng.common.cache.GpsInfoCache;
import com.navinfo.opentsp.dongfeng.common.constant.CloudConstants;
import com.navinfo.opentsp.dongfeng.common.dto.GpsInfoData;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.common.service.BaseService;
import com.navinfo.opentsp.dongfeng.common.util.ListUtil;
import com.navinfo.opentsp.dongfeng.common.util.StringUtil;
import com.navinfo.opentsp.dongfeng.common.util.httpUtil.HttpUtil;
import com.navinfo.opentsp.dongfeng.monitor.commands.car.QueryCarPolymerizeCommand;
import com.navinfo.opentsp.dongfeng.monitor.pojo.car.QueryPolymerizePojo;
import com.navinfo.opentsp.dongfeng.monitor.pojo.car.QuerySearchTreePojo;
import com.navinfo.opentsp.dongfeng.monitor.service.car.IQueryCarPolymerizeService;
import com.navinfo.opentsp.dongfeng.monitor.service.car.IQuerySearchTreeService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 *
 * @author xltianc-zh
 *
 */
@SuppressWarnings("rawtypes")
@Service
public class QueryCarPolymerizeServiceImpl extends BaseService implements IQueryCarPolymerizeService {

	private static final Logger logger = LoggerFactory.getLogger(QueryCarPolymerizeServiceImpl.class);

	private static final int POLYMERIZE_SIGNAL_VEHICLE_COUNT = 1;

	@Autowired
	private GpsCache gpsCache;
	@Autowired
	private GpsInfoCache gpsInfoCache;

	@Value("${polymerizePath}")
	private String polymerizePath;

	@Autowired
	private IQuerySearchTreeService searchTreeService;

	@Override
	public HttpCommandResultWithData queryCarPolymerize(QueryCarPolymerizeCommand command) {

		/**
		Map<String, Object> params = new HashMap<>(16);
		params.put("accountId", command.getUserInfor().getUserId());
		params.put("accountType", command.getUserInfor().getType());
		params.put("searchType", command.getSearchType());
		params.put("searchText", command.getSearchText());
		params.put("engineType", command.getEngineType());
		params.put("carType", command.getCarType());
		params.put("carPlace", command.getCarPlace());
		params.put("stdSalesStatus", command.getStdSalesStatus());
		params.put("aakSalesStatus", command.getAakSalesStatus());
		params.put("dealer", command.getDealer());
		params.put("customer", command.getCustomer());

		List<QuerySearchTreePojo> cars = new ArrayList<>();
		if (command.getUserInfor().getType() == UserTypeConstant.SYSTEM_ADMIN.getCode()
				|| command.getUserInfor().getType() == UserTypeConstant.CAR_FACTORY.getCode()
				|| command.getUserInfor().getType() == UserTypeConstant.BUSINESS.getCode()) {
			cars = queryNormalCarTreeSearch(params);
		} else if (command.getUserInfor().getType() == UserTypeConstant.TRANSPORT.getCode()) {
			cars = queryTransportCarTreeSearch(params);
		}

		List<QueryPolymerizePojo> polymerizePojos = filterVehicleStatus(command, cars);

		return queryPolymerize(command, polymerizePojos);
        **/

		return queryPolymerize(command, null);
	}

	private List<QueryPolymerizePojo> filterVehicleStatus(QueryCarPolymerizeCommand command, List<QuerySearchTreePojo> cars) {
		logger.info("filterVehicleStatus start");
		// 获取所有车辆状态缓存
		Map<String, GpsInfoData> gpsInfoMap = gpsInfoCache.getAllGpsInfoMap();
		List<QueryPolymerizePojo> polymerizePojos = new ArrayList<>();
		for (QuerySearchTreePojo car : cars) {
			QueryPolymerizePojo polymerizePojo = new QueryPolymerizePojo();
			// 在线状态封装入对象
			GpsInfoData gpsInfodata = gpsInfoMap.get(car.getCommId().toString());

			final boolean isGpsInfoCacheEmpty = (gpsInfodata == null || gpsInfodata.getCarStatue() == null);
			polymerizePojo.setCarStauts(isGpsInfoCacheEmpty ? Integer.valueOf(CloudConstants.CarState.VehicleStatusOfflineInvalid.getValue())
					: gpsInfodata.getCarStatue());

			polymerizePojo.setId(car.getCarId());
			polymerizePojo.setPlateNum(car.getCarCph());
			polymerizePojo.setChassisNum(car.getChassisNum());
			polymerizePojo.setCommId(String.valueOf(car.getCommId()));

			if (command.getCarStauts() != null) {
				if (searchTreeService.hasStauts(polymerizePojo.getCarStauts(), command.getCarStauts())) {
					polymerizePojos.add(polymerizePojo);
				}
			} else {
				polymerizePojos.add(polymerizePojo);
			}
		}
		cars.clear();
		logger.info("filterVehicleStatus end");
		return polymerizePojos;
	}

	/**
	 * 搜索系统管理员、车厂、经销商用户的车辆
	 *
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<QuerySearchTreePojo> queryNormalCarTreeSearch(Map<String, Object> params) {
		logger.info("queryNormalCarTreeSearch start");
		List<QuerySearchTreePojo> cars = null;
		try {
			// 获取用户权限下车辆
			cars = dao.sqlFind("searchCarsByAccountId", params, QuerySearchTreePojo.class);
		} catch (Exception e) {
			logger.error("queryNormalCarTreeSearch is error ", e);
			cars = Collections.emptyList();
		}
		logger.info("queryNormalCarTreeSearch end");
		return cars;
	}

	/**
	 * 搜索运输企业用户的车辆
	 *
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<QuerySearchTreePojo> queryTransportCarTreeSearch(Map<String, Object> params) {
		logger.info("queryTransportCarTreeSearch start");
		List<QuerySearchTreePojo> cars = null;
		try {
			// 获取用户权限下车辆
			cars = dao.sqlFind("searchBusiCarsByAccountId", params, QuerySearchTreePojo.class);
		} catch (Exception e) {
			logger.error("queryTransportCarTreeSearch is error ", e);
			cars = Collections.emptyList();
		}
		logger.info("queryTransportCarTreeSearch end");
		return cars;
	}

	@SuppressWarnings("unchecked")
	public HttpCommandResultWithData queryPolymerize(QueryCarPolymerizeCommand command, List<QueryPolymerizePojo> polymerizePojos) {
		HttpCommandResultWithData result = new HttpCommandResultWithData();

		String terminalIdsParam = "";
		if(polymerizePojos != null){
			List<Long> commandIds = new ArrayList<>();
			for (QueryPolymerizePojo polymerizePojo : polymerizePojos) {
				if (polymerizePojo.getCommId() != null) {
					commandIds.add(Long.parseLong(polymerizePojo.getCommId()));
				}
			}

			logger.info("queryPolymerize for car size is {}, communicationId size is {}",
					CollectionUtils.size(polymerizePojos), CollectionUtils.size(commandIds));
			if (ListUtil.isEmpty(commandIds)) {
				logger.warn("queryPolymerize parameters for commuIds is empty");
				return result;
			}
			terminalIdsParam += "terminalIds=";
			terminalIdsParam += ListUtil.list2String(commandIds);
			terminalIdsParam += "&";
		}


		// 获取所有有效末次位置数据
		Map<String,LCLocationData.LocationData> validGps = gpsCache.getAllLastGpsMap();

		try {
			String param = terminalIdsParam+"userId=" + command.getUserInfor().getUserId()
					+ "&level=" + command.getLevel() + "&leftLongitude=" + command.getLeftLng() + "&leftLatitude="
					+ command.getLeftLat() + "&rightLongitude=" + command.getRightLng() + "&rightLatitude="
					+ command.getRightLat();
			//String resultPolymerize = HttpUtil.get(polymerizePath, param, "");
			String resultPolymerize = HttpUtil.post(polymerizePath, param, "");

			JSONObject response = JSON.parseObject(resultPolymerize);
			final int resultCode = response.getIntValue("code");
			if (resultCode == 200) {
				JSONArray data = new JSONArray();
				JSONArray list = response.getJSONObject("data").getJSONArray("list");
				List<String> chassisNums = new ArrayList<String>();
				Map<String,JSONObject> map = new HashMap<String,JSONObject>();
				for (Object object : list) {
					//JSONObject jsonObject = new JSONObject();
					JSONObject jb = (JSONObject) JSON.toJSON(object);
					final int count = jb.getIntValue("count");
					if (count == POLYMERIZE_SIGNAL_VEHICLE_COUNT) {
						final String chassisNum = jb.getString("carNo");
						if(polymerizePojos != null){
							for (QueryPolymerizePojo polymerizePojo : polymerizePojos) {
								if (StringUtil.isNotEmpty(chassisNum)
										&& (chassisNum.equals(polymerizePojo.getChassisNum()) || chassisNum.equals(polymerizePojo.getPlateNum()))) {
									// 位置数据填充对象其余值
									LCLocationData.LocationData locationData = validGps.get(polymerizePojo.getCommId().toString());
									if (locationData != null) {
										polymerizePojo.setGpstime(locationData.getGpsDate());
										polymerizePojo.setDirection(locationData.getDirection());
										polymerizePojo.setLat(locationData.getLatitude() * 0.000001);
										polymerizePojo.setLng(locationData.getLongitude() * 0.000001);
									}
									jb.put("car", polymerizePojo);
									break;
								}
							}
						}else{
							chassisNums.add(chassisNum.trim());
							map.put(chassisNum.trim(),jb);

						}

					}

					jb.put("count", count);
					jb.put("lat", jb.getDoubleValue("latitude"));
					jb.put("lng", jb.getDoubleValue("longitude"));
					data.add(jb);
				}

				if(chassisNums.size()>0){
					Map<String,Object> params = new HashMap<String,Object>();
					params.put("chassisNums", StringUtils.join(chassisNums, ","));
					List<QueryPolymerizePojo> pojos =  dao.sqlFind("selectCarsBychassisNums", params, QueryPolymerizePojo.class);
					for (QueryPolymerizePojo carLocPojo:pojos) {
						//位置数据填充对象其余值
						LCLocationData.LocationData locationData = gpsCache.getLastGpsVlid(carLocPojo.getCommId().toString());
						if(locationData!=null){
							carLocPojo.setGpstime(locationData.getGpsDate());
							carLocPojo.setDirection(locationData.getDirection());
							carLocPojo.setLat(locationData.getLatitude()* 0.000001);
							carLocPojo.setLng(locationData.getLongitude()* 0.000001);
						}
						//在线状态封装入对象
						GpsInfoData gpsInfodata = gpsInfoCache.getGpsInfo(carLocPojo.getCommId().toString());
						carLocPojo.setCarStauts(gpsInfodata==null||gpsInfodata.getCarStatue()==null? CloudConstants.CarState.VehicleStatusOfflineInvalid.getValue():gpsInfodata.getCarStatue());
						JSONObject jb = map.get(carLocPojo.getChassisNum());
						jb.put("car",carLocPojo);
					}
				}

				result.setData(data);
			} else {
				result.setResultCode(resultCode);
				result.setMessage(response.getString("msg"));
			}
		} catch (Exception e) {
			logger.error("queryPolymerize is error: ", e);
			result.fillResult(ReturnCode.SERVER_ERROR);
		}
		if(polymerizePojos != null){
			polymerizePojos.clear();
		}
		return result;
	}
}
