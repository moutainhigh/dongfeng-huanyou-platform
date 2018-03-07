package com.navinfo.opentsp.dongfeng.monitor.service.risk.impl;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.service.BaseService;
import com.navinfo.opentsp.dongfeng.monitor.commands.risk.QueryRisksCommand;
import com.navinfo.opentsp.dongfeng.monitor.converter.risk.QueryRisksConverter;
import com.navinfo.opentsp.dongfeng.monitor.pojo.risk.QueryRisksPojo;
import com.navinfo.opentsp.dongfeng.monitor.service.risk.IQueryRisksService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 防控区域选择弹框
 * @Author liusanhu@aerozhonghuan.com
 * @Date 2017/3/24
 */
@Service
public class QueryRisksServiceImpl extends BaseService implements IQueryRisksService {

    public HttpCommandResultWithData queryRisks(QueryRisksCommand command) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("accountId", command.getUserInfor().getUserId());
        params.put("accountType", command.getUserInfor().getType());
        List<QueryRisksPojo> list = dao.sqlFind("queryRisks", params, QueryRisksPojo.class);
        result.setData(QueryRisksConverter.toQueryRisksDto(list));
        return result;
    }
}
