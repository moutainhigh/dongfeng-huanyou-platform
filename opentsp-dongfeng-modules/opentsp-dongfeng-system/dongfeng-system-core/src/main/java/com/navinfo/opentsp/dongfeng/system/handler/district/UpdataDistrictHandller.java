package com.navinfo.opentsp.dongfeng.system.handler.district;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.CommonResult;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.system.commands.district.UpdateDistrictCommand;
import com.navinfo.opentsp.dongfeng.system.service.IDistrictService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by liht on 2017/3/16.
 */
@Component
public class UpdataDistrictHandller extends ValidateTokenAndReSetAbstracHandler<UpdateDistrictCommand, CommonResult> {

    @Resource
    private IDistrictService districtService;

    public UpdataDistrictHandller() {super(UpdateDistrictCommand.class , CommonResult.class);}

    protected UpdataDistrictHandller(Class<UpdateDistrictCommand> commandType, Class<CommonResult> resultType) {super(commandType, resultType);}


    @Override
    protected CommonResult businessHandle(UpdateDistrictCommand command) {
        CommonResult result = new CommonResult();
        try {
            result = districtService.updateDistrict(command);
        } catch (Exception e) {
            result.fillResult(ReturnCode.SERVER_ERROR);
            logger.error(e.getMessage(), e);

        }
        return result;
    }
}
