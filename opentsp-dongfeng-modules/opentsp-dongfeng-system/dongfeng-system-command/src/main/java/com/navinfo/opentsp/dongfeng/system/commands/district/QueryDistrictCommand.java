package com.navinfo.opentsp.dongfeng.system.commands.district;

import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;

/**
 * 销售区域搜索Command
 *
 * @author Sunyu
 */
public class QueryDistrictCommand extends BaseCommand<HttpCommandResultWithData> {

    /**
     * 分组名称
     */
    private String tname;
    /**
     * 是否是公司
     */
    private String ttype;
    /**
     * 上级分组
     */
    private String parentId;
    /**
     * 联系人
     */
    private String tlinkman;

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public String getTtype() {
        return ttype;
    }

    public void setTtype(String ttype) {
        this.ttype = ttype;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getTlinkman() {
        return tlinkman;
    }

    public void setTlinkman(String tlinkman) {
        this.tlinkman = tlinkman;
    }

    @Override
    public Class<? extends HttpCommandResultWithData> getResultType() {
        return HttpCommandResultWithData.class;
    }

    @Override
    public String toString() {
        return "QueryDistrictCommand{" +
                "tname='" + tname + '\'' +
                ", ttype='" + ttype + '\'' +
                ", parentId='" + parentId + '\'' +
                ", tlinkman='" + tlinkman + '\'' +
                '}';
    }
}