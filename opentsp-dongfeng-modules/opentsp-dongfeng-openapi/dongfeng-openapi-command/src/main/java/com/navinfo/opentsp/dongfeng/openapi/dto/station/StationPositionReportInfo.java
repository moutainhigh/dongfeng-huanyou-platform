package com.navinfo.opentsp.dongfeng.openapi.dto.station;

import com.navinfo.opentsp.dongfeng.common.validation.group.GroupCommand;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author tushenghong
 * @version 1.0
 * @date 2017-07-17
 * @modify
 * @copyright Navi Tsp
 */
public class StationPositionReportInfo implements Serializable {
    @NotNull(message = "服务站ID(id) 不能为空", groups = {GroupCommand.class})
    @NotBlank(message = "服务站ID(id) 不能为空", groups = {GroupCommand.class})
    private String id;//服务站id
    @NotNull(message = "经度(lon) 不能为空", groups = {GroupCommand.class})
    @NotBlank(message = "经度(lon) 不能为空", groups = {GroupCommand.class})
    private String lon;//经度
    @NotNull(message = "纬度(lat) 不能为空", groups = {GroupCommand.class})
    @NotBlank(message = "纬度(lat) 不能为空", groups = {GroupCommand.class})
    private String lat;//纬度
    @NotNull(message = "服务站地址(address) 不能为空", groups = {GroupCommand.class})
    @NotBlank(message = "服务站地址(address) 不能为空", groups = {GroupCommand.class})
    private String address;//服务站逆地理地址
    @NotNull(message = "服务站级别(lv) 不能为空", groups = {GroupCommand.class})
    @NotBlank(message = "服务站级别(lv) 不能为空", groups = {GroupCommand.class})
    private String lv; //服务站级别
    @NotNull(message = "用户ID(userId) 不能为空", groups = {GroupCommand.class})
    @NotBlank(message = "用户ID(userId) 不能为空", groups = {GroupCommand.class})
    private String userId;//用户ID

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
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
        return "StationPositionReportInfo{" +
                "id='" + id + '\'' +
                ", lon='" + lon + '\'' +
                ", lat='" + lat + '\'' +
                ", address='" + address + '\'' +
                ", lv='" + lv + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
