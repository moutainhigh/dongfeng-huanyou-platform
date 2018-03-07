package com.navinfo.opentsp.dongfeng.report.dto.market;

import com.navinfo.opentsp.dongfeng.common.dto.BaseDto;

import java.io.Serializable;

/**
 * Created by xltianc.zh on 2017/3/28.
 */
public class QueryNotOnlineReportDTo extends BaseDto implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -5742157456073452163L;
	
	
	// 底盘号
    private String chassis_no;
    // 工作中心
    private String work_center;
    // 物资编码
    private String materials_id;
    // 物资名称
    private String materials_name;
    // 计划时间
    private String plan_time;
    // 入库时间
    private String instock_time;
    // 合格时间
    private String qualified_time;
    // 出库时间
    private String removal_time;
    // 库位
    private String storage_location;
    // 销售编码
    private String sale_id;

    private String xx_sj;
    private String zx_sj;
    private String netting_time;
    private String netting_log;
    private String netting_lat;

    private String last_time;
    private String last_location;

    public String getNetting_time() {
        return netting_time;
    }

    public void setNetting_time(String netting_time) {
        this.netting_time = netting_time;
    }

    public String getNetting_log() {
        return netting_log;
    }

    public void setNetting_log(String netting_log) {
        this.netting_log = netting_log;
    }

    public String getNetting_lat() {
        return netting_lat;
    }

    public void setNetting_lat(String netting_lat) {
        this.netting_lat = netting_lat;
    }

    public String getXx_sj() {
        return xx_sj;
    }

    public void setXx_sj(String xx_sj) {
        this.xx_sj = xx_sj;
    }

    public String getZx_sj() {
        return zx_sj;
    }

    public void setZx_sj(String zx_sj) {
        this.zx_sj = zx_sj;
    }

    private Long carId;

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public String getChassis_no() {
        return chassis_no;
    }

    public void setChassis_no(String chassis_no) {
        this.chassis_no = chassis_no;
    }

    public String getWork_center() {
        return work_center;
    }

    public void setWork_center(String work_center) {
        this.work_center = work_center;
    }

    public String getMaterials_id() {
        return materials_id;
    }

    public void setMaterials_id(String materials_id) {
        this.materials_id = materials_id;
    }

    public String getMaterials_name() {
        return materials_name;
    }

    public void setMaterials_name(String materials_name) {
        this.materials_name = materials_name;
    }

    public String getPlan_time() {
        return plan_time;
    }

    public void setPlan_time(String plan_time) {
        this.plan_time = plan_time;
    }

    public String getInstock_time() {
        return instock_time;
    }

    public void setInstock_time(String instock_time) {
        this.instock_time = instock_time;
    }

    public String getQualified_time() {
        return qualified_time;
    }

    public void setQualified_time(String qualified_time) {
        this.qualified_time = qualified_time;
    }

    public String getRemoval_time() {
        return removal_time;
    }

    public void setRemoval_time(String removal_time) {
        this.removal_time = removal_time;
    }

    public String getStorage_location() {
        return storage_location;
    }

    public void setStorage_location(String storage_location) {
        this.storage_location = storage_location;
    }

    public String getSale_id() {
        return sale_id;
    }

    public void setSale_id(String sale_id) {
        this.sale_id = sale_id;
    }

    public String getLast_time() {
        return last_time;
    }

    public String getLast_location() {
        return last_location;
    }

    public void setLast_time(String last_time) {
        this.last_time = last_time;
    }

    public void setLast_location(String last_location) {
        this.last_location = last_location;
    }
}
