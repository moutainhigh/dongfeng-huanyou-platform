package com.navinfo.opentsp.dongfeng.report.dto.disklibrary;


/**
 * 经销商弹窗dto
 * @author fengwm
 * @version 1.0
 * @date 2017-12-19
 * @modify
 * @copyright Navi Tsp
 */
public class QueryDealerTiptDto {
    //主键
    private Long id;
    //所属区域
    private String tname;
    //上级销售区域
    private String dealerCode;
    //联系人
    private String pname;
    //经营品牌
    private String manageBrand;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public String getDealerCode() {
        return dealerCode;
    }

    public void setDealerCode(String dealerCode) {
        this.dealerCode = dealerCode;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getManageBrand() {
        return manageBrand;
    }

    public void setManageBrand(String manageBrand) {
        this.manageBrand = manageBrand;
    }
}
