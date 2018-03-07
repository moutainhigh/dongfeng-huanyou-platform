package com.navinfo.opentsp.common.messaging.data;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.navinfo.opentsp.common.messaging.TypeDescriptor;

import java.util.HashMap;


public class FieldData extends HashMap<String, Object>
        implements TypeDescriptor {
    private String typeInfo;

    public FieldData() {
    }

    public FieldData(String typeInfo) {
        setTypeInfo(typeInfo);
    }

    public void setTypeInfo(String typeInfo) {
        this.typeInfo = typeInfo;
        put(JsonTypeInfo.Id.CLASS.getDefaultPropertyName(), typeInfo);
    }

    public String getTypeInfo() {
        return this.typeInfo;
    }

    public String toString() {
        return "FieldData{typeInfo='" + this.typeInfo + '\'' + "} " + super.toString();
    }
}