package com.navinfo.opentsp.dongfeng.system.pojo;

//import com.navinfo.opentsp.dongfeng.common.dto.BaseData;

/**
 * @author tushenghong
 * @version 1.0
 * @date 2017-03-23
 * @modify
 * @copyright Navi Tsp
 */
public class StationItemPojo {
    private Integer dataType;//类型
    private boolean checked = false;

    public StationItemPojo() {
    }

//    public StationItemPojo(Integer dataType) {
//        this.dataType = dataType;
//    }
//
//    public StationItemPojo(Integer dataType, BaseData baseData) {
//        this.dataType = dataType;
//        setId(baseData.getId());
//        setCode(baseData.getCode());
//        setValue(baseData.getValue());
//    }
//
//    public StationItemPojo(Integer dataType, boolean checked, BaseData baseData) {
//        this.dataType = dataType;
//        this.checked = checked;
//        setId(baseData.getId());
//        setCode(baseData.getCode());
//        setValue(baseData.getValue());
//    }


    public Integer getDataType() {
        return dataType;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
