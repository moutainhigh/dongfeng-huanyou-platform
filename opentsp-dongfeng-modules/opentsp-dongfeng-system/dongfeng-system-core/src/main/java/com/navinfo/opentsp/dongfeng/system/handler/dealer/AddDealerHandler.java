package com.navinfo.opentsp.dongfeng.system.handler.dealer;

import com.navinfo.opentsp.dongfeng.common.exception.BaseRuntimeException;
import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.system.commands.dealer.AddDealerCommand;
import com.navinfo.opentsp.dongfeng.system.service.DealerService;
import com.navinfo.opentsp.dongfeng.system.service.ISyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 增加经销商handler
 * Created by Rex on 2017/3/13.
 */
@Component
public class AddDealerHandler extends ValidateTokenAndReSetAbstracHandler<AddDealerCommand, HttpCommandResultWithData> {

    @Resource
    private DealerService dealerService;
    @Autowired
    private ISyncService syncService;
    @Value("${cloud.server.sync.stationTeam.switch}")
    private String syncStationTeamSwitch;
    protected AddDealerHandler() {
        super(AddDealerCommand.class, HttpCommandResultWithData.class);
    }

    @Override
    protected HttpCommandResultWithData businessHandle(AddDealerCommand command) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        try {
            result = dealerService.addDealer(command);
            if(Boolean.valueOf(syncStationTeamSwitch)){
                //新增成功，同步到云端
                if (result.getResultCode() == ReturnCode.OK.code()) {
                    String tId = (String) result.getData();
                    String province = String.valueOf(command.getDealerBean().getGovCodePrvc());
                    String city = String.valueOf(command.getDealerBean().getGovCodeCity());
                    int operateType = 1;
                    syncService.asyncDealer(tId, province, city, operateType);
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
