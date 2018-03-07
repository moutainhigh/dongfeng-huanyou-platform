package com.navinfo.opentsp.dongfeng.system.pojo.eletag;

/**
 * 标签库同步二级服务站信息
 *
 * @author: tushenghong
 * @version: 1.0
 * @since: 2017-09-06
 **/
public class SyncSecStationPojo {
    private String jf2300;//服务站代码
    private String jf2301;//东径
    private String jf2302;//北纬
    private String jf2303;//有效标志
    private int jf2304;//工作半经
    private String editMode;//编辑模式 0：新增 1：修改 2：删除

    public String getJf2300() {
        return jf2300;
    }

    public void setJf2300(String jf2300) {
        this.jf2300 = jf2300;
    }

    public String getJf2301() {
        return jf2301;
    }

    public void setJf2301(String jf2301) {
        this.jf2301 = jf2301;
    }

    public String getJf2302() {
        return jf2302;
    }

    public void setJf2302(String jf2302) {
        this.jf2302 = jf2302;
    }

    public String getJf2303() {
        return jf2303;
    }

    public void setJf2303(String jf2303) {
        this.jf2303 = jf2303;
    }

    public int getJf2304() {
        return jf2304;
    }

    public void setJf2304(int jf2304) {
        this.jf2304 = jf2304;
    }

    public String getEditMode() {
        return editMode;
    }

    public void setEditMode(String editMode) {
        this.editMode = editMode;
    }
}
