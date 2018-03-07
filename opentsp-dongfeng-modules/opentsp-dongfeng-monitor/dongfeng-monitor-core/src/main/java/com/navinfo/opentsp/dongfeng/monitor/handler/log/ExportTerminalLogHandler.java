package com.navinfo.opentsp.dongfeng.monitor.handler.log;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.PagingInfo;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.common.util.StringUtil;
import com.navinfo.opentsp.dongfeng.monitor.commands.log.ExportTerminalLogCommand;
import com.navinfo.opentsp.dongfeng.monitor.service.log.IQueryTerminalLogService;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

/**
 * @author tushenghong
 * @version 1.0
 * @date 2017-05-16
 * @modify
 * @copyright Navi Tsp
 */
@Component
public class ExportTerminalLogHandler extends ValidateTokenAndReSetAbstracHandler<ExportTerminalLogCommand, HttpCommandResultWithData> {

    protected static final Logger logger = LoggerFactory.getLogger(ExportTerminalLogHandler.class);

    public ExportTerminalLogHandler() {
        super(ExportTerminalLogCommand.class, HttpCommandResultWithData.class);
    }

    protected ExportTerminalLogHandler(Class<ExportTerminalLogCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Value("${report.module.path}")
    private String path;

    @Value("${fservice.url}")
    private String fservice;

    @Value("${download.record.size}")
    private String records;
    @Autowired
    private IQueryTerminalLogService queryTerminalLogService;

    @Override
    protected HttpCommandResultWithData businessHandle(ExportTerminalLogCommand command) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        PagingInfo pagingInfo = queryTerminalLogService.queryLogOfTerminal(command, false);
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
            queryTerminalLogService.asyncDownload(fservice, pagingInfo.getList(), command, path);
            result.setMessage(MessageFormat.format(ReturnCode.EXPORT_ASYNC_SUCCESS.message(), number, email));
        } else {
            JSONObject uploadResult = queryTerminalLogService.download(fservice, pagingInfo.getList(), command, path);
            result.setData(uploadResult.getJSONObject("data").getString("fullPath"));

        }
        return result;
    }
}