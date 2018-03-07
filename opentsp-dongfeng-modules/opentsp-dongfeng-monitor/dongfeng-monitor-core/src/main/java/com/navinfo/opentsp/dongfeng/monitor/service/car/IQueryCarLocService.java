package com.navinfo.opentsp.dongfeng.monitor.service.car;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.monitor.commands.car.QueryCarLocCommand;

/**
 * @wenya
 * @create 2017-03-10 9:40
 **/

public  interface IQueryCarLocService {
   /**
     * 地图打点
     * @param command
     * @return
     */
    public HttpCommandResultWithData queryCarLoc(QueryCarLocCommand command) ;
}
