package com.navinfo.opentsp.dongfeng.kafka.service.impl;

import com.navinfo.opentsp.dongfeng.common.service.BaseService;
import com.navinfo.opentsp.dongfeng.kafka.configuration.kafka.KafkaCommand;
import com.navinfo.opentsp.dongfeng.kafka.entity.HyCarEntity;
import com.navinfo.opentsp.dongfeng.kafka.pojo.CarInfoPojo;
import com.navinfo.opentsp.dongfeng.kafka.repository.CarRepository;
import com.navinfo.opentsp.dongfeng.kafka.service.IGps_3003_Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

/**
 * 位置信息service
 * <p>
 * Created by fwm on 2017/5/16
 */
@Service(value = "gps_3003_Service")
public class Gps_3003_ServiceImpl extends BaseService implements IGps_3003_Service {

    protected static final Logger logger = LoggerFactory.getLogger(Gps_3003_ServiceImpl.class);
    @Autowired
    private CarRepository carRepository;
    @Override
    @Transactional
    public void updateRegTime(KafkaCommand kafkaCommand) throws Exception {
        String sim = kafkaCommand.getKey();
        //获取车辆信息
        Map<String, Object> conMap = new HashMap<String, Object>();
        conMap.put("commId", new BigInteger(sim));
        //此处使用了3170的POJO实体类，因为只需要车辆ID字段
        CarInfoPojo pojo = (CarInfoPojo) dao.sqlFindObject("queryCarByCommId", conMap, CarInfoPojo.class);
        //获取到CAR对象
        HyCarEntity hyCarEntity = (HyCarEntity) carRepository.findByCarId(pojo.getCarId());
        //重新赋值注册时间，执行修改
        hyCarEntity.setRegisterTime(new BigInteger(String.valueOf(System.currentTimeMillis() / 1000)));
        dao.save(hyCarEntity);
        logger.info("kafka-----Gps_3003_ServiceImpl-----SIM卡号为"+sim+"的终端关联车辆注册时间更新");
    }
}
