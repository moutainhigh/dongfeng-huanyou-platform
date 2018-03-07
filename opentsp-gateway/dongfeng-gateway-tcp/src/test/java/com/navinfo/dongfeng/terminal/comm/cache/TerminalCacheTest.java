//package com.navinfo.dongfeng.terminal.comm.cache;
//
//import com.navinfo.Application;
//import org.junit.runner.RunWith;
//import org.springframework.boot.test.SpringApplicationConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
///**
// * Created by Administrator on 2016/12/5.
// */
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(classes = Application.class)
//public class TerminalCacheTest {/*
//    @Autowired
//    private TerminalCache terminalCache;
//
//    @Before
//    public void setUp() {
//        terminalCache.clear();
//    }
//
//    @Test
//    public void loadlist() {
//        List<HyTerminal> lists = new ArrayList<>();
//        HyTerminal po = new HyTerminal();
//        po.settId(123L);
//        po.settAutoCommunicationId(13998184711L);
//        po.setAutoFlag(1);
//        po.setDistrict(1);
//        po.setImportTime((new Date()).getTime());
//        lists.add(po);
//        HyTerminal po2 = new HyTerminal();
//        po2.settId(345L);
//        po2.settAutoCommunicationId(13998184712L);
//        po2.setAutoFlag(1);
//        po2.setDistrict(1);
//        po2.setImportTime((new Date()).getTime());
//        lists.add(po2);
//        terminalCache.loadlist(lists);
//   *//*     Assert.assertNotNull(terminalCache.getById("123"));
//        Assert.assertNotNull(terminalCache.getById("345"));
//        Assert.assertNotNull(terminalCache.getBySim("13998184711"));
//        Assert.assertNotNull(terminalCache.getBySim("13998184712"));*//*
//    }
//
//    @Test
//    public void addOrUpdate() {
//        HyTerminal po = new HyTerminal();
//        po.settId(1337L);
//        //terminalCache.addOrUpdate(po);
//
//*//*        Assert.assertNotNull(terminalCache.getById("1337"));
//        Assert.assertNotNull(terminalCache.getBySim("13483747858"));*//*
//    }
//
//    @Test
//    public void deleteAll() {
//        List<HyTerminal> lists = new ArrayList<>();
//        HyTerminal po = new HyTerminal();
//        po.settId(123L);
//        po.settAutoCommunicationId(13998184711L);
//        po.setAutoFlag(1);
//        po.setDistrict(1);
//        po.setImportTime((new Date()).getTime());
//        lists.add(po);
//        HyTerminal po2 = new HyTerminal();
//        po2.settId(345L);
//        po2.settAutoCommunicationId(13998184712L);
//        po2.setAutoFlag(1);
//        po2.setDistrict(1);
//        po2.setImportTime((new Date()).getTime());
//        lists.add(po2);
//        terminalCache.loadlist(lists);
//
//        Assert.assertNotNull(terminalCache.getById("123"));
//        Assert.assertNotNull(terminalCache.getById("345"));
//
//        List<String> listTid = new ArrayList<>();
//        listTid.add("123");
//        listTid.add("345");
//        terminalCache.deleteAll(listTid);
//
//        Assert.assertNull(terminalCache.getById("123"));
//        Assert.assertNull(terminalCache.getById("345"));
//    }
//
//    @Test
//    public void delete() {
//        HyTerminal po = new HyTerminal();
//        po.settId(123L);
//        po.settAutoCommunicationId(13998184712L);
//        po.setAutoFlag(1);
//        po.setDistrict(1);
//        po.setImportTime((new Date()).getTime());
//        terminalCache.addStraight(po);
//
//        Assert.assertNotNull(terminalCache.getById("123"));
//
//        terminalCache.delete("123");
//        Assert.assertNull(terminalCache.getById("123"));
//    }
//
//    @Test
//    public void clear() {
//        List<HyTerminal> lists = new ArrayList<>();
//        HyTerminal po = new HyTerminal();
//        po.settId(123L);
//        po.settAutoCommunicationId(13998184711L);
//        po.setAutoFlag(1);
//        po.setDistrict(1);
//        po.setImportTime((new Date()).getTime());
//        lists.add(po);
//        HyTerminal po2 = new HyTerminal();
//        po2.settId(345L);
//        po2.settAutoCommunicationId(13998184712L);
//        po2.setAutoFlag(1);
//        po2.setDistrict(1);
//        po2.setImportTime((new Date()).getTime());
//        lists.add(po2);
//        terminalCache.loadlist(lists);
//        Assert.assertNotNull(terminalCache.getById("123"));
//        Assert.assertNotNull(terminalCache.getById("345"));
//        Assert.assertNotNull(terminalCache.getBySim("13998184711"));
//        Assert.assertNotNull(terminalCache.getBySim("13998184712"));
//        terminalCache.clear();
//        Assert.assertNull(terminalCache.getById("123"));
//        Assert.assertNull(terminalCache.getById("345"));
//        Assert.assertNull(terminalCache.getBySim("13998184711"));
//        Assert.assertNull(terminalCache.getBySim("13998184712"));
//    }
//
//    @Test
//    public void addStraight() {
//        Assert.assertNull(terminalCache.getById("5678"));
//        Assert.assertNull(terminalCache.getBySim("13998184712"));
//        HyTerminal po = new HyTerminal();
//        po.settId(5678L);
//        po.settAutoCommunicationId(13998184712L);
//        po.setAutoFlag(1);
//        po.setDistrict(1);
//        po.setImportTime((new Date()).getTime());
//        terminalCache.addStraight(po);
//        Assert.assertNotNull(terminalCache.getById("5678"));
//        Assert.assertNotNull(terminalCache.getBySim("13998184712"));
//    }
//
//
//    @Test
//    public void getCacheLength() {
//        Assert.assertEquals(terminalCache.getSimCacheLength(), 0);
//        List<HyTerminal> lists = new ArrayList<>();
//        HyTerminal po = new HyTerminal();
//        po.settId(123L);
//        po.settAutoCommunicationId(13998184711L);
//        po.setAutoFlag(1);
//        po.setDistrict(1);
//        po.setImportTime((new Date()).getTime());
//        lists.add(po);
//        HyTerminal po2 = new HyTerminal();
//        po2.settId(345L);
//        po2.settAutoCommunicationId(13998184712L);
//        po2.setAutoFlag(1);
//        po2.setDistrict(1);
//        po2.setImportTime((new Date()).getTime());
//        lists.add(po2);
//        terminalCache.loadlist(lists);
//
//        Assert.assertEquals(terminalCache.getCacheLength(), 2);
//    }
//
//    @Test
//    public void getSimCacheLength() {
//        Assert.assertEquals(terminalCache.getSimCacheLength(), 0);
//        List<HyTerminal> lists = new ArrayList<>();
//        HyTerminal po = new HyTerminal();
//        po.settId(123L);
//        po.settAutoCommunicationId(13998184711L);
//        po.setAutoFlag(1);
//        po.setDistrict(1);
//        po.setImportTime((new Date()).getTime());
//        lists.add(po);
//        HyTerminal po2 = new HyTerminal();
//        po2.settId(345L);
//        po2.settAutoCommunicationId(13998184712L);
//        po2.setAutoFlag(1);
//        po2.setDistrict(1);
//        po2.setImportTime((new Date()).getTime());
//        lists.add(po2);
//        terminalCache.loadlist(lists);
//
//        Assert.assertEquals(terminalCache.getSimCacheLength(), 2);
//    }
//
//    @Test
//    public void getById() {
//        Assert.assertNull(terminalCache.getById("5678"));
//
//        Assert.assertNotNull(terminalCache.getById("1337"));
//
//        HyTerminal po = new HyTerminal();
//        po.settId(5678L);
//        po.settAutoCommunicationId(13998184712L);
//        po.setAutoFlag(1);
//        po.setDistrict(1);
//        po.setImportTime((new Date()).getTime());
//        terminalCache.addStraight(po);
//        Assert.assertNotNull(terminalCache.getById("5678"));
//
//        HyTerminal po2 = new HyTerminal();
//        po2.settId(27L);
//        po2.settAutoCommunicationId(13998184712L);
//        po2.setAutoFlag(1);
//        po2.setDistrict(1);
//        po2.setImportTime((new Date()).getTime());
//        terminalCache.addStraight(po2);
//        Assert.assertTrue(terminalCache.getById("27").gettAutoCommunicationId() == 13998184712L);
//    }
//
//    @Test
//    public void getBySim() {
//        Assert.assertNull(terminalCache.getBySim("13998184712"));
//
//        Assert.assertNotNull(terminalCache.getBySim("13483747858"));
//
//        HyTerminal po = new HyTerminal();
//        po.settId(5678L);
//        po.settAutoCommunicationId(13998184712L);
//        po.setAutoFlag(1);
//        po.setDistrict(1);
//        po.setImportTime((new Date()).getTime());
//        terminalCache.addStraight(po);
//        Assert.assertNotNull(terminalCache.getBySim("13998184712"));
//
//        HyTerminal po2 = new HyTerminal();
//        po2.settId(663L);
//        po2.settAutoCommunicationId(15901972968L);
//        po2.setAutoFlag(1);
//        po2.setDistrict(1);
//        po2.setImportTime((new Date()).getTime());
//        terminalCache.addStraight(po2);
//        Assert.assertTrue(terminalCache.getBySim("15901972968").gettId() == 663L);
//    }
//
//    @Test
//    public void getAll() {
//        Assert.assertEquals(terminalCache.getAll().size(), 0);
//        List<HyTerminal> lists = new ArrayList<>();
//        HyTerminal po = new HyTerminal();
//        po.settId(123L);
//        po.settAutoCommunicationId(13998184711L);
//        po.setAutoFlag(1);
//        po.setDistrict(1);
//        po.setImportTime((new Date()).getTime());
//        lists.add(po);
//        HyTerminal po2 = new HyTerminal();
//        po2.settId(345L);
//        po2.settAutoCommunicationId(13998184712L);
//        po2.setAutoFlag(1);
//        po2.setDistrict(1);
//        po2.setImportTime((new Date()).getTime());
//        lists.add(po2);
//        HyTerminal po3 = new HyTerminal();
//        po3.settId(456L);
//        po3.settAutoCommunicationId(13998184713L);
//        po3.setAutoFlag(1);
//        po3.setDistrict(1);
//        po3.setImportTime((new Date()).getTime());
//        lists.add(po3);
//        terminalCache.loadlist(lists);
//        Assert.assertEquals(terminalCache.getAll().size(), 3);
//    }
//*/
//
//
//}
