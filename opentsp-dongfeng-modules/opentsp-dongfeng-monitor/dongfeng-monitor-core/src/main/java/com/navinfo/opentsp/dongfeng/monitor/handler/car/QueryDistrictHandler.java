package com.navinfo.opentsp.dongfeng.monitor.handler.car;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.monitor.commands.car.QueryDistrictCommand;
import com.navinfo.opentsp.dongfeng.monitor.service.car.IQueryDistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 所属销售区域弹窗
 *
 * @wenya
 * @create 2017-03-24 17:27
 **/
@Component
public class QueryDistrictHandler extends ValidateTokenAndReSetAbstracHandler<QueryDistrictCommand, HttpCommandResultWithData> {
    @Autowired
    private IQueryDistrictService districtService;
    public QueryDistrictHandler(){
        super(QueryDistrictCommand.class,HttpCommandResultWithData.class);
    }
    public QueryDistrictHandler(Class<QueryDistrictCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Override
    protected HttpCommandResultWithData businessHandle(QueryDistrictCommand command) {
        HttpCommandResultWithData result = districtService.queryDistrict(command);
        return result;
    }
}
