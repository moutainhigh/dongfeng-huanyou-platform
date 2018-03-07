package com.navinfo.opentsp.dongfeng.monitor.commands.car;

import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.validation.group.GroupCommand;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * 轨迹回放:轨迹点command
 *
 * @author fengwm
 * @version 1.0
 * @date 2017-03-09
 * @modify
 * @copyright Navi Tsp
 */
public class QueryTracesCommand extends BaseCommand<HttpCommandResultWithData> {
    //轨迹查询开始时间点
    @NotNull(message = "开始时间 不能为NULL", groups = {GroupCommand.class})
    @NotBlank(message = "开始时间 不能为空", groups = {GroupCommand.class})
    private String beginDate;
    //轨迹查询结束时间点
    @NotNull(message = "结束时间 不能为NULL", groups = {GroupCommand.class})
    @NotBlank(message = "结束时间 不能为空", groups = {GroupCommand.class})
    private String endDate;
    //车辆ID
    @NotNull(message = "车辆ID 不能为NULL", groups = {GroupCommand.class})
    @NotBlank(message = "车辆ID 不能为空", groups = {GroupCommand.class})
    private String carId;
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
    //是否查询全部  -1：是   0否
    private String isAll;

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getCarId() {
        return carId;
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

    public void setCarId(String carId) {
        this.carId = carId;

    }

    public String getIsAll() {
        return isAll;
    }

    public void setIsAll(String isAll) {
        this.isAll = isAll;
    }

    @Override
    public Class<? extends HttpCommandResultWithData> getResultType() {
        return HttpCommandResultWithData.class;
    }

    @Override
    public String toString() {
        return "QueryTracesCommand{" +
                "beginDate='" + beginDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", carId='" + carId + '\'' +
                ", isAll='" + isAll + '\'' +
                '}';
    }
}