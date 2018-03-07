package com.navinfo.opentsp.dongfeng.monitor.pojo.car;

import java.util.List;

/**
 * @wenya
 * @create 2017-04-13 14:37
 **/
public class QueryThermodynamicPojo {
    private int countMax; //最大车次
    private List<BoundingBoxPojo> list; //网格数据

    public int getCountMax() {
        return countMax;
    }

    public void setCountMax(int countMax) {
        this.countMax = countMax;
    }

    public List<BoundingBoxPojo> getList() {
        return list;
    }

    public void setList(List<BoundingBoxPojo> list) {
        this.list = list;
    }
}
