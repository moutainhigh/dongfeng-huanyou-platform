package com.navinfo.opentsp.dongfeng.system.commands.nonf9;

import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.validation.group.GroupCommand;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * 查询非F9车辆
 *
 * @author tushenghong
 * @version 1.0
 * @date 2017-11-28
 * @modify
 * @copyright Navi Tsp
 */
public class QueryNonF9VehicleDetailCommand extends BaseCommand<HttpCommandResultWithData> {

    /**
     * 车辆ID
     */
    @NotNull(message = "车辆ID 不能为空", groups = {GroupCommand.class})
    @NotBlank(message = "车辆ID 不能为空", groups = {GroupCommand.class})
    private String vehicleId;

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    @Override
    public Class<? extends HttpCommandResultWithData> getResultType() {
        return HttpCommandResultWithData.class;
    }

}
