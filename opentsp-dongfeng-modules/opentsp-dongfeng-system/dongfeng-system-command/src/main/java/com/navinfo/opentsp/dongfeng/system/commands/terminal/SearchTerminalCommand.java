package com.navinfo.opentsp.dongfeng.system.commands.terminal;

import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;

/**
 * 查询终端指令
 *
 * @author: tushenghong
 * @version: 1.0
 * @since: 2017-03-08
 **/
public class SearchTerminalCommand extends BaseCommand<HttpCommandResultWithData> {
    private String terminalId; //终端ID
    private String simNo;//sim卡
    private String type;//终端类型
    private String reChassis;//关联底盘号
    private String reVehicleNum;//关联车牌号
    private String reAgent;//关联经销商
    private String devLabelId;//设备标签ID
    private String reStatus;//关联状态

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getReChassis() {
        return reChassis;
    }

    public void setReChassis(String reChassis) {
        this.reChassis = reChassis;
    }

    public String getReVehicleNum() {
        return reVehicleNum;
    }

    public void setReVehicleNum(String reVehicleNum) {
        this.reVehicleNum = reVehicleNum;
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

    public String getReStatus() {
        return reStatus;
    }

    public void setReStatus(String reStatus) {
        this.reStatus = reStatus;
    }

    @Override
    public Class<? extends HttpCommandResultWithData> getResultType() {
        return HttpCommandResultWithData.class;
    }

    @Override
    public String toString() {
        return "SearchTerminalCommand{" +
                "terminalId='" + terminalId + '\'' +
                ", simNo='" + simNo + '\'' +
                ", type='" + type + '\'' +
                ", reChassis='" + reChassis + '\'' +
                ", reVehicleNum='" + reVehicleNum + '\'' +
                ", reAgent='" + reAgent + '\'' +
                ", devLabelId='" + devLabelId + '\'' +
                ", reStatus='" + reStatus + '\'' +
                '}';
    }
}
