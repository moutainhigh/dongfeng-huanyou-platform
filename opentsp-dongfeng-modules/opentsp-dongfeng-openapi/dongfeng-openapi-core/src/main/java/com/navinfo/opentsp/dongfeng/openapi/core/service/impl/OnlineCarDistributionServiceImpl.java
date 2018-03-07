package com.navinfo.opentsp.dongfeng.openapi.core.service.impl;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.service.BaseService;
import com.navinfo.opentsp.dongfeng.common.service.IRestTemplateService;
import com.navinfo.opentsp.dongfeng.common.util.DateUtil;
import com.navinfo.opentsp.dongfeng.common.util.JsonUtil;
import com.navinfo.opentsp.dongfeng.openapi.commands.bigdata.QueryCarOnlineInfosCommand;
import com.navinfo.opentsp.dongfeng.openapi.commands.bigdata.QueryOnlineCarDistributionCommand;
import com.navinfo.opentsp.dongfeng.openapi.core.pojo.OnlineCarDistributionPojo;
import com.navinfo.opentsp.dongfeng.openapi.core.service.IOnlineCarDistributionService;
import com.navinfo.opentsp.dongfeng.openapi.core.util.OpenApiLoginUtil;
import com.navinfo.opentsp.dongfeng.openapi.dto.bigdata.CarOnlineInfosDto;
import com.navinfo.opentsp.dongfeng.openapi.dto.bigdata.OnlineCarDistributionDto;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

/**
 * 
 * @author xltianc.zh
 *
 */
@Service
@SuppressWarnings("rawtypes")
public class OnlineCarDistributionServiceImpl extends BaseService implements IOnlineCarDistributionService {

	@Autowired
	private IRestTemplateService restTemplateService;

	@Value("${cloud.districting.url:http://domain/dataview/location/carsInDistrictOnlineNum}")
	private String districting;

	@Value("${cloud.current.url:http://domain/dataview/location/currentCarsOnline}")
	private String current;

	@SuppressWarnings("unchecked")
	@Override
	public HttpCommandResultWithData onlineCarDistribution(QueryOnlineCarDistributionCommand command) {
		HttpCommandResultWithData result = OpenApiLoginUtil.getInstance().resultData();

		List<OnlineCarDistributionDto> dtos = new ArrayList<OnlineCarDistributionDto>();
		try {
			StringBuffer url = new StringBuffer(districting);
			url.append("?queryDay=").append(DateUtil.formatSimDate(new Date()));
			url.append("&districtType=").append(0);
			url.append("&districtCode=").append(0);
			ResponseEntity<String> response = restTemplateService.getForEntity(url.toString(), String.class);
			String responseBody = response.getBody();
			Map<String, Object> map = JsonUtil.toMap(responseBody);
			if (Integer.parseInt(Objects.toString(map.get("resultCode"))) == 200) {
				JSONArray data = JSONArray.fromObject(map.get("data"));
                if (data != null)
                {
                    Collection<OnlineCarDistributionPojo> pojo =
                        JSONArray.toCollection(data, OnlineCarDistributionPojo.class);
                    Map<String, Object> pMap = new HashMap<String, Object>();
                    if (pojo != null)
                    {
                        for (OnlineCarDistributionPojo onlineCarDistributionPojo : pojo)
                        {
                            if (onlineCarDistributionPojo != null)
                            {
                                OnlineCarDistributionDto dto = new OnlineCarDistributionDto();
                                pMap.put("district", onlineCarDistributionPojo.getDistrict());
                                List<String> cityName = (List<String>)dao.sqlFindField("queryBaseAreaInfo", pMap);
                                if (CollectionUtils.isNotEmpty(cityName))
                                {
                                    dto.setDistrict(cityName.get(0));
                                }
                                dto.setCounts(Long.valueOf(onlineCarDistributionPojo.getNumber()));
                                dtos.add(dto);
                            }
                        }
					}
                }
			}
			Collections.sort(dtos, new Comparator<OnlineCarDistributionDto>() {
				@Override
				public int compare(OnlineCarDistributionDto o1, OnlineCarDistributionDto o2) {
					return o2.getCounts().compareTo(o1.getCounts());
				}
			});
			result.setData(dtos);
		} catch (IOException e) {
			logger.error("query carsInDistrictOnlineNum from cloud is error", e);
		}

		// OnlineCarDistributionDto dt00 = new OnlineCarDistributionDto();
		// dt00.setDistrict("河北省 ");
		// dt00.setCounts(5100L);
		// dtos.add(dt00);
		//
		// OnlineCarDistributionDto dt01 = new OnlineCarDistributionDto();
		// dt01.setDistrict("山西省");
		// dt01.setCounts(4251L);
		// dtos.add(dt01);
		//
		// OnlineCarDistributionDto dt02 = new OnlineCarDistributionDto();
		// dt02.setDistrict("山东省");
		// dt02.setCounts(3300L);
		// dtos.add(dt02);
		//
		// OnlineCarDistributionDto dt03 = new OnlineCarDistributionDto();
		// dt03.setDistrict("江苏省");
		// dt03.setCounts(3100L);
		// dtos.add(dt03);
		//
		// OnlineCarDistributionDto dt04 = new OnlineCarDistributionDto();
		// dt04.setDistrict("河南省");
		// dt04.setCounts(2152L);
		// dtos.add(dt04);
		// result.setData(dtos);
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public HttpCommandResultWithData queryCarOnlineInfos(QueryCarOnlineInfosCommand command) {
		HttpCommandResultWithData result = OpenApiLoginUtil.getInstance().resultData();
		List<CarOnlineInfosDto> dtos = new ArrayList<CarOnlineInfosDto>();
		try {
			ResponseEntity<String> response = restTemplateService.getForEntity(current, String.class);
			String responseBody = response.getBody();

			Map<String, Object> map = JsonUtil.toMap(responseBody);
			if (Integer.parseInt(Objects.toString(map.get("resultCode"))) == 200) {
				JSONObject data = JSONObject.fromObject(map.get("data"));
				JSONArray jsonObject = JSONArray.fromObject(data.get("today"));
				JSONArray jsonObject1 = JSONArray.fromObject(data.get("yesterday"));
				Object[] todays = JSONArray.toCollection(jsonObject, String.class).toArray();
				Object[] yesterdays = JSONArray.toCollection(jsonObject1, String.class).toArray();
				// 获取当前日期
				String date = DateUtil.formatDate(new Date());

				for (int i = 0; i < todays.length; i++) {
					CarOnlineInfosDto dto = new CarOnlineInfosDto();
					if (i < 10) {
						dto.setTime(date + " 0" + i);
					} else {
						dto.setTime(date + " " + i);
					}
					dto.setCurrentCounts(Long.valueOf(todays[i].toString()));
					dto.setYesterdayCounts(Long.valueOf(yesterdays[i].toString()));
					dtos.add(dto);
				}
			}
			result.setData(dtos);
		} catch (IOException e) {
			logger.error("query queryCarOnlineInfos from cloud is error", e);
		}
		return result;
	}

}
