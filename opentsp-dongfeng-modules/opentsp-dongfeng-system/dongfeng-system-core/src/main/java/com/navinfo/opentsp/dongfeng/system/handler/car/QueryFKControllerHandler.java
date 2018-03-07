package com.navinfo.opentsp.dongfeng.system.handler.car;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.PagingInfo;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.system.commands.car.QueryFKControllerCommand;
import com.navinfo.opentsp.dongfeng.system.commands.dto.inDto.TerminalIndto;
import com.navinfo.opentsp.dongfeng.system.service.IFKControllerService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2017/3/9.
 */
@Component
public class QueryFKControllerHandler extends ValidateTokenAndReSetAbstracHandler<QueryFKControllerCommand, HttpCommandResultWithData> {

    @Resource
    private IFKControllerService fkControllerService;
    public QueryFKControllerHandler() {
        super(QueryFKControllerCommand.class , HttpCommandResultWithData.class);
    }

    protected QueryFKControllerHandler(Class<QueryFKControllerCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Override
    protected HttpCommandResultWithData businessHandle(QueryFKControllerCommand command) {
        logger.info(" ===== QueryKFControllerHandler start==========");

        HttpCommandResultWithData result = new HttpCommandResultWithData();

        if (null == command) {
            result = new HttpCommandResultWithData();
            result.setResultCode(ReturnCode.SERVER_ERROR.code());
            result.setMessage("dealerId must can not  be null .");
            return result;
        }

        try {
            PagingInfo<TerminalIndto> page = fkControllerService.QueryFKController(command);
            result.setResultCode(ReturnCode.OK.code());
            result.setMessage(ReturnCode.OK.message());
            result.setData(page);
        }catch (Exception e){
            result.fillResult(ReturnCode.SERVER_ERROR);
            logger.error(e.getMessage(), e);
        }
        logger.info(" ===== QueryKFControllerHandler end==========");
        return result;
    }

}
