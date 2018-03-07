package com.navinfo.opentsp.dongfeng.report.service.product;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.report.commands.product.QueryLSCommand;

/**
 * Created by wenya on 2017/3/28.
 */
public interface IQueryLSService {
    /**
     * 生产线漏扫
     *
     * @param command
     * @return
     */
    public HttpCommandResultWithData queryLs(QueryLSCommand command,boolean isExport);
}
