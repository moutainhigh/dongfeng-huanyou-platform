package com.navinfo.opentsp.dongfeng.system.handler.business;

import com.navinfo.opentsp.dongfeng.common.constant.OperateLogConstants;
import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.common.service.IOperateLogService;
import com.navinfo.opentsp.dongfeng.system.commands.business.AddBusinessCommand;
import com.navinfo.opentsp.dongfeng.system.service.IBusinessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author fengwm
 * @version 1.0
 * @date 2017-03-23
 * @modify
 * @copyright Navi Tsp
 */

@Component
public class AddBusinessHandler extends ValidateTokenAndReSetAbstracHandler<AddBusinessCommand, HttpCommandResultWithData> {

    protected static final Logger logger = LoggerFactory.getLogger(AddBusinessHandler.class);

    @Autowired
    private IBusinessService businessService;
    @Resource
    private IOperateLogService operateLogService;

    public AddBusinessHandler() {
        super(AddBusinessCommand.class, HttpCommandResultWithData.class);
    }

    protected AddBusinessHandler(Class<AddBusinessCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }
    @Override
    protected HttpCommandResultWithData businessHandle(AddBusinessCommand command) {
    	HttpCommandResultWithData result = new HttpCommandResultWithData();
        try {
            result = businessService.addBusiness(command);
        } catch(Exception e) {
            result.fillResult(ReturnCode.SERVER_ERROR);
            logger.error(DeleteBusinessHandler.class.getSimpleName() + " Exception:" , e);
        }
        operateLogService.addOperateLog(command, OperateLogConstants.OperateTypeEnum.ADD, OperateLogConstants.OperateContentPrefixEnum.BUSINESS.getValue() + command.getBusinessCode(), result);
        return result;
    }
}