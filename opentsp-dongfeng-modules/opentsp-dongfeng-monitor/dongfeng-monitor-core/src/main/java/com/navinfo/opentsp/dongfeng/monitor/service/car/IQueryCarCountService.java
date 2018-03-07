package com.navinfo.opentsp.dongfeng.monitor.service.car;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.monitor.commands.car.ExportCarCountCommand;
import com.navinfo.opentsp.dongfeng.monitor.commands.car.QueryCarCountCommand;
import net.sf.json.JSONObject;

import java.util.List;

/**
 * Created by wenya on 2017/4/17.
 */
public interface IQueryCarCountService {
    /**
     * 根据所画区域查询车次详细信息
     * @param command
     * @return
     */
    public HttpCommandResultWithData queryCarCount(QueryCarCountCommand command);
    /**
     * 车次导出
     * @param fservice
     * @param command
     * @param sourcePath
     */
    public void asyncDonwload(String fservice , ExportCarCountCommand command , String sourcePath);

    /**
     * 车次导出
     * @param fservice
     * @param list
     * @param command
     * @param sourcePath
     * @return
     */
    public JSONObject download(String fservice , List<? extends Object> list , ExportCarCountCommand command , String sourcePath);
}
