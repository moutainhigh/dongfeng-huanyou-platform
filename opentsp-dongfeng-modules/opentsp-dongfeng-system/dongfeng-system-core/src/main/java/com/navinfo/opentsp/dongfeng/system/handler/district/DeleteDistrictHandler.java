package com.navinfo.opentsp.dongfeng.system.handler.district;

import com.navinfo.opentsp.dongfeng.common.constant.OperateLogConstants;
import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.CommonResult;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.common.service.IOperateLogService;
import com.navinfo.opentsp.dongfeng.system.commands.district.DeleteDistrictCommand;
import com.navinfo.opentsp.dongfeng.system.service.DealerService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 销售区域删除Handler
 *
 * @author Sunyu
 */
@Component
public class DeleteDistrictHandler extends ValidateTokenAndReSetAbstracHandler<DeleteDistrictCommand, CommonResult> {

    @Resource
    private DealerService dealerService;

    public DeleteDistrictHandler() {
        super(DeleteDistrictCommand.class, CommonResult.class);
    }

    protected DeleteDistrictHandler(Class<DeleteDistrictCommand> commandType, Class<CommonResult> resultType) {
        super(commandType, resultType);
    }

    @Resource
    private IOperateLogService operateLogService;

    @Override
    protected CommonResult businessHandle(DeleteDistrictCommand command) {
        logger.info(" ===== businessHandle start ========== ");
        logger.info(command.toString());
        CommonResult result = new CommonResult();
        try {
            result = dealerService.deleteDealer(command.getTid());
        } catch (Exception e) {
            result.fillResult(ReturnCode.SERVER_ERROR);
            logger.error(e.getMessage(), e);
        }
        logger.info(" ===== businessHandle end ========== ");
        operateLogService.addOperateLog(command, OperateLogConstants.OperateTypeEnum.DELETE, OperateLogConstants.OperateContentPrefixEnum.DISTRICT.getValue() + command.gettName(), result);

        return result;
    }
}