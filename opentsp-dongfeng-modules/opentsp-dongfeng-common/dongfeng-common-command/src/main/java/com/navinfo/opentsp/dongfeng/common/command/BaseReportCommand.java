package com.navinfo.opentsp.dongfeng.common.command;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;

/**
 * 报表通用请求基类
 *
 * @author wt
 * @version 1.0
 * @date 2017-03-30
 * @modify
 */
public class BaseReportCommand extends BaseCommand<HttpCommandResultWithData>  {

    private long reportId;
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

    public long getReportId() {
        return reportId;
    }

    public void setReportId(long reportId) {
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
        return "BaseReportCommand{" +
                "reportId=" + reportId +
                ", downloadFlag=" + downloadFlag +
                ", email='" + email + '\'' +
                ", mailSubject='" + mailSubject + '\'' +
                ", mailContent='" + mailContent + '\'' +
                ", wm='" + wm + '\'' +
                ", sheetName='" + sheetName + '\'' +
                '}';
    }
}