package com.navinfo.opentsp.dongfeng.report.service.general.impl;

import com.google.protobuf.InvalidProtocolBufferException;
import com.lc.core.protocol.common.LCVehicleBreakdown.VehicleBreakdown;
import com.lc.core.protocol.webservice.originaldata.LCTerminalLocationData.TerminalLocationData;
import com.lc.core.protocol.webservice.originaldata.LCTerminalTrackRes.TerminalTrackRes;
import com.navinfo.opentsp.dongfeng.common.configuration.CloudDataRmiClientConfiguration;
import com.navinfo.opentsp.dongfeng.common.pojo.HyBasicdataPojo;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.PagingInfo;
import com.navinfo.opentsp.dongfeng.common.rmi.BasicDataQueryWebService;
import com.navinfo.opentsp.dongfeng.common.rmi.module.CommonParameterSerializer;
import com.navinfo.opentsp.dongfeng.common.service.BaseService;
import com.navinfo.opentsp.dongfeng.common.util.DateUtil;
import com.navinfo.opentsp.dongfeng.common.util.ListPageUtil;
import com.navinfo.opentsp.dongfeng.common.util.StringUtil;
import com.navinfo.opentsp.dongfeng.report.commands.general.QueryBreakDownRemindCommand;
import com.navinfo.opentsp.dongfeng.report.converter.general.BreakDownRemindDto;
import com.navinfo.opentsp.dongfeng.report.pojo.general.QueryBreakDownPojo;
import com.navinfo.opentsp.dongfeng.report.service.general.IBreakDownRemindService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BreakDownRemindServiceImpl extends BaseService implements IBreakDownRemindService {

    @Resource
    private CloudDataRmiClientConfiguration cloudDataRmiClientConfiguration;

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public HttpCommandResultWithData queryBreakDownRemind(QueryBreakDownRemindCommand command, boolean isExport) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        String carId = command.getCarId();
        String dateStr = command.getDateStr();
        if (carId == null || carId.isEmpty() || dateStr == null
                || dateStr.isEmpty()) {
            return result;
        }
        Map<String, Object> conMaps = new HashMap<String, Object>();
        conMaps.put("accountType", command.getUserInfor().getType());
        conMaps.put("accountName", command.getUserInfor().getUsername());
        conMaps.put("accountId", command.getUserInfor().getUserId());
        conMaps.put("carID", command.getCarId());
        conMaps.put("chassisNum", command.getChassisNum());
        QueryBreakDownPojo pojo = (QueryBreakDownPojo) dao.sqlFindObject("queryFaultSummary", conMaps,
                QueryBreakDownPojo.class);
        if (pojo == null) {
            return result;
        }
        if (command.getDateStr() != null && !command.getDateStr().isEmpty()) {
            command.setBeginTime(setTimeValue(command.getDateStr().substring(0, 10) + " 00:00:00"));
            command.setEndTime(setTimeValue(command.getDateStr().substring(13, 23) + " 23:59:59"));
        }
        List<QueryBreakDownPojo> dataList = new ArrayList<QueryBreakDownPojo>();
        int spn = (command.getSpn() == null || Integer.parseInt(command.getSpn()) < 0) ? -1
                : Integer.parseInt(command.getSpn().trim());
        int fmi = (command.getFmi() == null || Integer.parseInt(command.getFmi()) < 0) ? -1
                : Integer.parseInt(command.getFmi().trim());
        try {
            BasicDataQueryWebService bdqws = cloudDataRmiClientConfiguration.getBasicDataQueryWebService();
            Map<String, HyBasicdataPojo> maps = new HashMap<String, HyBasicdataPojo>();
            String eType = pojo.geteType();
            // 有故障码条件筛选
            if ((spn != 0L && fmi != 0L) || (spn == 0L && fmi == 0L)) {
                CommonParameterSerializer parameter = new CommonParameterSerializer();
                parameter.setMultipage(true);
                long breakdownCode = spn * 1000 + fmi;
                if (breakdownCode == 0L) {
                    breakdownCode = -1L;
                }
                if (command.getUserInfor().getType() == 1) {
                    command.getUserInfor().setUsername("_1");
                }
                byte[] data = bdqws.getTerminalTrack(pojo.getCommId().longValue(), command.getBeginTime(),
                        command.getEndTime(), true, breakdownCode, parameter,-1);
                logger.info("BreakDownRemindServiceImpl:cloudDataLength:" + (data == null ? "null" : data.length));

                if (null != data) {
                    getResultByNotEmptySF(command, pojo, dataList, maps, eType, data);
                }
            } else {
                // 单一故障码条件
                CommonParameterSerializer commonParameter = new CommonParameterSerializer();
                commonParameter.setMultipage(false);
                long breakdownCode = -1L;
                byte[] data = bdqws.getTerminalTrack(pojo.getCommId().longValue(), command.getBeginTime(),
                        command.getEndTime(), true, breakdownCode, commonParameter,-1);
                logger.info("BreakDownRemindServiceImpl:cloudDataLength:" + (data == null ? "null" : data.length));
                if (null != data) {
                    getResultByEmptySF(command, pojo, dataList, maps, eType, data);
                }
            }
        } catch (Exception ex) {
            logger.error("find exception...");
        }
        PagingInfo page = new PagingInfo();
        long count = dataList.size();
        int pageSize = Integer.parseInt(command.getPage_size());
        if (!isExport) {
            page = getResult(dataList, command);
        } else {
            page.setList(dataList);
        }
        page.setList(BreakDownRemindDto.convert(page.getList()));
        result.setData(page);
        page.setTotal(count);
        page.setPage_total((count + pageSize - 1) / pageSize);

        return result;
    }

    @SuppressWarnings("unchecked")
    private void getResultByEmptySF(QueryBreakDownRemindCommand command, QueryBreakDownPojo pojo,
                                    List<QueryBreakDownPojo> dataList, Map<String, HyBasicdataPojo> maps, String eType, byte[] data)
            throws InvalidProtocolBufferException, CloneNotSupportedException {
        List<TerminalLocationData> list;
        TerminalTrackRes builder = TerminalTrackRes.parseFrom(data);
        list = builder.getDatasList();
        if (list != null && list.size() > 0) {
            for (TerminalLocationData vo : list) {
                QueryBreakDownPojo pojo2 = (QueryBreakDownPojo) pojo.clone();
                List<VehicleBreakdown> breakdowns = vo.getLocationData().getBreakdownAddition().getBreakdownList();
                if (breakdowns.size() > 0) {
                    boolean isSpn = false;
                    boolean isFmi = false;
                    for (VehicleBreakdown item : breakdowns) {
                        HyBasicdataPojo discribe = null;
                        isSpn = command.getSpn() == null ? true
                                : (command.getSpn().trim().equals(item.getBreakdownSPNValue() + "")) ? true : false;
                        isFmi = command.getFmi() == null ? true
                                : (command.getFmi().trim().equals(item.getBreakdownFMIValue() + "")) ? true : false;
                        if (isSpn && isFmi) {
                            pojo2.setSpn(item.getBreakdownSPNValue() + "");
                            pojo2.setFmi(item.getBreakdownFMIValue() + "");
                            String spn_fmi = item.getBreakdownSPNValue() + "_" + item.getBreakdownFMIValue();
                            if (eType == null) {
                                if (command.getUserInfor().getType() != 1 && command.getUserInfor().getType() != 2) {
                                    continue;
                                } else {
                                    pojo2.setSpnFmi(spn_fmi);
                                    pojo2.setBreakdownDis("未知");
                                }
                            } else {
                                if (maps.containsKey(spn_fmi)) {
                                    discribe = maps.get(spn_fmi);
                                } else {
                                    Map<String, Object> map = new HashMap<String, Object>();
                                    map.put("dataType", eType);
                                    map.put("dataCode", spn_fmi);
                                    discribe = (HyBasicdataPojo) dao.sqlFindObject("queryHyBasicMapping", map,
                                            HyBasicdataPojo.class);
                                    maps.put(spn_fmi, discribe);
                                }
                                if (discribe != null) {
                                    pojo2.setBreakdownDis(
                                            (null == discribe.getDataValue()) ? "" : discribe.getDataValue());
                                } else {
                                    if (command.getUserInfor().getType() != 1
                                            && command.getUserInfor().getType() != 2) {
                                        continue;
                                    } else {
                                        pojo2.setSpnFmi(spn_fmi);
                                        pojo2.setBreakdownDis("未知");
                                    }
                                }
                                break;
                            }
                        }
                    }

                    if (isSpn && isFmi) {
                        pojo2.setbLog(vo.getLocationData().getLongitude() * 0.000001);
                        pojo2.setbLat(vo.getLocationData().getLatitude() * 0.000001);
                        pojo2.setbLoction(pojo2.getbLog() + ";" + pojo2.getbLat());// 导出使用
                        pojo2.setOccurDate(DateUtil.timeStr(vo.getLocationData().getGpsDate()));

                        //TODO
                        pojo2.setMaintenanceSchedule("更换正时皮带、调整或更换正时带轮");
                        pojo2.setMaintenanceCost("700");
                        dataList.add(pojo2);
                    }
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void getResultByNotEmptySF(QueryBreakDownRemindCommand command, QueryBreakDownPojo pojo,
                                       List<QueryBreakDownPojo> dataList, Map<String, HyBasicdataPojo> maps, String eType, byte[] data)
            throws InvalidProtocolBufferException, CloneNotSupportedException {
        List<TerminalLocationData> list;
        TerminalTrackRes builder = TerminalTrackRes.parseFrom(data);
        list = builder.getDatasList();
        if (list != null && list.size() > 0) {
            for (TerminalLocationData vo : list) {
                List<VehicleBreakdown> breakdowns = vo.getLocationData().getBreakdownAddition().getBreakdownList();
                if (breakdowns.size() > 0) {
                    String disStr = "";
                    String spn_fmiStr = "";
                    QueryBreakDownPojo pojo2 = (QueryBreakDownPojo) pojo.clone();
                    for (VehicleBreakdown item : breakdowns) {
                        String spn_fmi = item.getBreakdownSPNValue() + "_" + item.getBreakdownFMIValue();
                        HyBasicdataPojo discribe = null;
                        if (eType == null) {
                            if (command.getUserInfor().getType() != 1 && command.getUserInfor().getType() != 2) {
                                continue;
                            } else {
                                disStr += "未知;";
                            }
                        } else {
                            if (maps.containsKey(spn_fmi)) {
                                discribe = maps.get(spn_fmi);
                            } else {
                                Map<String, Object> map = new HashMap<String, Object>();
                                map.put("dataType", eType);
                                map.put("dataCode", spn_fmi);
                                discribe = (HyBasicdataPojo) dao.sqlFindObject("queryHyBasicMapping", map,
                                        HyBasicdataPojo.class);
                                maps.put(spn_fmi, discribe);
                            }
                            if (discribe != null) {
                                disStr += (null == discribe.getDataValue()) ? ";" : discribe.getDataValue() + ";";
                            } else {
                                if (command.getUserInfor().getType() != 1 && command.getUserInfor().getType() != 2) {
                                    continue;
                                } else {
                                    disStr += "未知;";
                                }
                            }
                        }
                        spn_fmiStr += spn_fmi + ";";
                    }
                    if (!StringUtil.isEmpty(spn_fmiStr)) {
                        pojo2.setSpnFmi(spn_fmiStr.substring(0, spn_fmiStr.length() - 1));
                    }
                    if (!StringUtil.isEmpty(disStr)) {
                        pojo2.setBreakdownDis(disStr.substring(0, disStr.length() - 1));
                    } else {
                        pojo2.setBreakdownDis("未知");
                    }
                    pojo2.setbLog(vo.getLocationData().getLongitude() * 0.000001);
                    pojo2.setbLat(vo.getLocationData().getLatitude() * 0.000001);
                    pojo2.setbLoction(pojo2.getbLog() + ";" + pojo2.getbLat());// 导出使用
                    pojo2.setOccurDate(DateUtil.timeStr(vo.getLocationData().getGpsDate()));

                    //TODO
                    pojo2.setMaintenanceSchedule("更换正时皮带、调整或更换正时带轮");
                    pojo2.setMaintenanceCost("700");
                    dataList.add(pojo2);
                }
            }

        }
    }

    /**
     * 分页
     *
     * @param summaryDtos
     * @param command
     * @return
     */
    @SuppressWarnings("unchecked")
    private PagingInfo<QueryBreakDownPojo> getResult(List<QueryBreakDownPojo> summaryDtos,
                                                     QueryBreakDownRemindCommand command) {

        int pageNumber = Integer.parseInt(command.getPage_number());
        int pageSize = Integer.parseInt(command.getPage_size());
        return ListPageUtil.getPagingInfo(pageNumber, pageSize, summaryDtos);
    }

    private Long setTimeValue(String value) {// 秒级别
        Long date = 0L;
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            date = df.parse(value).getTime() / 1000;
        } catch (ParseException e) {
            logger.error("异常", e);
        }
        return date;
    }

}
