package com.navinfo.opentsp.dongfeng.monitor.service.car;

import com.navinfo.opentsp.dongfeng.common.result.CommonResult;
import com.navinfo.opentsp.dongfeng.monitor.commands.car.LockCarCommand;

/**
 * 远程锁车
 * @Author liusanhu@aerozhonghuan.com
 * @Date 2017/3/30
 */
public interface ILockCarService {
    /**
     * 远程锁车
     *
     * @param command
     */
    CommonResult lockCar(LockCarCommand command);
}
