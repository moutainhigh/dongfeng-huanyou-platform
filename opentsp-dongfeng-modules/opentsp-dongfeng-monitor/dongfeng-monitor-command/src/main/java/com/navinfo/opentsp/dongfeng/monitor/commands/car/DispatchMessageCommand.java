package com.navinfo.opentsp.dongfeng.monitor.commands.car;

import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.validation.group.GroupCommand;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * 短信调度
 *
 * @author tushenghong
 * @version 1.0
 * @date 2017-03-28
 * @modify
 * @copyright Navi Tsp
 */
public class DispatchMessageCommand extends BaseCommand<HttpCommandResultWithData> {
    @NotNull(message = "车辆ID不能为空", groups = {GroupCommand.class})
    private Long carId;//车辆ID
    @NotNull(message = "消息内容不能为空", groups = {GroupCommand.class})
    @NotBlank(message = "消息内容不能为空", groups = {GroupCommand.class})
    private String messageContent;
    @NotNull(message = "显示方式不能为空", groups = {GroupCommand.class})
    private Integer showType;
    private String operateIp;//操作人IP

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public Integer getShowType() {
        return showType;
    }

    public void setShowType(Integer showType) {
        this.showType = showType;
    }

    public String getOperateIp() {
        return operateIp;
    }

    public void setOperateIp(String operateIp) {
        this.operateIp = operateIp;
    }

    @Override
    public Class<? extends HttpCommandResultWithData> getResultType() {
        return HttpCommandResultWithData.class;
    }
}