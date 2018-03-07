package com.navinfo.opentsp.dongfeng.system.handler.station;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.PagingInfo;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.common.util.StringUtil;
import com.navinfo.opentsp.dongfeng.system.commands.station.ExportStationCommand;
import com.navinfo.opentsp.dongfeng.system.service.IStationService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

/**
 * @author tushenghong
 * @version 1.0
 * @date 2017-07-24
 * @modify
 * @copyright Navi Tsp
 */
@Component
public class ExportStationHandler extends ValidateTokenAndReSetAbstracHandler<ExportStationCommand, HttpCommandResultWithData> {
    public ExportStationHandler(Class<ExportStationCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    public ExportStationHandler() {
        super(ExportStationCommand.class, HttpCommandResultWithData.class);
    }

    @Value("${report.module.path}")
    private String path;

    @Value("${fservice.url}")
    private String fservice;

    @Value("${download.carrecord.size}")
    private String records;

    @Autowired
    private IStationService stationService;

    @Override
    protected HttpCommandResultWithData businessHandle(ExportStationCommand command) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        try {
            PagingInfo pagingInfo = stationService.queryStation(command, false);
            if (StringUtil.isEmpty(pagingInfo) || StringUtil.isEmpty(pagingInfo.getList())) {
                result.setMessage("没有可导出的数据");
                return result;
            }
            int number = Integer.valueOf(records);
            if (pagingInfo.getList().size() >= number) {
                final String email = StringUtil.isEmpty(command.getUserInfor().getEmail()) ? command.getEmail() : command.getUserInfor().getEmail();
                if (StringUtil.isEmpty(email)) {
                    result.setResultCode(ReturnCode.EXPORT_EMAIL_ISNOT_EMPTY.code());
                    result.setMessage(MessageFormat.format(ReturnCode.EXPORT_EMAIL_ISNOT_EMPTY.message(), number));
                    return result;
                }
                command.setEmail(email);
                // path表示模板在文件服务器的位置.
                stationService.asyncDownload(fservice, pagingInfo.getList(), command, path);
                result.setMessage(MessageFormat.format(ReturnCode.EXPORT_ASYNC_SUCCESS.message(), number, email));
            } else {
                JSONObject uploadResult = stationService.download(fservice, pagingInfo.getList(), command, path);
                result.setData(uploadResult.getJSONObject("data").getString("fullPath"));
            }
            return result;
        } catch (Exception e) {
            logger.error("export station failed,", e);
            result.fillResult(ReturnCode.EXPORT_STATION_FAILED);
        }
        return result;

    }
}
