package com.navinfo.opentsp.dongfeng.monitor.service.car.impl;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.service.BaseService;
import com.navinfo.opentsp.dongfeng.monitor.commands.car.QueryDealersCommand;
import com.navinfo.opentsp.dongfeng.monitor.pojo.car.QueryDealersPojo;
import com.navinfo.opentsp.dongfeng.monitor.service.car.IQueryDealersService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 经销商选择弹框
 * @Author liusanhu@aerozhonghuan.com
 * @Date 2017/3/24
 */
@Service
public class QueryDealersServiceImpl extends BaseService implements IQueryDealersService {

    public HttpCommandResultWithData queryDealers(QueryDealersCommand command) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("accountId", command.getUserInfor().getUserId());
        params.put("accountType", command.getUserInfor().getType());
        List<QueryDealersPojo> list = dao.sqlFind("queryDealers", params, QueryDealersPojo.class);
        result.setData(list);
        return result;
    }
}
