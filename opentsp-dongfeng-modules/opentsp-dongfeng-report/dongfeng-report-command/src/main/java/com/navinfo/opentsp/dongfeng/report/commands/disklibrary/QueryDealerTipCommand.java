package com.navinfo.opentsp.dongfeng.report.commands.disklibrary;

import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;

/**
 * 所属销售区域弹窗
 *
 * @wenya
 * @create 2017-03-24 17:13
 **/
public class QueryDealerTipCommand extends BaseCommand<HttpCommandResultWithData> {
    //销售区域名称
    private String tname;

    private String tid;

    private Integer type;
    //盘库、末次位置报表使用  默认：1
    private String isDealerAll;

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getIsDealerAll() {
        return isDealerAll;
    }

    public void setIsDealerAll(String isDealerAll) {
        this.isDealerAll = isDealerAll;
    }

    @Override
    public Class<? extends HttpCommandResultWithData> getResultType() {
        return HttpCommandResultWithData.class;
    }
    @Override
    public String toString() {
        return "QueryDistrictCommand{" +
                "tname='" + tname + '\'' +
                ", tid='" + tid + '\'' +
                ", type=" + type +
                ", isDealerAll='" + isDealerAll + '\'' +
                '}';
    }
}
