package com.navinfo.opentsp.dongfeng.system.handler.business;

import com.navinfo.opentsp.dongfeng.common.constant.OperateLogConstants;
import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.CommonResult;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.common.service.IOperateLogService;
import com.navinfo.opentsp.dongfeng.system.commands.business.DeleteBusinessCommand;
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
public class DeleteBusinessHandler extends ValidateTokenAndReSetAbstracHandler<DeleteBusinessCommand, CommonResult> {

    protected static final Logger logger = LoggerFactory.getLogger(DeleteBusinessHandler.class);

    @Autowired
    private IBusinessService businessService;
    @Resource
    private IOperateLogService operateLogService;

    public DeleteBusinessHandler() {
        super(DeleteBusinessCommand.class, CommonResult.class);
    }

    protected DeleteBusinessHandler(Class<DeleteBusinessCommand> commandType, Class<CommonResult> resultType) {
        super(commandType, resultType);
    }

    @Override
    protected CommonResult businessHandle(DeleteBusinessCommand command) {
        CommonResult result = new CommonResult();
        try {
            result = businessService.deleteBusiness(command);
        } catch(Exception e) {
            result.fillResult(ReturnCode.SERVER_ERROR);
            logger.error(DeleteBusinessHandler.class.getSimpleName() + " Exception:" , e);
        }
        operateLogService.addOperateLog(command, OperateLogConstants.OperateTypeEnum.DELETE, OperateLogConstants.OperateContentPrefixEnum.BUSINESS.getValue() + command.getBusinessCode(), result);
        return result;
    }
}