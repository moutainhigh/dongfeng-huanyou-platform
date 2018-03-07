package com.navinfo.opentsp.dongfeng.system.handler.dealer;

import com.navinfo.opentsp.dongfeng.common.exception.BaseRuntimeException;
import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.CommonResult;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.system.commands.dealer.SynDealerToTbossCommand;
import com.navinfo.opentsp.dongfeng.system.service.DealerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author fengwm
 * @version 1.0
 * @date 2017-09-07
 * @modify
 * @copyright Navi Tsp
 */

@Component
public class SynDealerToTbossHandler extends ValidateTokenAndReSetAbstracHandler<SynDealerToTbossCommand, CommonResult> {

    protected static final Logger logger = LoggerFactory.getLogger(SynDealerToTbossHandler.class);
    @Resource
    private DealerService dealerService;

    public SynDealerToTbossHandler() {
        super(SynDealerToTbossCommand.class, CommonResult.class);
    }

    protected SynDealerToTbossHandler(Class<SynDealerToTbossCommand> commandType, Class<CommonResult> resultType) {
        super(commandType, resultType);
    }

    @Override
    protected CommonResult businessHandle(SynDealerToTbossCommand command) {
        CommonResult result = new CommonResult();
        try {
            result = dealerService.synDealerToTboss(command);
        } catch (Exception e) {
            if(e instanceof BaseRuntimeException) {
                result.setResultCode(ReturnCode.SECTEAM_SYN_FAIL.code());
                result.setMessage(e.getMessage());
            }else{
                result.fillResult(ReturnCode.SERVER_ERROR);
            }
        }
        return result;
    }
}