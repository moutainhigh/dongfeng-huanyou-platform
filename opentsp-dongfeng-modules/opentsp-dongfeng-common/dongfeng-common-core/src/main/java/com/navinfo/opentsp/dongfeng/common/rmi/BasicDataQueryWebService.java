package com.navinfo.opentsp.dongfeng.common.rmi;


import com.lc.rp.webservice.service.CommonParameter;
import com.lc.rp.webservice.service.Point;

import java.util.List;

public interface BasicDataQueryWebService {

    public byte[] getTerminalTrack(long terminalId, long beginDate, long endDate, boolean isFilterBreakdown,
                                   long breakdownCode, CommonParameter commonParameter, int isAll) throws Exception;

    public byte[] getTerminalTrack(long terminalId, long beginDate, long endDate, boolean isFilterBreakdown,
                                   long breakdownCode, boolean isThin, int level, CommonParameter commonParameter,int isAll) throws Exception;

    public byte[] getCANBusData(List<Long> terminalIds, int type, long beginDate, long endDate,
                                CommonParameter commonParameter);

    public byte[] getLastestLocationData(List<Long> terminalIds, int type);

    public byte[] getLastestLocationData(List<Long> terminalIds, String milesRange, int type, CommonParameter commonParameter);

    public byte[] getTerminalLocationData(Long terminalid, Long queryDate, int index);

    public byte[] getLastestLocationDataByAlarmFilter(List<Long> terminalIds, boolean isFilter, Long alarmFilter,
                                                      int isCancel, int type, CommonParameter commonParameter);

    public byte[] getTerminalRegular(List<Long> terminalIds, List<Long> areaIds, long accessTocken1) throws Exception;

    public List<Long[]> getTerminalMessageSwitch(List<Long> terminalIds);

    public byte[] getTerminalInDistrict(List<Long> terminalIds, int districtCode);

    public byte[] getTerminalInArea(List<Long> terminalIds, int type, List<Point> points, int radius);

    public byte[] getLastestTerminalStatus(List<Long> list, long l);

    /**
     * 获取首次连线时间
     *
     * @param terminalIds 终端通讯号
     * @return
     */
    byte[] getTerminalFirstReceiveDate(List<Long> terminalIds);
}
