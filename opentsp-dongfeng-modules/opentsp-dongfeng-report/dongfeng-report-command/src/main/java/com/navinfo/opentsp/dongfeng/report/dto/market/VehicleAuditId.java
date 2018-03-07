package com.navinfo.opentsp.dongfeng.report.dto.market;

/**
 * @author wt
 * @version 1.0
 * @date 2017/11/28
 * @modify
 * @copyright
 */
public class VehicleAuditId {

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                '}';
    }
}
