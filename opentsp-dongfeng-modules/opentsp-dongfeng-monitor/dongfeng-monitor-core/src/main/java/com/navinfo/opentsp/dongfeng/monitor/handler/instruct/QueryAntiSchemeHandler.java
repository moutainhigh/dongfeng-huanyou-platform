package com.navinfo.opentsp.dongfeng.monitor.handler.instruct;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.monitor.commands.instruct.QueryAntiSchemeCommand;
import com.navinfo.opentsp.dongfeng.monitor.service.instruct.IQueryAntiSchemeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 获取防拆方案下拉菜单
 *
 * @wenya
 * @create 2017-03-23 15:19
 **/
@Component
public class QueryAntiSchemeHandler extends ValidateTokenAndReSetAbstracHandler<QueryAntiSchemeCommand ,HttpCommandResultWithData> {
    @Autowired
    private IQueryAntiSchemeService queryAntiScheme;
    protected static final Logger logger = LoggerFactory.getLogger(QueryAntiSchemeHandler.class);

    public QueryAntiSchemeHandler() {
        super(QueryAntiSchemeCommand.class,HttpCommandResultWithData.class);
    }

    public QueryAntiSchemeHandler(Class<QueryAntiSchemeCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Override
    protected HttpCommandResultWithData businessHandle(QueryAntiSchemeCommand command) {
        logger.info(" ===== businessHandle start==========");
        logger.info(command.toString());
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        try {
            result = queryAntiScheme.queryAntiScheme(command);
        }catch (Exception e){
            result.fillResult(ReturnCode.SERVER_ERROR);
            logger.error(e.getMessage(), e);
        }
        logger.info(" ===== businessHandle end==========");
        return result;
    }
}
