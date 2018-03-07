package com.navinfo.opentsp.dongfeng.monitor.commands.log;

import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;

/**
 * 查询操作日志
 *
 * @author tushenghong
 * @version 1.0
 * @date 2017-03-13
 * @modify
 * @copyright Navi Tsp
 */
public class QueryOperateLogCommand extends BaseCommand<HttpCommandResultWithData> {

    private String operateType;//操作类型
    private String operateName;//操作名称
    private String operateContent;//操作日志内容
    private String operateUser;//操作用户
    private String operateUserType;//操作用户类型
    private String beginTime;//日志时间-开始时间
    private String endTime;//日志时间-结束时间

    public String getOperateType() {
        return operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }

    public String getOperateName() {
        return operateName;
    }

    public void setOperateName(String operateName) {
        this.operateName = operateName;
    }

    public String getOperateContent() {
        return operateContent;
    }

    public void setOperateContent(String operateContent) {
        this.operateContent = operateContent;
    }

    public String getOperateUser() {
        return operateUser;
    }

    public void setOperateUser(String operateUser) {
        this.operateUser = operateUser;
    }

    public String getOperateUserType() {
        return operateUserType;
    }

    public void setOperateUserType(String operateUserType) {
        this.operateUserType = operateUserType;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @Override
    public Class<? extends HttpCommandResultWithData> getResultType() {
        return HttpCommandResultWithData.class;
    }

    @Override
    public String toString() {
        return "QueryOperateLogCommand{" +
                "operateType='" + operateType + '\'' +
                ", operateName='" + operateName + '\'' +
                ", operateContent='" + operateContent + '\'' +
                ", operateUser='" + operateUser + '\'' +
                ", operateUserType='" + operateUserType + '\'' +
                ", beginTime='" + beginTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", " + super.toString() +
                '}';
    }
}