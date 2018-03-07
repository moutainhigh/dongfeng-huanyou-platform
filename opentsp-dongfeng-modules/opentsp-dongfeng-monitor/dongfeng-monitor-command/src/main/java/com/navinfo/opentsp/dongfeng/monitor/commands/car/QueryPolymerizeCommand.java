package com.navinfo.opentsp.dongfeng.monitor.commands.car;

import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.validation.group.GroupCommand;

import javax.validation.constraints.NotNull;


/**
 * @Author liusanhu@aerozhonghuan.com
 * @Date 2017/3/23
 */
public class QueryPolymerizeCommand extends BaseCommand<HttpCommandResultWithData>{

    @NotNull(message = "地图显示等级不能为空", groups = {GroupCommand.class})
    private Integer level; //地图显示等级
    @NotNull(message = "左下角经度不能为空", groups = {GroupCommand.class})
    private Double leftLng; //左下角经度
    @NotNull(message = "左下角纬度不能为空", groups = {GroupCommand.class})
    private Double leftLat; //左下角纬度
    @NotNull(message = "右上角经度不能为空", groups = {GroupCommand.class})
    private Double rightLng; //右上角经度
    @NotNull(message = "右上角纬度不能为空", groups = {GroupCommand.class})
    private Double rightLat; //右上角纬度

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Double getLeftLng() {
        return leftLng;
    }

    public void setLeftLng(Double leftLng) {
        this.leftLng = leftLng;
    }

    public Double getLeftLat() {
        return leftLat;
    }

    public void setLeftLat(Double leftLat) {
        this.leftLat = leftLat;
    }

    public Double getRightLng() {
        return rightLng;
    }

    public void setRightLng(Double rightLng) {
        this.rightLng = rightLng;
    }

    public Double getRightLat() {
        return rightLat;
    }

    public void setRightLat(Double rightLat) {
        this.rightLat = rightLat;
    }

    @Override
    public Class<? extends HttpCommandResultWithData> getResultType() {
        return HttpCommandResultWithData.class;
    }

    @Override
    public String toString() {
        return "QueryPolymerizeCommand{}";
    }
}
