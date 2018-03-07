package com.navinfo.opentsp.dongfeng.monitor.service.car;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.monitor.commands.car.QuerySearchTreeCommand;

/**
 * 获取车辆搜索树
 * @Author liusanhu@aerozhonghuan.com
 * @Date 2017/3/11
 */
public interface IQuerySearchTreeService {
    /**
     * 获取车辆搜索树
     *
     * @param command
     * @return
     */
    public HttpCommandResultWithData querySearchTree(QuerySearchTreeCommand command);

    /**
     * 判断车辆状态是否包含查询参数状态
     *
     * @param carStauts
     * @param paramStauts
     * @return
     */
    Boolean hasStauts(Integer carStauts, Integer paramStauts);
}
