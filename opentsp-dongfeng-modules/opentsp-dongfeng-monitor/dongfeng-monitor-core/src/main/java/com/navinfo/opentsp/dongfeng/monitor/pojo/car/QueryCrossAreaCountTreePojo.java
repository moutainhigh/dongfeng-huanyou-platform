package com.navinfo.opentsp.dongfeng.monitor.pojo.car;

import com.navinfo.opentsp.dongfeng.monitor.entity.RootTree;

import java.util.List;

/**
 * 服务站车次树pojo（包含树节点下车明细）
 * @author fengwm
 * @version 1.0
 * @date 2017-03-15
 * @modify
 * @copyright Navi Tsp
 */

public class QueryCrossAreaCountTreePojo extends RootTree{

    private int allcounts;//总数
    private List<CrossAreaCountPojo> carList;//只有叶子有list（树节点下车明细）
    public int getAllcounts() {
        return allcounts;
    }

    public void setAllcounts(int allcounts) {
        this.allcounts = allcounts;
    }

    public List<CrossAreaCountPojo> getCarList() {
        return carList;
    }

    public void setCarList(List<CrossAreaCountPojo> carList) {
        this.carList = carList;
    }
}