package com.navinfo.opentsp.dongfeng.system.entity;

import javax.persistence.*;

/**
 * 菜单表
 *
 * @author tushenghong
 * @version 1.0
 * @date 2017-03-14
 * @modify
 * @copyright Navi Tsp
 */
@Entity
@Table(name = "hy_action")
public class HyActionEntity {
    private int aCode;
    private String aName;
    private String aDesc;
    private Integer aParentCode;
//    private String aIcon;
//    private String aUrl;
//    private Integer aDisplayOrder;

    @Id
    @Column(name = "A_CODE", nullable = false)
    public int getaCode() {
        return aCode;
    }

    public void setaCode(int aCode) {
        this.aCode = aCode;
    }

    @Basic
    @Column(name = "A_NAME", nullable = true, length = 200)
    public String getaName() {
        return aName;
    }

    public void setaName(String aName) {
        this.aName = aName;
    }

    @Basic
    @Column(name = "A_DESC", nullable = true, length = 2000)
    public String getaDesc() {
        return aDesc;
    }

    public void setaDesc(String aDesc) {
        this.aDesc = aDesc;
    }

    @Basic
    @Column(name = "A_PARENT_CODE", nullable = true)
    public Integer getaParentCode() {
        return aParentCode;
    }

    public void setaParentCode(Integer aParentCode) {
        this.aParentCode = aParentCode;
    }

//    @Basic
//    @Column(name = "A_ICON", nullable = true, length = 200)
//    public String getaIcon() {
//        return aIcon;
//    }
//
//    public void setaIcon(String aIcon) {
//        this.aIcon = aIcon;
//    }

//    @Basic
//    @Column(name = "A_URL", nullable = true, length = 200)
//    public String getaUrl() {
//        return aUrl;
//    }
//
//    public void setaUrl(String aUrl) {
//        this.aUrl = aUrl;
//    }

//    @Basic
//    @Column(name = "A_DISPLAY_ORDER", nullable = true)
//    public Integer getaDisplayOrder() {
//        return aDisplayOrder;
//    }
//
//    public void setaDisplayOrder(Integer aDisplayOrder) {
//        this.aDisplayOrder = aDisplayOrder;
//    }

}
