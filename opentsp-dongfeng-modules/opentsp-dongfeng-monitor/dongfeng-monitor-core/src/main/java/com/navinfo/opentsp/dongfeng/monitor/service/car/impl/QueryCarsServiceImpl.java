package com.navinfo.opentsp.dongfeng.monitor.service.car.impl;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.PagingInfo;
import com.navinfo.opentsp.dongfeng.common.service.BaseService;
import com.navinfo.opentsp.dongfeng.monitor.commands.car.QueryCarsCommand;
import com.navinfo.opentsp.dongfeng.monitor.pojo.car.QueryCarsPojo;
import com.navinfo.opentsp.dongfeng.monitor.service.car.IQueryCarsService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 车辆选择弹框
 * @Author liusanhu@aerozhonghuan.com
 * @Date 2017/3/24
 */
@Service
public class QueryCarsServiceImpl extends BaseService implements IQueryCarsService {

    public HttpCommandResultWithData queryCars(QueryCarsCommand command) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("accountId", command.getUserInfor().getUserId());
        params.put("accountType", command.getUserInfor().getType());
        params.put("chassisNum",command.getChassisNum());
        params.put("searchText",command.getSearchText());
        PagingInfo<QueryCarsPojo> page = dao.sqlPagelFind("queryCars", params,Integer.valueOf(command.getPage_number()), Integer.valueOf(command.getPage_size()), QueryCarsPojo.class);
        result.setData(page);
        return result;
    }
}
