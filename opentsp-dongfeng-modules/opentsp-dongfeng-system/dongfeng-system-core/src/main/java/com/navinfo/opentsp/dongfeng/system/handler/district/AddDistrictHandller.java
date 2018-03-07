package com.navinfo.opentsp.dongfeng.system.handler.district;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.CommonResult;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.system.commands.district.AddDistrictCommand;
import com.navinfo.opentsp.dongfeng.system.service.IDistrictService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by liht on 2017/3/13.
 *
 * 新建销售区域
 */
@Component
public class AddDistrictHandller extends ValidateTokenAndReSetAbstracHandler<AddDistrictCommand, CommonResult> {

    @Resource
    private IDistrictService districtService;

    public AddDistrictHandller() {
        super(AddDistrictCommand.class , CommonResult.class);
    }

    protected AddDistrictHandller(Class<AddDistrictCommand> commandType, Class<CommonResult> resultType) {
        super(commandType, resultType);
    }


    @Override
    protected CommonResult businessHandle(AddDistrictCommand command) {
        CommonResult result = new CommonResult();
        try {
            result = districtService.addDistrict(command);
        } catch(Exception e) {
            result.fillResult(ReturnCode.SERVER_ERROR);
            logger.error(e.getMessage(), e);
        }
        return result;
    }
}
