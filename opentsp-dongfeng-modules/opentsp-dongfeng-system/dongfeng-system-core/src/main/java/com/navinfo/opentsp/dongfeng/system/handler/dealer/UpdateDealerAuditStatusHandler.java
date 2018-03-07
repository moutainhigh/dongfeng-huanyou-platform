package com.navinfo.opentsp.dongfeng.system.handler.dealer;

import com.navinfo.opentsp.dongfeng.common.constant.OperateLogConstants;
import com.navinfo.opentsp.dongfeng.common.exception.BaseRuntimeException;
import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.CommonResult;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.common.service.IOperateLogService;
import com.navinfo.opentsp.dongfeng.system.commands.dealer.UpdateDealerAuditStatusCommand;
import com.navinfo.opentsp.dongfeng.system.service.DealerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author fengwm
 * @version 1.0
 * @date 2017-06-06
 * @modify
 * @copyright Navi Tsp
 */

@Component
public class UpdateDealerAuditStatusHandler extends ValidateTokenAndReSetAbstracHandler<UpdateDealerAuditStatusCommand, CommonResult> {

    protected static final Logger logger = LoggerFactory.getLogger(UpdateDealerAuditStatusHandler.class);
    @Resource
    private DealerService dealerService;
    @Resource
    private IOperateLogService operateLogService;
    public UpdateDealerAuditStatusHandler() {
        super(UpdateDealerAuditStatusCommand.class, CommonResult.class);
    }

    protected UpdateDealerAuditStatusHandler(Class<UpdateDealerAuditStatusCommand> commandType, Class<CommonResult> resultType) {
        super(commandType, resultType);
    }

    @Override
    protected CommonResult businessHandle(UpdateDealerAuditStatusCommand command) {
        CommonResult result = new CommonResult();
        try {
            result = dealerService.updateDealerAuditStatus(command);
        } catch(Exception e) {
            if(e instanceof BaseRuntimeException) {
                result.setResultCode(ReturnCode.SECTEAM_SYN_FAIL.code());
                result.setMessage(e.getMessage());
            }else{
                result.fillResult(ReturnCode.SERVER_ERROR);
            }
            logger.error(DeleteDealerHandler.class.getSimpleName() + " Exception:" , e);
        }
        operateLogService.addOperateLog(command, OperateLogConstants.OperateTypeEnum.UPDATE, OperateLogConstants.OperateContentPrefixEnum.DEALER.getValue() + command.getDealerCode(), result);
        return result;
    }
}