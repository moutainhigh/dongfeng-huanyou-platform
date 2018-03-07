package com.navinfo.opentsp.dongfeng.report.service.general.impl;

import com.lc.core.protocol.common.LCLocationData;
import com.lc.core.protocol.common.LCStatusType;
import com.lc.core.protocol.common.LCVehicleStatusData;
import com.lc.core.protocol.dataaccess.terminal.LCLastestLocationDataRes;
import com.lc.core.protocol.webservice.originaldata.LCTerminalLocationData;
import com.navinfo.opentsp.dongfeng.common.cache.GpsCache;
import com.navinfo.opentsp.dongfeng.common.configuration.CloudDataRmiClientConfiguration;
import com.navinfo.opentsp.dongfeng.common.constant.CloudConstants;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.PagingInfo;
import com.navinfo.opentsp.dongfeng.common.rmi.BasicDataQueryWebService;
import com.navinfo.opentsp.dongfeng.common.rmi.module.CommonParameterSerializer;
import com.navinfo.opentsp.dongfeng.common.service.BaseService;
import com.navinfo.opentsp.dongfeng.common.util.*;
import com.navinfo.opentsp.dongfeng.report.commands.general.QueryCarOperateCommand;
import com.navinfo.opentsp.dongfeng.report.pojo.general.QueryCarOperatePojo;
import com.navinfo.opentsp.dongfeng.report.service.general.ICarOperateService;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 运营车况查看
 *
 * @Author liusanhu@aerozhonghuan.com
 * @Date 2017/3/27
 */
@Service
public class CarOperateServiceImpl extends BaseService implements ICarOperateService {
    @Resource
    private CloudDataRmiClientConfiguration cloudDataRmiClientConfiguration;

    @Resource
    private GpsCache gpsCache;

    @Override
    public HttpCommandResultWithData queryCarOperate(QueryCarOperateCommand command) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("accountId", command.getUserInfor().getUserId());
        params.put("accountType", command.getUserInfor().getType());
        params.put("chassisNum", command.getChassisNum());
        params.put("plateNum", command.getPlateNum());
        params.put("engineType", command.getEngineType());

        //如果查询参数有整车里程需要通过位置云接口检索，在内存做分页。
        if (command.getMileage() != null && command.getMileage().intValue() != -1) {
            List<QueryCarOperatePojo> list = dao.sqlFind("queryCarOperate", params, QueryCarOperatePojo.class);
            //封装位置云位置相关数据
            List<QueryCarOperatePojo> locPojos = packData(list, command);
            if (command.getDownloadFlag() == 0) {
                int pn = Integer.parseInt(command.getPage_number());
                int ps = Integer.parseInt(command.getPage_size());
                result.setData(PagingInfoUtil.getPagingInfo(locPojos, pn, ps));
            } else {
                result.setData(locPojos);
            }
            return result;
        } else {
            if (command.getDownloadFlag() == 0) {
                PagingInfo<QueryCarOperatePojo> page = dao.sqlPagelFind("queryCarOperate", params, Integer.valueOf(command.getPage_number()), Integer.valueOf(command.getPage_size()), QueryCarOperatePojo.class);
                //封装位置云位置相关数据
                packData(page.getList(), command);

                result.setData(page);
            } else {
                List<QueryCarOperatePojo> list = dao.sqlFind("queryCarOperate", params, QueryCarOperatePojo.class);
                //封装位置云位置相关数据
                List<QueryCarOperatePojo> locPojos = packData(list, command);
                result.setData(locPojos);
            }

            return result;
        }


    }

    /**
     * 封装位置云位置相关数据
     *
     * @param list
     * @param command
     */
    public List<QueryCarOperatePojo> packData(List<QueryCarOperatePojo> list, QueryCarOperateCommand command) {
        List<Long> commIds = new ArrayList<Long>();
        for (QueryCarOperatePojo pojo : list) {
            commIds.add(pojo.getCommId().longValue());
        }
        //获取位置云末次有效位置数据
//        Map<Long, LCLocationData.LocationData> map = getTerminalLocationData(commIds, command);
        Map<String, LCLocationData.LocationData> map =  gpsCache.getAllLastGpsMap();
        ArrayList<QueryCarOperatePojo> parsePojos = new ArrayList<QueryCarOperatePojo>();

        for (QueryCarOperatePojo pojo : list) {
            LCLocationData.LocationData loc = null;
            if (pojo.getCommId() != null) {
                loc = map.get(StringUtil.valueOf(pojo.getCommId().longValue()));
            }
            if (loc == null) {
                pojo.setAccStatus("");
                pojo.setLastGpsLat(0d);
                pojo.setLastGpsLng(0d);
                pojo.setLastGpsTime("");
                pojo.setGpsCommu("");
                pojo.setGpsLocation("");
                pojo.setGpsPower("");
                pojo.setGpsWire("");
                pojo.setRotation("0");
                pojo.setMileage("0");
                pojo.setOilValue("0");
                pojo.setCumuRunningTime("0");
                pojo.setGpsMileage("0");
                pojo.setAccPedalPos("0");
                pojo.setTotalFuelConsumption("0");

                //如果查询参数有整车里程需要通过位置云接口检索，在内存做分页。
//                if (command.getMileage() != null && command.getMileage().intValue() == -1) {
//                    parsePojos.add(pojo);
//                }
                parsePojos.add(pojo);
                continue;
            }
            long status = loc.getStatus();
            pojo.setAccStatus(CloudUtil.getAccStatus(status));// 点火状态:0：ACC 关；1： ACC 开
            pojo.setLastGpsLat(loc.getLatitude() * 0.000001);
            pojo.setLastGpsLng(loc.getLongitude() * 0.000001);
            pojo.setLastGpsTime(DateUtil.timeStr(loc.getGpsDate()));
            pojo.setGpsCommu("在线");
            pojo.setGpsLocation((status & 0x000002) == 0 ? "未定位" : "定位");// GPS定位状态:0未定位1定位
            long alarm = loc.getAlarm();
            pojo.setGpsPower(((alarm >> 8) & 1) == 1 ? "未连接" : "连接");// GPS电源状态（主电源掉电(B)）
            pojo.setGpsWire(((alarm >> 5) & 1) == 1 ? "未连接" : "连接");// GPS天线状态（GNSS天线未接或被剪断(B)）
            //update fwm 2017.08.03 gps里程赋值为标准里程|总油耗赋值为标准油耗
            pojo.setGpsMileage(loc.getStandardMileage() + "");
            pojo.setTotalFuelConsumption(loc.getStandardFuelCon() + "");
            //update fwm 2017.08.03 gps里程赋值为标准里程|总油耗赋值为标准油耗
            if (loc.getStatusAddition().getStatusList().size() > 0) {
                LCLocationData.VehicleStatusAddition vstatus = loc.getStatusAddition();
                List<LCVehicleStatusData.VehicleStatusData> vlists = vstatus.getStatusList();
                for (LCVehicleStatusData.VehicleStatusData status1 : vlists) {
                    int t = status1.getTypes().getNumber();
                    switch (t) {
                        case LCStatusType.StatusType.rotation_VALUE:
                            pojo.setRotation(getDoubleData(status1.getStatusValue() * 0.01));//  当前转速
                            break;
                        case LCStatusType.StatusType.mileage_VALUE:
                            pojo.setMileage(getDoubleData(status1.getStatusValue() * 0.01));// 整车里程（Km）
                            break;
                        case LCStatusType.StatusType.oilValue_VALUE:
                            //汽量
                            if (null != pojo.getFuel() && pojo.getFuel() == 0) {
                                pojo.setOilValue(String.valueOf(CloudUtil.getResGas(loc)));
                            } else {
                                Double oil = status1.getStatusValue() * 0.01 * 0.01;
                                pojo.setOilValue(getDoubleData(Double.parseDouble(pojo.getOilCapacity()) * oil));// 当前油量
                            }
                            break;
                        case LCStatusType.StatusType.cumulativeRunningTime_VALUE:
                            Double runTimeDouble = status1.getStatusValue() * 0.01;
                            pojo.setCumuRunningTime(getDoubleData(runTimeDouble == null ? 0 :
                                    (runTimeDouble / (60 * 60))) + "小时");//发动机累计运行时长（s）
                            break;
                        case LCStatusType.StatusType.accPedalPos_VALUE:
                            pojo.setAccPedalPos(getDoubleData(status1.getStatusValue() * 0.01));
                            break;
//                        case LCStatusType.StatusType.totalFuelConsumption_VALUE:
//                            pojo.setTotalFuelConsumption(getDoubleData(status1.getStatusValue() * 0.01));
//                            break;
                        case LCStatusType.StatusType.mileageDD_VALUE://仪表总里程--新增内部协议（mileageDD）
                            double mileageDD_VALUE = status1.getStatusValue();
                            pojo.setMileageDD(NumberFormatUtil.getDoubleValueData((double) (mileageDD_VALUE / 100), 2) + "");
                            break;
                        case LCStatusType.StatusType.integralFuelConsumption_VALUE:    //积分油耗
                            long integralFuelConsumption_VALUE = status1.getStatusValue();
                            pojo.setIntegralFuelConsumption(NumberFormatUtil.getDoubleValueData((double) integralFuelConsumption_VALUE / 100, 2)
                                    + "");
                        default:
                            break;
                    }
                }
            } else {
                pojo.setRotation("0");
                pojo.setMileage("0");
                pojo.setOilValue("0");
                pojo.setCumuRunningTime("0");
                pojo.setAccPedalPos("0");
                pojo.setTotalFuelConsumption("0");
            }
            parsePojos.add(pojo);

        }

        if (map != null && !map.isEmpty()) {
            map.clear();
        }

        // 过滤整车里程条件
        double[] ranges = mileageRanges(String.valueOf(command.getMileage()));
        if (ArrayUtils.isEmpty(ranges)) {
            logger.warn("ranges is empty");
            return parsePojos;
        }

        ArrayList<QueryCarOperatePojo> results = new ArrayList<>();
        for (QueryCarOperatePojo pojo : parsePojos) {
            double mileages = Double.parseDouble(pojo.getGpsMileage());
            if (mileages > ranges[0] && mileages <= ranges[1]) {
                results.add(pojo);
            }
        }
        return results;
    }


    public double[] mileageRanges(String type) {
        double[] ranges = new double[2];
        // 增加里程查询条件
        if (CloudConstants.MilesRange.NO_CHOOSE.getType().equals(type)) {
            // 未选择
            return ArrayUtils.EMPTY_DOUBLE_ARRAY;
        } else if (CloudConstants.MilesRange.CHOOSE_0.getType().equals(type)) {
            ranges[0] = -1;
            ranges[1] = 0;
        } else if (CloudConstants.MilesRange.CHOOSE_0_3000.getType().equals(type)) {
            // >=0 && < 3000
            ranges[0] = 0;
            ranges[1] = 3000;
        } else if (CloudConstants.MilesRange.CHOOSE_3000_10000.getType().equals(type)) {
            // >=3000 && < 10000
            ranges[0] = 3000;
            ranges[1] = 10000;
        } else if (CloudConstants.MilesRange.CHOOSE_10000_100000.getType().equals(type)) {
            // >=10000 && < 100000
            ranges[0] = 10000;
            ranges[1] = 100000;
        } else if (CloudConstants.MilesRange.CHOOSE_ABOVE_100000.getType().equals(type)) {
            // > 100000
            ranges[0] = 100000;
            ranges[1] = Double.MAX_VALUE;
        }
        return ranges;
    }

    //取小数点后一位
    public String getDoubleData(Double data) {
        java.text.DecimalFormat df = new java.text.DecimalFormat("#0.0");
        try {
            String d = data == null ? "0" : df.format(data) + "";//整车里程（Km）
            return d;
        } catch (NumberFormatException e) {
            // TODO Auto-generated catch block
            logger.error(e.getMessage(), e);
        }
        return "0";
    }

    /**
     * 获取位置云末次有效位置数据
     *
     * @param commIds
     * @return
     */
    public Map<Long, LCLocationData.LocationData> getTerminalLocationData(List<Long> commIds, QueryCarOperateCommand command) {
        Map<Long, LCLocationData.LocationData> map = new HashMap<Long, LCLocationData.LocationData>();
        try {
            BasicDataQueryWebService basicDataQueryWebService = cloudDataRmiClientConfiguration.getBasicDataQueryWebService();

            CommonParameterSerializer commonParameter = new CommonParameterSerializer();
            commonParameter.setMultipage(true);
            commonParameter.setPageIndex(Integer.parseInt(command.getPage_number()));
            commonParameter.setPageSize(Integer.parseInt(command.getPage_size()));
//            String milesRange = convertMileage(command.getMileage() + "");
            byte[] data = basicDataQueryWebService.getLastestLocationData(commIds, "", 3, commonParameter);
            if (data != null) {
                LCLastestLocationDataRes.LastestLocationDataRes builder = LCLastestLocationDataRes.LastestLocationDataRes
                        .parseFrom(data);
                List<LCTerminalLocationData.TerminalLocationData> list = builder.getDatasList();
                for (LCTerminalLocationData.TerminalLocationData tld : list) {
                    map.put(tld.getTerminalId(), tld.getLocationData());
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return map;
    }

    /**
     * 页面里程数转换
     *
     * @param type
     * @return String
     * @throws
     * @Method: convertMilage
     * @Description: 页面里程数转换
     */
    public String convertMileage(String type) {
        // 增加里程查询条件
        String mileages = "";
        if (CloudConstants.MilesRange.NO_CHOOSE.getType().equals(type)) {
            // 未选择
            mileages = CloudConstants.MilesRange.NO_CHOOSE.getValue();
        } else if (CloudConstants.MilesRange.CHOOSE_0.getType().equals(type)) {
            // =0
            mileages = CloudConstants.MilesRange.CHOOSE_0.getValue();
        } else if (CloudConstants.MilesRange.CHOOSE_0_3000.getType().equals(type)) {
            // >=0 && < 3000
            mileages = CloudConstants.MilesRange.CHOOSE_0_3000.getValue();
        } else if (CloudConstants.MilesRange.CHOOSE_3000_10000.getType().equals(type)) {
            // >=3000 && < 10000
            mileages = CloudConstants.MilesRange.CHOOSE_3000_10000.getValue();
        } else if (CloudConstants.MilesRange.CHOOSE_10000_100000.getType().equals(type)) {
            // >=10000 && < 100000
            mileages = CloudConstants.MilesRange.CHOOSE_10000_100000.getValue();
        } else if (CloudConstants.MilesRange.CHOOSE_ABOVE_100000.getType().equals(type)) {
            // >= 100000
            mileages = CloudConstants.MilesRange.CHOOSE_ABOVE_100000.getValue();
        }
        return mileages;
    }
}
