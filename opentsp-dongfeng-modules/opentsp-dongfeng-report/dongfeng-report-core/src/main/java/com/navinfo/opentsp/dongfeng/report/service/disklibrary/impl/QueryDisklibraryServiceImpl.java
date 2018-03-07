package com.navinfo.opentsp.dongfeng.report.service.disklibrary.impl;

import com.navinfo.opentsp.dongfeng.common.cache.GpsCache;
import com.navinfo.opentsp.dongfeng.common.constant.QueryConstants;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.PagingInfo;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.common.service.BaseService;
import com.navinfo.opentsp.dongfeng.common.util.DateUtil;
import com.navinfo.opentsp.dongfeng.common.util.StringUtil;
import com.navinfo.opentsp.dongfeng.report.commands.disklibrary.QueryDealerTipCommand;
import com.navinfo.opentsp.dongfeng.report.commands.disklibrary.QueryDisklibraryCommand;
import com.navinfo.opentsp.dongfeng.report.converter.disklibrary.QueryDisklibraryConverter;
import com.navinfo.opentsp.dongfeng.report.dto.disklibrary.DisklibraryDto;
import com.navinfo.opentsp.dongfeng.report.dto.disklibrary.QueryDealerTiptDto;
import com.navinfo.opentsp.dongfeng.report.pojo.disklibrary.DisklibraryPojo;
import com.navinfo.opentsp.dongfeng.report.pojo.disklibrary.QueryDealerTipPojo;
import com.navinfo.opentsp.dongfeng.report.service.disklibrary.IQueryDisklibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author fengwm
 * @version 1.0
 * @date 2017-12-19
 * @modify
 * @copyright Navi Tsp
 */
@Service
public class QueryDisklibraryServiceImpl extends BaseService implements IQueryDisklibraryService {

    @Autowired
    private GpsCache gpsCache;

    @Override
    public PagingInfo queryDisklibrary(QueryDisklibraryCommand command, boolean isPaging) {
        PagingInfo page = new PagingInfo();
        Map<String, Object> param = new HashMap<>();
        if(StringUtil.isNotEmpty(command.getQueryDate())){
            param.put("beDate", DateUtil.strTimeChangeLong(command.getQueryDate()+DateUtil.start));
            param.put("enDate", DateUtil.strTimeChangeLong(command.getQueryDate() + DateUtil.end));
        }else{
            param.put("beDate", null);
            param.put("enDate", null);
        }
        param.put("dealerName", command.getDealerName());
        param.put("dealerCode", command.getDealerCode());
        if (StringUtil.isNotEmpty(command.getInvaFlag()) && !command.getInvaFlag().equals(QueryConstants.CONDITION_ALL)) {//车辆型号
            param.put("invaFlag", command.getInvaFlag());
        }
        PagingInfo<DisklibraryPojo> pageInfo = new PagingInfo<>();
        List<DisklibraryPojo> list = new ArrayList<DisklibraryPojo>();
        if (isPaging) {
            pageInfo = dao.sqlPagelFind("queryDisklibraryByParam",
                    param,
                    StringUtil.toInteger(command.getPage_number()),
                    StringUtil.toInteger(command.getPage_size()),
                    DisklibraryPojo.class);
                    list = pageInfo.getList();
        } else {
            list = dao.sqlFind("queryDisklibraryByParam", param, DisklibraryPojo.class);
            pageInfo.setList(list);
            pageInfo.setPage_total(0);
            pageInfo.setTotal(0);
        }
        List<DisklibraryDto> dtos= QueryDisklibraryConverter.convert(list);
        page.setList(dtos);
        page.setPage_total(pageInfo.getPage_total());
        page.setTotal(pageInfo.getTotal());
        return page;
    }

    /**
     * 经销商tip(目前盘库报表使用的查询结果为全部)
     * @param command
     * @return
     */
    @Override
    public HttpCommandResultWithData queryDealerTip(QueryDealerTipCommand command) {
        logger.info("=====  queryDistrict start  =====");
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        PagingInfo page = new PagingInfo();
        Map<String,Object> parm = new HashMap<String,Object>();
        parm.put("accountId", command.getUserInfor().getUserId());
        parm.put("accountType",command.getUserInfor().getType());
        parm.put("tname",command.getTname());
        parm.put("type",command.getType());
        if(command.getTid()!=null){
            parm.put("id", Long.parseLong(command.getTid()));
        }
        PagingInfo<QueryDealerTipPojo> pageinfo = dao.sqlPagelFind("queryAllDealer", parm, Integer.valueOf(command.getPage_number()), Integer.valueOf(command.getPage_size()), QueryDealerTipPojo.class);
        List<QueryDealerTipPojo> list = pageinfo.getList();
        page.setList(convertToDtoList(list));
        page.setPage_total(pageinfo.getPage_total());
        page.setTotal(pageinfo.getTotal());
        result.setData(page);
        result.fillResult(ReturnCode.OK);
        logger.info("===== queryDistrict end  =====");
        return result;
    }
    /**
     * pojo集合转dto集合
     */
    public static List<QueryDealerTiptDto> convertToDtoList(List<QueryDealerTipPojo> list) {
        List<QueryDealerTiptDto> listDto = new ArrayList<QueryDealerTiptDto>();
        if (!list.isEmpty()) {
            for (QueryDealerTipPojo pojo : list) {
                QueryDealerTiptDto dto = pojoToDto(pojo);
                listDto.add(dto);
            }
        }
        return listDto;
    }
    /**
     * pojo转dto
     */
    public static QueryDealerTiptDto pojoToDto(QueryDealerTipPojo pojo) {
        QueryDealerTiptDto dto = new QueryDealerTiptDto();
        dto.setId(pojo.getId().longValue());
        dto.setTname(pojo.getTname());
        dto.setPname(pojo.getPname());
        dto.setManageBrand(pojo.getManageBrand());
        dto.setDealerCode(pojo.getDealerCode());
        return dto;
    }
}
