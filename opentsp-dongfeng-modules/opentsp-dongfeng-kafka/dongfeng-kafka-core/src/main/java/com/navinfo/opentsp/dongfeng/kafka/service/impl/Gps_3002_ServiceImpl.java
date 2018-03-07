package com.navinfo.opentsp.dongfeng.kafka.service.impl;


import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.lc.core.protocol.common.LCLocationData;
import com.lc.core.protocol.common.LCStatusType;
import com.lc.core.protocol.common.LCVehicleStatusData;
import com.mongodb.client.MongoCollection;
import com.navinfo.opentsp.dongfeng.common.cache.AlarmCache;
import com.navinfo.opentsp.dongfeng.common.cache.CarStatueCache;
import com.navinfo.opentsp.dongfeng.common.cache.GpsCache;
import com.navinfo.opentsp.dongfeng.common.dto.Packet;
import com.navinfo.opentsp.dongfeng.common.service.BaseService;
import com.navinfo.opentsp.dongfeng.common.service.IRedisService;
import com.navinfo.opentsp.dongfeng.common.util.MongoDBUtil;
import com.navinfo.opentsp.dongfeng.common.util.RedisDBUtil;
import com.navinfo.opentsp.dongfeng.common.util.StringUtil;
import com.navinfo.opentsp.dongfeng.common.util.TimeUtil;
import com.navinfo.opentsp.dongfeng.common.util.json.JacksonUtils;
import com.navinfo.opentsp.dongfeng.kafka.client.CmdClient;
import com.navinfo.opentsp.dongfeng.kafka.configuration.kafka.KafkaCommand;
import com.navinfo.opentsp.dongfeng.kafka.entity.HyCarEntity;
import com.navinfo.opentsp.dongfeng.kafka.entity.VehicleOilChangeEntity;
import com.navinfo.opentsp.dongfeng.kafka.entity.VehicleOilExistEntity;
import com.navinfo.opentsp.dongfeng.kafka.pojo.CarInfoPojo;
import com.navinfo.opentsp.dongfeng.kafka.pojo.Gps_2170_Cmd;
import com.navinfo.opentsp.dongfeng.kafka.pojo.Gps_2271_Cmd;
import com.navinfo.opentsp.dongfeng.kafka.repository.CarRepository;
import com.navinfo.opentsp.dongfeng.kafka.service.IGps_3002_Service;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

/**
 * 位置信息service
 * <p>
 * Created by zhangyu on 2017/3/10.
 */
@Service(value = "gps_3002_Service")
public class Gps_3002_ServiceImpl extends BaseService implements IGps_3002_Service {

    @Value("${3002.use.pipelined}")
    private String use_pipelined;

    @Value("${3002.stop.alarm.server}")
    private String stop_alarm;

    @Autowired
    private CmdClient cmdClient;

    protected static final Logger logger = LoggerFactory.getLogger(Gps_3002_ServiceImpl.class);

    @Autowired
    private GpsCache gpsCache;

    @Autowired
    private AlarmCache alamCache;

    @Autowired
    private CarStatueCache statueCache;
    @Autowired
    private CarRepository carRepository;
    @Resource
    private IRedisService redisService;

    // 油箱容积的MAP键
    private final static String redis_vehicle_oil_volume = "vehicle_oil_volume";
    // 上一个有效点的MAP键
    private final static String redis_vehicle_oil_exist = "vehicle_oil_exist";
    // 存在加油减油的MAP键
    private final static String redis_vehicle_oil_change = "vehicle_oil_change";
    // 存在加油减油的MAP键
    private final static String collectionName = "vehicle_change_oil_";

    // 油量变化阀值
    private long t_oilChange;
    // 油量变化时间间隔阀值
    private long t_oilChangeInterval;

    private RedisDBUtil redisDBUtil;
    private MongoDBUtil mongoDBUtil;

    /**
     * 位置信息写入缓存
     *
     * @param kafkaCommand
     * @throws JsonProcessingException
     */
    @Override
    public void writeToCache(KafkaCommand kafkaCommand) throws IOException {
        String sim = kafkaCommand.getKey();

        try {
            Packet packet = JacksonUtils.objectMapperBuilder().readValue(kafkaCommand.getMessage(), Packet.class);
            byte[] locationDataByte = packet.getContent();
            LCLocationData.LocationData locationData = LCLocationData.LocationData.parseFrom(locationDataByte);

            if (Boolean.parseBoolean(use_pipelined)) {
                // 管道方式
                List<Map> params = new ArrayList<Map>();
                // 添加末次位置缓存
                gpsCache.commitLastGps(sim, locationDataByte, locationData, params);

                // 添加末次有效位置缓存
                gpsCache.commitLastGpsValid(sim, locationDataByte, locationData, params);

                // 添加车辆相关信息缓存
                statueCache.commitCarStatue(sim, locationData, null, params);

                if (params.size() > 0) {
                    redisService.commitRedisHash(params);
                }

                // 添加报警提醒缓存
                if (locationData != null) {
                    if (!Boolean.parseBoolean(stop_alarm)) {
                        alamCache.alarmConverter(sim, locationData);
                    }
                }
            } else {
                // 添加末次位置缓存
                gpsCache.addLastGps(sim, locationDataByte, locationData);

                // 添加末次有效位置缓存
                gpsCache.addLastGpsValid(sim, locationDataByte, locationData);

                // 添加车辆相关信息缓存
                statueCache.addCarStatue(sim, locationData, null);

                // 添加报警提醒缓存
                if (locationData != null) {
                    if (!Boolean.parseBoolean(stop_alarm)) {
                        alamCache.alarmConverter(sim, locationData);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("更新末次位置失败", e);
        }
    }

    // 判断是否有离线缓存需要下发
    @Override
    public void sendOffline(KafkaCommand kafkaCommand) {
        String sim = kafkaCommand.getKey();
        // redis中是否有缓存指令需要下发
        Set<String> keys = redisService.getKeys("MSGPUSH_*_" + sim + "_" + 2170);
        if (keys == null) {
            return;
        }
        Iterator<String> it = keys.iterator();
        Gps_2170_Cmd data = null;
        while (it.hasNext()) {
            data = redisService.getJson(it.next(), Gps_2170_Cmd.class);
        }
        if (data == null) {
            return;
        }
        // 获取车辆信息
        Map<String, Object> conMap = new HashMap<String, Object>();
        conMap.put("commId", new BigInteger(sim));
        CarInfoPojo pojo = (CarInfoPojo) dao.sqlFindObject("queryCarByCommId", conMap, CarInfoPojo.class);
        data.setStatue(1);
        // 判断是否需要下发(车辆状态与要下发的指令不符，则不发（比如车辆未激活状态，则只可以下发激活指令，其他指令均不发）)
        try {
            if (data.getFlag() == 0 && data.getSign() == 1
                    && (pojo.getLockStatue() == null || pojo.getLockStatue() == 0 || pojo.getLockStatue() == 4)) {// 激活
                cmdClient.call_2170(data);
                // 下发2217指令
                Set<String> key = redisService.getKeys("MSGPUSH_*_" + sim + "_" + 2271);
                Iterator<String> its = key.iterator();
                Gps_2271_Cmd cmd = null;
                while (its.hasNext()) {
                    cmd = redisService.getJson(its.next(), Gps_2271_Cmd.class);
                }
                if (cmd != null) {
                    cmdClient.call_2271(cmd);
                }
            } else if (data.getFlag() == 0 && data.getSign() == 2
                    && (pojo.getLockStatue() == 2 || pojo.getLockStatue() == 5)) { // 取消激活
                cmdClient.call_2170(data);
            } else if (data.getFlag() == 1 && data.getSign() == 3
                    && (pojo.getLockStatue() == 2 || pojo.getLockStatue() == 6)) {// 锁车
                cmdClient.call_2170(data);
            } else if (data.getFlag() == 1 && data.getSign() == 4
                    && (pojo.getLockStatue() == 3 || pojo.getLockStatue() == 7)) { // 取消锁车
                cmdClient.call_2170(data);
            }
        } catch (Exception e) {
            logger.error("离线指令发送异常", e);
        }

    }

    @Override
    @Transactional
    public void updateFirstOnlineTime(KafkaCommand kafkaCommand) throws Exception {
        try {
            Packet packet = JacksonUtils.objectMapperBuilder().readValue(kafkaCommand.getMessage(), Packet.class);
            LCLocationData.LocationData locationData = LCLocationData.LocationData.parseFrom(packet.getContent());
            String sim = kafkaCommand.getKey();
            LCLocationData.LocationData lastGpsVlid = gpsCache.getLastGpsVlid(sim);
            if (lastGpsVlid != null) {
                return;
            }
            // 获取车辆信息
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("commId", new BigInteger(sim));
            CarInfoPojo pojo = (CarInfoPojo) dao.sqlFindObject("queryCarByCommId", param, CarInfoPojo.class);
            if (pojo != null) {
                HyCarEntity entity = carRepository.findByCarId(pojo.getCarId());
                if (entity.getNettingLat() == null || entity.getNettingLog() == null
                        || entity.getNettingLat().intValue() == 0 || entity.getNettingLog().intValue() == 0
                        || entity.getNettingTime() == null || entity.getNettingTime().intValue() == 0) {
                    if (locationData.getLatitude() != 0 && locationData.getLatitude() != 0) {
                        entity.setNettingLat(StringUtil.toBigInteger(locationData.getLatitude()));
                        entity.setNettingLog(StringUtil.toBigInteger(locationData.getLongitude()));
                        entity.setNettingTime(StringUtil.toBigInteger(locationData.getGpsDate()));
                        dao.save(entity);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("更新首次上线时间失败", e);
            throw e;
        }
    }

    @Override
    public void writeOilChangeBoltToCache(KafkaCommand kafkaCommand) {

        logger.info("[writeOilChangeBoltToCache execute]now time:{}", System.currentTimeMillis());

        try {
            Packet packet = JacksonUtils.objectMapperBuilder().readValue(kafkaCommand.getMessage(), Packet.class);
            String vid = packet.getUniqueMark();

            // 解析位置数据
            LCLocationData.LocationData taNowLocation = LCLocationData.LocationData.parseFrom(packet.getContent());

            // 过滤掉主要字段是否为空的位置数据
            if (taNowLocation.getGpsDate() == 0) {
                logger.info("LocationData gpsDate is null, vid:{}", vid);
                return;
            }
            long nowOilPercentage = 0;
            StringBuffer paramsBuffer = new StringBuffer();
            // 获取油量百分比(已经被乘以100)
            for (LCVehicleStatusData.VehicleStatusData statusData : taNowLocation.getStatusAddition().getStatusList()) {
                paramsBuffer.append(statusData.getTypes()).append("_");
                if (statusData.getTypes() == LCStatusType.StatusType.oilValue) {
                    // 原始数据被扩大10000倍，除以100后，实际是被扩大100倍
                    nowOilPercentage = statusData.getStatusValue() / 100;
                }
            }
            if (nowOilPercentage == 0) {
                logger.info("LocationData oil is zero, vid:{}, statusData:{}", vid, paramsBuffer.toString());
                return;
            }
            logger.info("vid:{}, nowOilPercentage:{}", vid, nowOilPercentage);

            // 查询车的油箱容积，如果油箱容积不存在，流程结束
            String maxVolume = redisDBUtil.getHashValue(redis_vehicle_oil_volume, vid);
            if (maxVolume == null) {
//                saveOilLocation2Redis(taNowLocation, vid, nowOilPercentage, 0);
                logger.info("oilVolume is null, redisKey:{}, vid:{}", redis_vehicle_oil_volume, vid);
                return;
            }
            Long maxVolumeLong = Long.parseLong(maxVolume);

            // 查询上个有效的位置点，存在：流程继续；不存在：保存此位置点、流程结束
            String existLocation = redisDBUtil.getHashValue(redis_vehicle_oil_exist, vid);
            if (existLocation == null) {
                logger.info("existLocation is null, redisKey:{}, vid:{}", redis_vehicle_oil_exist, vid);
                VehicleOilExistEntity existEntity = saveOilLocation2Redis(taNowLocation, vid, nowOilPercentage, maxVolumeLong);

                // 将最新位置作为加油减油事件的开始点
                String changeRecord = redisDBUtil.getHashValue(redis_vehicle_oil_change, vid);
                logger.info("query redis:{}, vid:{}, changeRecord:{}", redis_vehicle_oil_change, vid, changeRecord);
                if (changeRecord == null) {
                    operateOilChange2Redis(new VehicleOilChangeEntity(), existEntity, true, 0, 0L, existEntity.getOilValue());
                }
                return;
            }
            // 如果当前位置点时间早于之前一个点，流程结束
            VehicleOilExistEntity beforeLocation = JSON.parseObject(existLocation, VehicleOilExistEntity.class);
            if (taNowLocation.getGpsDate() < beforeLocation.getGpsDate()) {
                logger.info("taNowLocation gpsDate wrong, vid:{}, now gpsDate:{}, before gpsDate:{}",
                        vid, taNowLocation.getGpsDate(), beforeLocation.getGpsDate());
                return;
            }

//            // 保存当前有效位置点到redis
//            VehicleOilExistEntity nowLocation = saveOilLocation2Redis(taNowLocation, vid, nowOilPercentage, maxVolumeLong);

            // ---------------- 加油减油判断----------------
            boolean isExceedFlag = false;
            // 两个位置点的油量(已经乘以100)、时间
            Long nowOilVolume = maxVolumeLong * nowOilPercentage;
            Long nowTime = taNowLocation.getGpsDate();
            Long beforeOilVolume = maxVolumeLong * beforeLocation.getOilPercentage();
            Long beforeTime = beforeLocation.getGpsDate();
            logger.info("calculate number(value*100), maxVolumeLong:{}, nowOilVolume:{}, nowTime:{}, beforeOilVolume:{}, beforeTime:{}",
                    maxVolumeLong, nowOilVolume, nowTime, beforeOilVolume, beforeTime);
            // 阀值，已经乘以100
            Double changeThreshold_double = new BigDecimal((float) t_oilChange / t_oilChangeInterval).setScale(2, BigDecimal.ROUND_HALF_DOWN).doubleValue() * 100;
            Long changeThreshold = changeThreshold_double.longValue();
            // 油量变化值
            Long changeOilVolume = nowOilVolume - beforeOilVolume;
            // 实际变化值(除以时间)
            Long changeResult = new BigDecimal((float) changeOilVolume / (nowTime - beforeTime)).setScale(0, BigDecimal.ROUND_HALF_DOWN).longValue();
            Long changeResult_abc = Math.abs(changeResult.longValue());
            logger.info("calculate result(value*100), changeOilVolume:{}, changeThreshold:{}, changeResult:{}, changeResult_abc:{}", changeOilVolume, changeThreshold, changeResult, changeResult_abc);
            // 是否超过阀值的标记
            if (changeResult_abc > changeThreshold) {
                // 超过阀值
                logger.info("exceed changeThreshold, vid:{}, changeResult_abc:{}", vid, changeResult_abc);
                // 如果超过阀值的数值(已经乘以100)超过了油箱最大容积*100，忽略加油减油记录，否则保存加油减油记录
                if (changeResult_abc > maxVolumeLong * 100) {
                    logger.info("exceed maxVolumeLong, vid:{}, changeResult_abc:{}, maxVolumeLong*100:{}", vid, changeResult_abc, maxVolumeLong * 100);
                    return;
                } else {
                    isExceedFlag = true;
                    // 保存当前有效位置点到redis
                    VehicleOilExistEntity nowLocation = saveOilLocation2Redis(taNowLocation, vid, nowOilPercentage, maxVolumeLong);

                    // 油量变化类型：加油/减油
                    int changeType = changeResult > 0 ?
                            VehicleOilChangeEntity.ChangeType.addOil.code : VehicleOilChangeEntity.ChangeType.reduceOil.code;
                    String changeRecord = redisDBUtil.getHashValue(redis_vehicle_oil_change, vid);
                    logger.info("vid:{}, changeResult:{}, changeType:{}, changeRecord:{}", vid, changeResult, changeType, changeRecord);
                    if (changeRecord == null) {
                        logger.info("changeRecord is null, save it, redisKey:{}, vid:{}", redis_vehicle_oil_change, vid);
                        // 没有加油减油的记录时，出现加油减油情况，将上个点和当前点一起保存到保存加油减油记录
                        VehicleOilChangeEntity changeEntity = initOilChangeBeginInfoByBefore(beforeLocation, changeType);
                        operateOilChange2Redis(changeEntity, nowLocation, true, changeType, 0L, nowOilVolume);
                    } else {
                        VehicleOilChangeEntity changeEntity = JSON.parseObject(changeRecord, VehicleOilChangeEntity.class);
                        // 判断超过标记与已有油量变化记录的标记是否相同，或者加油减油标记为0，则更新上加油减油事件的结束信息
                        if (changeType == changeEntity.getOilType() || changeEntity.getOilType() == 0) {
                            // 相同，则更新加油减油记录
                            operateOilChange2Redis(changeEntity, nowLocation, false, changeType, changeOilVolume, nowOilVolume);
                        } else {
                            logger.info("[saveMongodb]changeType not equals, nowType:{}, beforeType:{}", changeType, changeEntity.getOilType());
                            // 不相同，则将数据保存在mongodb
                            saveMongodbAndDelRedis(changeEntity);
                            // 再保存新的redis信息，保存到mongo的结束信息，作为新事件的开始信息，此标记不相同的点作为结束信息
                            VehicleOilChangeEntity newChangeEntity = initOilChangeBeginInfo(changeEntity, changeType);
                            operateOilChange2Redis(newChangeEntity, nowLocation, false, changeType, changeOilVolume, nowOilVolume);
                        }

                    }
                }
            } else {
                // 保存此位置点
                VehicleOilExistEntity nowLocation = saveOilLocation2Redis(taNowLocation, vid, nowOilPercentage, maxVolumeLong);
                // 不超过阀值
                String changeRecord = redisDBUtil.getHashValue(redis_vehicle_oil_change, vid);
                logger.info("not exceed changeThreshold, vid:{}, changeRecord:{}", vid, changeRecord);
                if (changeRecord == null) {
                    operateOilChange2Redis(new VehicleOilChangeEntity(), nowLocation, true, 0, 0L, nowLocation.getOilValue());
                } else {
                    logger.info("changeRecord is not null, vid:{}", vid);
                    VehicleOilChangeEntity changeEntity = JSON.parseObject(changeRecord, VehicleOilChangeEntity.class);
                    // 如果存在加油减油事件，且没有加油减油标识，则覆盖加油减油事件的开始点
                    if (changeEntity.getOilType() == 0) {
                        operateOilChange2Redis(changeEntity, nowLocation, true, 0, 0L, nowLocation.getOilValue());
                    } else {
                        logger.info("[saveMongodb]normal oil, changeType:{}", changeEntity.getOilType());
                        // 将数据保存在mongodb
                        saveMongodbAndDelRedis(changeEntity);
                        // 保存加油减油事件的开始信息
                        operateOilChange2Redis(new VehicleOilChangeEntity(), nowLocation, true, 0, 0L, nowLocation.getOilValue());
                    }

                }
            }
            logger.info("[OilChangeBolt execute]finish, vid:{}, isExceedFlag:{}", vid, isExceedFlag);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /**
     * 保存有效的位置点数据
     * nowOil 油量百分比（乘以100）
     * locationData PB位置点数据
     */
    public VehicleOilExistEntity saveOilLocation2Redis(LCLocationData.LocationData locationData, String vid, long oilPercentage100, long maxVolume) {
        VehicleOilExistEntity entity = new VehicleOilExistEntity();
        entity.setVid(Long.valueOf(vid));
        entity.setGpsDate(locationData.getGpsDate());
        entity.setGpsSpeed(locationData.getSpeed());
        entity.setLongitude(locationData.getLongitude());
        entity.setLatitude(locationData.getLatitude());
        entity.setMileage(locationData.getMileage());
        entity.setOilPercentage(oilPercentage100);
        entity.setMaxVolume(maxVolume);
        entity.setOilValue(oilPercentage100 * maxVolume);

        logger.info("save redis:{}, vid:{}, value:{}", redis_vehicle_oil_exist, vid, entity);
        redisDBUtil.setHashValue(redis_vehicle_oil_exist, vid, JSON.toJSONString(entity));

//        // 将最新位置作为加油减油事件的开始点
//        String changeRecord = redisDBUtil.getHashValue(redis_vehicle_oil_change, vid);
//        logger.info("query redis:{}, vid:{}, changeRecord:{}", redis_vehicle_oil_change, vid, changeRecord);
//        if (changeRecord == null) {
//            operateOilChange2Redis(new VehicleOilChangeEntity(), entity, true, 0, 0L, entity.getOilValue());
//        }
        // 如果已经存在加油减油事件，如果此点是加油减油的点，就不能预先覆盖事件的开始点
         /*else {
            // 如果存在，且没有加油减油标识，则覆盖加油减油事件的开始点
            VehicleOilChangeEntity changeEntity = JSON.parseObject(changeRecord, VehicleOilChangeEntity.class);
            if (changeEntity.getOilType() == 0) {
                operateOilChange2Redis(changeEntity, entity, true, 0, 0L, entity.getOilValue());
            }
        }*/
        return entity;
    }

    /**
     * 将加油减油记录保存到mongodb中
     * entity 油量变化实体
     */
    public void saveMongodbAndDelRedis(VehicleOilChangeEntity entity) {
        String databaseName = mongoDBUtil.getDatabaseName();
        String month = TimeUtil.getTime("yyyyMM");
        MongoCollection<Document> collection = mongoDBUtil.getMongoClient().getDatabase(databaseName).getCollection(collectionName + month);
        String record = JSON.toJSONString(entity);
        Document document = Document.parse(record);
        collection.insertOne(document);
        logger.info("saveMongodb, collectionName:{}, record:{}", collectionName + month, record);

//        redisDBUtil.delHashValue(redis_vehicle_oil_change, String.valueOf(entity.getVid()));
//        logger.info("delRedis, redisKey:{}, vid:{}", redis_vehicle_oil_change, entity.getVid());
    }

    /**
     * 用本身对象初始化加油减油记录
     */
    public VehicleOilChangeEntity initOilChangeBeginInfo(VehicleOilChangeEntity changeEntity, int changeType) {
        VehicleOilChangeEntity newChangeEntity = new VehicleOilChangeEntity();
        newChangeEntity.setCreateTime(TimeUtil.getTime());
        newChangeEntity.setVid(changeEntity.getVid());
        newChangeEntity.setOilType(changeType);
        newChangeEntity.setChangeTime(0);
        newChangeEntity.setBeginDate(changeEntity.getEndDate());
        newChangeEntity.setBeginLongitude(changeEntity.getEndLongitude());
        newChangeEntity.setBeginLatitude(changeEntity.getEndLatitude());
        newChangeEntity.setBeginGpsSpeed(changeEntity.getEndGpsSpeed());
        newChangeEntity.setBeginStealOil(changeEntity.getEndStealOil());
        newChangeEntity.setBeginMileage(changeEntity.getEndMileage());
        return newChangeEntity;
    }

    /**
     * 用之前位置点初始化加油减油记录
     */
    public VehicleOilChangeEntity initOilChangeBeginInfoByBefore(VehicleOilExistEntity oilExist, int changeType) {
        VehicleOilChangeEntity newChangeEntity = new VehicleOilChangeEntity();
        newChangeEntity.setCreateTime(TimeUtil.getTime());
        newChangeEntity.setVid(oilExist.getVid());
        newChangeEntity.setOilType(changeType);
        newChangeEntity.setChangeTime(0);
        newChangeEntity.setBeginDate(oilExist.getGpsDate());
        newChangeEntity.setBeginLongitude(oilExist.getLongitude());
        newChangeEntity.setBeginLatitude(oilExist.getLatitude());
        newChangeEntity.setBeginGpsSpeed(oilExist.getGpsSpeed());
        newChangeEntity.setBeginStealOil(oilExist.getOilValue());
        newChangeEntity.setBeginMileage(oilExist.getMileage());
        return newChangeEntity;
    }

//    @Override
//    public void declareOutputFields(OutputFieldsDeclarer declarer) {
//
//    }

    /**
     * 保存加油/减油的记录到redis
     * isAdd 添加或更新标识，添加为true，修改为flase
     * changeType 加油减油标识
     * changeEntity 油量变化实体
     * existEntity 当前位置点实体
     * changeOilVolume 油量差的绝对值(没除以时间)
     * nowOilVolume 当前油量
     */
    public void operateOilChange2Redis(VehicleOilChangeEntity changeEntity, VehicleOilExistEntity existEntity,
                                       boolean isAdd, int changeType, Long changeOilVolume, Long nowOilVolume) {

        logger.info("save redis:{}, isAdd:{}, vid:{}, value:{}", redis_vehicle_oil_change, isAdd, changeEntity.getVid(), changeEntity);
        redisDBUtil.setHashValue(redis_vehicle_oil_change, String.valueOf(changeEntity.getVid()), JSON.toJSONString(changeEntity));
        if (isAdd) {
            changeEntity.setCreateTime(TimeUtil.getTime());
            changeEntity.setVid(existEntity.getVid());
            changeEntity.setOilType(changeType);
            changeEntity.setChangeTime(0);

            changeEntity.setBeginDate(existEntity.getGpsDate());
            changeEntity.setBeginLongitude(existEntity.getLongitude());
            changeEntity.setBeginLatitude(existEntity.getLatitude());
            changeEntity.setBeginGpsSpeed(existEntity.getGpsSpeed());
            changeEntity.setBeginStealOil(nowOilVolume);
            changeEntity.setBeginMileage(existEntity.getMileage());
        } else {
            // 相邻两个位置的时间差
            long beforeTime = changeEntity.getEndDate() == 0 ? changeEntity.getBeginDate() : changeEntity.getEndDate();
            long recentChangeTime = existEntity.getGpsDate() - beforeTime;
            changeEntity.setChangeTime(changeEntity.getChangeTime() + recentChangeTime);
            changeEntity.setOilType(changeType);

            changeEntity.setEndDate(existEntity.getGpsDate());
            changeEntity.setEndLongitude(existEntity.getLongitude());
            changeEntity.setEndLatitude(existEntity.getLatitude());
            changeEntity.setEndGpsSpeed(existEntity.getGpsSpeed());
            changeEntity.setEndStealOil(nowOilVolume);
            changeEntity.setEndMileage(existEntity.getMileage());
            changeEntity.setOilingDvalue(changeEntity.getOilingDvalue() + Math.abs(changeOilVolume));
        }
    }
}
