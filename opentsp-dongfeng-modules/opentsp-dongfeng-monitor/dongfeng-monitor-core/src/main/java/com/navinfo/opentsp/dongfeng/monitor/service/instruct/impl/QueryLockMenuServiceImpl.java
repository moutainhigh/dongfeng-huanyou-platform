package com.navinfo.opentsp.dongfeng.monitor.service.instruct.impl;

import com.navinfo.opentsp.dongfeng.common.dto.BaseData;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.common.service.BaseService;
import com.navinfo.opentsp.dongfeng.common.service.IBaseDataService;
import com.navinfo.opentsp.dongfeng.monitor.commands.instruct.QueryLockMenuCommand;
import com.navinfo.opentsp.dongfeng.monitor.pojo.instruct.QueryLockMenuPojo;
import com.navinfo.opentsp.dongfeng.monitor.service.instruct.IQueryLockMenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @wenya
 * @create 2017-03-23 16:30
 **/
@Service
public class QueryLockMenuServiceImpl extends BaseService implements IQueryLockMenuService{
    @Resource
    private IBaseDataService baseDataService;
    /**
     * 获取锁车方案中下拉菜单
     * @param command
     * @return
     */
    @Override
    public HttpCommandResultWithData queryLockMenu(QueryLockMenuCommand command) {
        logger.info("=====  queryLockMenu start  =====");
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        try {
            List<BaseData> menuList = new ArrayList<BaseData>();
            Map<String,Long> parm = new HashMap<String,Long>();
            parm.put("carId", command.getCarId());
            //sql定义在car-QueryCommonCar-dynamic.xml中
            QueryLockMenuPojo lockMenuPojo = (QueryLockMenuPojo)dao.sqlFindObject("queryTerCarMenu", parm, QueryLockMenuPojo.class);
             //一体机
            //1江淮，2大运,3一汽  一体机只有转速限制
            if(lockMenuPojo.getCar_termianlId().longValue()>0){
                menuList = baseDataService.queryList(68);
            }else{
                BaseData menu = baseDataService.getData("1",68);
                menuList.add(menu);
            }
            result.setData(menuList);
            result.fillResult(ReturnCode.OK);
        }catch (Exception e){
            result.fillResult(ReturnCode.SERVER_ERROR);
            result.setMessage("锁车方案下拉菜单异常");
            logger.error(e.getMessage(), e);
        }
        logger.info("===== queryLockMenu end  =====");
        return result;
    }
}
