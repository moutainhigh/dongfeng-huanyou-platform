package com.navinfo.opentsp.dongfeng.report.entity.market;

import javax.persistence.*;
import java.math.BigInteger;

/**
 * ${DESCRIPTION}
 *
 * @author wt
 * @version 1.0
 * @date 2017-06-06
 * @modify
 * @copyright Navi Tsp
 */
@Entity
@Table(name = "hy_scancode_info")
public class ScanCodeRecordEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(nullable = false, name = "id", columnDefinition="bigint")
    private BigInteger id;

    @Column(nullable = true, name = "vin")
    private String vin;

    @Column(nullable = true, name = "company")
    private String company;

    @Column(nullable = true, name = "factory")
    private String factory;

    @Column(nullable = true, name = "team")
    private String team;

    @Column(nullable = true, name = "propertywh")
    private String propertywh;

    @Column(nullable = true, name = "physicalwh")
    private String physicalwh;

    @Column(nullable = true, name = "area")
    private String area;

    @Column(nullable = true, name = "scrutator")
    private String scrutator;

    @Column(nullable = true, name = "relateman")
    private String relateman;

    @Column(nullable = true, name = "operator")
    private String operator;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getFactory() {
        return factory;
    }

    public void setFactory(String factory) {
        this.factory = factory;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getPropertywh() {
        return propertywh;
    }

    public void setPropertywh(String propertywh) {
        this.propertywh = propertywh;
    }

    public String getPhysicalwh() {
        return physicalwh;
    }

    public void setPhysicalwh(String physicalwh) {
        this.physicalwh = physicalwh;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getScrutator() {
        return scrutator;
    }

    public void setScrutator(String scrutator) {
        this.scrutator = scrutator;
    }

    public String getRelateman() {
        return relateman;
    }

    public void setRelateman(String relateman) {
        this.relateman = relateman;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
}
