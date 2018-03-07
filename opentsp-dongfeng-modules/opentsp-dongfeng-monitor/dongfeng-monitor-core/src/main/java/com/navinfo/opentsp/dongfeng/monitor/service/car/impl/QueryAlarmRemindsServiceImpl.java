package com.navinfo.opentsp.dongfeng.monitor.service.car.impl;

import com.lc.core.protocol.common.LCLocationData;
import com.navinfo.opentsp.dongfeng.common.cache.AlarmCache;
import com.navinfo.opentsp.dongfeng.common.cache.GpsCache;
import com.navinfo.opentsp.dongfeng.common.constant.CloudConstants;
import com.navinfo.opentsp.dongfeng.common.dto.AlarmInfoData;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.common.service.BaseService;
import com.navinfo.opentsp.dongfeng.common.util.CopyPropUtil;
import com.navinfo.opentsp.dongfeng.common.util.DateUtil;
import com.navinfo.opentsp.dongfeng.common.util.NumberFormatUtil;
import com.navinfo.opentsp.dongfeng.monitor.commands.car.QueryAlarmRemindsCommand;
import com.navinfo.opentsp.dongfeng.monitor.converter.car.QueryAlarmRemindsConverter;
import com.navinfo.opentsp.dongfeng.monitor.pojo.car.QueryAlarmRemindsPojo;
import com.navinfo.opentsp.dongfeng.monitor.service.car.IQueryAlarmRemindsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.*;

/**
 * 报警提醒service
 *
 * @author fengwm
 * @version 1.0
 * @date 2017-03-09
 * @modify
 * @copyright Navi Tsp
 */
@Service
public class QueryAlarmRemindsServiceImpl extends BaseService implements IQueryAlarmRemindsService {
    @Autowired
    AlarmCache alarmCache;
    @Autowired
    GpsCache gpsCache;
    /**
     * QueryAlarmRemindServiceImpl LOG
     */
    protected static final Logger logger = LoggerFactory.getLogger(QueryAlarmRemindsServiceImpl.class);

    /**
     * 报警提醒查询
     *
     * @param command
     * @return
     */
    @Override
    public HttpCommandResultWithData queryAlarmRemind(QueryAlarmRemindsCommand command) {

        HttpCommandResultWithData result = new HttpCommandResultWithData();
        try {
            //查询用户下所有的车辆信息
            Map<String, Object> parm = new HashMap<String, Object>();
            parm.put("accountId", command.getUserInfor().getUserId());
            parm.put("accountType", command.getUserInfor().getType());
            List<QueryAlarmRemindsPojo> carList = dao.sqlFind("queryCarsByAccountIdForAlarm", parm, QueryAlarmRemindsPojo.class);
            //组装一个通讯号集合,并进行通讯号与对象映射
            Map<String, QueryAlarmRemindsPojo> carListMap = new HashMap<String, QueryAlarmRemindsPojo>();
            List<String> commIds = new ArrayList<String>();
            for (QueryAlarmRemindsPojo pojoC : carList) {
                commIds.add(pojoC.getCommId().toString());
                carListMap.put(pojoC.getCommId().toString(), pojoC);
            }
            //获取原始报警缓存
            List<AlarmInfoData> alarmInfoDatas = alarmCache.getAlarmInfos(commIds, 60);
            //AlarmInfoData>>QueryAlarmRemindsPojo
            List<QueryAlarmRemindsPojo> alarmInitList = new ArrayList<QueryAlarmRemindsPojo>();
            for (AlarmInfoData info : alarmInfoDatas) {
                QueryAlarmRemindsPojo copyAlarm = new QueryAlarmRemindsPojo();
                CopyPropUtil.copyProp(info, copyAlarm);
                copyAlarm.setCommId(new BigInteger(info.getSim()));
                copyAlarm.setLat(info.getLatitudes());
                copyAlarm.setLng(info.getLongitudes());
                alarmInitList.add(copyAlarm);
            }
            //重新组装报警数据
            setAlarmData(alarmInitList, carListMap);
            result.setData(QueryAlarmRemindsConverter.convertToDtoList(alarmInitList));
        } catch (Exception e) {
            logger.error(this.getClass().getName() + "`s QueryAlarmRemindsServiceImpl has error : " + e.getMessage());
            result.fillResult(ReturnCode.SERVER_ERROR);
            result.setMessage("查询报警提醒异常");
        }
        return result;
    }

    /**
     * 组装报警数据
     * @param alarmInitList
     * @param carListMap
     */
    public void setAlarmData(List<QueryAlarmRemindsPojo> alarmInitList, Map<String, QueryAlarmRemindsPojo> carListMap) {
        //重新组装报警数据
        for (QueryAlarmRemindsPojo pojo : alarmInitList) {
            //组装底盘号。。。信息使用
            QueryAlarmRemindsPojo fixedPojo = carListMap.get(pojo.getCommId().toString());
            //底盘号
            pojo.setChassisNum(fixedPojo.getChassisNum());
            //车辆ID
            pojo.setCarId(fixedPojo.getCarId());
            //报警时间
            pojo.setAlarmDateStr(DateUtil.timeStr(pojo.getAlarmDate()));
            //报警类型转换
            String alarmTypeStr = "";
            switch (pojo.getAlarmType()) {
                case 1:
                    alarmTypeStr = CloudConstants.AlarmRemindType.inOutAreaAlarm.getName();
                    break;
                case 2:
                    alarmTypeStr = CloudConstants.AlarmRemindType.retainedTimeoutAlarm.getName();
                    break;
                case 3:
                    alarmTypeStr = CloudConstants.AlarmRemindType.terminalRemoveAlarm.getName();
                    break;
                case 4:
                    // 赋值经纬度
                    LCLocationData.LocationData gpsdata = gpsCache.getLastGpsVlid(pojo.getCommId().toString());
                    if(gpsdata != null) {
                        pojo.setLat(NumberFormatUtil.getLatitudeOrLongitude(gpsdata.getLatitude()));
                        pojo.setLng(NumberFormatUtil.getLatitudeOrLongitude(gpsdata.getLongitude()));
                    }
                    break;
                case 5:
                    alarmTypeStr = CloudConstants.AlarmRemindType.dNotMatchAlarm.getName();
                    break;
            }
            pojo.setAlarmTypeStr(alarmTypeStr);
            //报警次数
            if (pojo != null && pojo.getAlarmOverType() == 0) {
                pojo.setAlarmtimes((long) Math.floor((new Date().getTime() / 1000 - pojo.getAlarmDate()) / 60));
            }
        }
    }
}