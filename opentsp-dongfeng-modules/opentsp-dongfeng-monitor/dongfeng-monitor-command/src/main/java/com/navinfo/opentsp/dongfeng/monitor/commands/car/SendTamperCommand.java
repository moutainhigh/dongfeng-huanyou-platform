package com.navinfo.opentsp.dongfeng.monitor.commands.car;

import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.validation.group.GroupCommand;

import javax.validation.constraints.NotNull;

/**
 * 防控激活指令下发
 *
 * @wenya
 * @create 2017-03-27 17:52
 **/
public class SendTamperCommand extends BaseCommand<HttpCommandResultWithData> {
    @NotNull(message = "车辆ID不能为空", groups = {GroupCommand.class})
    private Long carId;//车辆id
    @NotNull(message = "密码不能为空", groups = {GroupCommand.class})
    private String password;//输入密码
    private String accountIp;//操作人ip
    @NotNull(message = "激活标志不能为空", groups = {GroupCommand.class})
    private Integer sign;//标志 1：激活 2：关闭激活
    private Long carFKDate;//防控时效
//    private String sendFlag; //是否已发送过 0:未发送 1：已发送
    private String content; //操作备注
    private String common;//8103防拆类型（code）

    public String getAccountIp() {
        return accountIp;
    }

    public void setAccountIp(String accountIp) {
        this.accountIp = accountIp;
    }

    public Long getCarFKDate() {
        return carFKDate;
    }

    public void setCarFKDate(Long carFKDate) {
        this.carFKDate = carFKDate;
    }

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public String getCommon() {
        return common;
    }

    public void setCommon(String common) {
        this.common = common;
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

    public Integer getSign() {
        return sign;
    }

    public void setSign(Integer sign) {
        this.sign = sign;
    }

    @Override
    public Class<? extends HttpCommandResultWithData> getResultType() {
        return HttpCommandResultWithData.class;
    }

    @Override
    public String toString() {
        return "SendTamperCommand{" +
                "accountIp='" + accountIp + '\'' +
                ", carId=" + carId +
                ", passWord='" + password + '\'' +
                ", sign='" + sign + '\'' +
                ", carFKDate=" + carFKDate +
                ", content='" + content + '\'' +
                ", common='" + common + '\'' +
                '}';
    }
}
