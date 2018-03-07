package com.navinfo.opentsp.dongfeng.monitor.service.car.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.navinfo.opentsp.common.messaging.ResultCode;
import com.navinfo.opentsp.dongfeng.common.cache.GpsCache;
import com.navinfo.opentsp.dongfeng.common.cache.GpsInfoCache;
import com.navinfo.opentsp.dongfeng.common.constant.CacheKeyConstants;
import com.navinfo.opentsp.dongfeng.common.constant.Constants;
import com.navinfo.opentsp.dongfeng.common.constant.UserTypeConstant;
import com.navinfo.opentsp.dongfeng.common.dto.GpsInfoData;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.service.BaseService;
import com.navinfo.opentsp.dongfeng.monitor.commands.car.QuerySearchTreeCommand;
import com.navinfo.opentsp.dongfeng.monitor.entity.CarTree;
import com.navinfo.opentsp.dongfeng.monitor.entity.RootTree;
import com.navinfo.opentsp.dongfeng.monitor.entity.TeamTree;
import com.navinfo.opentsp.dongfeng.monitor.pojo.car.QuerySearchTreePojo;
import com.navinfo.opentsp.dongfeng.monitor.service.car.IQuerySearchTreeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 获取车辆搜索树
 * 
 * @Author liusanhu@aerozhonghuan.com
 * @Date 2017/3/11
 */
@Service
public class QuerySearchTreeServiceImpl extends BaseService implements IQuerySearchTreeService
{
    @Resource
    private GpsInfoCache gpsInfoCache;
    
    @Resource
    private GpsCache gpsCache;
    
    /**
     * 获取车辆搜索树
     *
     * @param command
     * @return
     */
    @Override
    public HttpCommandResultWithData querySearchTree(QuerySearchTreeCommand command)
    {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        ArrayList<RootTree> list = null;
        if (command.getUserInfor().getType() == UserTypeConstant.SYSTEM_ADMIN.getCode()
            || command.getUserInfor().getType() == UserTypeConstant.CAR_FACTORY.getCode()
            || command.getUserInfor().getType() == UserTypeConstant.BUSINESS.getCode())
        {
            list = queryNormalCarTreeSearch(command);
        }
        else if (command.getUserInfor().getType() == UserTypeConstant.TRANSPORT.getCode())
        {
            list = queryTransportCarTreeSearch(command);
        }
        else
        {
            list = new ArrayList<RootTree>();
        }
        result.setResultCode(ResultCode.OK.code());
        result.setData(list);
        return result;
    }
    
    /**
     * 搜索系统管理员、车厂、经销商用户的车辆树
     * 
     * @param command
     * @return
     */
    public ArrayList<RootTree> queryNormalCarTreeSearch(QuerySearchTreeCommand command)
    {
        
        ArrayList<RootTree> result = new ArrayList<RootTree>();
        
        // 获取用户权限下分组
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("accountId", command.getUserInfor().getUserId());
        params.put("accountType", command.getUserInfor().getType());
        
        // SQL定义于car-QueryAsyncTree-dynamic.xml
        List<TeamTree> teams = dao.sqlFind("selectTeamsByAccountId", params, TeamTree.class);
        
        // 封装组Map，并将最高节点组及直属组装载到Result
        Map<Long, TeamTree> map = new HashMap<Long, TeamTree>();
        
        // 封装Map,方便递归用
        for (TeamTree team : teams)
        {
            map.put(team.getDid().longValue(), team);
            result.add(team);
        }
        
        ArrayList<Long> pids = new ArrayList<Long>();
        // 父节点，默认用户是系统管理员类型
        pids.add(1L);
        if (command.getUserInfor().getType() > 1)
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
        
        params.put("searchType", command.getSearchType());
        params.put("searchText", command.getSearchText());
        params.put("engineType", command.getEngineType());
        params.put("carType", command.getCarType());
        params.put("carPlace", command.getCarPlace());
        params.put("stdSalesStatus", command.getStdSalesStatus());
        params.put("aakSalesStatus", command.getAakSalesStatus());
        params.put("dealer", command.getDealer());
        params.put("customer", command.getCustomer());
        params.put("carStauts", command.getCarStauts());
        
        // 获取用户权限下车辆
        List<QuerySearchTreePojo> cars = dao.sqlFind("searchCarsByAccountId", params, QuerySearchTreePojo.class);
        
        // 获取所有车辆状态缓存
        Map<String, GpsInfoData> gpsInfoMap = gpsInfoCache.getAllGpsInfoMap();
        // 添加在线数/总数
        addRecOnlineAndTotal(cars, map, command, gpsInfoMap);
        for (Long pid : pids)
        {
            TeamTree parentTree = map.get(pid);
            recTeamTree(parentTree, map);
        }
        
        // 装载车辆
        for (QuerySearchTreePojo c : cars)
        {
            CarTree carTree = convertCarTree(c, c.getCarTeamId().longValue(), false, gpsInfoMap);
            // 判断车辆状态是否包含查询参数状态
            if (hasStauts(carTree.getCarStauts(), command.getCarStauts()))
            {
                result.add(carTree);
            }
        }
        
        // 清理空组
        for (TeamTree teamTree : map.values())
        {
            if (command.getDealer() != null && !command.getDealer().trim().equals("")
                && teamTree.getName().indexOf(command.getDealer()) > -1)
            {
                continue;
            }
            
            if (command.getSearchType() != null && command.getSearchType().intValue() == 4)
            {
                if (command.getSearchText() != null && teamTree.getName().indexOf(command.getSearchText()) > -1)
                {
                    continue;
                }
            }
            
            if (teamTree.getTotal().longValue() == 0)
            {
                result.remove(teamTree);
            }
        }
        
        return result;
    }
    
    /**
     * 搜索运输企业用户的车辆树
     * 
     * @param command
     * @return
     */
    public ArrayList<RootTree> queryTransportCarTreeSearch(QuerySearchTreeCommand command)
    {
        
        ArrayList<RootTree> result = new ArrayList<RootTree>();
        
        // 获取运输企业用户所属客户
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("accountId", command.getUserInfor().getUserId());
        // SQL定义于car-QueryAsyncTree-dynamic.xml
        List<TeamTree> teams = dao.sqlFind("selectBusinessByAccountId", params, TeamTree.class);
        
        Map<Long, TeamTree> map = new HashMap<Long, TeamTree>();
        for (TeamTree team : teams)
        {
            result.add(team);
            map.put(team.getDid().longValue(), team);
        }
        
        // 获取运输企业用户权限下的车辆
        params.put("searchType", command.getSearchType());
        params.put("searchText", command.getSearchText());
        params.put("engineType", command.getEngineType());
        params.put("carType", command.getCarType());
        params.put("carPlace", command.getCarPlace());
        params.put("stdSalesStatus", command.getStdSalesStatus());
        params.put("aakSalesStatus", command.getAakSalesStatus());
        params.put("dealer", command.getDealer());
        params.put("customer", command.getCustomer());
        params.put("carStauts", command.getCarStauts());
        List<QuerySearchTreePojo> cars = dao.sqlFind("searchBusiCarsByAccountId", params, QuerySearchTreePojo.class);
        
        // 获取所有车辆状态缓存
        Map<String, GpsInfoData> gpsInfoMap = gpsInfoCache.getAllGpsInfoMap();
        // 添加在线数/总数
        addRecOnlineAndTotal(cars, map, command, gpsInfoMap);
        for (TeamTree team : teams)
        {
            // TeamTree team = map.get(hyBusiness.getCid().longValue());
            team.setName(team.getName() + "(" + team.getOnline() + "/" + team.getTotal() + ")");
        }
        
        // 装载车辆
        for (QuerySearchTreePojo c : cars)
        {
            CarTree carTree = convertCarTree(c, c.getCarTeamId().longValue(), false, gpsInfoMap);
            // 判断车辆状态是否包含查询参数状态
            if (hasStauts(carTree.getCarStauts(), command.getCarStauts()))
            {
                result.add(carTree);
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
    public void addRecOnlineAndTotal(List<? extends QuerySearchTreePojo> cars, Map<Long, TeamTree> map,
        QuerySearchTreeCommand command, Map<String, GpsInfoData> gpsInfoMap)
    {
        // 封装在线数/总数
        for (QuerySearchTreePojo car : cars)
        {
            TeamTree team = map.get(car.getCarTeamId().longValue());
            if (gpsInfoMap != null)
            {
                GpsInfoData gpsInfoData = gpsInfoMap.get(car.getCommId() + "");
                // GpsInfoData gpsInfoData = gpsInfoCache.getGpsInfo(car.getCommId()+"");
                
                // 不在线不定位
                Integer statue = Constants.CarState.VehicleStatusOfflineInvalid.getValue();
                
                if (gpsInfoData != null)
                {
                    statue = gpsInfoData.getCarStatue();
                    
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
                    }
                }
                // 判断车辆状态是否包含查询参数状态
                if (!hasStauts(statue, command.getCarStauts()))
                {
                    continue;
                }
                
                if (statue != null)
                {
                    if ((statue.intValue() & 1) > 0)
                    {
                        if (team != null)
                        {
                            team.setOnline(team.getOnline() + 1);
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
    public CarTree convertCarTree(QuerySearchTreePojo car, Long teamId, Boolean checked,
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
            carTree.setCarStauts(gpsInfoData.getCarStatue());
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
        }
        
        return carTree;
    }
    
    /**
     * 判断车辆状态是否包含查询参数状态
     * 
     * @param carStauts
     * @param paramStauts
     * @return
     */
    @Override
    public Boolean hasStauts(Integer carStauts, Integer paramStauts)
    {
        /*
         * command.getCarStauts()： 二进制 000001在线,000010定位,000100行驶 001000离线,010000未定位,100000停车
         * 
         * carTree.getCarStauts() : 在线行驶(111)7，在线停车(011)3，在线不定位(001)1，不在线行驶(110)6,不在线停车(010)2，不在线不定位(000)0
         * 第一位表示在线状态（0：不在线，1：在线） 第二位表示定位状态（0：不定位，1：定位） 第三位表示行驶状态（0：停车，1：行驶）
         */
        
        if (paramStauts == null)
        {
            paramStauts = 000000;
        }
        if (carStauts == null)
        {
            carStauts = 000;
        }
        
        // 取在线状态的在线
        Boolean result_on = false;
        if ((paramStauts.intValue() & 1) > 0)
        {
            result_on = true;
        }
        
        // 取在线状态的离线
        Boolean result_off = false;
        
        // 将command.getCarStauts().intValue() 右移3位获取001离线,010未定位,100停车
        int cd = paramStauts.intValue() >> 3;
        if ((cd & 1) > 0)
        {
            result_off = true;
        }
/*        // 如果在线状态都打钩，直接返回真
        if (result_on && result_off)
        {
            return true;
        }*/
        
        // 取行驶状态的定位
        Boolean result_loc = false;
        if ((paramStauts.intValue() & 2) > 0)
        {
            result_loc = true;
        }
        
        // 取行驶状态的未定位
        Boolean result_unloc = false;
        if ((cd & 2) > 0)
        {
            result_unloc = true;
        }
        
        // 取行驶状态的行驶
        Boolean result_run = false;
        if ((paramStauts.intValue() & 4) > 0)
        {
            result_run = true;
        }
        
        // 取行驶状态的停车
        Boolean result_park = false;
        if ((cd & 4) > 0)
        {
            result_park = true;
        }
        /*
         * 取反状态 将carTree.getCarStauts()取反后与7，可以获得 第一位表示在线状态（1：不在线，0：在线） 第二位表示定位状态（1：不定位，0：定位） 第三位表示行驶状态（1：停车，0：行驶）
         */
        int oppositeStauts = ~carStauts.intValue() & 7;
        
        // 如果入参在线状态都未打钩,通过入参行驶状态判断
/*        if (!result_on && !result_off)
        {
            // 如果入参定位打钩，且车辆状态定位，返回真
            if (result_loc)
            {
                if ((carStauts.intValue() & 2) > 0)
                {
                    return true;
                }
            }
            
            // 如果入参未定位打钩，且车辆状态未定位，返回真
            if (result_unloc)
            {
                if ((oppositeStauts & 2) > 0)
                {
                    return true;
                }
            }
            
            // 如果入参行驶打钩，且车辆状态行驶，返回真
            if (result_run)
            {
                if ((carStauts.intValue() & 4) > 0)
                {
                    return true;
                }
            }
            
            // 如果入参停止打钩，且车辆状态停止，返回真
            if (result_park)
            {
                if ((oppositeStauts & 4) > 0)
                {
                    return true;
                }
            }
        }*/
        
        // 入参在线状态在线打钩
        if (result_on && ((carStauts.intValue() & 1) > 0))
        {
            // 如果入参定位打钩，且车辆状态定位，返回真
            if (result_loc)
            {
                if ((carStauts.intValue() & 2) > 0)
                {
                    return true;
                }
            }

            // 如果入参未定位打钩，且车辆状态未定位，返回真
            if (result_unloc)
            {
                if ((oppositeStauts & 2) > 0)
                {
                    return true;
                }

            }

            // 如果入参行驶打钩，且车辆状态行驶，返回真
            if (result_run)
            {
                if ((carStauts.intValue() & 4) > 0)
                {
                    return true;
                }
            }

            // 如果入参停止打钩，且车辆状态停止，返回真
            if (result_park)
            {
                if ((oppositeStauts & 4) > 0)
                {
                    return true;
                }
            }

/*            // 如果入参行驶状态都未打钩，返回真
            if (!result_loc && !result_unloc && !result_run && !result_park)
            {
                return true;
            }*/
        }
        
        // 入参在线状态离线打钩
        if (result_off && ((carStauts.intValue() & 1) == 0))
        {
            // 如果入参定位打钩，且车辆状态定位，返回真
            if (result_loc)
            {
                if ((carStauts.intValue() & 2) > 0)
                {
                    return true;
                }
            }
            
            // 如果入参未定位打钩，且车辆状态未定位，返回真
            if (result_unloc)
            {
                if ((oppositeStauts & 2) > 0)
                {
                    return true;
                }
            }
            
            // 如果入参行驶打钩，且车辆状态行驶，返回真
            if (result_run)
            {
                if ((carStauts.intValue() & 4) > 0)
                {
                    return true;
                }
            }
            
            // 如果入参停止打钩，且车辆状态停止，返回真
            if (result_park)
            {
                // 车辆状态定位，且停止，才算是真正停止
                if ((oppositeStauts & 4) > 0 && (carStauts.intValue() & 2) > 0)
                {
                    return true;
                }
            }
            
/*            // 如果入参行驶状态都未打钩，返回真
            if (!result_loc && !result_unloc && !result_run && !result_park)
            {
                return true;
            }*/

        }
        
        /*
         * //取在线、定位、行驶 if((carStauts.intValue()&paramStauts.intValue()) >0){ return true; }else{ //取离线、未定位、停车
         *//*
            * 将carTree.getCarStauts()取反后与7，可以获得 第一位表示在线状态（1：不在线，0：在线） 第二位表示定位状态（1：不定位，0：定位） 第三位表示行驶状态（1：停车，0：行驶）
            *//*
               * int ct = ~carStauts.intValue()&7; //将command.getCarStauts().intValue() 右移3位获取001离线,010未定位,100停车 int cd
               * = paramStauts.intValue() >>3;
               * 
               * if((ct&cd) >0){ return true; } }
               */
        return false;
    }
    
}
