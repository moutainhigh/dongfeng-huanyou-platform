//package com.navinfo.huanyou.terminal.comm.cache;
//
//import Application;
//import com.navinfo.huanyou.terminal.comm.common.util.redis.RedisKey;
//import org.junit.Before;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.SpringApplicationConfiguration;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import java.util.Iterator;
//import java.util.Set;
//
///**
// * Created by Administrator on 2016/12/5.
// */
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(classes = Application.class)
//public class GpsCacheTest {
//    @Autowired
//    private GpsCache gpsCache;
//
//    @Autowired
//    private StringRedisTemplate gpsRedisTemplate;
//
///*    @Before
//    public void setUp() {
//        Set<String> nameCodeSet = gpsRedisTemplate.keys(RedisKey.HY_GPS_ + "*");
//        Iterator<String> nameCodeIt = nameCodeSet.iterator();
//        while(nameCodeIt.hasNext()){
//            String keyStr = nameCodeIt.next();
//            gpsRedisTemplate.delete(keyStr);
//        }
//    }*/
//
////    @Test
////    public void getAllGps() {
////        LocationData.Builder dataBuilder = LocationData.newBuilder();
////        dataBuilder.setLatitude(100);
////        dataBuilder.setLongitude(100);
////        dataBuilder.setAlarm(100L);
////        dataBuilder.setStatus(100L);
////        dataBuilder.setHeight(100);
////        dataBuilder.setSpeed(100);
////        dataBuilder.setDirection(100);
////        dataBuilder.setGpsDate(100);
////        dataBuilder.setReceiveDate(100);
////        dataBuilder.setIsPatch(true);
////        gpsCache.addStraight("123", dataBuilder.build());
////        LocationData.Builder dataBuilder2 = LocationData.newBuilder();
////        dataBuilder2.setLatitude(110);
////        dataBuilder2.setLongitude(110);
////        dataBuilder2.setAlarm(110L);
////        dataBuilder2.setStatus(110L);
////        dataBuilder2.setHeight(110);
////        dataBuilder2.setSpeed(110);
////        dataBuilder2.setDirection(110);
////        dataBuilder2.setGpsDate(110);
////        dataBuilder2.setReceiveDate(110);
////        dataBuilder2.setIsPatch(true);
////        gpsCache.addStraight("345", dataBuilder2.build());
////
////        Assert.assertEquals(2, gpsCache.getAllGps().size());
////    }
////
////
////    @Test
////    public void addGpsCache() {
////        // redis中不存在老数据
////        Assert.assertNull(gpsCache.getGpsCache("123"));
////        LocationData.Builder dataBuilder = LocationData.newBuilder();
////        dataBuilder.setLatitude(100);
////        dataBuilder.setLongitude(100);
////        dataBuilder.setAlarm(100L);
////        dataBuilder.setStatus(100L);
////        dataBuilder.setHeight(100);
////        dataBuilder.setSpeed(100);
////        dataBuilder.setDirection(100);
////        dataBuilder.setGpsDate(100);
////        dataBuilder.setReceiveDate(100);
////        dataBuilder.setIsPatch(true);
////        Assert.assertEquals(gpsCache.addGpsCache("123", dataBuilder.build()), true);
////        Assert.assertNotNull(gpsCache.getGpsCache("123"));
////
////        // redis中存在老数据，但新数据Gps时间新
////        LocationData.Builder dataBuilder2 = LocationData.newBuilder();
////        dataBuilder2.setLatitude(101);
////        dataBuilder2.setLongitude(101);
////        dataBuilder2.setAlarm(101L);
////        dataBuilder2.setStatus(101L);
////        dataBuilder2.setHeight(101);
////        dataBuilder2.setSpeed(101);
////        dataBuilder2.setDirection(101);
////        dataBuilder2.setGpsDate((new Date()).getTime());
////        dataBuilder2.setReceiveDate(101);
////        dataBuilder2.setIsPatch(true);
////        Assert.assertEquals(gpsCache.addGpsCache("123", dataBuilder2.build()), true);
////        Assert.assertEquals(gpsCache.getGpsCache("123").getLatitude(),101);
////
////        // redis中存在老数据，但新数据Gps时间老
////        LocationData.Builder dataBuilder3 = LocationData.newBuilder();
////        dataBuilder3.setLatitude(105);
////        dataBuilder3.setLongitude(105);
////        dataBuilder3.setAlarm(105L);
////        dataBuilder3.setStatus(105L);
////        dataBuilder3.setHeight(105);
////        dataBuilder3.setSpeed(105);
////        dataBuilder3.setDirection(105);
////        dataBuilder3.setGpsDate(105);
////        dataBuilder3.setReceiveDate(105);
////        dataBuilder3.setIsPatch(true);
////        Assert.assertEquals(gpsCache.addGpsCache("123", dataBuilder3.build()),false);
////        Assert.assertEquals(gpsCache.getGpsCache("123").getLatitude(),101);
////    }
////
////    @Test
////    public void getGpsCache() {
////        // redis中不存在老数据
////        Assert.assertNull(gpsCache.getGpsCache("123"));
////        LocationData.Builder dataBuilder = LocationData.newBuilder();
////        dataBuilder.setLatitude(100);
////        dataBuilder.setLongitude(100);
////        dataBuilder.setAlarm(100L);
////        dataBuilder.setStatus(100L);
////        dataBuilder.setHeight(100);
////        dataBuilder.setSpeed(100);
////        dataBuilder.setDirection(100);
////        dataBuilder.setGpsDate(100);
////        dataBuilder.setReceiveDate(100);
////        dataBuilder.setIsPatch(true);
////        Assert.assertEquals(gpsCache.addGpsCache("123", dataBuilder.build()), true);
////        Assert.assertEquals(gpsCache.getGpsCache("123").getLatitude(),100);
////    }
////
////    @Test
////    public void addStraight() {
////        // redis中不存在老数据
////        Assert.assertNull(gpsCache.getGpsCache("123"));
////        LocationData.Builder dataBuilder = LocationData.newBuilder();
////        dataBuilder.setLatitude(100);
////        dataBuilder.setLongitude(100);
////        dataBuilder.setAlarm(100L);
////        dataBuilder.setStatus(100L);
////        dataBuilder.setHeight(100);
////        dataBuilder.setSpeed(100);
////        dataBuilder.setDirection(100);
////        dataBuilder.setGpsDate(100);
////        dataBuilder.setReceiveDate(100);
////        dataBuilder.setIsPatch(true);
////        Assert.assertEquals(gpsCache.addStraight("123", dataBuilder.build()), true);
////        Assert.assertEquals(gpsCache.getGpsCache("123").getLatitude(),100);
////    }
////
////    @Test
////    public void getCacheLength() {
////        LocationData.Builder dataBuilder = LocationData.newBuilder();
////        dataBuilder.setLatitude(100);
////        dataBuilder.setLongitude(100);
////        dataBuilder.setAlarm(100L);
////        dataBuilder.setStatus(100L);
////        dataBuilder.setHeight(100);
////        dataBuilder.setSpeed(100);
////        dataBuilder.setDirection(100);
////        dataBuilder.setGpsDate(100);
////        dataBuilder.setReceiveDate(100);
////        dataBuilder.setIsPatch(true);
////        Assert.assertEquals(gpsCache.addStraight("123", dataBuilder.build()),true);
////
////        // redis中存在老数据，但新数据Gps时间新
////        LocationData.Builder dataBuilder2 = LocationData.newBuilder();
////        dataBuilder2.setLatitude(101);
////        dataBuilder2.setLongitude(101);
////        dataBuilder2.setAlarm(101L);
////        dataBuilder2.setStatus(101L);
////        dataBuilder2.setHeight(101);
////        dataBuilder2.setSpeed(101);
////        dataBuilder2.setDirection(101);
////        dataBuilder2.setGpsDate((new Date()).getTime());
////        dataBuilder2.setReceiveDate(101);
////        dataBuilder2.setIsPatch(true);
////        Assert.assertEquals(gpsCache.addStraight("345", dataBuilder2.build()), true);
////
////        // redis中存在老数据，但新数据Gps时间老
////        LocationData.Builder dataBuilder3 = LocationData.newBuilder();
////        dataBuilder3.setLatitude(105);
////        dataBuilder3.setLongitude(105);
////        dataBuilder3.setAlarm(105L);
////        dataBuilder3.setStatus(105L);
////        dataBuilder3.setHeight(105);
////        dataBuilder3.setSpeed(105);
////        dataBuilder3.setDirection(105);
////        dataBuilder3.setGpsDate((new Date()).getTime() + 5);
////        dataBuilder3.setReceiveDate(105);
////        dataBuilder3.setIsPatch(true);
////        Assert.assertEquals(gpsCache.addStraight("456", dataBuilder3.build()), true);
////
////        Assert.assertEquals(gpsCache.getCacheLength(),3);
////    }
//}
