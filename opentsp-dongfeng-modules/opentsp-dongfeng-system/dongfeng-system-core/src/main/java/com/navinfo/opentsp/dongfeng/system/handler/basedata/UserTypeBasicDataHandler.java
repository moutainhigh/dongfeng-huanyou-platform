package com.navinfo.opentsp.dongfeng.system.handler.basedata;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.service.IBaseDataService;
import com.navinfo.opentsp.dongfeng.system.commands.basedata.BaseDataCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by yaocy on 2017/03/10. 查询基础数据
 */
@Component
public class UserTypeBasicDataHandler extends
    ValidateTokenAndReSetAbstracHandler<BaseDataCommand, HttpCommandResultWithData>
{
    
    protected static final Logger logger = LoggerFactory.getLogger(BaseDataCommand.class);
    
    @Autowired
    private IBaseDataService baseDataService;
    
    public UserTypeBasicDataHandler()
    {
        super(BaseDataCommand.class, HttpCommandResultWithData.class);
    }
    
    protected UserTypeBasicDataHandler(Class<BaseDataCommand> commandType, Class<HttpCommandResultWithData> resultType)
    {
        super(commandType, resultType);
    }
    
    @Override
    protected HttpCommandResultWithData businessHandle(BaseDataCommand command)
    {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        // if (UserTypeConstant.SYSTEM_ADMIN.getCode() == command.getUserInfor().getType())
        // {
        // result.setData(baseDataService.queryList(Integer.parseInt(command.getType())));
        // }
        //
        // if (UserTypeConstant.CAR_FACTORY.getCode() == command.getUserInfor().getType())
        // {
        // result.setData(baseDataService.queryList(Integer.parseInt(command.getType()), 2));
        // }
        //
        // if (UserTypeConstant.BUSINESS.getCode() == command.getUserInfor().getType())
        // {
        // result.setData(baseDataService.queryList(Integer.parseInt(command.getType()), 5));
        // }
        //
        // if (UserTypeConstant.TRANSPORT.getCode() == command.getUserInfor().getType())
        // {
        // result.setData(baseDataService.queryList(Integer.parseInt(command.getType())));
        // }
        //
        // if (UserTypeConstant.SERVICE_STATION.getCode() == command.getUserInfor().getType())
        // {
        // result.setData(baseDataService.queryList(Integer.parseInt(command.getType())));
        // }
        //
        // if (UserTypeConstant.JOB_TYPE.getCode() == command.getUserInfor().getType())
        // {
        // result.setData(baseDataService.queryList(Integer.parseInt(command.getType())));
        // }
        result.setData(baseDataService.queryList(Integer.parseInt(command.getType())));
        return result;
    }
}
