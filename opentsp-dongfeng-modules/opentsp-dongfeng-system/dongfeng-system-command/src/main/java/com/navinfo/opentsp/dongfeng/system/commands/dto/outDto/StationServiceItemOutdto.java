package com.navinfo.opentsp.dongfeng.system.commands.dto.outDto;

import com.navinfo.opentsp.dongfeng.common.dto.BaseData;

/**
 * @author tushenghong
 * @version 1.0
 * @date 2017-03-24
 * @modify
 * @copyright Navi Tsp
 */
public class StationServiceItemOutdto extends BaseData {
    private Integer dataType;//类型
    private boolean checked = false;

    public StationServiceItemOutdto() {
    }

    public StationServiceItemOutdto(Integer dataType) {
        this.dataType = dataType;
    }

    public StationServiceItemOutdto(Integer dataType, BaseData baseData) {
        this.dataType = dataType;
        setId(baseData.getId());
        setCode(baseData.getCode());
        setValue(baseData.getValue());
    }

    public StationServiceItemOutdto(Integer dataType, boolean checked, BaseData baseData) {
        this.dataType = dataType;
        this.checked = checked;
        setId(baseData.getId());
        setCode(baseData.getCode());
        setValue(baseData.getValue());
    }


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
