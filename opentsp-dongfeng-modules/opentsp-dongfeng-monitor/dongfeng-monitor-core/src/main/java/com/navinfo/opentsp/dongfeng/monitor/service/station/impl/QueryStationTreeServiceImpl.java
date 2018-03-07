package com.navinfo.opentsp.dongfeng.monitor.service.station.impl;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.service.BaseService;
import com.navinfo.opentsp.dongfeng.monitor.commands.station.QueryStationTreeCommand;
import com.navinfo.opentsp.dongfeng.monitor.constant.TeamConstant;
import com.navinfo.opentsp.dongfeng.monitor.entity.RootTree;
import com.navinfo.opentsp.dongfeng.monitor.entity.TeamTree;
import com.navinfo.opentsp.dongfeng.monitor.pojo.station.QueryStationTreePojo;
import com.navinfo.opentsp.dongfeng.monitor.service.station.IQueryStationTreeService;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author liusanhu@aerozhonghuan.com
 * @Date 2017/3/13
 */
@Service
public class QueryStationTreeServiceImpl extends BaseService implements IQueryStationTreeService {


    @Override
    public HttpCommandResultWithData queryStationTree(QueryStationTreeCommand command) {

        HttpCommandResultWithData result = new HttpCommandResultWithData();
        ArrayList<RootTree> list = new ArrayList<RootTree>();

        // 获取根节点
        Map<String,Object> ps = new HashMap<String,Object>();
        ps.put("teamId", TeamConstant.teamRootId);
        TeamTree root = (TeamTree) dao.sqlFindObject("selectTeamByTeamId", ps, TeamTree.class);
        list.add(root);


        // 获取用户权限下服务站
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("accountId", command.getUserInfor().getUserId());
        params.put("accountType", command.getUserInfor().getType());
        params.put("searchType", command.getSearchType());
        params.put("searchText", command.getSearchText());
        List<QueryStationTreePojo> pojos = dao.sqlFind("searchStationsByAccountId", params, QueryStationTreePojo.class);

        Map<Long, RootTree> map = new HashMap<Long, RootTree>();
        for (QueryStationTreePojo pojo:pojos) {
            RootTree parent = map.get(pojo.getPovince().longValue());
            if (parent == null){
                parent = new RootTree();
                parent.setId(pojo.getPovince().longValue()+"");
                parent.setDid(BigInteger.valueOf(pojo.getPovince().longValue()));
                parent.setChecked(false);
                parent.setIsParent(true);
                parent.setName(pojo.getGovName());
                parent.setOpen(true);
                parent.setpId("1");
                map.put(pojo.getPovince().longValue(),parent);
                list.add(parent);
            }

            RootTree rt = new RootTree();
            rt.setId("L"+pojo.getId().longValue());
            rt.setDid(BigInteger.valueOf(pojo.getId().longValue()));
            rt.setChecked(false);
            rt.setIsParent(false);
            rt.setName(pojo.getStationName());
            rt.setOpen(false);
            rt.setpId(parent.getId());
            list.add(rt);
        }
        result.setData(list);
        return result;
    }
}
