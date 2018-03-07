package com.navinfo.opentsp.dongfeng.monitor.pojo.station;

import java.math.BigInteger;

/**
 * 查询服务站评价DTO
 * @author fengwm
 * @version 1.0
 * @date 2017-03-14
 * @modify
 * @copyright Navi Tsp
 */

public class StationCommentsPojo {

    //主键
    private BigInteger id;
    //点评内容
    private String comment;
    //点评时间
    private BigInteger commentTime;
    //点评时间
    private String commentTimeStr;
    //总体评价星级
    private Integer level;
    //底盘号
    private String chassium;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public BigInteger getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(BigInteger commentTime) {
        this.commentTime = commentTime;
    }

    public String getCommentTimeStr() {
        return commentTimeStr;
    }

    public void setCommentTimeStr(String commentTimeStr) {
        this.commentTimeStr = commentTimeStr;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getChassium() {
        return chassium;
    }

    public void setChassium(String chassium) {
        this.chassium = chassium;
    }
}