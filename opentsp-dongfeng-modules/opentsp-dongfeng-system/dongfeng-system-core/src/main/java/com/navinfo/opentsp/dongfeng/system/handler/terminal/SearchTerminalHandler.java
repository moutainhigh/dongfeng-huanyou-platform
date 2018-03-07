package com.navinfo.opentsp.dongfeng.system.handler.terminal;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.PagingInfo;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.common.service.IOperateLogService;
import com.navinfo.opentsp.dongfeng.system.commands.terminal.SearchTerminalCommand;
import com.navinfo.opentsp.dongfeng.system.service.ITerminalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author tushenghong
 * @version 1.0
 * @date 2017-03-08
 * @modify
 * @copyright Navi Tsp
 */
@Component
public class SearchTerminalHandler extends ValidateTokenAndReSetAbstracHandler<SearchTerminalCommand, HttpCommandResultWithData> {

    protected static final Logger logger = LoggerFactory.getLogger(SearchTerminalHandler.class);

    public SearchTerminalHandler() {
        super(SearchTerminalCommand.class, HttpCommandResultWithData.class);
    }

    protected SearchTerminalHandler(Class<SearchTerminalCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Autowired
    private ITerminalService terminalService;
    @Resource
    private IOperateLogService operateLogService;

    /**
     * 前台备注：终端类型搜索不用传-1 ，对应tstyle字段
     * 根据id是否为空判断是查询单个还是批量查询
     * 单个查询逻辑：
     * 1.判断用户是否有该终端所在分组的权限
     * 2.判断用户是否有该终端所绑定的车辆的权限
     * 3.根据权限给出相应的提示
     * 批量查询逻辑：
     * 1.查询用户的分组
     * 2.查询用户分组下的所有车辆
     * 3.查询用户分组下的所有终端
     *
     * @param command
     * @return
     */
    @Override
    protected HttpCommandResultWithData businessHandle(SearchTerminalCommand command) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        try {
            PagingInfo pagingInfo = terminalService.batchQuery(command, true);
            result.setData(pagingInfo);
        } catch (Exception e) {
            result.fillResult(ReturnCode.SEARCH_TERMINAL_FAILED);
            logger.error(result.getMessage() + e);
        }
        return result;
    }
}