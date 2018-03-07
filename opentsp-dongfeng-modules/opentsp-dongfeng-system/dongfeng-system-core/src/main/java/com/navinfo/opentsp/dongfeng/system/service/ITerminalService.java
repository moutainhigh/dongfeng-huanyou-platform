package com.navinfo.opentsp.dongfeng.system.service;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.PagingInfo;
import com.navinfo.opentsp.dongfeng.system.commands.terminal.*;
import net.sf.json.JSONObject;

import java.util.List;

/**
 * 终端管理服务
 *
 * @author tushenghong
 * @version 1.0
 * @date 2017-03-10
 * @modify
 * @copyright Navi Tsp
 */
public interface ITerminalService {
    /**
     * 新增终端
     *
     * @param command
     * @param source  来源
     * @return
     */
    HttpCommandResultWithData addTerminal(AddTerminalCommand command, int source);

    /**
     * 更新终端
     *
     * @param command
     * @return
     */
    HttpCommandResultWithData updateTerminal(UpdateTerminalCommand command);

    /**
     * 查询终端详细
     *
     * @param command
     * @return
     */
    HttpCommandResultWithData detailQuery(QueryTerminalDetailCommand command);

    /**
     * 查询终端完整信息，包括终端绑定车辆，经销商，客户信息
     *
     * @param command
     * @return
     */
    HttpCommandResultWithData queryCompleteInfo(QueryTerminalCompleteInfoCommand command);

    /**
     * 批量查询
     *
     * @param command
     * @return
     */
    PagingInfo batchQuery(SearchTerminalCommand command, boolean isPaging);

    /**
     * 删除终端
     *
     * @param command
     * @return
     */
    HttpCommandResultWithData delete(DeleteTerminalCommand command) throws Exception;

    /**
     * 终端设置
     *
     * @param command
     * @return
     */
    HttpCommandResultWithData setting(SettingTerminalCommand command) throws Exception;


    /**
     * 查询终端所支持的协议
     *
     * @param command
     * @return
     */
    HttpCommandResultWithData queryProtocolOfTerminal(TerminalProtocolCommand command);

    /**
     * 查询终端设置目录
     *
     * @param command
     * @return
     */
    HttpCommandResultWithData queryTerminalSettingParam(QueryTerminalSettingParamCommand command);

    /**
     * 异步导出终端
     *
     * @param fservice
     * @param command
     * @param sourcePath
     */
    void asyncDownload(String fservice, List<? extends Object> list, ExportTerminalCommand command, String sourcePath);

    /**
     * 同步导出终端
     *
     * @param fservice
     * @param list
     * @param command
     * @param sourcePath
     * @return
     */
    JSONObject download(String fservice, List<? extends Object> list, ExportTerminalCommand command, String sourcePath);

    /**
     * 异步导入
     *
     * @param datas
     * @param command
     */
    void asyncImportTerminal(List<Object> datas, ImportTerminalCommand command);

    /**
     * 同步导入
     *
     * @param datas
     * @param command
     * @return
     */
    HttpCommandResultWithData importTerminal(List<Object> datas, ImportTerminalCommand command);

    /**
     * 查询CAN 总线数据
     *
     * @param pids pid多个用逗号隔开
     * @return
     */
    HttpCommandResultWithData queryCanBusData(String pids);


}
