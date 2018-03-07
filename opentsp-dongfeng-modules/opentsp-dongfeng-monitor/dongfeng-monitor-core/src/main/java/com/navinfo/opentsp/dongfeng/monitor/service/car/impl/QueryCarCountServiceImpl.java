package com.navinfo.opentsp.dongfeng.monitor.service.car.impl;

import com.lc.core.protocol.webservice.statisticsdata.LCCrossGridCounts;
import com.lc.core.protocol.webservice.statisticsdata.entity.LCCrossGridRecord;
import com.navinfo.opentsp.dongfeng.common.client.MailClient;
import com.navinfo.opentsp.dongfeng.common.configuration.CloudDataRmiClientConfiguration;
import com.navinfo.opentsp.dongfeng.common.constant.DictionaryConstant;
import com.navinfo.opentsp.dongfeng.common.dto.BaseData;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.common.service.BaseService;
import com.navinfo.opentsp.dongfeng.common.service.IBaseDataService;
import com.navinfo.opentsp.dongfeng.common.util.*;
import com.navinfo.opentsp.dongfeng.mail.commands.SendEmailCommand;
import com.navinfo.opentsp.dongfeng.monitor.commands.car.ExportCarCountCommand;
import com.navinfo.opentsp.dongfeng.monitor.commands.car.QueryCarCountCommand;
import com.navinfo.opentsp.dongfeng.monitor.dto.car.CarCountPoint;
import com.navinfo.opentsp.dongfeng.monitor.pojo.car.CrossAreaCountPojo;
import com.navinfo.opentsp.dongfeng.monitor.pojo.car.QueryCrossAreaCountTreePojo;
import com.navinfo.opentsp.dongfeng.monitor.service.car.IQueryCarCountService;
import com.navinfo.opentsp.dongfeng.monitor.service.car.IQueryThermodynamicService;
import net.sf.json.JSONObject;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @wenya
 * @create 2017-04-17 15:33
 */
@Service
@EnableAsync
public class QueryCarCountServiceImpl extends BaseService implements IQueryCarCountService {
    protected static final Logger logger = LoggerFactory.getLogger(QueryCarCountServiceImpl.class);

    @Autowired
    private IQueryThermodynamicService queryThermodynamicService;

    @Resource
    private CloudDataRmiClientConfiguration cloudDataRmiClientConfiguration;

    @Autowired
    private IBaseDataService iBaseDataService;

    @Autowired
    private MailClient mailClient;

    /**
     * 根据所画区域查询车次详细信息
     */
    @Override
    public HttpCommandResultWithData queryCarCount(QueryCarCountCommand command) {
        logger.info("=====  queryCarCount start  =====");
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        // 时间参数转换
        long beginTime = DateUtil.getMinMonthDate(command.getTime());
        long endTime = DateUtil.getMaxMonthDate(command.getTime());
        // 获取用户权限下车辆
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("accountId", command.getUserInfor().getUserId());
        params.put("accountType", command.getUserInfor().getType());
        List<CrossAreaCountPojo> cars = dao.sqlFind("selectCommByAccountId", params, CrossAreaCountPojo.class);
        Map<Long, Object> commId = new HashMap<Long, Object>();
        for (CrossAreaCountPojo car : cars) {
            commId.put(car.getCommId().longValue(), car);
        }
        // 瓦片id生成
        List<Long> tileids = getTileids(command.getPointBean());
        // 调用位置云接口获取数据
        Map<String, Object> resultMap =
                getCarCount(new ArrayList(commId.keySet()), tileids, beginTime, endTime, commId);
        // 生成树形结构
        List<QueryCrossAreaCountTreePojo> tree = getTree(resultMap);
        result.setData(tree);
        result.fillResult(ReturnCode.OK);
        logger.info("===== queryCarCount end  =====");
        return result;
    }

    private List<QueryCrossAreaCountTreePojo> getTree(Map<String, Object> resultMap) {
        Map<Integer, Map<Integer, Integer>> treeNodes = (Map<Integer, Map<Integer, Integer>>) resultMap.get("tree");
        Map<Integer, List<CrossAreaCountPojo>> cars = (Map<Integer, List<CrossAreaCountPojo>>) resultMap.get("cars");
        // 车辆类型基础数据
        List<BaseData> carTypeList = iBaseDataService.queryList(DictionaryConstant.BaseDataType.CAR_TYPE.getType());
        // 发动机类型基础数据
        List<BaseData> carEngineList =
                iBaseDataService.queryList(DictionaryConstant.BaseDataType.ENGINE_TYPE.getType());
        List<QueryCrossAreaCountTreePojo> resultList = new ArrayList<QueryCrossAreaCountTreePojo>();
        if (treeNodes != null) {
            QueryCrossAreaCountTreePojo totalTree = new QueryCrossAreaCountTreePojo();
            int allCount = 0;
            for (Integer carType : treeNodes.keySet()) {
                QueryCrossAreaCountTreePojo carTree = new QueryCrossAreaCountTreePojo();
                int carCount = 0;
                String carTypeStr = "";
                BaseData cartypedata = getCarTypeBaseData(String.valueOf(carType), carTypeList);
                if (cartypedata != null) {
                    carTypeStr =
                            cartypedata.getValue() == null || "".equals(cartypedata.getValue()) ? ""
                                    : cartypedata.getValue();
                }
                Map<Integer, Integer> temp = treeNodes.get(carType);
                for (Integer engType : temp.keySet()) {
                    QueryCrossAreaCountTreePojo engTree = new QueryCrossAreaCountTreePojo();
                    String engTypeStr = "";
                    BaseData engTypeData = getCarEngTypeBaseData(String.valueOf(engType), carEngineList);
                    if (cartypedata != null) {
                        engTypeStr =
                                engTypeData == null || engTypeData.getValue() == null || "".equals(engTypeData.getValue()) ? "未知"
                                        : engTypeData.getValue();
                    }
                    if (cars.get(engType) != null) {
                        int sum = 0;
                        for (CrossAreaCountPojo crossAreaCountPojo : cars.get(engType)) {
                            sum += crossAreaCountPojo.getCarCount();
                        }
                        engTree.setAllcounts(sum);
                    } else {
                        engTree.setAllcounts(0);
                    }
                    engTree.setId(String.valueOf(engType));
                    engTree.setIsParent(false);
                    engTree.setName(engTypeStr);
                    engTree.setOpen(false);
                    engTree.setpId(String.valueOf(carType));
                    engTree.setCarList(cars.get(engType));
                    resultList.add(engTree);
                    carCount += engTree.getAllcounts();
                }
                carTree.setAllcounts(carCount);
                carTree.setId(String.valueOf(carType));
                carTree.setIsParent(true);
                carTree.setName(carTypeStr);
                carTree.setOpen(false);
                carTree.setpId("0");
                resultList.add(carTree);
                allCount += carCount;
            }
            totalTree.setAllcounts(allCount);
            totalTree.setId("0");
            totalTree.setpId("-1");
            totalTree.setIsParent(true);
            totalTree.setName("全部车辆");
            totalTree.setOpen(true);
            resultList.add(totalTree);


            return resultList;
        } else {
            resultList = null;
            return resultList;
        }
    }

    private Map<String, Object> getCarCount(List<Long> communicateid, List<Long> tileids, long beginTime, long endTime,
                                            Map<Long, Object> commId) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            logger.info("Call cloud interface getGridCrossCounts start");
            logger.info("parameters is startDate:{}, endDate:{}, communicateid:{}, tileids:{}", beginTime, endTime, communicateid, tileids);
            byte[] bytes = cloudDataRmiClientConfiguration.getDataAnalysisWebService().getGridCrossCounts(communicateid, tileids, beginTime, endTime);
            logger.info("call clould interface result size is {}", CollectionUtils.size(bytes));
            if (bytes != null) {
                LCCrossGridCounts.CrossGridCounts cross = LCCrossGridCounts.CrossGridCounts.parseFrom(bytes);
                List<LCCrossGridRecord.CrossGridRecord> crosses = cross.getRecordsList();
                logger.info("getCarCount#CrossGridRecord CrossDetails is {}", CollectionUtils.size(crosses));
                Map<Integer, Map<Integer, Integer>> treeNodes = new HashMap<Integer, Map<Integer, Integer>>();
                Map<Integer, List<CrossAreaCountPojo>> engineTypeCarMap = new HashMap<>();
                if (crosses != null && !crosses.isEmpty()) {
                    for (LCCrossGridRecord.CrossGridRecord entry : crosses) {
                        List<CrossAreaCountPojo> carlist = new ArrayList<CrossAreaCountPojo>();
                        if (entry.getCounts() > 0) {
                            CrossAreaCountPojo carCount = (CrossAreaCountPojo) commId.get(entry.getTerminalId());
                            if (carCount != null) {
                                Integer carType = carCount.getCarType();
                                String engineType = carCount.getEngineType();
                                if (carType != null && engineType != null && carType > 0 && !engineType.equals("")
                                        && Integer.parseInt(engineType) > 0) {
                                    // 填充树
                                    if (treeNodes.get(carType) != null) {
                                        Integer engType = Integer.parseInt(engineType);
                                        if (treeNodes.get(carType).get(engType) != null) {
                                            int old = treeNodes.get(carType).get(Integer.parseInt(engineType));
                                            treeNodes.get(carType).put(Integer.parseInt(engineType),
                                                    old + entry.getCounts());
                                        } else {
                                            treeNodes.get(carType).put(Integer.parseInt(engineType), entry.getCounts());
                                        }
                                    } else {
                                        Map<Integer, Integer> temp = new HashMap<Integer, Integer>();
                                        temp.put(Integer.parseInt(engineType), entry.getCounts());
                                        treeNodes.put(carType, temp);
                                    }
                                    // 填充列表
                                    CrossAreaCountPojo crossAreaCount = new CrossAreaCountPojo();
                                    crossAreaCount.setChassisNum(carCount.getChassisNum());
                                    crossAreaCount.setCarTypeValue(carCount.getCarTypeValue());
                                    crossAreaCount.setEngineTypeValue(carCount.getEngineTypeValue());
                                    crossAreaCount.setCarType(carCount.getCarType());
                                    crossAreaCount.setEngineType(carCount.getEngineType());
                                    //
                                    // 三个属性
                                    //
                                    crossAreaCount.setCarCount(entry.getCounts());

                                    List<CrossAreaCountPojo> crossAreaCountPojos = engineTypeCarMap.get(carCount.getEngineType());
                                    if (StringUtil.isEmpty(crossAreaCountPojos)) {
                                        carlist.add(crossAreaCount);
                                        engineTypeCarMap.put(Integer.parseInt(carCount.getEngineType()), carlist);
                                    } else {
                                        crossAreaCountPojos.add(crossAreaCount);
                                        engineTypeCarMap.put(Integer.parseInt(carCount.getEngineType()), crossAreaCountPojos);
                                    }
                                }
                            }
                        }
                        resultMap.put("cars", engineTypeCarMap);
                        resultMap.put("tree", treeNodes);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("getGridCrossCounts is error", e);
        }
        return resultMap;
    }

    // 生成瓦片id
    private List<Long> getTileids(List<CarCountPoint> points) {
        List<Long> tileids = new ArrayList<Long>();
        for (CarCountPoint key : points) {
            int n = 0;
            if (key.getType() == 3) { // 圆
                Double lat = key.getPointBeans().get(0).getLat();
                Double lon = key.getPointBeans().get(0).getLng();
                int[] tilezxy = queryThermodynamicService.getTile(lat.longValue(), lon.longValue(), 13);
                int roadnum = (int) Math.ceil(Double.parseDouble(key.getRadius()));
                if (roadnum % 4800 != 0) {
                    n = roadnum / 4800 + 1;
                } else {
                    n = roadnum / 4800;
                }
                for (int i = tilezxy[1] - (n - 1); i <= tilezxy[1] + (n - 1); i++) {
                    for (int j = tilezxy[2] - (n - 1); j <= tilezxy[2] + (n - 1); j++) {
                        long e = queryThermodynamicService.xyzToTileId(tilezxy[0], i, j);
                        tileids.add(e);
                    }
                }
            }
            if (key.getType() == 1) { // 矩形，点按顺时针方向放入
                int[] tilezxy1 =
                        queryThermodynamicService.getTile(key.getPointBeans().get(0).getLat().longValue(),
                                key.getPointBeans().get(0).getLng().longValue(),
                                13);
                int[] tilezxy2 =
                        queryThermodynamicService.getTile(key.getPointBeans().get(1).getLat().longValue(),
                                key.getPointBeans().get(1).getLng().longValue(),
                                13);
                int[] tilezxy3 =
                        queryThermodynamicService.getTile(key.getPointBeans().get(2).getLat().longValue(),
                                key.getPointBeans().get(2).getLng().longValue(),
                                13);
                for (int i = tilezxy1[1]; i <= tilezxy2[1]; i++) {
                    for (int j = tilezxy1[2]; j <= tilezxy3[2]; j++) {
                        long e = queryThermodynamicService.xyzToTileId(tilezxy1[0], i, j);
                        tileids.add(e);
                    }
                }
            }
        }
        return tileids;
    }

    /**
     * 查询车辆类型BaseData
     *
     * @param carType 车辆类型 PS：因为车辆表中车辆类型关联的是基础表中的主键ID
     * @return 车辆类型BaseData
     */
    public BaseData getCarTypeBaseData(String carType, List<BaseData> carTypeList) {
        for (BaseData bs : carTypeList) {
            if (bs.getId().toString().equals(carType)) {
                return bs;
            }
        }
        return null;
    }

    /**
     * 查询车辆发动机类型BaseData
     *
     * @param engType 车辆发动机类型
     * @return 车辆发动机类型BaseData
     */
    public BaseData getCarEngTypeBaseData(String engType, List<BaseData> carEngineList) {
        for (BaseData bs : carEngineList) {
            if (bs.getCode().toString().equals(engType)) {
                return bs;
            }
        }
        return null;
    }

    @Override
    @Async
    public void asyncDonwload(String fservice, ExportCarCountCommand command, String sourcePath) {
        try {
            QueryCarCountCommand queryCarCountCommand = new QueryCarCountCommand();
            CopyPropUtil.copyProp(command, queryCarCountCommand);
            queryCarCountCommand.setUserInfor(command.getUserInfor());
            queryCarCountCommand.setToken(command.getToken());
            queryCarCountCommand.setRequest(command.getRequest());
            queryCarCountCommand.setPage_number(command.getPage_number());
            queryCarCountCommand.setPage_size(command.getPage_size());
            List<QueryCrossAreaCountTreePojo> reuslt =
                    (List<QueryCrossAreaCountTreePojo>) queryCarCount(queryCarCountCommand).getData();
            List<CrossAreaCountPojo> cacList = new ArrayList<CrossAreaCountPojo>();
            for (QueryCrossAreaCountTreePojo tree : reuslt) {
                if (!tree.getIsParent()) {// 叶子
                    List<CrossAreaCountPojo> list = tree.getCarList();
                    for (int i = 1; i < list.size(); i++) {
                        cacList.add(list.get(i));
                    }
                }
            }
            JSONObject uploadResult = download(fservice, cacList, command, sourcePath);
            // 调用邮件接口发送邮件信息
            SendEmailCommand mailCommand = new SendEmailCommand();
            List<String> toEmails = new ArrayList<>();
            toEmails.add(command.getMail());
            mailCommand.setToEmails(StringUtil.collectionToDelimitedString(toEmails, ","));
            mailCommand.setSubject(command.getMailSubject());
            mailCommand.setContent(uploadResult.getString("fullPath"));
            mailClient.sendMail(mailCommand);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public JSONObject download(String fservice, List<? extends Object> list, ExportCarCountCommand command,
                               String sourcePath) {
        ReportConfig config = new ReportConfig("车次导出");

        config.setColumn("", "序号", 0);
        config.setColumn("chassisNum", "底盘号", 1);
        config.setColumn("engineTypeValue", "发动机型号", 2);
        config.setColumn("carTypeValue", "车型", 3);
        config.setColumn("carCount", "车次", 4);

        config.setSourcePath(sourcePath);
        File file = ExcelUtil.getExcel(config, list, command.getSheetName(), 0);

        MultiValueMap<String, Object> param = new LinkedMultiValueMap<String, Object>();
        param.add("account", command.getUserInfor().getUserId());
        param.add("file", new FileSystemResource(file));
        logger.info("******************************生成Excel（createExcel）-E**************************************");
        RestTemplate restTemplate = new RestTemplate();
        String httpResult = restTemplate.postForObject(fservice, param, String.class);
        return JSONObject.fromObject(httpResult);
    }
}
