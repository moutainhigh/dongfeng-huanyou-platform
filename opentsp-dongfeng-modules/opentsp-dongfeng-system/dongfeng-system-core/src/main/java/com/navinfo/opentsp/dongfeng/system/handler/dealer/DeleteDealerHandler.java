package com.navinfo.opentsp.dongfeng.system.handler.dealer;

import com.navinfo.opentsp.dongfeng.common.constant.OperateLogConstants;
import com.navinfo.opentsp.dongfeng.common.exception.BaseRuntimeException;
import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.common.service.IOperateLogService;
import com.navinfo.opentsp.dongfeng.system.commands.dealer.DeleteDealerCommand;
import com.navinfo.opentsp.dongfeng.system.service.DealerService;
import com.navinfo.opentsp.dongfeng.system.service.ISyncService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 删除经销商handler
 *
 * @Author zhaoming@mapbar.com
 * @Date 2017/3/10 10:37
 **/
@Component
public class DeleteDealerHandler extends ValidateTokenAndReSetAbstracHandler<DeleteDealerCommand, HttpCommandResultWithData> {
    //日志类
    protected static final Logger logger = LoggerFactory.getLogger(DeleteDealerHandler.class);

    @Resource
    private DealerService dealerService;

    @Resource
    private IOperateLogService operateLogService;
    @Autowired
    private ISyncService syncService;
    @Value("${cloud.server.sync.stationTeam.switch}")
    private String syncStationTeamSwitch;
    public DeleteDealerHandler() {
        super(DeleteDealerCommand.class, HttpCommandResultWithData.class);
    }

    @Override
    protected HttpCommandResultWithData businessHandle(DeleteDealerCommand command) {
        logger.info(" ===== DeleteDealerHandler start==========");
        logger.info(command.toString());
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        try {
            result = dealerService.deleteDealer(command.getTid());
        } catch (Exception e) {
            if(e instanceof BaseRuntimeException) {
                result.setResultCode(ReturnCode.SECTEAM_SYN_FAIL.code());
                result.setMessage(e.getMessage());
            }else{
                result.fillResult(ReturnCode.SERVER_ERROR);
            }
        }
        operateLogService.addOperateLog(command, OperateLogConstants.OperateTypeEnum.DELETE, OperateLogConstants.OperateContentPrefixEnum.DEALER.getValue() + command.getDealerCode(), result);
        logger.info(" ===== DeleteDealerHandler end==========");
        return result;
    }
}
