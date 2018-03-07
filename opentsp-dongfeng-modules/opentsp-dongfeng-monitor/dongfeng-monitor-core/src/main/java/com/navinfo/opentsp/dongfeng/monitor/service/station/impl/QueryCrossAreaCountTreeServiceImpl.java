package com.navinfo.opentsp.dongfeng.monitor.service.station.impl;

import com.google.protobuf.InvalidProtocolBufferException;
import com.lc.core.protocol.webservice.statisticsdata.LCVehiclePassInAreaRecords;
import com.lc.core.protocol.webservice.statisticsdata.entity.LCVehiclePassDetail;
import com.lc.core.protocol.webservice.statisticsdata.entity.LCVehiclePassInArea;
import com.navinfo.opentsp.dongfeng.common.client.MailClient;
import com.navinfo.opentsp.dongfeng.common.configuration.CloudDataRmiClientConfiguration;
import com.navinfo.opentsp.dongfeng.common.constant.DictionaryConstant;
import com.navinfo.opentsp.dongfeng.common.dto.BaseData;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.rmi.DataAnalysisWebService;
import com.navinfo.opentsp.dongfeng.common.service.BaseService;
import com.navinfo.opentsp.dongfeng.common.service.IBaseDataService;
import com.navinfo.opentsp.dongfeng.common.util.*;
import com.navinfo.opentsp.dongfeng.mail.commands.SendEmailCommand;
import com.navinfo.opentsp.dongfeng.monitor.commands.station.ExportCrossAreaCountTreeCommand;
import com.navinfo.opentsp.dongfeng.monitor.commands.station.QueryCrossAreaCountTreeCommand;
import com.navinfo.opentsp.dongfeng.monitor.pojo.car.CrossAreaCountPojo;
import com.navinfo.opentsp.dongfeng.monitor.pojo.car.QueryCrossAreaCountTreePojo;
import com.navinfo.opentsp.dongfeng.monitor.service.station.IQueryCrossAreaCountTreeService;
import net.sf.json.JSONObject;
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
 * @author fengwm
 * @version 1.0
 * @date 2017-03-15
 * @modify
 * @copyright Navi Tsp
 */
@Service
@EnableAsync
public class QueryCrossAreaCountTreeServiceImpl extends BaseService implements IQueryCrossAreaCountTreeService
{
    
    @Autowired
    private IBaseDataService iBaseDataService;
    
    @Resource
    private CloudDataRmiClientConfiguration cloudDataRmiClientConfiguration;
    
    @Autowired
    private MailClient mailClient;
    
    @Override
    public HttpCommandResultWithData queryCrossAreaCountTree(QueryCrossAreaCountTreeCommand crossAreaCountTreeCommand)
    {
        
        Map<Integer, Map<Integer, List<CrossAreaCountPojo>>> treeNodes =
            new HashMap<Integer, Map<Integer, List<CrossAreaCountPojo>>>();
        
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        // 服务站ID
        String stationId = crossAreaCountTreeCommand.getStationId();
        // 查询月份
        String time = crossAreaCountTreeCommand.getTime();
        DataAnalysisWebService dataAnalysisWebService = null;
        try
        {
            dataAnalysisWebService = cloudDataRmiClientConfiguration.getDataAnalysisWebService();
        }
        catch (Exception e)
        {
            logger.error(e.getMessage(),e);
        }
        // 位置云查询条件组装
        long beginDate = DateUtil.getMinMonthDate(time);
        long endDate = DateUtil.getMaxMonthDate(time);
        List<Integer> districtCodes = new ArrayList<Integer>();
        districtCodes.add(Integer.valueOf(stationId));
        // 发起查询
        byte[] bytes = dataAnalysisWebService.getVehiclePassInArea(districtCodes, 5, beginDate, endDate);
        LCVehiclePassInAreaRecords.VehiclePassInAreaRecords vPassInAreaRecords = null;
        if(bytes != null){
            try
            {
                // 解析返回结果
                vPassInAreaRecords = LCVehiclePassInAreaRecords.VehiclePassInAreaRecords.parseFrom(bytes);
            }
            catch (InvalidProtocolBufferException e)
            {
                logger.error(e.getMessage(),e);
            }
        }
        if (vPassInAreaRecords != null && !ListUtil.isEmpty(vPassInAreaRecords.getDataListList()))
        {
            LCVehiclePassInArea.VehiclePassInArea vPassInArea = vPassInAreaRecords.getDataListList().get(0);
            int times = vPassInArea.getTimes();
            if (times > 0)
            {
                // 位置云返回数据
                List<LCVehiclePassDetail.VehiclePassDetail> vPassDetaillist = vPassInArea.getDataListList();
                // 查询通讯号对应的车辆信息
                Map<Long, CrossAreaCountPojo> carMap = getCarsByCommids(vPassDetaillist);
                for (LCVehiclePassDetail.VehiclePassDetail vpd : vPassDetaillist)
                {
                    CrossAreaCountPojo cacPojo = carMap.get(vpd.getTid());
                    if (null == cacPojo)
                    {
                        continue;
                    }
                    Integer carType = cacPojo.getCarType();// 车辆类型
                    String engType = cacPojo.getEngineType();// 发动机类型
                    cacPojo.setMileage(new Double(vpd.getMileage()));// 里程数
                    cacPojo.setIsBreakDown(vpd.getHasFaultCode() == true ? "是" : "否");// 是否有故障
                    cacPojo.setEngineTime(DateUtil.formatLongToTimeString(new Long(vpd.getRunTime())));// 发动机运行时长
                    cacPojo.setCarCount(vpd.getTimes());// 车次
                    //BUG #5845 【车辆监控-服务站统计】现网数据兼容时报错。现网发动机类型，库里可能会存在非int型
                    if(null==engType||"".equals(engType)|| !CheckUtil.isNumericZidai(engType)){
                        continue;
                    }
                    // 车次计算
                    if (treeNodes.get(carType) != null)
                    {
                        if (treeNodes.get(carType).get(engType) != null)
                        {
                            int old = treeNodes.get(carType).get(engType).get(0).getCount();
                            treeNodes.get(carType).get(engType).get(0).setCount(old + vpd.getTimes());
                            treeNodes.get(carType).get(engType).add(cacPojo);
                        }
                        else
                        {
                            List<CrossAreaCountPojo> caclist = new ArrayList<CrossAreaCountPojo>();
                            CrossAreaCountPojo cac0 = new CrossAreaCountPojo();
                            cac0.setCount(vpd.getTimes());
                            caclist.add(cac0);
                            caclist.add(cacPojo);
                            treeNodes.get(carType).put(Integer.valueOf(engType), caclist);
                        }
                    }
                    else
                    {
                        Map<Integer, List<CrossAreaCountPojo>> temp = new HashMap<Integer, List<CrossAreaCountPojo>>();
                        List<CrossAreaCountPojo> caclist = new ArrayList<CrossAreaCountPojo>();
                        CrossAreaCountPojo cac0 = new CrossAreaCountPojo();
                        cac0.setCount(vpd.getTimes());
                        caclist.add(cac0);
                        caclist.add(cacPojo);
                        temp.put(Integer.valueOf(engType), caclist);
                        treeNodes.put(carType, temp);
                    }
                    
                }
            }
            List<QueryCrossAreaCountTreePojo> tree = getTree(treeNodes);
            result.setData(tree);
            return result;
        }
        return result;
    }
    
    @Override
    @Async
    public void asyncDonwload(String fservice, ExportCrossAreaCountTreeCommand command, String sourcePath, List<? extends Object> list)
    {
        try
        {
            JSONObject uploadResult = download(fservice, list, command, sourcePath);
            // 调用邮件接口发送邮件信息
            SendEmailCommand mailCommand = new SendEmailCommand();
            mailCommand.setToEmails(command.getEmail());
            mailCommand.setSubject("车次");
            mailCommand.setContent(uploadResult.getJSONObject("data").getString("fullPath"));
            mailCommand.setWm("1");
            mailClient.sendMail(mailCommand);
        }
        catch (Exception e)
        {
            logger.error(e.getMessage(),e);
        }
    }
    
    @Override
    public JSONObject download(String fservice, List<? extends Object> list, ExportCrossAreaCountTreeCommand command,
        String sourcePath)
    {
        ReportConfig config = new ReportConfig("车次导出");
        
        config.setColumn("", "序号", 0);
        config.setColumn("chassisNum", "底盘号", 1);
        config.setColumn("engineTypeValue", "发动机型号", 2);
        config.setColumn("carTypeValue", "车型", 3);
        config.setColumn("mileage", "里程数", 4);
        config.setColumn("engineTime", "发动机运行时长", 5);
        config.setColumn("isBreakDown", "当月是否有故障", 6);
        config.setColumn("carCount", "车次", 7);
        
        config.setSourcePath(sourcePath);
        File file = ExcelUtil.getExcel(config, list, command.getSheetName(), 0);
        
        MultiValueMap<String, Object> param = new LinkedMultiValueMap<String, Object>();
        param.add("account", command.getUserInfor().getUserId());
        param.add("file", new FileSystemResource(file));
        RestTemplate restTemplate = new RestTemplate();
        String httpResult = restTemplate.postForObject(fservice, param, String.class);
        return JSONObject.fromObject(httpResult);
    }
    
    /**
     * 生成树结构目录数据
     *
     * @param treeNodes
     * @return List<QueryCrossAreaCountTreePojo>
     */
    public List<QueryCrossAreaCountTreePojo> getTree(Map<Integer, Map<Integer, List<CrossAreaCountPojo>>> treeNodes)
    {
        // 车辆类型基础数据
        List<BaseData> carTypeList = iBaseDataService.queryList(DictionaryConstant.BaseDataType.CAR_TYPE.getType());
        // 发动机类型基础数据
        List<BaseData> carEngineList =
            iBaseDataService.queryList(DictionaryConstant.BaseDataType.ENGINE_TYPE.getType());
        List<QueryCrossAreaCountTreePojo> resultList = new ArrayList<QueryCrossAreaCountTreePojo>();
        QueryCrossAreaCountTreePojo totalTree = new QueryCrossAreaCountTreePojo();
        int allCount = 0;
        if (treeNodes != null)
        {
            for (Integer carType : treeNodes.keySet())
            {
                QueryCrossAreaCountTreePojo carTree = new QueryCrossAreaCountTreePojo();
                int carCount = 0;
                Map<Integer, List<CrossAreaCountPojo>> temp = treeNodes.get(carType);
                if (temp != null && temp.size() > 0)
                {
                    for (Integer engType : temp.keySet())
                    {
                        QueryCrossAreaCountTreePojo engTree = new QueryCrossAreaCountTreePojo();
                        String engTypeStr = "";
                        BaseData engTypeData = getCarEngTypeBaseData(String.valueOf(engType), carEngineList);
                        if (engTypeData != null)
                        {
                            engTypeStr =
                                engTypeData.getValue() == null || "".equals(engTypeData.getValue()) ? "未知"
                                    : engTypeData.getValue();
                        }
                        else
                        {
                            engTypeStr = "未知";
                        }
                        engTree.setAllcounts(temp.get(engType).get(0).getCount());
                        engTree.setId(String.valueOf(engType));
                        engTree.setIsParent(false);
                        engTree.setName(engTypeStr);
                        engTree.setOpen(false);
                        engTree.setpId(String.valueOf(carType));
                        List<CrossAreaCountPojo> tempJO = temp.get(engType);
                        tempJO.remove(0);
                        engTree.setCarList(tempJO);
                        resultList.add(engTree);
                        carCount += engTree.getAllcounts();
                    }
                }
                String carTypeStr = "";
                BaseData cartypedata = getCarTypeBaseData(String.valueOf(carType), carTypeList);
                if (cartypedata != null)
                {
                    carTypeStr =
                        cartypedata.getValue() == null || "".equals(cartypedata.getValue()) ? "未知"
                            : cartypedata.getValue();
                }
                else
                {
                    carTypeStr = "未知";
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
        }
        totalTree.setAllcounts(allCount);
        totalTree.setId("0");
        totalTree.setIsParent(true);
        totalTree.setName("全部车辆");
        totalTree.setOpen(true);
        resultList.add(totalTree);
        return resultList;
    }
    
    /**
     * 根据通讯号查询车辆信息
     *
     * @param vPassDetaillist
     * @return
     */
    public Map<Long, CrossAreaCountPojo> getCarsByCommids(List<LCVehiclePassDetail.VehiclePassDetail> vPassDetaillist)
    {
        Map<Long, CrossAreaCountPojo> result = new HashMap<Long, CrossAreaCountPojo>();
        // 查询通讯号对应的车辆信息
        StringBuffer commIdSb = new StringBuffer();
        int i = 0;
        for (LCVehiclePassDetail.VehiclePassDetail vpd : vPassDetaillist)
        {
            i++;
            if (i == vPassDetaillist.size())
            {
                commIdSb.append(vpd.getTid());
            }
            else
            {
                commIdSb.append(vpd.getTid()).append(",");
            }
        }
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("commIds", commIdSb.toString());
        List<CrossAreaCountPojo> carList = dao.sqlFind("queryCommonCar_extend_1", param, CrossAreaCountPojo.class);
        for (CrossAreaCountPojo pojo : carList)
        {
            result.put(pojo.getCommId().longValue(), pojo);
        }
        return result;
    }
    
    /**
     * 查询车辆类型BaseData
     *
     * @param carType 车辆类型 PS：因为车辆表中车辆类型关联的是基础表中的主键ID
     * @return 车辆类型BaseData
     */
    public BaseData getCarTypeBaseData(String carType, List<BaseData> carTypeList)
    {
        for (BaseData bs : carTypeList)
        {
            if (bs.getId().toString().equals(carType))
            {
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
    public BaseData getCarEngTypeBaseData(String engType, List<BaseData> carEngineList)
    {
        for (BaseData bs : carEngineList)
        {
            if (bs.getCode().toString().equals(engType))
            {
                return bs;
            }
        }
        return null;
    }
}