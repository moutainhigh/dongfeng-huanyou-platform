package com.navinfo.opentsp.dongfeng.monitor.service.car;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.monitor.commands.car.QueryDistrictCommand;

/**
 * @wenya
 * @create 2017-03-24 17:35
 **/
public interface IQueryDistrictService {
    /**
     * 点击所属区域，弹出所属销售区域弹窗
     * @param command
     * @return
     */
    public HttpCommandResultWithData queryDistrict(QueryDistrictCommand command);
}
