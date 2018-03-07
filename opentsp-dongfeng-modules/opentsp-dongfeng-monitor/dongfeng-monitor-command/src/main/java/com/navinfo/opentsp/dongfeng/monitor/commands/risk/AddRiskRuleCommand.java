package com.navinfo.opentsp.dongfeng.monitor.commands.risk;

import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.validation.group.GroupCommand;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * 新增车辆风险防控规则
 *
 * @author tushenghong
 * @version 1.0
 * @date 2017-03-20
 * @modify
 * @copyright Navi Tsp
 */
public class AddRiskRuleCommand extends BaseCommand<HttpCommandResultWithData> {
    @NotNull(message = "风控区ID 不能为NULL", groups = {GroupCommand.class})
    @NotBlank(message = "风控区ID 不能为NULL", groups = {GroupCommand.class})
    private String regionId;//区域ID
    @NotNull(message = "车辆ID 不能为NULL", groups = {GroupCommand.class})
    @NotBlank(message = "车辆ID 不能为NULL", groups = {GroupCommand.class})
    private String vehicleId;
    @NotNull(message = "防控类型 不能为NULL", groups = {GroupCommand.class})
    @NotBlank(message = "防控类型 不能为NULL", groups = {GroupCommand.class})
    private String type;//防控类型
    @NotNull(message = "防控值 不能为NULL", groups = {GroupCommand.class})
    @NotBlank(message = "防控值 不能为NULL", groups = {GroupCommand.class})
    private String value;

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public Class<? extends HttpCommandResultWithData> getResultType() {
        return HttpCommandResultWithData.class;
    }
}