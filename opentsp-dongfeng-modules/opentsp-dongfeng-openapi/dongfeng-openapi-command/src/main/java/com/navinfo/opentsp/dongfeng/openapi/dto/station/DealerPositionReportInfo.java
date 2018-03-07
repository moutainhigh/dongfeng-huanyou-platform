package com.navinfo.opentsp.dongfeng.openapi.dto.station;

import com.navinfo.opentsp.dongfeng.common.validation.group.GroupCommand;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author fwm
 * @version 1.0
 * @date 2017-07-17
 * @modify
 * @copyright Navi Tsp
 */
public class DealerPositionReportInfo implements Serializable {
    @NotNull(message = "经销商ID(tid) 不能为空", groups = {GroupCommand.class})
    @NotBlank(message = "经销商ID(tid) 不能为空", groups = {GroupCommand.class})
    private String tid;//经销商id
    @NotNull(message = "经度(tlon) 不能为空", groups = {GroupCommand.class})
    @NotBlank(message = "经度(tlon) 不能为空", groups = {GroupCommand.class})
    private String tlon;//经度
    @NotNull(message = "纬度(tlat) 不能为空", groups = {GroupCommand.class})
    @NotBlank(message = "纬度(tlat) 不能为空", groups = {GroupCommand.class})
    private String tlat;//纬度
    @NotNull(message = "经销商地址(address) 不能为空", groups = {GroupCommand.class})
    @NotBlank(message = "经销商地址(address) 不能为空", groups = {GroupCommand.class})
    private String address;//经销商逆地理地址
    @NotNull(message = "经销商级别(lv) 不能为空", groups = {GroupCommand.class})
    @NotBlank(message = "经销商级别(lv) 不能为空", groups = {GroupCommand.class})
    private String lv; //经销商级别
    @NotNull(message = "用户ID(userId) 不能为空", groups = {GroupCommand.class})
    @NotBlank(message = "用户ID(userId) 不能为空", groups = {GroupCommand.class})
    private String userId;//用户ID

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getTlon() {
        return tlon;
    }

    public void setTlon(String tlon) {
        this.tlon = tlon;
    }

    public String getTlat() {
        return tlat;
    }

    public void setTlat(String tlat) {
        this.tlat = tlat;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLv() {
        return lv;
    }

    public void setLv(String lv) {
        this.lv = lv;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "DealerPositionReportInfo{" +
                "tid='" + tid + '\'' +
                ", tlon='" + tlon + '\'' +
                ", tlat='" + tlat + '\'' +
                ", address='" + address + '\'' +
                ", lv='" + lv + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
