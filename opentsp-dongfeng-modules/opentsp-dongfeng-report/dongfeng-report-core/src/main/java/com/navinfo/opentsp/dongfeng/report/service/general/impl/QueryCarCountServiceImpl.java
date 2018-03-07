package com.navinfo.opentsp.dongfeng.report.service.general.impl;

import com.lc.core.protocol.common.LCLocationData;
import com.lc.core.protocol.webservice.statisticsdata.LCVehiclePassInAreaRecords;
import com.lc.core.protocol.webservice.statisticsdata.entity.LCVehiclePassDetail;
import com.lc.core.protocol.webservice.statisticsdata.entity.LCVehiclePassInArea;
import com.navinfo.opentsp.dongfeng.common.cache.GpsCache;
import com.navinfo.opentsp.dongfeng.common.configuration.CloudDataRmiClientConfiguration;
import com.navinfo.opentsp.dongfeng.common.constant.CloudConstants;
import com.navinfo.opentsp.dongfeng.common.dto.GovData;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.common.service.BaseService;
import com.navinfo.opentsp.dongfeng.common.service.IGovDataService;
import com.navinfo.opentsp.dongfeng.common.util.DateUtil;
import com.navinfo.opentsp.dongfeng.common.util.NumberFormatUtil;
import com.navinfo.opentsp.dongfeng.report.commands.general.ExportCarCountCommand;
import com.navinfo.opentsp.dongfeng.report.commands.general.QueryCarCountCommand;
import com.navinfo.opentsp.dongfeng.report.converter.general.QueryCarCountConverter;
import com.navinfo.opentsp.dongfeng.report.pojo.car.CarCountPojo;
import com.navinfo.opentsp.dongfeng.report.pojo.general.QueryCarCountPojo;
import com.navinfo.opentsp.dongfeng.report.service.general.IQueryCarCountService;
import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.comparators.ComparatorChain;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.*;

/**
 * 全国车次统计
 *
 * @wenya
 * @create 2017-03-28 14:09
 **/
@Service
public class QueryCarCountServiceImpl extends BaseService implements IQueryCarCountService {
    @Autowired
    private IGovDataService govDataService;
    @Autowired
    private GpsCache gpsCache;
    @Resource
    private CloudDataRmiClientConfiguration cloudDataRmiClientConfiguration;
    @Override
    public HttpCommandResultWithData queryCarCount(QueryCarCountCommand command) {
        logger.info("=====  queryCarCount start  =====");
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        //时间参数转换
        long beginDate = DateUtil.getMinMonthDate(command.getTime());
        long endDate = DateUtil.getMaxMonthDate(command.getTime());
        //获取省市编码list
        List<Integer> govCodeList = new ArrayList<Integer>();
        int cloudType = 0;
        Map<Integer, List<Integer>> map = getGovCodeList(command.getGovCode());
        Iterator<Integer> it = map.keySet().iterator();
        while (it.hasNext()) {
            cloudType = it.next();  //取出位置云查询类型
            govCodeList = map.get(cloudType);  //取出省市编码
        }
        //调用位置云接口
        List<QueryCarCountPojo> datas = getCarCount(beginDate, endDate, govCodeList, cloudType, command.getType());
        result.setData(QueryCarCountConverter.convertToDtoList(datas));
        result.fillResult(ReturnCode.OK);
        logger.info("===== queryCarCount end  =====");
        return result;
    }

    @Override
    public HttpCommandResultWithData exportCarCount(ExportCarCountCommand command) {
        logger.info("=====  exportCarCount start  =====");
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        try {
            //时间参数转换
            long beginDate = DateUtil.getMinMonthDate(command.getTime());
            long endDate = DateUtil.getMaxMonthDate(command.getTime());
            //获取省市编码list
            List<Integer> govCodeList = new ArrayList<Integer>();
            govCodeList.add(Integer.parseInt(command.getCityCode()));
            //调用位置云接口
            final int cloudType = isProvince(command) ? CloudConstants.PROVINCE : CloudConstants.CITY;
            List<QueryCarCountPojo> datas = exportCarCount(beginDate, endDate, govCodeList, cloudType, command);
            result.setData(QueryCarCountConverter.convertToDtoList(datas));
            result.fillResult(ReturnCode.OK);
        } catch (Exception e) {
            result.fillResult(ReturnCode.SERVER_ERROR);
            result.setMessage("导出全国车次统计异常");
            logger.error("===== exportCarCount Exception =====", e);
        }
        logger.info("===== exportCarCount end  =====");
        return result;
    }

    // 直辖市作省处理
    private boolean isProvince(final ExportCarCountCommand command) {
        return command.getCityCode().substring(command.getCityCode().length() - 4, command.getCityCode().length()).equals("0000");
    }

    //导出车次统计
    private List<QueryCarCountPojo> exportCarCount(long beginDate, long endDate, List<Integer> govCodeList, int type, ExportCarCountCommand command) {
        String sims = "";
        Map<BigInteger, Object> simCountMap = new HashMap<BigInteger, Object>();
        List<QueryCarCountPojo> mainCarCount = new ArrayList<QueryCarCountPojo>();
        try {
            logger.info("Call cloud interface getGridCrossCounts start");
            logger.info("parameters is startDate:{}, endDate:{}, govCodeList:{}, cloudType:{}", beginDate, endDate, govCodeList, type);
            byte[] bytes = cloudDataRmiClientConfiguration.getDataAnalysisWebService().getVehiclePassInArea(govCodeList, type, beginDate, endDate);
            logger.info("call clould interface result bytes is {}", CollectionUtils.size(bytes));
            if (bytes != null) {
                LCVehiclePassInAreaRecords.VehiclePassInAreaRecords vPassInAreaRecords = LCVehiclePassInAreaRecords.VehiclePassInAreaRecords.parseFrom(bytes);
                if (vPassInAreaRecords != null) {
                    List<LCVehiclePassInArea.VehiclePassInArea> countList = vPassInAreaRecords.getDataListList();
                    logger.info("exportCarCount#VehiclePassInArea countList is {}", CollectionUtils.size(countList));
                    if (countList != null && countList.size() > 0) {
                        for (LCVehiclePassInArea.VehiclePassInArea entry : countList) {
                            logger.info("exportCarCount#VehiclePassInArea PassDetails is {}", CollectionUtils.size(entry.getDataListList()));
                            if (entry.getDataListList() != null && entry.getDataListList().size() > 0) {
                                for (LCVehiclePassDetail.VehiclePassDetail key : entry.getDataListList()) {
                                    if (key != null) {
                                        sims += key.getTid() + ",";
                                        simCountMap.put(BigInteger.valueOf(key.getTid()), key.getTimes());
                                    }
                                }
                            }
                        }
                    }
                    if (!sims.equals("")) {
                        logger.info("exportCarCount#sims is {}", sims);
                        logger.info("exportCarCount#simCount is {}", simCountMap);
                        Map<String, Object> params = new HashMap<String, Object>();
                        params.put("sims", sims.substring(0, sims.length() - 1));
                        params.put("accountId", command.getUserInfor().getUserId());
                        params.put("accountType", command.getUserInfor().getType());
                        List<CarCountPojo> carList = dao.sqlFind("queryCarByCommIds", params, CarCountPojo.class);
                        for (CarCountPojo car : carList) {
                            QueryCarCountPojo carCount = new QueryCarCountPojo();
                            carCount.setCount((int) simCountMap.get(car.getCommunicationId()));
                            carCount.setCarNum(car.getCarNum());
                            carCount.setChassicNum(car.getChassicNum());
                            carCount.setCarType(car.getCarType());
                            carCount.setEngineType(car.getEngineType());
                            carCount.setStructureNum(car.getStructureNum());
                            carCount.setGovName(command.getCityName());
                            carCount.setGovCityName(command.getProvinceName());
                            //末次有效
                            LCLocationData.LocationData locationDataValid = gpsCache.getLastGps(car.getCommunicationId() + "");
                            if (locationDataValid != null) {
                                carCount.setLastCarDateStr(DateUtil.timeStr(locationDataValid.getGpsDate()));
                                carCount.setLastLat(NumberFormatUtil.getDoubleValueData(locationDataValid.getLatitude() * 0.000001, 6));
                                carCount.setLastLon(NumberFormatUtil.getDoubleValueData(locationDataValid.getLongitude() * 0.000001, 6));
                                carCount.setLastLoction(carCount.getLastLon() + ";" + carCount.getLastLat());
                            }
                            mainCarCount.add(carCount);
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return mainCarCount;
    }
    //查询车次统计
    private List<QueryCarCountPojo> getCarCount(long beginDate, long endDate, List<Integer> govCodeList, int cloudType, String type) {
        logger.info("Call cloud interface getGridCrossCounts start");
        logger.info("parameters is startDate:{}, endDate:{}, govCodeList:{}, cloudType:{},  type:{}", beginDate, endDate, govCodeList, cloudType, type);
        LCVehiclePassInAreaRecords.VehiclePassInAreaRecords vPassInAreaRecords = null;
        List<LCVehiclePassInArea.VehiclePassInArea> countList = null;
        try {
            byte[] bytes = cloudDataRmiClientConfiguration.getDataAnalysisWebService().getVehiclePassInArea(govCodeList, cloudType, beginDate, endDate);
            logger.info("call clould interface result bytes is {}", CollectionUtils.size(bytes));
            if (bytes != null) {
                vPassInAreaRecords = LCVehiclePassInAreaRecords.VehiclePassInAreaRecords.parseFrom(bytes);
                if (vPassInAreaRecords != null) {
                    countList = vPassInAreaRecords.getDataListList();
                }
                logger.info("getCarCount#VehiclePassInArea PassDetails is {}", CollectionUtils.size(countList));
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        List<QueryCarCountPojo> mainCarCount = new ArrayList<QueryCarCountPojo>();
        List<Integer> list = new ArrayList<Integer>();
        Map<Integer, GovData> codeMap = govDataService.queryDatas(govCodeList);
        if (countList != null && countList.size() > 0) {
            if (type.equals("1")) {//地图
                for (LCVehiclePassInArea.VehiclePassInArea key : countList) {
                    QueryCarCountPojo carCount = new QueryCarCountPojo();
                    carCount.setCount(key.getTimes());
                    carCount.setGovCode((int) key.getId());
                    carCount.setGovName(codeMap.get((int) key.getId()).getGovName());
                    mainCarCount.add(carCount);
                    list.add((int) key.getId());
                }
            } else { //图表
                for (LCVehiclePassInArea.VehiclePassInArea key : countList) {
                    QueryCarCountPojo carCount = new QueryCarCountPojo();
                    carCount.setCount(key.getTimes());
                    carCount.setGovCode((int) key.getId());
                    carCount.setGovName(codeMap.get((int) key.getId()).getGovName());
                    mainCarCount.add(carCount);
                    list.add((int) key.getId());
                }
            }
            if (list.size() != govCodeList.size()) {
                List<Integer> difflist = getDiffrentInt(govCodeList, list);
                for (int tt : difflist) {
                    QueryCarCountPojo carCount = new QueryCarCountPojo();
                    carCount.setCount(0);
                    carCount.setGovCode(tt);
                    carCount.setGovName(codeMap.get(tt).getGovName());
                    mainCarCount.add(carCount);
                }
            }
            ComparatorChain chain = new ComparatorChain();
            chain.addComparator(new BeanComparator("count"), true);//true,fase正序反序
            Collections.sort(mainCarCount, chain);
        } else {
            for (int key : govCodeList) {
                QueryCarCountPojo carCount = new QueryCarCountPojo();
                carCount.setCount(0);
                carCount.setGovCode(key);
                carCount.setGovName(codeMap.get(key).getGovName());
                mainCarCount.add(carCount);
            }
            logger.warn("Query CarCount is empty");
        }
        return mainCarCount;
    }

    private Map<Integer, List<Integer>> getGovCodeList(String govCode) {
        List<Integer> govCodeList = new ArrayList<Integer>();
        int cloudType = 0;
        HashMap<Integer, List<Integer>> resultMap = new HashMap<>();
        if (govCode.equals("0")) {//全国
            List<GovData> list = govDataService.queryParList(0);
            for (GovData key : list) {
                govCodeList.add(key.getGovCode());
            }
            cloudType = CloudConstants.PROVINCE;
        } else {
//            List<GovData> list = govDataService.queryParList(Integer.parseInt(govCode));
            List<GovData> list = govDataService.queryParListNotCounty(Integer.parseInt(govCode));
            if (list.get(0).getGovName().equals("县") || list.get(0).getGovName().equals("市辖区")) { //直辖市，当省对待
                govCodeList.add(Integer.parseInt(govCode));
                cloudType = CloudConstants.PROVINCE;
            } else {
                String top = govCode.substring(0, 2);
                int temp = Integer.parseInt(top + "9000");
//                List<GovData> citylist = govDataService.queryParList(temp);
                List<GovData> citylist = govDataService.queryParListNotCounty(temp);
                for (GovData key : list) {
                    if (key.getGovCode().intValue() == temp) {
                        for (GovData entry : citylist) {
                            govCodeList.add(entry.getGovCode());
                        }
                    } else {
                        govCodeList.add(key.getGovCode());
                    }
                }
                cloudType = CloudConstants.CITY;
            }
        }
        resultMap.put(cloudType, govCodeList);
        return resultMap;
    }
    //找出两个list中不同元素
    public static List<Integer> getDiffrentInt(List<Integer> list1,
                                               List<Integer> list2) {
        List<Integer> diff = new ArrayList<Integer>();
        Map<Integer, Integer> map = new HashMap<Integer, Integer>(list1.size());
        for (int string : list1) {
            map.put(string, 1);
        }
        for (int string : list2) {
            if (map.get(string) != null) {
                map.put(string, 2);
                continue;
            }
            diff.add(string);
        }
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (entry.getValue() == 1) {
                diff.add(entry.getKey());
            }
        }
        return diff;
    }
}
