package com.navinfo.opentsp.dongfeng.system.pojo;

import java.math.BigInteger;

/**
 * Created by ZHANGYUE on 2017/3/13.
 */

public class BasicdataPojo {

    private BigInteger dataId;
    private String dataCode;
    private String dataValue;
    private int dataType;
    private String parentCode;

    public BigInteger getDataId() {
        return dataId;
    }

    public void setDataId(BigInteger dataId) {
        this.dataId = dataId;
    }

    public String getDataCode() {
        return dataCode;
    }

    public void setDataCode(String dataCode) {
        this.dataCode = dataCode;
    }

    public String getDataValue() {
        return dataValue;
    }

    public void setDataValue(String dataValue) {
        this.dataValue = dataValue;
    }

    public int getDataType() {
        return dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

}
