package com.navinfo.opentsp.dongfeng.monitor.commands.car;

import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.result.CommonResult;
import com.navinfo.opentsp.dongfeng.common.validation.group.GroupCommand;

import javax.validation.constraints.NotNull;

/**
 * 远程锁车
 * @Author liusanhu@aerozhonghuan.com
 * @Date 2017/3/30
 */
public class LockCarCommand extends BaseCommand<CommonResult> {

    @NotNull(message = "车辆ID不能为空", groups = {GroupCommand.class})
    private Long carId;//车辆ID
    @NotNull(message = "密码不能为空", groups = {GroupCommand.class})
    private String password;
    @NotNull(message = "锁车标志不能为空", groups = {GroupCommand.class})
    private Integer sign;//1:锁车,0:解锁
    //@NotNull(message = "锁车方案不能为空", groups = {GroupCommand.class})
    private Integer lockMethod;
    //参数（喷油限制 、扭矩限制、转速限制 ）
    //@NotNull(message = "设置参数不能为空", groups = {GroupCommand.class})
    private Integer lockParam;
    //操作人ip
    private String accountIp;
    //操作备注
    private String content;

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
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

    public Integer getLockMethod() {
        return lockMethod;
    }

    public void setLockMethod(Integer lockMethod) {
        this.lockMethod = lockMethod;
    }

    public Integer getLockParam() {
        return lockParam;
    }

    public void setLockParam(Integer lockParam) {
        this.lockParam = lockParam;
    }

    public String getAccountIp() {
        return accountIp;
    }

    public void setAccountIp(String accountIp) {
        this.accountIp = accountIp;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public Class<? extends CommonResult> getResultType() {
        return CommonResult.class;
    }
}
