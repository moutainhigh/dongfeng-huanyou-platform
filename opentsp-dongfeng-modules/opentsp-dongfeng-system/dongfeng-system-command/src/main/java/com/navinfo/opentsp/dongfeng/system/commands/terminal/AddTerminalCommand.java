package com.navinfo.opentsp.dongfeng.system.commands.terminal;

import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.util.RegexpUtils;
import com.navinfo.opentsp.dongfeng.common.validation.group.GroupCommand;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * 添加终端指令
 *
 * @author: tushenghong
 * @version: 1.0
 * @since: 2017-03-08
 **/
public class AddTerminalCommand extends BaseCommand<HttpCommandResultWithData> {

    @NotNull(message = "终端ID 不能为NULL", groups = {GroupCommand.class})
    @NotBlank(message = "终端ID 不能为空", groups = {GroupCommand.class})
    private String terminalId; //终端ID
    @NotNull(message = "SIM卡 不能为NULL", groups = {GroupCommand.class})
    @NotBlank(message = "SIM卡 不能为空", groups = {GroupCommand.class})
    @Pattern(message = "终端SIM卡格式不合法", regexp = RegexpUtils.ELEVEN_NUMBER_ONLY_REGEXP, groups = GroupCommand.class)
    private String simNo;//sim卡
    @NotNull(message = "终端通讯号 不能为NULL", groups = {GroupCommand.class})
    @NotBlank(message = "终端通讯号 不能为空", groups = {GroupCommand.class})
    @Pattern(message = "终端通讯号格式不合法", regexp = RegexpUtils.ELEVEN_NUMBER_ONLY_REGEXP, groups = GroupCommand.class)
    private String communicationId;//终端通讯号
    @NotNull(message = "终端类型 不能为NULL", groups = {GroupCommand.class})
    @NotBlank(message = "终端类型 不能为空", groups = {GroupCommand.class})
    private String type;//终端类型
    private String model;//终端型号
    @NotNull(message = "终端协议 不能为NULL", groups = {GroupCommand.class})
    @NotBlank(message = "终端协议 不能为空", groups = {GroupCommand.class})
    private String protocol;//终端协议
    @NotNull(message = "所属经销商 不能为NULL", groups = {GroupCommand.class})
    @NotBlank(message = "所属经销商 不能为空", groups = {GroupCommand.class})
    private String reAgent;//所属经销商
    private String devLabelId;//设备标签ID
    private String camera;//摄像头
    private String mic;//mic
    private String remark;//备注
    private String from;//来源，从网页添加还是通过其他工具添加

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

    public String getCommunicationId() {
        return communicationId;
    }

    public void setCommunicationId(String communicationId) {
        this.communicationId = communicationId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getReAgent() {
        return reAgent;
    }

    public void setReAgent(String reAgent) {
        this.reAgent = reAgent;
    }

    public String getDevLabelId() {
        return devLabelId;
    }

    public void setDevLabelId(String devLabelId) {
        this.devLabelId = devLabelId;
    }

    public String getCamera() {
        return camera;
    }

    public void setCamera(String camera) {
        this.camera = camera;
    }

    public String getMic() {
        return mic;
    }

    public void setMic(String mic) {
        this.mic = mic;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    @Override
    public Class<? extends HttpCommandResultWithData> getResultType() {
        return HttpCommandResultWithData.class;
    }

    @Override
    public String toString() {
        return "AddTerminalCommand{" +
                "terminalId='" + terminalId + '\'' +
                ", simNo='" + simNo + '\'' +
                ", communicationId='" + communicationId + '\'' +
                ", type='" + type + '\'' +
//                ", model='" + model + '\'' +
                ", protocol='" + protocol + '\'' +
                ", reAgent='" + reAgent + '\'' +
                ", devLabelId='" + devLabelId + '\'' +
                ", camera='" + camera + '\'' +
                ", mic='" + mic + '\'' +
                ", remark='" + remark + '\'' +
                ", from='" + from + '\'' +
                ", " + super.toString() +
                '}';
    }
}
