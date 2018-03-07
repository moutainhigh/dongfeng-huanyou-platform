package com.navinfo.opentsp.dongfeng.monitor.entity;

import javax.persistence.*;
import java.math.BigInteger;

/**
 * @Author liusanhu@aerozhonghuan.com
 * @Date 2017/3/13
 */
@Entity
@Table(name = "hy_draw_map")
public class HyDrawMapEntity {
    private BigInteger dmId;
    private int districtId;
    private String dmName;
    private Integer dmType;
    private Integer dmImg;
    private Integer dmIsShare;
    private String dmAccountName;
    private BigInteger dmGroupId;
    private BigInteger dmDate;
    private BigInteger dmUdate;
    private String dmDescribe;
    private String dmNumber;
    private String dmAddress;
    private String dmLink;
    private BigInteger dmParent;
    private Integer dmFlag;
    private Integer dmWidth;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "DM_ID", nullable = false, columnDefinition="bigint")
    public BigInteger getDmId() {
        return dmId;
    }

    public void setDmId(BigInteger dmId) {
        this.dmId = dmId;
    }

    @Basic
    @Column(name = "DISTRICT_ID", nullable = false)
    public int getDistrictId() {
        return districtId;
    }

    public void setDistrictId(int districtId) {
        this.districtId = districtId;
    }

    @Basic
    @Column(name = "DM_NAME", nullable = true, length = 200)
    public String getDmName() {
        return dmName;
    }

    public void setDmName(String dmName) {
        this.dmName = dmName;
    }

    @Basic
    @Column(name = "DM_TYPE", nullable = true)
    public Integer getDmType() {
        return dmType;
    }

    public void setDmType(Integer dmType) {
        this.dmType = dmType;
    }

    @Basic
    @Column(name = "DM_IMG", nullable = true)
    public Integer getDmImg() {
        return dmImg;
    }

    public void setDmImg(Integer dmImg) {
        this.dmImg = dmImg;
    }

    @Basic
    @Column(name = "DM_IS_SHARE", nullable = true)
    public Integer getDmIsShare() {
        return dmIsShare;
    }

    public void setDmIsShare(Integer dmIsShare) {
        this.dmIsShare = dmIsShare;
    }

    @Basic
    @Column(name = "DM_ACCOUNT_NAME", nullable = true, length = 50)
    public String getDmAccountName() {
        return dmAccountName;
    }

    public void setDmAccountName(String dmAccountName) {
        this.dmAccountName = dmAccountName;
    }

    @Basic
    @Column(name = "DM_GROUP_ID", nullable = true, columnDefinition="bigint")
    public BigInteger getDmGroupId() {
        return dmGroupId;
    }

    public void setDmGroupId(BigInteger dmGroupId) {
        this.dmGroupId = dmGroupId;
    }

    @Basic
    @Column(name = "DM_DATE", nullable = true, columnDefinition="bigint")
    public BigInteger getDmDate() {
        return dmDate;
    }

    public void setDmDate(BigInteger dmDate) {
        this.dmDate = dmDate;
    }

    @Basic
    @Column(name = "DM_UDATE", nullable = true, columnDefinition="bigint")
    public BigInteger getDmUdate() {
        return dmUdate;
    }

    public void setDmUdate(BigInteger dmUdate) {
        this.dmUdate = dmUdate;
    }

    @Basic
    @Column(name = "DM_DESCRIBE", nullable = true, length = 2000)
    public String getDmDescribe() {
        return dmDescribe;
    }

    public void setDmDescribe(String dmDescribe) {
        this.dmDescribe = dmDescribe;
    }

    @Basic
    @Column(name = "DM_NUMBER", nullable = false, length = 100)
    public String getDmNumber() {
        return dmNumber;
    }

    public void setDmNumber(String dmNumber) {
        this.dmNumber = dmNumber;
    }

    @Basic
    @Column(name = "DM_ADDRESS", nullable = true, length = 200)
    public String getDmAddress() {
        return dmAddress;
    }

    public void setDmAddress(String dmAddress) {
        this.dmAddress = dmAddress;
    }

    @Basic
    @Column(name = "DM_LINK", nullable = true, length = 200)
    public String getDmLink() {
        return dmLink;
    }

    public void setDmLink(String dmLink) {
        this.dmLink = dmLink;
    }

    @Basic
    @Column(name = "DM_PARENT", nullable = true, columnDefinition="bigint")
    public BigInteger getDmParent() {
        return dmParent;
    }

    public void setDmParent(BigInteger dmParent) {
        this.dmParent = dmParent;
    }

    @Basic
    @Column(name = "DM_FLAG", nullable = true)
    public Integer getDmFlag() {
        return dmFlag;
    }

    public void setDmFlag(Integer dmFlag) {
        this.dmFlag = dmFlag;
    }

    @Basic
    @Column(name = "DM_WIDTH", nullable = true)
    public Integer getDmWidth() {
        return dmWidth;
    }

    public void setDmWidth(Integer dmWidth) {
        this.dmWidth = dmWidth;
    }

}
