package com.navinfo.opentsp.dongfeng.report.dto.general;

/**
 * Created by zhangtiantong on 2018/1/31/031.
 */
public class FailureStatisticsDto {

    /**
     * 发动机类型编码
     */
    private String engineTypeCode;

    /**
     * 发动机类型名称
     */
    private String engineTypeName;

    /**
     * 故障所占百分比
     */
    private double percent;

    public String getEngineTypeName() {
        return engineTypeName;
    }

    public void setEngineTypeName(String engineTypeName) {
        this.engineTypeName = engineTypeName;
    }

    public String getEngineTypeCode() {
        return engineTypeCode;
    }

    public void setEngineTypeCode(String engineTypeCode) {
        this.engineTypeCode = engineTypeCode;
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }

    @Override
    public String toString() {
        return "FailureStatisticsDto{" +
                "engineTypeCode='" + engineTypeCode + '\'' +
                ", engineTypeName='" + engineTypeName + '\'' +
                ", percent=" + percent +
                '}';
    }
}

