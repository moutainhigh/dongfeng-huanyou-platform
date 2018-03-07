package com.navinfo.opentsp.dongfeng.monitor.commands.risk;

import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.validation.group.Converter;
import com.navinfo.opentsp.dongfeng.common.validation.group.GroupCommand;
import com.navinfo.opentsp.dongfeng.common.validation.group.ParameterType;
import com.navinfo.opentsp.dongfeng.monitor.dto.risk.Point;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;

/**
 * 新增区域
 *
 * @author tushenghong
 * @version 1.0
 * @date 2017-03-20
 * @modify
 * @copyright Navi Tsp
 */
public class AddRiskRegionCommand extends BaseCommand<HttpCommandResultWithData> {
    @NotNull(message = "风控区类型 不能为NULL", groups = {GroupCommand.class})
    @NotBlank(message = "风控区类型 不能为NULL", groups = {GroupCommand.class})
    private String regionType;
    @NotNull(message = "风控区名称 不能为NULL", groups = {GroupCommand.class})
    @NotBlank(message = "风控区名称 不能为空", groups = {GroupCommand.class})
    private String regionName; //风控区名称
    @NotNull(message = "所属经销商/区域 不能为NULL", groups = {GroupCommand.class})
    @NotBlank(message = "所属经销商/区域 不能为空", groups = {GroupCommand.class})
    private String regionParent;//所属经销商/区域
    private String createTime;//创建时间
    private String userName;//修改人
    private String remark;//备注信息
    private String radius;//半径(圆形风控区域半径，其他为空)
    @NotNull(message = "区域点坐标 不能为NULL", groups = {GroupCommand.class})
    @NotBlank(message = "区域点坐标 不能为空", groups = {GroupCommand.class})
    @Converter(target = "pointArray", type = ParameterType.LIST)
    private String points;//区域坐标点点坐标
    private ArrayList<Point> pointArray;//区域坐标点点坐标

    public String getRegionType() {
        return regionType;
    }

    public void setRegionType(String regionType) {
        this.regionType = regionType;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getRegionParent() {
        return regionParent;
    }

    public void setRegionParent(String regionParent) {
        this.regionParent = regionParent;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRadius() {
        return radius;
    }

    public void setRadius(String radius) {
        this.radius = radius;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public ArrayList<Point> getPointArray() {
        return pointArray;
    }

    public void setPointArray(ArrayList<Point> pointArray) {
        this.pointArray = pointArray;
    }

    /**
     * @return
     */
    @Override
    public Class<? extends HttpCommandResultWithData> getResultType() {
        return HttpCommandResultWithData.class;
    }
}