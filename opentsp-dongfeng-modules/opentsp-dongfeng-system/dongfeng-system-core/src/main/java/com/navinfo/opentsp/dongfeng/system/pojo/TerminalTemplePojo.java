package com.navinfo.opentsp.dongfeng.system.pojo;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * 终端导入模板model
 *
 * @author tushenghong
 * @version 1.0
 * @date 2017-04-07
 * @modify
 * @copyright Navi Tsp
 */
public class TerminalTemplePojo {
    private String id;//序号
    @NotNull(message = "终端型号 不能为NULL")
    @NotBlank(message = "终端型号 不能为空")
    private String model;//终端型号
    @NotNull(message = "终端ID 不能为NULL")
    @NotBlank(message = "终端ID 不能为空")
    private String terminalId;//终端ID号
    @NotNull(message = "SIM卡 不能为NULL")
    @NotBlank(message = "SIM卡 不能为空")
    private String simNo;//SIM卡号
    @NotNull(message = "终端类型 不能为NULL")
    @NotBlank(message = "终端类型 不能为空")
    @Length(min = 1, max = 37, message = "设备标签ID由1-37位字符组成")
    private String labelId;//终端标签ID号
    @NotNull(message = "终端类型 不能为NULL")
    @NotBlank(message = "终端类型 不能为空")
    private String type;//终端类型
    @NotNull(message = "终端协议 不能为NULL")
    @NotBlank(message = "终端协议 不能为空")
    private String protocol;//终端协议
    @NotNull(message = "所属经销商 不能为NULL")
    @NotBlank(message = "所属经销商 不能为空")
    private String dealerCode;//所属经销商编码

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public String getSimNo() {
        return simNo;
    }

    public void setSimNo(String simNo) {
        this.simNo = simNo;
    }

    public String getLabelId() {
        return labelId;
    }

    public void setLabelId(String labelId) {
        this.labelId = labelId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getDealerCode() {
        return dealerCode;
    }

    public void setDealerCode(String dealerCode) {
        this.dealerCode = dealerCode;
    }

    @Override
    public String toString() {
        return "TerminalTemplePojo{" +
                "id='" + id + '\'' +
                ", model='" + model + '\'' +
                ", terminalId='" + terminalId + '\'' +
                ", simNo='" + simNo + '\'' +
                ", labelId='" + labelId + '\'' +
                ", type='" + type + '\'' +
                ", protocol='" + protocol + '\'' +
                ", dealerCode='" + dealerCode + '\'' +
                '}';
    }
}
