package com.navinfo.opentsp.dongfeng.monitor.service.risk.impl;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.service.BaseService;
import com.navinfo.opentsp.dongfeng.monitor.commands.risk.QueryRiskTreeCommand;
import com.navinfo.opentsp.dongfeng.monitor.entity.RootTree;
import com.navinfo.opentsp.dongfeng.monitor.entity.TeamTree;
import com.navinfo.opentsp.dongfeng.monitor.service.risk.IQueryRiskTreeService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author liusanhu@aerozhonghuan.com
 * @Date 2017/3/13
 */
@Service
public class QueryRiskTreeServiceImpl extends BaseService implements IQueryRiskTreeService {
    @Override
    public HttpCommandResultWithData queryRiskTree(QueryRiskTreeCommand command) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        ArrayList<RootTree> list = new ArrayList<RootTree>();
        // 获取用户权限下分组
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("accountId", command.getUserInfor().getUserId());
        params.put("accountType", command.getUserInfor().getType());
        List<TeamTree> teams = dao.sqlFind("selectTeamsByAccountId", params, TeamTree.class);
        list.addAll(teams);

        params.put("searchType", command.getSearchType());
        params.put("searchText", command.getSearchText());
        List<RootTree> risks = dao.sqlFind("searchRisksByAccountId", params, RootTree.class);

        for (RootTree risk:risks) {
            risk.setIsParent(false);
            list.add(risk);
        }

        //如果有搜索，清理空组
        if(command.getSearchText() != null){
            clearEmptyTeam(teams,risks,list);
        }




        result.setData(list);
        return result;
    }

    /**
     * 清理空组
     * @param teams
     * @param risks
     */
    public void clearEmptyTeam(List<TeamTree> teams,List<RootTree> risks,ArrayList<RootTree> list) {
        Map<Long,TeamTree> teamMap = new HashMap<Long,TeamTree>();
        for (TeamTree team:teams) {
            teamMap.put(team.getDid().longValue(),team);
        }

        ArrayList<Long> pids = new ArrayList<Long>();
        //父节点，默认用户是系统管理员类型
        pids.add(1L);
        // 寻找根节点
        for (TeamTree team : teams) {
            TeamTree parent = teamMap.get(Long.parseLong(team.getpId()));
            if (parent == null) {
                pids.add(team.getDid().longValue());
            }
        }

        addRecTotal(risks,teamMap);
        for (Long pid : pids) {
            TeamTree parentTree = teamMap.get(pid);
            if(parentTree != null){
                recTeamTree(parentTree,teamMap);
            }
        }

        // 清理空组
        for (TeamTree teamTree : teamMap.values()) {
            if (teamTree.getTotal().longValue() == 0) {
                list.remove(teamTree);
            }
        }
    }


    /**
     * 添加总数
     *
     * @param risks
     * @param map
     */
    public void addRecTotal(List<RootTree> risks,
                                     Map<Long, TeamTree> map) {
        // 封装在线数/总数
        for (RootTree risk : risks) {
            TeamTree team = map.get(Long.parseLong(risk.getpId()));
            if (team != null) {
                team.setTotal(team.getTotal() + 1);
            }
        }
    }

    /**
     * 递归下级在线数/总数
     *
     * @param parent
     * @param map
     */
    public void recTeamTree(TeamTree parent, Map<Long, TeamTree> map) {

        for (RootTree rootTree : map.values()) {
            TeamTree teamTree = (TeamTree) rootTree;
            if (teamTree.getpId().equals(parent.getId())) {
                if (teamTree.getIsParent()) {
                    recTeamTree(teamTree, map);
                    parent.setTotal(parent.getTotal() + teamTree.getTotal());
                }
            }
        }

    }

}
