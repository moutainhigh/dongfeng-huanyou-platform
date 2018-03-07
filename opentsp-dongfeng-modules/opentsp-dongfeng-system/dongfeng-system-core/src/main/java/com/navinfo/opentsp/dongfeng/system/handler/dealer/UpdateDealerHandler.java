package com.navinfo.opentsp.dongfeng.system.handler.dealer;

import com.navinfo.opentsp.dongfeng.common.exception.BaseRuntimeException;
import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.system.commands.dealer.UpdateDealerCommand;
import com.navinfo.opentsp.dongfeng.system.service.DealerService;
import com.navinfo.opentsp.dongfeng.system.service.ISyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by Rex on 2017/3/13.
 */
@Component
public class UpdateDealerHandler extends ValidateTokenAndReSetAbstracHandler<UpdateDealerCommand, HttpCommandResultWithData> {
    @Resource
    private DealerService dealerService;
    @Autowired
    private ISyncService syncService;
    @Value("${cloud.server.sync.stationTeam.switch}")
    private String syncStationTeamSwitch;

    protected UpdateDealerHandler() {
        super(UpdateDealerCommand.class, HttpCommandResultWithData.class);
    }

    @Override
    protected HttpCommandResultWithData businessHandle(UpdateDealerCommand command) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        try {
            result = dealerService.updateDealer(command);
            if(Boolean.valueOf(syncStationTeamSwitch)){
                //更新成功，同步到云端
                if (result.getResultCode() == ReturnCode.OK.code()) {
                    String stationId = String.valueOf(command.getDealerIndto().getId());
                    String province = String.valueOf(command.getDealerIndto().getGovCodePrvc());
                    String city = String.valueOf(command.getDealerIndto().getGovCodeCity());
                    int operateType = 5;
                    syncService.asyncDealer(stationId, province, city, operateType);
                }
            }
        } catch (Exception e) {
            if(e instanceof BaseRuntimeException) {
                result.setResultCode(ReturnCode.SECTEAM_SYN_FAIL.code());
                result.setMessage(e.getMessage());
            }else{
                result.fillResult(ReturnCode.SERVER_ERROR);
            }
            logger.error(this.getClass().getName() + "`s businessHandle has error : ", e);

        }
        return result;
    }
}
