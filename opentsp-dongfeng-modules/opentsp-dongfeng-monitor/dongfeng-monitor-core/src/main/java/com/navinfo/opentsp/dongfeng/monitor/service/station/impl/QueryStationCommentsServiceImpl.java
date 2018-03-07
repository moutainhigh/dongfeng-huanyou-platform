package com.navinfo.opentsp.dongfeng.monitor.service.station.impl;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.PagingInfo;
import com.navinfo.opentsp.dongfeng.common.service.BaseService;
import com.navinfo.opentsp.dongfeng.monitor.commands.station.QueryStationCommentsCommand;
import com.navinfo.opentsp.dongfeng.monitor.converter.station.QueryStationCommentsConverter;
import com.navinfo.opentsp.dongfeng.monitor.pojo.station.QueryStationCommentsPojo;
import com.navinfo.opentsp.dongfeng.monitor.pojo.station.StationCommentsPojo;
import com.navinfo.opentsp.dongfeng.monitor.service.station.IQueryStationCommentsService;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author fengwm
 * @version 1.0
 * @date 2017-03-14
 * @modify
 * @copyright Navi Tsp
 */
@Service
public class QueryStationCommentsServiceImpl extends BaseService implements IQueryStationCommentsService {

    public static final int LEVEL_ONE = 1;
    public static final int LEVEL_TWO = 2;
    public static final int LEVEL_THREE = 3;
    public static final int LEVEL_FOUR = 4;
    public static final int LEVEL_FIVE = 5;

    @Override
    public HttpCommandResultWithData queryStationComments(QueryStationCommentsCommand queryStationCommentsCommand) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        //查询评价信息
        Map<String, Object> parm = new HashMap<>();
        parm.put("stationId", new BigInteger(queryStationCommentsCommand.getStationId()));
        PagingInfo commonsPageInfo = dao.sqlPagelFind("queryStationComments", parm, Integer.valueOf(queryStationCommentsCommand.getPage_number()), Integer.valueOf(queryStationCommentsCommand.getPage_size()), StationCommentsPojo.class);
        //查询星级数量
        List<QueryStationCommentsPojo> poLevels = (List<QueryStationCommentsPojo>) dao.sqlFind("queryStationLevel", parm, QueryStationCommentsPojo.class);
        //组装成返回格式数据
        QueryStationCommentsPojo queryStationCommentsPojo = new QueryStationCommentsPojo();
        queryStationCommentsPojo.setPagingInfo(commonsPageInfo);
        int allCount = 0;
        for (QueryStationCommentsPojo scPojo : poLevels) {
            allCount += scPojo.getCommentCount().intValue();
            switch (scPojo.getLevelType()) {
                case LEVEL_ONE: {
                    queryStationCommentsPojo.setOneStarCount(scPojo.getCommentCount());
                    break;
                }
                case LEVEL_TWO: {
                    queryStationCommentsPojo.setTwoStarCount(scPojo.getCommentCount());
                    break;
                }
                case LEVEL_THREE: {
                    queryStationCommentsPojo.setThreeStarCount(scPojo.getCommentCount());
                    break;
                }
                case LEVEL_FOUR: {
                    queryStationCommentsPojo.setFourStarCount(scPojo.getCommentCount());
                    break;
                }
                case LEVEL_FIVE: {
                    queryStationCommentsPojo.setFiveStarCount(scPojo.getCommentCount());
                    break;
                }
                default:
            }
        }
        queryStationCommentsPojo.setCommentCount(new BigInteger(String.valueOf(allCount)));
        result.setData(QueryStationCommentsConverter.stationDetailConverter(queryStationCommentsPojo));
        return result;
    }
}