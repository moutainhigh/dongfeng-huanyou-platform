package com.navinfo.opentsp.dongfeng.kafka.pojo;


import com.navinfo.opentsp.dongfeng.common.command.BaseTcpCommand;

/**
 * 发送防控激活指令
 *
 * @wenya
 * @create 2017-03-29 9:31
 **/
public class Gps_2170_Cmd extends BaseTcpCommand {
    //激活使用
    private String engineType;
    private String terminalId;
    private Integer sign;//标志 1：激活 2：关闭激活
    private Integer flag;//0：防控激活 1：远程锁车
    //锁车方案
    private Integer lockMethod;
    //参数（喷油限制 、扭矩限制、转速限制 ）
    private Integer lockParam;
    private Integer statue;//在线状态
    private String logId;//日志id

    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getEngineType() {
        return engineType;
    }

    public void setEngineType(String engineType) {
        this.engineType = engineType;
    }

    public Integer getSign() {
        return sign;
    }

    public void setSign(Integer sign) {
        this.sign = sign;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public Integer getLockMethod() {
        return lockMethod;
    }

    public void setLockMethod(Integer lockMethod) {
        this.lockMethod = lockMethod;
    }

    public Integer getLockParam() {
        return lockParam;
    }

    public void setLockParam(Integer lockParam) {
        this.lockParam = lockParam;
    }

    public Integer getStatue() {
        return statue;
    }

    public void setStatue(Integer statue) {
        this.statue = statue;
    }
}
