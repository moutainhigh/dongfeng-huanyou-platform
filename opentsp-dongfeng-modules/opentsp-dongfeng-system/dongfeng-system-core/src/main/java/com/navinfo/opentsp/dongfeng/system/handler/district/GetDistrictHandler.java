package com.navinfo.opentsp.dongfeng.system.handler.district;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.system.commands.district.GetDistrictCommand;
import com.navinfo.opentsp.dongfeng.system.service.IDistrictService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @wenya
 * @create 2017-04-24 14:14
 **/
@Component
public class GetDistrictHandler extends ValidateTokenAndReSetAbstracHandler<GetDistrictCommand, HttpCommandResultWithData> {
    @Resource
    private IDistrictService districtService;
    public GetDistrictHandler(){
        super(GetDistrictCommand.class,HttpCommandResultWithData.class);
    }
    protected GetDistrictHandler(Class<GetDistrictCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Override
    protected HttpCommandResultWithData businessHandle(GetDistrictCommand command) {
        HttpCommandResultWithData result = districtService.getDistrict(command);
        return result;
    }
}
