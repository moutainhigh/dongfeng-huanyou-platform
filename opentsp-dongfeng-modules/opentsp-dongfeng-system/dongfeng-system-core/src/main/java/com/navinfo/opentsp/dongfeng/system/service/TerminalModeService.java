package com.navinfo.opentsp.dongfeng.system.service;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.system.commands.termodel.*;


public interface TerminalModeService {
    //新增终端型号
    public HttpCommandResultWithData addTerminaModel(AddTerminaModelCommand command) throws Exception;

    /**
     * 删除终端型号
     *
     * @param command
     * @return
     */
    public HttpCommandResultWithData deleteTerminaModel(DeleteTerminalModelCommand command) throws Exception;

    /**
     * 修改端型号数据
     *
     * @param command
     * @return
     */
    public HttpCommandResultWithData updateTerminaModel(UpdateTerminalModelCommand command) throws Exception;

    /**
     * 查询修改端型号数据
     *
     * @param command
     * @return
     */
    public HttpCommandResultWithData queryUpdateData(QueryUpTerminalModelCommand command);

    /**
     * 查询（定位模式，通信模式，适用车型）type 数据
     *
     * @param command
     * @return
     */
    public HttpCommandResultWithData queryTerminaModel(QueryTerminalModelCommand command);

    /**
     * 查询终端型号分页
     *
     * @param command
     * @return
     */
    public HttpCommandResultWithData listTerminalModelPage(ListTerminalModelCommand command);
}
