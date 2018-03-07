package com.navinfo.opentsp.dongfeng.monitor.service.car.impl;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.PagingInfo;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.common.service.BaseService;
import com.navinfo.opentsp.dongfeng.monitor.commands.car.QueryDistrictCommand;
import com.navinfo.opentsp.dongfeng.monitor.converter.car.QueryDistrictConverter;
import com.navinfo.opentsp.dongfeng.monitor.pojo.car.QueryDistrictPojo;
import com.navinfo.opentsp.dongfeng.monitor.service.car.IQueryDistrictService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @wenya
 * @create 2017-03-24 17:37
 **/
@Service
public class QueryDistrictServiceImpl extends BaseService implements IQueryDistrictService{
    @Override
    public HttpCommandResultWithData queryDistrict(QueryDistrictCommand command) {
        logger.info("=====  queryDistrict start  =====");
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        PagingInfo page = new PagingInfo();
        Map<String,Object> parm = new HashMap<String,Object>();
        parm.put("accountId", command.getUserInfor().getUserId());
        parm.put("accountType",command.getUserInfor().getType());
        parm.put("districtName",command.getDistrictName());
        parm.put("type",command.getType());
        if(command.getTid()!=null){
            parm.put("id",Long.parseLong(command.getTid()));
        }
        PagingInfo<QueryDistrictPojo> pageinfo = dao.sqlPagelFind("queryDistrict", parm, Integer.valueOf(command.getPage_number()), Integer.valueOf(command.getPage_size()), QueryDistrictPojo.class);
        List<QueryDistrictPojo> list = pageinfo.getList();
        page.setList(QueryDistrictConverter.convertToDtoList(list));
        page.setPage_total(pageinfo.getPage_total());
        page.setTotal(pageinfo.getTotal());
        result.setData(page);
        result.fillResult(ReturnCode.OK);
        logger.info("===== queryDistrict end  =====");
        return result;
    }
}
