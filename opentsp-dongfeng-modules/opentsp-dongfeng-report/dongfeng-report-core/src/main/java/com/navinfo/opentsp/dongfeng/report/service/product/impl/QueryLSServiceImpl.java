package com.navinfo.opentsp.dongfeng.report.service.product.impl;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.PagingInfo;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.common.service.BaseService;
import com.navinfo.opentsp.dongfeng.report.commands.product.QueryLSCommand;
import com.navinfo.opentsp.dongfeng.report.converter.product.QueryLSConverter;
import com.navinfo.opentsp.dongfeng.report.pojo.product.QueryLSPojo;
import com.navinfo.opentsp.dongfeng.report.service.product.IQueryLSService;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 生产线漏扫
 *
 * @wenya
 * @create 2017-03-28 11:24
 **/
@Service
public class QueryLSServiceImpl extends BaseService implements IQueryLSService{
    @Override
    public HttpCommandResultWithData queryLs(QueryLSCommand command,boolean isExport) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        Map<String, Object> param = new HashMap<String , Object>();
        param.put("chassisNum", command.getChassisNum());
        param.put("storageLoc", command.getStorageLoc());
        PagingInfo datas = new PagingInfo();
        if (isExport) {
            List<QueryLSPojo> list = dao.sqlFind("queryLsReport", param, QueryLSPojo.class);
            datas.setList(list);
            datas.setTotal(list.size());
        } else {
            datas = dao.sqlPagelFind("queryLsReport", param, NumberUtils.toInt(command.getPage_number()),
                    NumberUtils.toInt(command.getPage_size()), QueryLSPojo.class);
        }
        PagingInfo page = new PagingInfo();
        page.setList(QueryLSConverter.convertToDtoList(datas.getList()));
        page.setPage_total(datas.getPage_total());
        page.setTotal(datas.getTotal());
        result.setData(page);
        result.fillResult(ReturnCode.OK);
        return result;
    }
}
