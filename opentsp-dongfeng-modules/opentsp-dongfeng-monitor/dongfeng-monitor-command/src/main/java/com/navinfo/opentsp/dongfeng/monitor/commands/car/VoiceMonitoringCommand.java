package com.navinfo.opentsp.dongfeng.monitor.commands.car;

import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.result.CommonResult;
import com.navinfo.opentsp.dongfeng.common.validation.group.GroupCommand;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * @author fengwm
 * @version 1.0
 * @date 2017-03-27
 * @modify
 * @copyright Navi Tsp
 */
public class VoiceMonitoringCommand extends BaseCommand<CommonResult> {

    //车辆id
    @NotNull(message = "车辆ID 不能为NULL", groups = {GroupCommand.class})
    @NotBlank(message = "车辆ID 不能为空", groups = {GroupCommand.class})
    private String carId;
    //电话
    @NotNull(message = "电话 不能为NULL", groups = {GroupCommand.class})
    @NotBlank(message = "电话 不能为空", groups = {GroupCommand.class})
    private String phone;
    //监控类型
    @NotNull(message = "监控类型 不能为NULL", groups = {GroupCommand.class})
    @NotBlank(message = "监控类型 不能为空", groups = {GroupCommand.class})
    private String type;
    //客户端ip
    private String clientIp;

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    @Override
    public Class<? extends CommonResult> getResultType() {
        return CommonResult.class;
    }
}