package com.navinfo.opentsp.dongfeng.monitor.handler.instruct;
import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.monitor.commands.instruct.QueryLockMenuCommand;
import com.navinfo.opentsp.dongfeng.monitor.service.instruct.IQueryLockMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 锁车方案下拉菜单
 *
 * @wenya
 * @create 2017-03-23 16:22
 **/
@Component
public class QueryLockMenuHandler extends ValidateTokenAndReSetAbstracHandler<QueryLockMenuCommand,HttpCommandResultWithData> {
    @Autowired
    private IQueryLockMenuService queryLockMenuService;
    public QueryLockMenuHandler(){
        super(QueryLockMenuCommand.class,HttpCommandResultWithData.class);
    }
    public QueryLockMenuHandler(Class<QueryLockMenuCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Override
    protected HttpCommandResultWithData businessHandle(QueryLockMenuCommand command) {
        logger.info(" ===== businessHandle start==========");
        logger.info(command.toString());
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        try {
            result = queryLockMenuService.queryLockMenu(command);
        }catch (Exception e){
            result.fillResult(ReturnCode.SERVER_ERROR);
            logger.error(e.getMessage(), e);
        }
        logger.info(" ===== businessHandle end==========");
        return result;
    }
}
