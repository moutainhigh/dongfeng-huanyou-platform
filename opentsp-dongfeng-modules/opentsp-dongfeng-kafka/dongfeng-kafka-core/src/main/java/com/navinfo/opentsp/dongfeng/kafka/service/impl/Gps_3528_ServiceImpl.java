package com.navinfo.opentsp.dongfeng.kafka.service.impl;

import com.lc.core.protocol.webservice.newstatisticsdata.entity.LCInOrOutAreaNotify;
import com.navinfo.opentsp.dongfeng.common.cache.AreaEnterCache;
import com.navinfo.opentsp.dongfeng.common.dto.AreaEnterData;
import com.navinfo.opentsp.dongfeng.common.dto.Packet;
import com.navinfo.opentsp.dongfeng.common.service.BaseService;
import com.navinfo.opentsp.dongfeng.common.util.StringUtil;
import com.navinfo.opentsp.dongfeng.common.util.json.JacksonUtils;
import com.navinfo.opentsp.dongfeng.kafka.configuration.kafka.KafkaCommand;
import com.navinfo.opentsp.dongfeng.kafka.service.IGps_3528_Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 */
@Service(value = "gps_3528_Service")
public class Gps_3528_ServiceImpl extends BaseService implements IGps_3528_Service {

	protected static final Logger logger = LoggerFactory.getLogger(Gps_3528_ServiceImpl.class);
	@Autowired
	private AreaEnterCache areaEnterCache;

	@Override
	@Transactional
	public void insertSecondInfoWithCar(KafkaCommand kafkaCommand) throws Exception {
		try {
			Packet packet = JacksonUtils.objectMapperBuilder().readValue(kafkaCommand.getMessage(), Packet.class);
			LCInOrOutAreaNotify.InOrOutAreaNotify inOrOutAreaNotify = LCInOrOutAreaNotify.InOrOutAreaNotify
					.parseFrom(packet.getContent());
			String sim = kafkaCommand.getKey();
			long areaIdentify = inOrOutAreaNotify.getAreaIdentify();// areaIdentify
																	// 区域标识
			int status = inOrOutAreaNotify.getStatus();// status 1：进；2：出
			long gpsDate = inOrOutAreaNotify.getGpsDate();// gpsDate 当前gps时间
			int lat = inOrOutAreaNotify.getLat();// lat 纬度
			int lon = inOrOutAreaNotify.getLon();// lon 经度
			logger.info("sim: " + sim + " 3528：区域标识：" + areaIdentify + " 进出状态：" + (status == 1 ? "进" : "出") + " 当前时间："
					+ gpsDate + " 纬度：" + lat + " 经度：" + lon);
			if (StringUtil.isEmpty(sim)) {
				return;
			}
			Long areaId = 0L;
			Integer areaType = 0;
			if (areaIdentify / 10000000 == 95) {
				areaId = areaIdentify % 10000000;
				areaType = 12;
			} else if (areaIdentify < 100000000) {
				areaId = areaIdentify;
				areaType = 11;// 服务站一级网点
			} else {
				return;
			}
			long gpsDateTemp = gpsDate > 0 ? gpsDate : StringUtil.getCurrentTimeSeconds();
			AreaEnterData temp = areaEnterCache.getAreaEnterData(sim, areaId, areaType);
			if (temp == null) {
				temp = new AreaEnterData();
				temp.setSimNo(sim);
				temp.setAreaId(areaId);
				temp.setAreaType(areaType);
			}
			if (temp.getInTime() == null) {
				temp.setInTime(0L);
			}
			if (temp.getOutTime() == null) {
				temp.setOutTime(0L);
			}
			// 数据入库
			if (status == 1) {// 进站
				temp.setInTime(gpsDateTemp);
			} else if (status == 2) {// 出站
				// 出区域更新时间
				temp.setOutTime(gpsDateTemp);
			} else {
				return;
			}
			areaEnterCache.saveAreaEnterData(temp);
		} catch (Exception e) {
			logger.error("保存位置区域信息失败... ", e);
			throw e;
		}
	}

}
