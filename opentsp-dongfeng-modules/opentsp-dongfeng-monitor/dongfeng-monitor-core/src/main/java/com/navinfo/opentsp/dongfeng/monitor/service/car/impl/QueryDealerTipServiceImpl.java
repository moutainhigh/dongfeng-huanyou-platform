package com.navinfo.opentsp.dongfeng.monitor.service.car.impl;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.service.BaseService;
import com.navinfo.opentsp.dongfeng.monitor.commands.car.QueryDealerTipCommand;
import com.navinfo.opentsp.dongfeng.monitor.pojo.car.QueryDealerTipPojo;
import com.navinfo.opentsp.dongfeng.monitor.service.car.IQueryDealerTipService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 经销商Tip框
 * @Author liusanhu@aerozhonghuan.com
 * @Date 2017/5/26
 */
@Service
public class QueryDealerTipServiceImpl extends BaseService implements IQueryDealerTipService {
    @Override
    public HttpCommandResultWithData queryDealerTip(QueryDealerTipCommand command) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("accountId", command.getUserInfor().getUserId());
        params.put("accountType", command.getUserInfor().getType());
        params.put("id", command.getId());
        QueryDealerTipPojo pojo = (QueryDealerTipPojo)dao.sqlFindObject("queryDealerTip", params, QueryDealerTipPojo.class);
        result.setData(pojo);
        return result;
    }
}
