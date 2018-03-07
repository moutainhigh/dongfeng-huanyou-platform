package com.navinfo.opentsp.dongfeng.monitor.commands.station;

import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.validation.group.GroupCommand;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * @author fengwm
 * @version 1.0
 * @date 2017-03-15
 * @modify
 * @copyright Navi Tsp
 */
public class QueryCrossAreaCountTreeCommand extends BaseCommand<HttpCommandResultWithData> {
    //服务站ID
    @NotNull(message = "服务站ID 不能为NULL", groups = {GroupCommand.class})
    @NotBlank(message = "服务站ID 不能为空", groups = {GroupCommand.class})
    private String stationId;
    //查询日期
    private String time;
    //reportId
    private Long reportId;
    //导出标示  0 - 导出当前  1- 导出全部
    private int downloadFlag;
    //邮件地址
    private String email;
    //邮件主题
    private String mailSubject;
    //邮件内容
    private String mailContent;
    //邮件下标
    private String wm;
    //excel sheet页名称
    private String sheetName;
    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Long getReportId() {
        return reportId;
    }

    public void setReportId(Long reportId) {
        this.reportId = reportId;
    }

    public int getDownloadFlag() {
        return downloadFlag;
    }

    public void setDownloadFlag(int downloadFlag) {
        this.downloadFlag = downloadFlag;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMailSubject() {
        return mailSubject;
    }

    public void setMailSubject(String mailSubject) {
        this.mailSubject = mailSubject;
    }

    public String getMailContent() {
        return mailContent;
    }

    public void setMailContent(String mailContent) {
        this.mailContent = mailContent;
    }

    public String getWm() {
        return wm;
    }

    public void setWm(String wm) {
        this.wm = wm;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    @Override
    public Class<? extends HttpCommandResultWithData> getResultType() {
        return HttpCommandResultWithData.class;
    }
    @Override
    public String toString() {
        return "QueryCrossAreaCountTreeCommand{" +
                "stationId='" + stationId + '\'' +
                ",time='" + time + '\'' +
                '}';
    }
}