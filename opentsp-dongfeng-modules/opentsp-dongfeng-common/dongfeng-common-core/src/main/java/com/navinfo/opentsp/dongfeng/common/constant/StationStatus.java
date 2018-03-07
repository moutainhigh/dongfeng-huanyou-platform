package com.navinfo.opentsp.dongfeng.common.constant;

/**
 * 服务站状态
 * @Author zhangyu
 * @Date 2017/07/19
 */
public enum StationStatus {
    STATION_DISABLE(0, "服务站停用"),
    STATION_ENABLE(1, "服务站启用标志");

    private final int index;

    private String name;

    StationStatus(final int index, final String name)
    {
        this.index = index;
        this.name = name;
    }

    public int getIndex()
    {
        return index;
    }

    public String getName()
    {
        return name;
    }

}
