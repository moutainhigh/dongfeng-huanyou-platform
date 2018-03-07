package com.navinfo.opentsp.dongfeng.monitor.service.car.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.navinfo.opentsp.common.messaging.ResultCode;
import com.navinfo.opentsp.dongfeng.common.cache.GpsInfoCache;
import com.navinfo.opentsp.dongfeng.common.constant.CacheKeyConstants;
import com.navinfo.opentsp.dongfeng.common.constant.Constants;
import com.navinfo.opentsp.dongfeng.common.constant.UserTypeConstant;
import com.navinfo.opentsp.dongfeng.common.dto.GpsInfoData;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.service.BaseService;
import com.navinfo.opentsp.dongfeng.common.util.StringUtil;
import com.navinfo.opentsp.dongfeng.monitor.commands.car.QueryAsyncTreeCommand;
import com.navinfo.opentsp.dongfeng.monitor.constant.TeamConstant;
import com.navinfo.opentsp.dongfeng.monitor.entity.CarTree;
import com.navinfo.opentsp.dongfeng.monitor.entity.RootTree;
import com.navinfo.opentsp.dongfeng.monitor.entity.TeamTree;
import com.navinfo.opentsp.dongfeng.monitor.pojo.car.QueryAsyncTreePojo;
import com.navinfo.opentsp.dongfeng.monitor.service.car.IQueryAsyncTreeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 获取车辆异步树
 *
 * @Author liusanhu@aerozhonghuan.com
 * @Date 2017/3/11
 */
@Service
public class QueryAsyncTreeServiceImpl extends BaseService implements IQueryAsyncTreeService
{
    
    @Resource
    private GpsInfoCache gpsInfoCache;
    
    /**
     * 获取车辆异步树
     *
     * @param command
     * @return
     */
    @Override
    public HttpCommandResultWithData queryAsyncTree(QueryAsyncTreeCommand command)
    {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        try
        {
            ArrayList<RootTree> list = null;
            if (command.getUserInfor().getType() == UserTypeConstant.SYSTEM_ADMIN.getCode()
                || command.getUserInfor().getType() == UserTypeConstant.CAR_FACTORY.getCode()
                || command.getUserInfor().getType() == UserTypeConstant.BUSINESS.getCode())
            {
                list = queryNormalCarTreeAsync(command);
            }
            else if (command.getUserInfor().getType() == UserTypeConstant.TRANSPORT.getCode())
            {
                list = queryTransportCarTreeAsync(command);
            }
            else
            {
                list = new ArrayList<RootTree>();
            }
            result.setResultCode(ResultCode.OK.code());
            result.setData(list);
        }
        catch (Exception e)
        {
            logger.error("==queryAsyncTree error!==", e);
            result.setResultCode(ResultCode.SERVER_ERROR.code());
        }
        return result;
    }
    
    /**
     * 获取系统管理员、车厂、经销商用户的车辆异步树根节点及直属下级节点
     *
     * @param command
     * @return
     */
    public ArrayList<RootTree> queryNormalCarTreeAsync(QueryAsyncTreeCommand command)
    {
        
        ArrayList<RootTree> result = new ArrayList<RootTree>();
        
        // 获取用户权限下分组
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("accountId", command.getUserInfor().getUserId());
        params.put("accountType", command.getUserInfor().getType());
        params.put("checked", command.getChecked());
        List<TeamTree> teams = dao.sqlFind("selectTeamsByAccountId", params, TeamTree.class);
        
        // 封装组Map，并将最高节点组及直属组装载到Result
        Map<Long, TeamTree> map = new HashMap<Long, TeamTree>();
        
        // 封装Map,方便递归用
        for (TeamTree team : teams)
        {
            map.put(team.getDid().longValue(), team);
        }
        
        ArrayList<Long> pids = new ArrayList<Long>();
        // 父节点，默认用户是系统管理员类型
        pids.add(TeamConstant.teamRootId);
        // 如果查询节点不为空,此节点为父节点
        if (command.getId() != null)
        {
            pids.clear();
            pids.add(Long.parseLong(command.getId()));
        }
        else if (command.getUserInfor().getType() > 1)
        {// 如果车厂和经销商用户，查询最高节点
            pids.clear();
            // 寻找根节点
            for (TeamTree team : teams)
            {
                TeamTree parent = map.get(Long.parseLong(team.getpId()));
                if (parent == null)
                {
                    pids.add(team.getDid().longValue());
                }
            }
        }
        
        // 装载直属组
        for (TeamTree team : teams)
        {
            for (Long pid : pids)
            {
                if (Long.parseLong(team.getpId()) == pid.longValue())
                {
                    team.setChecked(command.getChecked());
                    result.add(team);
                }
            }
        }
        
        // 获取父节点
        for (Long pid : pids)
        {
            TeamTree parentTree = map.get(pid);
            // 默认获取最高节点，需要装载
            if (command.getId() == null)
            {
                result.add(parentTree);
            }
        }
        
        // 获取用户权限下车辆
        List<QueryAsyncTreePojo> cars = dao.sqlFind("selectCarsByAccountId", params, QueryAsyncTreePojo.class);
        
        // 获取所有车辆状态缓存
        Map<String, GpsInfoData> gpsInfoMap = gpsInfoCache.getAllGpsInfoMap();
        
        // 添加在线数/总数
        addRecOnlineAndTotal(cars, map, gpsInfoMap);
        
        for (Long pid : pids)
        {
            TeamTree parentTree = map.get(pid);
            recTeamTree(parentTree, map);
        }
        
        // 装载车辆
        for (QueryAsyncTreePojo c : cars)
        {
            for (Long pid : pids)
            {
                if (c.getCarTeamId().longValue() == pid.longValue())
                {
                    CarTree carTree = convertCarTree(c, pid, command.getChecked(), gpsInfoMap);
                    result.add(carTree);
                }
            }
        }
        
        return result;
        
    }
    
    /**
     * 获取运输企业的车辆异步树根节点及直属下级节点
     *
     * @param command
     * @return
     */
    public ArrayList<RootTree> queryTransportCarTreeAsync(QueryAsyncTreeCommand command)
    {
        
        ArrayList<RootTree> result = new ArrayList<RootTree>();
        
        // 获取运输企业用户所属客户
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("accountId", command.getUserInfor().getUserId());
        List<TeamTree> teams = dao.sqlFind("selectBusinessByAccountId", params, TeamTree.class);
        
        Map<Long, TeamTree> map = new HashMap<Long, TeamTree>();
        for (TeamTree team : teams)
        {
            if (command.getId() == null)
            {
                result.add(team);
            }
            map.put(team.getDid().longValue(), team);
        }
        
        // 获取运输企业用户权限下的车辆
        List<QueryAsyncTreePojo> cars = dao.sqlFind("selectBusiCarsByAccountId", params, QueryAsyncTreePojo.class);
        
        // 获取所有车辆状态缓存
        Map<String, GpsInfoData> gpsInfoMap = gpsInfoCache.getAllGpsInfoMap();
        // 添加在线数/总数
        addRecOnlineAndTotal(cars, map, gpsInfoMap);
        for (TeamTree team : teams)
        {
            team.setName(team.getName() + "(" + team.getOnline() + "/" + team.getTotal() + ")");
        }
        
        if (command.getId() != null)
        {
            long pid = Long.parseLong(command.getId());
            // 装载车辆
            for (QueryAsyncTreePojo c : cars)
            {
                if (c.getCarTeamId().longValue() == pid)
                {
                    CarTree carTree = convertCarTree(c, pid, command.getChecked(), gpsInfoMap);
                    result.add(carTree);
                }
            }
        }
        
        return result;
        
    }
    
    /**
     * 添加在线数/总数
     *
     * @param cars
     * @param map
     */
    public void addRecOnlineAndTotal(List<? extends QueryAsyncTreePojo> cars, Map<Long, TeamTree> map,
        Map<String, GpsInfoData> gpsInfoMap)
    {
        if (StringUtil.isEmpty(cars) || StringUtil.isEmpty(map))
        {
            return;
        }
        // 封装在线数/总数
        for (QueryAsyncTreePojo car : cars)
        {
            TeamTree team = map.get(car.getCarTeamId().longValue());
            if (gpsInfoMap != null)
            {
                GpsInfoData gpsInfoData = gpsInfoMap.get(car.getCommId() + "");
                // GpsInfoData gpsInfoData = gpsInfoCache.getGpsInfo(car.getCommId()+"");
                if (gpsInfoData != null)
                {
                    Integer statue = gpsInfoData.getCarStatue();

                    // 如果在线，判断最新gps时间是是否超过系统时间10分钟
                    if (statue != null && statue != Constants.CarState.VehicleStatusOfflineInvalid.getValue()
                            && statue != Constants.CarState.VehicleStatusOffline.getValue()
                            && statue != Constants.CarState.VehicleStatusOfflineStop.getValue())
                    {
                        // gps时间
                        Long gpsDate = gpsInfoData.getGpsDate();
                        
                        // 最新gps时间不在10分钟内，设为离线，并存储reids
                        if (gpsDate == null || gpsDate == 0 || System.currentTimeMillis() / 1000 - gpsDate >= 600)
                        {
                            
                            // 不在线不定位
                            statue = Constants.CarState.VehicleStatusOfflineInvalid.getValue();
                            gpsInfoData.setCarStatue(statue);
                            try
                            {
                                redisService.saveObjectToJson(CacheKeyConstants.GPS_INFO_KEY,
                                    car.getCommId() + "",
                                    gpsInfoData);
                            }
                            catch (JsonProcessingException e)
                            {
                                logger.error(e.getMessage(), e);
                            }
                        }
                        else
                        {
                            if (team != null)
                            {
                                team.setOnline(team.getOnline() + 1);
                            }
                        }
                    }
                }
            }
            if (team != null)
            {
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
    public void recTeamTree(TeamTree parent, Map<Long, TeamTree> map)
    {
        
        for (RootTree rootTree : map.values())
        {
            TeamTree teamTree = (TeamTree)rootTree;
            if (teamTree.getpId().equals(parent.getId()))
            {
                if (teamTree.getIsParent())
                {
                    recTeamTree(teamTree, map);
                    parent.setOnline(parent.getOnline() + teamTree.getOnline());
                    parent.setTotal(parent.getTotal() + teamTree.getTotal());
                }
            }
        }
        
        parent.setName(parent.getName() + "(" + parent.getOnline() + "/" + parent.getTotal() + ")");
    }
    
    /**
     * 车对象转树节点
     *
     * @param teamId
     * @return
     */
    public CarTree convertCarTree(QueryAsyncTreePojo car, Long teamId, Boolean checked,
        Map<String, GpsInfoData> gpsInfoMap)
    {
        CarTree carTree = new CarTree();
        carTree.setId("L" + car.getCarId().longValue());
        carTree.setDid(BigInteger.valueOf(car.getCarId().longValue()));
        carTree.setpId(teamId + "");
        carTree.setName(car.getChassisNum());
        carTree.setChecked(checked);
        // 锁车状态：0（未激活未锁车00）1（未激活锁车01）2（激活未锁车10）3（激活锁车11）
        carTree.setLockStauts(car.getLockStauts());
        
        GpsInfoData gpsInfoData = gpsInfoMap.get(car.getCommId() + "");
        if (gpsInfoData != null)
        {
            if (gpsInfoData.getCarStatue() != null)
            {
                carTree.setCarStauts(gpsInfoData.getCarStatue());
            }
            else
            {
                carTree.setCarStauts(0);
            }
            
            if (gpsInfoData.getLng() != null && gpsInfoData.getLat() != null)
            {
                carTree.setLng(gpsInfoData.getLng() * 0.000001);
                carTree.setLat(gpsInfoData.getLat() * 0.000001);
            }
            else
            {
                carTree.setLng(0);
                carTree.setLat(0);
            }
        }
        else
        {
            carTree.setCarStauts(0);
            carTree.setLng(0);
            carTree.setLat(0);
        }
        return carTree;
    }
    
}
