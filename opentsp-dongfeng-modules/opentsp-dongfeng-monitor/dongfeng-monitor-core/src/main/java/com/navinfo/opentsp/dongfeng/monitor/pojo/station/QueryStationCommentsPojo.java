package com.navinfo.opentsp.dongfeng.monitor.pojo.station;

import com.navinfo.opentsp.dongfeng.common.result.PagingInfo;

import java.math.BigInteger;

/**
 * @author fengwm
 * @version 1.0
 * @date 2017-03-14
 * @modify
 * @copyright Navi Tsp
 */

public class QueryStationCommentsPojo {
    //服务站评价
    PagingInfo<StationCommentsPojo> pagingInfo;
    //一星总数
    BigInteger oneStarCount;
    //二星总数
    BigInteger twoStarCount;
    //三星总数
    BigInteger threeStarCount;
    //四星总数
    BigInteger fourStarCount;
    //五星总数
    BigInteger fiveStarCount;
    //评价总数
    BigInteger commentCount;
    //星级类型
    Integer levelType;

    public PagingInfo<StationCommentsPojo> getPagingInfo() {
        return pagingInfo;
    }

    public void setPagingInfo(PagingInfo<StationCommentsPojo> pagingInfo) {
        this.pagingInfo = pagingInfo;
    }

    public BigInteger getOneStarCount() {
        return oneStarCount;
    }

    public void setOneStarCount(BigInteger oneStarCount) {
        this.oneStarCount = oneStarCount;
    }

    public BigInteger getTwoStarCount() {
        return twoStarCount;
    }

    public void setTwoStarCount(BigInteger twoStarCount) {
        this.twoStarCount = twoStarCount;
    }

    public BigInteger getThreeStarCount() {
        return threeStarCount;
    }

    public void setThreeStarCount(BigInteger threeStarCount) {
        this.threeStarCount = threeStarCount;
    }

    public BigInteger getFourStarCount() {
        return fourStarCount;
    }

    public void setFourStarCount(BigInteger fourStarCount) {
        this.fourStarCount = fourStarCount;
    }

    public BigInteger getFiveStarCount() {
        return fiveStarCount;
    }

    public BigInteger getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(BigInteger commentCount) {
        this.commentCount = commentCount;
    }

    public void setFiveStarCount(BigInteger fiveStarCount) {
        this.fiveStarCount = fiveStarCount;
    }

    public Integer getLevelType() {
        return levelType;
    }

    public void setLevelType(Integer levelType) {
        this.levelType = levelType;
    }
}