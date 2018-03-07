package com.navinfo.opentsp.dongfeng.monitor.commands.car;

import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.validation.group.GroupCommand;

import javax.validation.constraints.NotNull;

/**
 * 取消防控激活中间状态
 * 正在激活，正在取消激活，正在锁车，正在解锁
 * @wenya
 * @create 2017-04-12 10:56
 **/
public class CancelStatueCommand extends BaseCommand<HttpCommandResultWithData> {
    @NotNull(message = "车辆ID不能为空", groups = {GroupCommand.class})
    private Long carId;//车辆ID
    @NotNull(message = "密码不能为空", groups = {GroupCommand.class})
    private String password;
    private String accountIp;//操作人ip
    //操作备注
    private String content;

    public String getAccountIp() {
        return accountIp;
    }

    public void setAccountIp(String accountIp) {
        this.accountIp = accountIp;
    }

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "CancelStatueCommand{" +
                "carId=" + carId +
                ", password='" + password + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

    @Override
    public Class<? extends HttpCommandResultWithData> getResultType() {
        return HttpCommandResultWithData.class;
    }
}
