package com.navinfo.opentsp.dongfeng.common.constant;

/**
 * 经销商岗位类型
 *
 * @author wt
 * @version 1.0
 * @date 2017-07-06
 * @modify
 * @copyright Navi Tsp
 */
public enum BusinessJobType {
    MANAGER(1, "经理"),
    SALESMAN(2, "业务员"),
    UNKNOWN(99, "unknown");

    private final int index;

    private String name;

    BusinessJobType(final int index, final String name)
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
        for (BusinessJobType jobType : BusinessJobType.values()) {
            if (jobType.index == index) {
                return jobType.name;
            }
        }
        return UNKNOWN.name;
    }
}
