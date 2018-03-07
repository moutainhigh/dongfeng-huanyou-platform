package com.navinfo.opentsp.dongfeng.monitor.commands.car;

import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;

/**
 * 所属销售区域弹窗
 *
 * @wenya
 * @create 2017-03-24 17:13
 **/
public class QueryDistrictCommand extends BaseCommand<HttpCommandResultWithData> {
    //销售区域名称
    private String districtName;

    private String tid;

    private Integer type;

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public Class<? extends HttpCommandResultWithData> getResultType() {
        return HttpCommandResultWithData.class;
    }

    @Override
    public String toString() {
        return "QueryDistrictCommand{" +
                "districtName='" + districtName + '\'' +
                ", tid='" + tid + '\'' +
                ", type=" + type +
                '}';
    }
}
