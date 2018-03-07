package com.navinfo.opentsp.dongfeng.common.dto;


/**
 * 3170指令redis中存放的对象
 *
 * @wenya
 * @create 2017-04-01 11:46
 **/
public class TamperData {
    private String token;
    private String logId;    //存入的日志id
    private String commanId; //指令id
    private String type;//指令类型，1：激活 2：取消激活 3：锁车 4：取消锁车（为了流水号为FFFF处理）

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCommanId() {
        return commanId;
    }

    public void setCommanId(String commanId) {
        this.commanId = commanId;
    }

    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
