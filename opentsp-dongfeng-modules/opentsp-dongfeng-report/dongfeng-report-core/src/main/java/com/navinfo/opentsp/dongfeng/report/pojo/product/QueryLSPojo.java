package com.navinfo.opentsp.dongfeng.report.pojo.product;

/**
 * @wenya
 * @create 2017-03-28 11:14
 **/
public class QueryLSPojo {
    private String chassisNum;
    private String workCenter;
    private String materialsId;
    private String materialsName;
    private String planTime;
    private String instockTime;
    private String qualifiedTime;
    private String removalTime;
    private String storageLoc;

    public String getChassisNum() {
        return chassisNum;
    }

    public void setChassisNum(String chassisNum) {
        this.chassisNum = chassisNum;
    }

    public String getInstockTime() {
        return instockTime;
    }

    public void setInstockTime(String instockTime) {
        this.instockTime = instockTime;
    }

    public String getMaterialsId() {
        return materialsId;
    }

    public void setMaterialsId(String materialsId) {
        this.materialsId = materialsId;
    }

    public String getMaterialsName() {
        return materialsName;
    }

    public void setMaterialsName(String materialsName) {
        this.materialsName = materialsName;
    }

    public String getPlanTime() {
        return planTime;
    }

    public void setPlanTime(String planTime) {
        this.planTime = planTime;
    }

    public String getQualifiedTime() {
        return qualifiedTime;
    }

    public void setQualifiedTime(String qualifiedTime) {
        this.qualifiedTime = qualifiedTime;
    }

    public String getRemovalTime() {
        return removalTime;
    }

    public void setRemovalTime(String removalTime) {
        this.removalTime = removalTime;
    }

    public String getStorageLoc() {
        return storageLoc;
    }

    public void setStorageLoc(String storageLoc) {
        this.storageLoc = storageLoc;
    }

    public String getWorkCenter() {
        return workCenter;
    }

    public void setWorkCenter(String workCenter) {
        this.workCenter = workCenter;
    }
}
