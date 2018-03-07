package com.navinfo.opentsp.dongfeng.common.constant;

/**
 * 服务站岗位类型
 * @Author liusanhu@aerozhonghuan.com
 * @Date 2017/3/24
 */
public enum StationJobType {
    MANAGER(1, "经理"),
    SALESMAN(2, "业务员"),
    UNKNOWN(99, "unknown");

    private final int index;

    private String name;

    StationJobType(final int index, final String name)
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

    public static String getValue(final int index) {
        for (StationJobType jobType : StationJobType.values()) {
            if (jobType.index == index) {
                return jobType.name;
            }
        }
        return UNKNOWN.name;
    }
}
