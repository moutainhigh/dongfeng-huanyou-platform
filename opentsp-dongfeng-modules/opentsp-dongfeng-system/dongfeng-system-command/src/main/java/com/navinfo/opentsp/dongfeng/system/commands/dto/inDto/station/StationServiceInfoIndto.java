package com.navinfo.opentsp.dongfeng.system.commands.dto.inDto.station;

/**
 * 服务站服务内容
 *
 * @author tushenghong
 * @version 1.0
 * @date 2017-03-23
 * @modify
 * @copyright Navi Tsp
 */
public class StationServiceInfoIndto {
    private String serviceType;//服务类型
    private String spareParts;//备件
    private String serviceContent;//服务内容
    private String promoteTool;//提升工具

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getSpareParts() {
        return spareParts;
    }

    public void setSpareParts(String spareParts) {
        this.spareParts = spareParts;
    }

    public String getServiceContent() {
        return serviceContent;
    }

    public void setServiceContent(String serviceContent) {
        this.serviceContent = serviceContent;
    }

    public String getPromoteTool() {
        return promoteTool;
    }

    public void setPromoteTool(String promoteTool) {
        this.promoteTool = promoteTool;
    }
}
