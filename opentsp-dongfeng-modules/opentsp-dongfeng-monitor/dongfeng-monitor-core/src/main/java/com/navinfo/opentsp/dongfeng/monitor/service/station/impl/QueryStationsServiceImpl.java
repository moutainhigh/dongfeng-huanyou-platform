package com.navinfo.opentsp.dongfeng.monitor.service.station.impl;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.service.BaseService;
import com.navinfo.opentsp.dongfeng.monitor.commands.station.QueryStationsCommand;
import com.navinfo.opentsp.dongfeng.monitor.pojo.station.QueryStationsPojo;
import com.navinfo.opentsp.dongfeng.monitor.service.station.IQueryStationsService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 服务站选择弹框
 * @Author liusanhu@aerozhonghuan.com
 * @Date 2017/3/24
 */
@Service
public class QueryStationsServiceImpl extends BaseService implements IQueryStationsService {

    public HttpCommandResultWithData queryStations(QueryStationsCommand command) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("accountId", command.getUserInfor().getUserId());
        params.put("accountType", command.getUserInfor().getType());
        List<QueryStationsPojo> list = dao.sqlFind("queryStations", params, QueryStationsPojo.class);
        result.setData(list);
        return result;
    }
}
