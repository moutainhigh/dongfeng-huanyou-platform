package com.navinfo.opentsp.dongfeng.report.client;

import com.navinfo.opentsp.dongfeng.common.result.CommonResult;
import com.navinfo.opentsp.dongfeng.system.commands.car.UpdateStorageStateCommand;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * ${DESCRIPTION}
 *
 * @author wt
 * @version 1.0
 * @date 2017-07-05
 * @modify
 * @copyright Navi Tsp
 */
@FeignClient("system")
public interface VehicleClient {
    /**
     * 依据车辆ID更新车辆扫码在库状态
     *
     * @param cmd
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/car/updateStorageState", consumes = "application/json")
    CommonResult update(@RequestBody UpdateStorageStateCommand cmd);
}
