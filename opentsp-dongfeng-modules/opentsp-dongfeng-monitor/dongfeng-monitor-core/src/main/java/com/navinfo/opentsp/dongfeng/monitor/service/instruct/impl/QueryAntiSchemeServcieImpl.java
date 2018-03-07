package com.navinfo.opentsp.dongfeng.monitor.service.instruct.impl;/**
 * Created by Administrator on 2017/3/23.
 */

import com.navinfo.opentsp.dongfeng.common.dto.BaseData;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.common.service.BaseService;
import com.navinfo.opentsp.dongfeng.common.service.IBaseDataService;
import com.navinfo.opentsp.dongfeng.monitor.commands.instruct.QueryAntiSchemeCommand;
import com.navinfo.opentsp.dongfeng.monitor.pojo.instruct.QueryAntiShemePojo;
import com.navinfo.opentsp.dongfeng.monitor.service.instruct.IQueryAntiSchemeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 获取防拆方案中下拉菜单
 *
 * @wenya
 * @create 2017-03-23 15:30
 **/
@Service
public class QueryAntiSchemeServcieImpl extends BaseService implements IQueryAntiSchemeService {
    @Resource
    private IBaseDataService baseDataService;

    protected static final Logger logger = LoggerFactory.getLogger(QueryAntiSchemeServcieImpl.class);
    /**
     * 获取防拆方案中下拉菜单
     * @param command
     * @return
     */
    @Override
    public HttpCommandResultWithData queryAntiScheme(QueryAntiSchemeCommand command) {
        logger.info("=====  queryAntiScheme start  =====");
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        try {
            List<BaseData> menuList = new ArrayList<BaseData>();
            Map<String,Long> parm = new HashMap<String,Long>();
            parm.put("carId", command.getCarId());
            //sql定义在car-QueryCommonCar-dynamic.xml中
            QueryAntiShemePojo antiShemePojo = (QueryAntiShemePojo)dao.sqlFindObject("queryTerminalCar", parm, QueryAntiShemePojo.class);
            //F9+一体机：F9未启用或通用 且一体机未启用时,3个选项
            if(antiShemePojo.getCar_termianlId().longValue()!=0&&antiShemePojo.getTerminalId().longValue()!=0){
                /*
			     * 适用发动机类型：终端初始化参数设置保存的数据（数据库初始化默认值0）
			     * 0:未启用
			     * 1：大运通用 2：大运专用
			     * 3：江淮通用 4：江淮专用
			     * 5：东风通用 6：东风专用
			     * 7：华菱通用 8：华菱专用
			     */
                //一体机适用车辆类型，未启用时
                if(antiShemePojo.getBdApplianceCarType().longValue() == 0){
                    //F9适用车辆类型
                    long act = antiShemePojo.getFkApplianceCarType().longValue();
                    //F9未启用或通用
                    if((act == 0) || (act%2==1)){
                        menuList = baseDataService.queryList(56);
                    }
                }
            }else{
                BaseData menu  = baseDataService.getData("0",56);
                menuList.add(menu);
            }
            result.setData(menuList);
            result.fillResult(ReturnCode.OK);
        }catch (Exception e){
            result.fillResult(ReturnCode.SERVER_ERROR);
            result.setMessage("防拆方案下拉菜单异常");
            logger.error(e.getMessage(), e);
        }
        logger.info("===== queryAntiScheme end  =====");
        return result;
    }
}
