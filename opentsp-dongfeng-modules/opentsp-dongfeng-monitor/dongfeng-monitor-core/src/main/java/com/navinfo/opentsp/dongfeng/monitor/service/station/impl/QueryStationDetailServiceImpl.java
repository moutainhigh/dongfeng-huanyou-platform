package com.navinfo.opentsp.dongfeng.monitor.service.station.impl;

import com.navinfo.opentsp.dongfeng.common.constant.DictionaryConstant;
import com.navinfo.opentsp.dongfeng.common.constant.UserTypeConstant;
import com.navinfo.opentsp.dongfeng.common.dto.BaseData;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.service.BaseService;
import com.navinfo.opentsp.dongfeng.common.service.IBaseDataService;
import com.navinfo.opentsp.dongfeng.common.util.ListUtil;
import com.navinfo.opentsp.dongfeng.common.util.StringUtil;
import com.navinfo.opentsp.dongfeng.monitor.commands.station.QueryStationDetailCommand;
import com.navinfo.opentsp.dongfeng.monitor.converter.station.QueryStationDetailConverter;
import com.navinfo.opentsp.dongfeng.monitor.pojo.station.QueryStationDetailPojo;
import com.navinfo.opentsp.dongfeng.monitor.service.station.IQueryStationDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author fengwm
 * @version 1.0
 * @date 2017-03-13
 * @modify
 * @copyright Navi Tsp
 */
@Service
public class QueryStationDetailServiceImpl extends BaseService implements IQueryStationDetailService {

    @Autowired
    private IBaseDataService iBaseDataService;

    @Override
    public HttpCommandResultWithData queryStationDetail(QueryStationDetailCommand queryStationDetailCommand) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        //根据服务站ID获取服务站pojo
        Map<String, Object> parm = new HashMap<>();
        //用户服务站权限过滤---start
        StringBuffer stationIds = new StringBuffer();
        if(queryStationDetailCommand.getUserInfor().getType()> UserTypeConstant.SYSTEM_ADMIN.getCode()){
            parm.put("accountId", queryStationDetailCommand.getUserInfor().getUserId());
            parm.put("accountType", queryStationDetailCommand.getUserInfor().getType());
            List<QueryStationDetailPojo> queryStationDetailIds = (List<QueryStationDetailPojo>) dao.sqlFind("queryStationIdsByAccountId", parm, QueryStationDetailPojo.class);
            if(!ListUtil.isEmpty(queryStationDetailIds)){
                int i = 0;
                for (QueryStationDetailPojo idObj : queryStationDetailIds) {
                    i++;
                    if (i == queryStationDetailIds.size()) {
                        stationIds.append(idObj.getId().toString());
                    } else {
                        stationIds.append(idObj.getId().toString()).append(",");
                    }
                }
            }
        }
        parm.put("id", Long.valueOf(queryStationDetailCommand.getStationId()));
        parm.put("stationIds",stationIds.toString());
        //用户服务站权限过滤---end
        QueryStationDetailPojo queryStationDetailPojo = (QueryStationDetailPojo) dao.sqlFindObject("queryStationDetail", parm, QueryStationDetailPojo.class);
        if (null != queryStationDetailPojo) {
            //查询服务站总评价数目
            QueryStationDetailPojo commentCountsPojo = (QueryStationDetailPojo) dao.sqlFindObject("queryStationCommentCounts", parm, QueryStationDetailPojo.class);
            if (null == commentCountsPojo) {
                queryStationDetailPojo.setCommentCounts(new BigInteger("0"));
            } else {
                queryStationDetailPojo.setCommentCounts(commentCountsPojo.getCommentCounts());
            }
            //查询服务站总星级评价
            QueryStationDetailPojo commentStarLeverCountsPojo = (QueryStationDetailPojo) dao.sqlFindObject("queryStationStarLeverCounts", parm, QueryStationDetailPojo.class);
            if (null == commentStarLeverCountsPojo) {
                queryStationDetailPojo.setStarLeverCounts(new BigInteger("0"));
            } else {
                queryStationDetailPojo.setStarLeverCounts(commentStarLeverCountsPojo.getStarLeverCounts());
            }
            //服务内容
            queryStationDetailPojo.setServiceContent(getContent(1, queryStationDetailPojo.getServiceContent()));
            //服务配置
            queryStationDetailPojo.setPartsContent(getContent(2, queryStationDetailPojo.getPartsContent()));
            result.setData(QueryStationDetailConverter.stationDetailConverter(queryStationDetailPojo));
        }
        return result;
    }

    /**
     * @param type    1:服务内容转换   2：服务明细转换
     * @param content 服务内容OR服务明细
     */
    public String getContent(Integer type, String content) {
        //返回结果
        String result = "";
        //明细OR内容  eg：1,2,...
        String[] sec = StringUtil.isEmpty(content) ? null : content.split(",");
        List<BaseData> contentList = new ArrayList<BaseData>();
        if (type == 1) {
            contentList = iBaseDataService.queryList(DictionaryConstant.BaseDataType.SERVICE_CONTENT.getType());
        }
        if (type == 2) {
            contentList = iBaseDataService.queryList(DictionaryConstant.BaseDataType.SERVICE_PARTS.getType());
        }
        if (sec != null) {
            for (int i = 0; i < sec.length; i++) {
                for (BaseData entry : contentList) {
                    if (sec[i].equals(entry.getCode())) {
                        result += entry.getValue() + ",";
                    }
                }
            }
        }
        return result;
    }
}