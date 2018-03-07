package com.navinfo.opentsp.dongfeng.common.constant;

/**
 * 车厂岗位类型
 */
public enum JobTypeConstant
{
    JOB_TYPE(1, "整体管理"),
    ADJUSTMENT_WORK_SECTION(2, "调整工段"),
    VEHICLE_QUALITY_INSPECTION(3, "整车质检"),
    JOB_TYPE_DELIVERY_ROOM(4, "交付室"),
    DISPATCH_TEAM(5, "发运队"),
    UNKNOWN(99, "unknown");
    
    private final int code;
    
    private String name;
    
    JobTypeConstant(final int code, final String name)
    {
        this.code = code;
        this.name = name;
    }
    
    public int getCode()
    {
        return code;
    }
    
    public String getName()
    {
        return name;
    }

    public static String getValue(final int index) {
        for (JobTypeConstant jobType : JobTypeConstant.values()) {
            if (jobType.code == index) {
                return jobType.name;
            }
        }
        return UNKNOWN.name;
    }
    
}
