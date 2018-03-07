package com.navinfo.opentsp.dongfeng.common.result;

import java.util.List;

/**
 * Created by yinsihua on 2016/3/21.
 */
public class ListResult<T> {

    /**
     * 当前数据集合
     */
    private List<T> list;

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public ListResult() {
    }

    public ListResult(List<T> list) {
        this.list = list;
    }
}
