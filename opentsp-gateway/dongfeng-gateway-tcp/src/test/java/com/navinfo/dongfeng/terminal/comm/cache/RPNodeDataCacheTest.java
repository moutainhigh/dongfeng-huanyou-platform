//package com.navinfo.dongfeng.terminal.comm.cache;
//
//import com.lc.core.protocol.platform.LCRPNodeData;
//import com.lc.core.protocol.platform.LCRPNodeData.RPNodeData;
//import com.navinfo.Application;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.SpringApplicationConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
///**
// * Created by Administrator on 2016/12/5.
// */
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(classes = Application.class)
//public class RPNodeDataCacheTest {
//    @Autowired
//    private RPNodeDataCache rPNodeDataCache;
//
//    @Before
//    public void setUp() {
//        rPNodeDataCache.clear();
//        rPNodeDataCache.clearGpsRP();
//    }
//
//    @Test
//    public void addWebServiceRP() {
//        List<String> listIps = new ArrayList<String>();
//        listIps.add("192.168.1.1:22000");
//        listIps.add("192.168.1.2:22002");
//        rPNodeDataCache.addWebServiceRP("123", listIps);
//
//        List<String> listIpsOut = rPNodeDataCache.getWebServiceRP("123");
//        Assert.assertNotNull(listIpsOut);
//        Assert.assertEquals("192.168.1.1:22000", listIpsOut.get(0));
//        Assert.assertEquals("192.168.1.2:22002", listIpsOut.get(1));
//    }
//
//    @Test
//    public void getWebServiceRP() {
//        List<String> listIps = new ArrayList<String>();
//        listIps.add("192.168.1.1:22000");
//        listIps.add("192.168.1.2:22002");
//        rPNodeDataCache.addWebServiceRP("123", listIps);
//
//        List<String> listIpsOut = rPNodeDataCache.getWebServiceRP("123");
//        Assert.assertNotNull(listIpsOut);
//        Assert.assertEquals("192.168.1.1:22000", listIpsOut.get(0));
//        Assert.assertEquals("192.168.1.2:22002",listIpsOut.get(1));
//    }
//
//    @Test
//    public void getWebServiceCache() {
//        List<String> listIps = new ArrayList<String>();
//        listIps.add("192.168.1.1:22000");
//        listIps.add("192.168.1.2:22002");
//        rPNodeDataCache.addWebServiceRP("123", listIps);
//
//        List<String> listIps2 = new ArrayList<String>();
//        listIps2.add("192.168.2.1:22000");
//        listIps2.add("192.168.2.2:22002");
//        rPNodeDataCache.addWebServiceRP("345", listIps2);
//
//        List<String> listIps3 = new ArrayList<String>();
//        listIps3.add("192.168.3.1:22000");
//        listIps3.add("192.168.3.2:22002");
//        rPNodeDataCache.addWebServiceRP("456", listIps3);
//
//        Map<String,List<String>> out = rPNodeDataCache.getWebServiceCache();
//
//        Assert.assertNotNull(out);
//        Assert.assertEquals(3, out.size());
//        Assert.assertEquals("192.168.1.1:22000", out.get("123").get(0));
//        Assert.assertEquals("192.168.2.2:22002",out.get("345").get(1));
//        Assert.assertEquals("192.168.3.1:22000",out.get("456").get(0));
//    }
//
//    @Test
//    public void removeWebServiceRP() {
//        List<String> listIps = new ArrayList<String>();
//        listIps.add("192.168.1.1:22000");
//        listIps.add("192.168.1.2:22002");
//        rPNodeDataCache.addWebServiceRP("123", listIps);
//
//        Assert.assertNotNull(rPNodeDataCache.getWebServiceRP("123"));
//
//        List < String > listIpsOut = rPNodeDataCache.removeWebServiceRP("123");
//        Assert.assertNotNull(listIpsOut);
//        Assert.assertEquals("192.168.1.1:22000", listIpsOut.get(0));
//        Assert.assertEquals("192.168.1.2:22002",listIpsOut.get(1));
//        Assert.assertNull(rPNodeDataCache.getWebServiceRP("123"));
//
//    }
//
//    @Test
//    public void clear() {
//        List<String> listIps = new ArrayList<String>();
//        listIps.add("192.168.1.1:22000");
//        listIps.add("192.168.1.2:22002");
//        rPNodeDataCache.addWebServiceRP("123", listIps);
//
//        List<String> listIps2 = new ArrayList<String>();
//        listIps2.add("192.168.2.1:22000");
//        listIps2.add("192.168.2.2:22002");
//        rPNodeDataCache.addWebServiceRP("345", listIps2);
//
//        List<String> listIps3 = new ArrayList<String>();
//        listIps3.add("192.168.3.1:22000");
//        listIps3.add("192.168.3.2:22002");
//        rPNodeDataCache.addWebServiceRP("456", listIps3);
//
//        Map<String,List<String>> out = rPNodeDataCache.getWebServiceCache();
//
//        Assert.assertEquals(3, out.size());
//        rPNodeDataCache.clear();
//        out = rPNodeDataCache.getWebServiceCache();
//        Assert.assertEquals(0, out.size());
//    }
//
//    @Test
//    public void addGpsRP() {
//        List<RPNodeData> listIps = new ArrayList<RPNodeData>();
//        LCRPNodeData.RPNodeData.Builder dataBuilder = LCRPNodeData.RPNodeData.newBuilder();
//        dataBuilder.setRpIp("192.168.1.1");
//        dataBuilder.setRpPort(1234);
//        dataBuilder.setTypes(dataBuilder.getTypes());
//        listIps.add(dataBuilder.build());
//        LCRPNodeData.RPNodeData.Builder dataBuilder2 = LCRPNodeData.RPNodeData.newBuilder();
//        dataBuilder2.setRpIp("192.168.2.2");
//        dataBuilder2.setRpPort(2345);
//        dataBuilder2.setTypes(dataBuilder2.getTypes());
//        listIps.add(dataBuilder2.build());
//        rPNodeDataCache.addGpsRP("123", listIps);
//
//        List<RPNodeData> listIpsOut = rPNodeDataCache.getGpsRP("123");
//        Assert.assertNotNull(listIpsOut);
//        Assert.assertEquals("192.168.1.1", listIpsOut.get(0).getRpIp());
//        Assert.assertEquals("192.168.2.2", listIpsOut.get(1).getRpIp());
//    }
//
//    @Test
//    public void getGpsRP() {
//        List<RPNodeData> listIps = new ArrayList<RPNodeData>();
//        LCRPNodeData.RPNodeData.Builder dataBuilder = LCRPNodeData.RPNodeData.newBuilder();
//        dataBuilder.setRpIp("192.168.1.1");
//        dataBuilder.setRpPort(1234);
//        dataBuilder.setTypes(dataBuilder.getTypes());
//        listIps.add(dataBuilder.build());
//        LCRPNodeData.RPNodeData.Builder dataBuilder2 = LCRPNodeData.RPNodeData.newBuilder();
//        dataBuilder2.setRpIp("192.168.2.2");
//        dataBuilder2.setRpPort(2345);
//        dataBuilder2.setTypes(dataBuilder2.getTypes());
//        listIps.add(dataBuilder2.build());
//        rPNodeDataCache.addGpsRP("123", listIps);
//
//        List<RPNodeData> listIpsOut = rPNodeDataCache.getGpsRP("123");
//        Assert.assertNotNull(listIpsOut);
//        Assert.assertEquals("192.168.1.1", listIpsOut.get(0).getRpIp());
//        Assert.assertEquals("192.168.2.2", listIpsOut.get(1).getRpIp());
//    }
//
//    @Test
//    public void removeGpsRP() {
//        List<RPNodeData> listIps = new ArrayList<RPNodeData>();
//        LCRPNodeData.RPNodeData.Builder dataBuilder = LCRPNodeData.RPNodeData.newBuilder();
//        dataBuilder.setRpIp("192.168.1.1");
//        dataBuilder.setRpPort(1234);
//        dataBuilder.setTypes(dataBuilder.getTypes());
//        listIps.add(dataBuilder.build());
//        LCRPNodeData.RPNodeData.Builder dataBuilder2 = LCRPNodeData.RPNodeData.newBuilder();
//        dataBuilder2.setRpIp("192.168.2.2");
//        dataBuilder2.setRpPort(2345);
//        dataBuilder2.setTypes(dataBuilder2.getTypes());
//        listIps.add(dataBuilder2.build());
//        rPNodeDataCache.addGpsRP("123", listIps);
//
//        List<RPNodeData> listIpsOut = rPNodeDataCache.getGpsRP("123");
//        Assert.assertNotNull(listIpsOut);
//        Assert.assertEquals("192.168.1.1", listIpsOut.get(0).getRpIp());
//        Assert.assertEquals("192.168.2.2", listIpsOut.get(1).getRpIp());
//
//        rPNodeDataCache.removeGpsRP("123");
//        listIpsOut = rPNodeDataCache.getGpsRP("123");
//        Assert.assertNull(listIpsOut);
//
//    }
//
//    @Test
//    public void clearGpsRP() {
//        List<RPNodeData> listIps = new ArrayList<RPNodeData>();
//        LCRPNodeData.RPNodeData.Builder dataBuilder = LCRPNodeData.RPNodeData.newBuilder();
//        dataBuilder.setRpIp("192.168.1.1");
//        dataBuilder.setRpPort(1234);
//        dataBuilder.setTypes(dataBuilder.getTypes());
//        listIps.add(dataBuilder.build());
//        LCRPNodeData.RPNodeData.Builder dataBuilder2 = LCRPNodeData.RPNodeData.newBuilder();
//        dataBuilder2.setRpIp("192.168.2.2");
//        dataBuilder2.setRpPort(2345);
//        dataBuilder2.setTypes(dataBuilder2.getTypes());
//        listIps.add(dataBuilder2.build());
//        rPNodeDataCache.addGpsRP("123", listIps);
//        rPNodeDataCache.addGpsRP("345", listIps);
//        rPNodeDataCache.addGpsRP("456", listIps);
//
//        Assert.assertNotNull(rPNodeDataCache.getGpsRP("123"));
//        Assert.assertNotNull(rPNodeDataCache.getGpsRP("345"));
//        Assert.assertNotNull(rPNodeDataCache.getGpsRP("456"));
//        rPNodeDataCache.clearGpsRP();
//        Assert.assertNull(rPNodeDataCache.getGpsRP("123"));
//        Assert.assertNull(rPNodeDataCache.getGpsRP("345"));
//        Assert.assertNull(rPNodeDataCache.getGpsRP("456"));
//    }
//}
