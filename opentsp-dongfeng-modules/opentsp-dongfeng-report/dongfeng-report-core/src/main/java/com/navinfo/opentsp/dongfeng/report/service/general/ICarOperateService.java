package com.navinfo.opentsp.dongfeng.report.service.general;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.report.commands.general.QueryCarOperateCommand;

/**
 * 运营车况查看
 * @Author liusanhu@aerozhonghuan.com
 * @Date 2017/3/27
 */
public interface ICarOperateService {
    /**
     * 运营车况查看
     *
     * @param command
     * @return
     */
    public HttpCommandResultWithData queryCarOperate(QueryCarOperateCommand command);
}
