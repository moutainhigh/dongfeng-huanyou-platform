package com.navinfo.opentsp.dongfeng.monitor.entity;

import javax.persistence.*;
import java.math.BigInteger;

/**
 * 风险防控区域数据点
 *
 * @author tushenghong
 * @version 1.0
 * @date 2017-03-20
 * @modify
 * @copyright Navi Tsp
 */
@Entity
@Table(name = "hy_draw_map_data")
public class HyDrawMapDataEntity {
    private BigInteger dmdId;
    private Long dmId;
    private Long dmLng;
    private Long dmLat;
    private Integer dmdRoadNum;
    private Long dmdInflectionNum;
    private Long dmdSort;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "DMD_ID", columnDefinition = "bigint")
    public BigInteger getDmdId() {
        return dmdId;
    }

    public void setDmdId(BigInteger dmdId) {
        this.dmdId = dmdId;
    }

    @Basic
    @Column(name = "DM_ID")
    public Long getDmId() {
        return dmId;
    }

    public void setDmId(Long dmId) {
        this.dmId = dmId;
    }

    @Basic
    @Column(name = "DM_LNG")
    public Long getDmLng() {
        return dmLng;
    }

    public void setDmLng(Long dmLng) {
        this.dmLng = dmLng;
    }

    @Basic
    @Column(name = "DM_LAT")
    public Long getDmLat() {
        return dmLat;
    }

    public void setDmLat(Long dmLat) {
        this.dmLat = dmLat;
    }

    @Basic
    @Column(name = "DMD_ROAD_NUM")
    public Integer getDmdRoadNum() {
        return dmdRoadNum;
    }

    public void setDmdRoadNum(Integer dmdRoadNum) {
        this.dmdRoadNum = dmdRoadNum;
    }

    @Basic
    @Column(name = "DMD_INFLECTION_NUM")
    public Long getDmdInflectionNum() {
        return dmdInflectionNum;
    }

    public void setDmdInflectionNum(Long dmdInflectionNum) {
        this.dmdInflectionNum = dmdInflectionNum;
    }

    @Basic
    @Column(name = "DMD_SORT")
    public Long getDmdSort() {
        return dmdSort;
    }

    public void setDmdSort(Long dmdSort) {
        this.dmdSort = dmdSort;
    }
}
