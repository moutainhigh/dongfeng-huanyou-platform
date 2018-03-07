package com.navinfo.opentsp.dongfeng.monitor.commands.car;/**
 * Created by Administrator on 2017/4/18.
 */

import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.validation.group.Converter;
import com.navinfo.opentsp.dongfeng.common.validation.group.GroupCommand;
import com.navinfo.opentsp.dongfeng.common.validation.group.ParameterType;
import com.navinfo.opentsp.dongfeng.monitor.dto.car.CarCountPoint;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 导出地图车次
 *
 * @wenya
 * @create 2017-04-18 9:39
 **/
public class ExportCarCountCommand extends BaseCommand<HttpCommandResultWithData> {
    @Converter(target = "pointBean", type = ParameterType.LIST)
    private String points;
    //区域坐标点点坐标
    private List<CarCountPoint> pointBean;
    @NotNull(message = "时间不能为空", groups = {GroupCommand.class})
    private String time;//时间
    //邮件地址
    private String mail;
    //邮件主题
    private String mailSubject;
    //excel sheet页名称
    private String sheetName;


    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public List<CarCountPoint> getPointBean() {
        return pointBean;
    }

    public void setPointBean(List<CarCountPoint> pointBean) {
        this.pointBean = pointBean;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getMailSubject() {
        return mailSubject;
    }

    public void setMailSubject(String mailSubject) {
        this.mailSubject = mailSubject;
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
        return "ExportCarCountCommand{" +
                "points='" + points + '\'' +
                ", pointBean=" + pointBean +
                ", time='" + time + '\'' +
                ", mail='" + mail + '\'' +
                ", mailSubject='" + mailSubject + '\'' +
                ", sheetName='" + sheetName + '\'' +
                '}';
    }
}
