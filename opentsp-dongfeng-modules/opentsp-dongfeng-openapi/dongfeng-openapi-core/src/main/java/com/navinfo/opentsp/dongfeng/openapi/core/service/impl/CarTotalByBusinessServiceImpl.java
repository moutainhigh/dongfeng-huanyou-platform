package com.navinfo.opentsp.dongfeng.openapi.core.service.impl;

import com.navinfo.opentsp.dongfeng.common.cache.GpsInfoCache;
import com.navinfo.opentsp.dongfeng.common.dto.GpsInfoData;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.service.BaseService;
import com.navinfo.opentsp.dongfeng.common.util.DateUtil;
import com.navinfo.opentsp.dongfeng.openapi.commands.bigdata.QueryCarTotalByTypeCommand;
import com.navinfo.opentsp.dongfeng.openapi.commands.bigdata.QueryCarTotalThatHaveAAKTimeCommand;
import com.navinfo.opentsp.dongfeng.openapi.core.pojo.VehiclePojo;
import com.navinfo.opentsp.dongfeng.openapi.core.service.ICarTotalByBusinessService;
import com.navinfo.opentsp.dongfeng.openapi.core.util.OpenApiLoginUtil;
import com.navinfo.opentsp.dongfeng.openapi.dto.bigdata.CarTotalByTypeDto;
import com.navinfo.opentsp.dongfeng.openapi.dto.bigdata.CarTotalThatHaveAAKTimeDto;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.*;

/**
 * 
 * @author xltianc.zh
 *
 */
@Service
@SuppressWarnings("rawtypes")
public class CarTotalByBusinessServiceImpl extends BaseService implements ICarTotalByBusinessService {

	/**
	 * 销售信息
	 *  历史累计销售=120万+15732（17年销售的非F9车辆）+17年销售的F9车辆
	 *  当年累计销售=    15732（17年销售的非F9车辆）+17年销售的F9车辆
	 *  注：销售取STD
	 */
	@Value("${bigdata.car.baseCounts}")
	private String baseCounts;
	@Value("${bigdata.car.saleCounts}")
	private String saleCounts;

	@Resource
	private GpsInfoCache gpsInfoCache;

	@SuppressWarnings("unchecked")
	@Override
	public HttpCommandResultWithData queryCarTotalThatHaveAAKTime(QueryCarTotalThatHaveAAKTimeCommand command) {
		HttpCommandResultWithData result = OpenApiLoginUtil.getInstance().resultData();
		CarTotalThatHaveAAKTimeDto aakTimeDto = new CarTotalThatHaveAAKTimeDto();

		Map<String, Object> map = new HashMap<String, Object>();
		Long minYearDate = DateUtil.getYearFirst();
		Long maxYearDate = DateUtil.getYearLast();
		map.put("BYW", "byYear");
		map.put("minYearDate", minYearDate);
		map.put("maxYearDate", maxYearDate);
		List<BigInteger> totals = (List<BigInteger>) dao.sqlFindField("queryCarTotalThatHaveAAKTimeTotal", map);
		if (CollectionUtils.isNotEmpty(totals)) {
			aakTimeDto.setTotal((totals.get(0) == null ? 0 : totals.get(0).longValue())+ NumberUtils.toLong(baseCounts)+NumberUtils.toLong(saleCounts));
		}
		List<BigInteger> totalByYear = (List<BigInteger>) dao.sqlFindField("queryCarTotalThatHaveAAKTimeTotal", map);
		if (CollectionUtils.isNotEmpty(totals)) {
			aakTimeDto.setTotalByYears(totalByYear.get(0) == null ? 0 : totalByYear.get(0).longValue());
		}
		Long minMonthDate = DateUtil.getMinMonthDate(DateUtil.formatDate(new Date()));
		Long maxMonthDate = DateUtil.getMaxMonthDate(DateUtil.formatDate(new Date()));
		map.put("BYW", "byMonth");
		map.put("minMonthDate", minMonthDate);
		map.put("maxMonthDate", maxMonthDate);
		List<BigInteger> totalByMonth = (List<BigInteger>) dao.sqlFindField("queryCarTotalThatHaveAAKTimeTotalForMonth", map);
		if (CollectionUtils.isNotEmpty(totals)) {
			aakTimeDto.setTotalByMonth(totalByMonth.get(0) == null ? 0 : totalByMonth.get(0).longValue());
		}

		// aakTimeDto.setTotal(74283L);
		// aakTimeDto.setTotalByYears(72544L);
		// aakTimeDto.setTotalByMonth(88L);

		result.setData(aakTimeDto);
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public HttpCommandResultWithData queryCarTotalByType(QueryCarTotalByTypeCommand command) {
		HttpCommandResultWithData result = OpenApiLoginUtil.getInstance().resultData();
		List<CarTotalByTypeDto> byTypeDtos = new ArrayList<CarTotalByTypeDto>();

		Map<String, Object> paramMap = new HashMap<String, Object>();
		String carIds = processCarIds();
		if (carIds == null || carIds.length() < 1) {
			return result;
		}
		paramMap.put("carIds", carIds);

		//厢式车归到载货
		CarTotalByTypeDto byTypeDtoXS = new CarTotalByTypeDto();
		List<BigInteger> totals4 = (List<BigInteger>) dao.sqlFindField("queryCarTotalByXSType", paramMap);
		if (CollectionUtils.isNotEmpty(totals4)) {
			byTypeDtoXS.setCarType("厢式车");
			byTypeDtoXS.setCarTotal(totals4.get(0).longValue());
		}
		List<BigInteger> totals = (List<BigInteger>) dao.sqlFindField("queryCarTotalByZHType", paramMap);
		if (CollectionUtils.isNotEmpty(totals)) {
			CarTotalByTypeDto byTypeDto1 = new CarTotalByTypeDto();
			byTypeDto1.setCarType("载货车");
			byTypeDto1.setCarTotal(totals.get(0).longValue()+byTypeDtoXS.getCarTotal());
			byTypeDtos.add(byTypeDto1);
		}

		//去掉越野
		/*List<BigInteger> totals1 = (List<BigInteger>) dao.sqlFindField("queryCarTotalByYEType", paramMap);
		if (CollectionUtils.isNotEmpty(totals1)) {
			CarTotalByTypeDto byTypeDto1 = new CarTotalByTypeDto();
			byTypeDto1.setCarType("越野车");
			byTypeDto1.setCarTotal(totals1.get(0).longValue());
			byTypeDtos.add(byTypeDto1);
		}*/

		List<BigInteger> totals2 = (List<BigInteger>) dao.sqlFindField("queryCarTotalByZXType", paramMap);
		if (CollectionUtils.isNotEmpty(totals2)) {
			CarTotalByTypeDto byTypeDto1 = new CarTotalByTypeDto();
			byTypeDto1.setCarType("自卸车");
			byTypeDto1.setCarTotal(totals2.get(0).longValue());
			byTypeDtos.add(byTypeDto1);
		}

		//半挂车归到牵引
		CarTotalByTypeDto byTypeDtoBG = new CarTotalByTypeDto();
		List<BigInteger> totals5 = (List<BigInteger>) dao.sqlFindField("queryCarTotalByBGType", paramMap);
		if (CollectionUtils.isNotEmpty(totals5)) {
			byTypeDtoBG.setCarType("半挂车");
			byTypeDtoBG.setCarTotal(totals5.get(0).longValue());
		}
		List<BigInteger> totals3 = (List<BigInteger>) dao.sqlFindField("queryCarTotalByQYType", paramMap);
		if (CollectionUtils.isNotEmpty(totals3)) {
			CarTotalByTypeDto byTypeDto1 = new CarTotalByTypeDto();
			byTypeDto1.setCarType("牵引车");
			byTypeDto1.setCarTotal(totals3.get(0).longValue()+byTypeDtoBG.getCarTotal());
			byTypeDtos.add(byTypeDto1);
		}





		// CarTotalByTypeDto byTypeDto0 = new CarTotalByTypeDto();
		// byTypeDto0.setCarType("载货");
		// byTypeDto0.setCarTotal(12366L);
		// byTypeDtos.add(byTypeDto0);
		//
		// CarTotalByTypeDto byTypeDto1 = new CarTotalByTypeDto();
		// byTypeDto1.setCarType("自卸");
		// byTypeDto1.setCarTotal(10122L);
		// byTypeDtos.add(byTypeDto1);
		//
		// CarTotalByTypeDto byTypeDto2 = new CarTotalByTypeDto();
		// byTypeDto2.setCarType("牵引");
		// byTypeDto2.setCarTotal(4904L);
		// byTypeDtos.add(byTypeDto2);
		//
		// CarTotalByTypeDto byTypeDto3 = new CarTotalByTypeDto();
		// byTypeDto3.setCarType("厢式");
		// byTypeDto3.setCarTotal(3510L);
		// byTypeDtos.add(byTypeDto3);

		result.setData(byTypeDtos);
		return result;
	}

	private String processCarIds() {
		StringBuilder carIds = new StringBuilder();
		@SuppressWarnings("unchecked")
		List<VehiclePojo> vehicles = dao.sqlFind("queryVehicles", new HashMap<String, Object>(), VehiclePojo.class);
		// 获取所有车辆状态缓存
		Map<String, GpsInfoData> gpsInfoMap = gpsInfoCache.getAllGpsInfoMap();
		for (VehiclePojo vehicle : vehicles) {
			if (checkOnline(gpsInfoMap, vehicle)) {
				carIds.append(vehicle.getCarId()).append(",");
			}
		}
		if (carIds.length() > 1) {
			carIds.delete(carIds.length() - 1, carIds.length());
		}
		return carIds.toString();
	}

	private boolean checkOnline(Map<String, GpsInfoData> gpsInfoMap, VehiclePojo vehicle) {
		if (MapUtils.isEmpty(gpsInfoMap)) {
			return false;
		}
		GpsInfoData gpsInfo = gpsInfoMap.get(vehicle.getCommId() + "");
		if(gpsInfo != null && gpsInfo.getCarStatue() != null && (gpsInfo.getCarStatue() & 1) >0){
			Long gpsDate = gpsInfo.getGpsDate();
			// 最新gps时间在10分钟内
			if (gpsDate != null && gpsDate != 0 && System.currentTimeMillis() / 1000 - gpsDate < 600)
			{
				return true;
			}
		}
		return false;
	}

}
