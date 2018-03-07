package com.navinfo.opentsp.dongfeng.system.service;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.PagingInfo;
import com.navinfo.opentsp.dongfeng.system.commands.nonf9.ExportNonF9VehicleCommand;
import com.navinfo.opentsp.dongfeng.system.commands.nonf9.QueryNonF9VehicleCommand;
import com.navinfo.opentsp.dongfeng.system.commands.nonf9.QueryNonF9VehicleDetailCommand;
import com.navinfo.opentsp.dongfeng.system.commands.nonf9.UpdateNonF9VehicleCommand;
import com.navinfo.opentsp.dongfeng.system.pojo.NonF9VehiclePojo;

/**
 * 非F9车辆服务
 *
 * @author: tushenghong
 * @version: 1.0
 * @since: 2017-11-28
 **/
public interface INonF9VehicleService {
    /**
     * 查询非F9车辆
     *
     * @param command
     * @param isPaging 是否分页
     * @return
     */
    PagingInfo queryNonF9Vehicle(QueryNonF9VehicleCommand command, boolean isPaging);

    /**
     * 查询非F9车辆详情
     *
     * @param command
     * @return
     */
    NonF9VehiclePojo queryNonF9VehicleDetail(QueryNonF9VehicleDetailCommand command);

    /**
     * 更新非F9车辆
     *
     * @param command
     * @return
     */
    HttpCommandResultWithData updateNonF9Vehicle(UpdateNonF9VehicleCommand command);

    /**
     * 导出当前页
     *
     * @param command
     * @return
     */
    HttpCommandResultWithData exportCurrentPage(ExportNonF9VehicleCommand command);

    /**
     * 导出全部
     *
     * @param command
     * @return
     */
    HttpCommandResultWithData exportAll(ExportNonF9VehicleCommand command);

}
