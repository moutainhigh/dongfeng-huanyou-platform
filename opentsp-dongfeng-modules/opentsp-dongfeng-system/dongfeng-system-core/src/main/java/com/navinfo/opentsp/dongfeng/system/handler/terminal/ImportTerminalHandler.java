package com.navinfo.opentsp.dongfeng.system.handler.terminal;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.common.util.ExcelUtil;
import com.navinfo.opentsp.dongfeng.common.util.ReportConfig;
import com.navinfo.opentsp.dongfeng.common.util.StringUtil;
import com.navinfo.opentsp.dongfeng.system.commands.terminal.ImportTerminalCommand;
import com.navinfo.opentsp.dongfeng.system.pojo.TerminalTemplePojo;
import com.navinfo.opentsp.dongfeng.system.service.ITerminalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.List;

/**
 * 终端导入
 *
 * @author tushenghong
 * @version 1.0
 * @date 2017-04-07
 * @modify
 * @copyright Navi Tsp
 */
@Component
public class ImportTerminalHandler extends ValidateTokenAndReSetAbstracHandler<ImportTerminalCommand, HttpCommandResultWithData> {

    protected static final Logger logger = LoggerFactory.getLogger(ImportTerminalHandler.class);

    public ImportTerminalHandler() {
        super(ImportTerminalCommand.class, HttpCommandResultWithData.class);
    }

    protected ImportTerminalHandler(Class<ImportTerminalCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Autowired
    private ITerminalService terminalService;
    @Value("${import.terminal.record.size}")
    private int record;

    @Override
    protected HttpCommandResultWithData businessHandle(ImportTerminalCommand command) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        try {
            ReportConfig config = new ReportConfig("终端导入");
            config.setColumn("id", "序号", 0);
            config.setColumn("model", "终端型号", 1);
            config.setColumn("terminalId", "终端ID号", 2);
            config.setColumn("simNo", "SIM卡号", 3);
            config.setColumn("labelId", "终端标签ID号", 4);
            config.setColumn("type", "终端类型", 5);
            config.setColumn("protocol", "终端协议", 6);
            config.setColumn("dealerCode", "所属经销商编码", 7);
            config.setSourcePath(command.getFilePath());

            List<Object> datas = null;
            try {
                Thread.sleep(2000);
                datas = ExcelUtil.readExcel(config, 0, 0, TerminalTemplePojo.class);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
            if (StringUtil.isEmpty(datas)) {
                result.fillResult(ReturnCode.IMPORT_DATA_IS_NULL);
                return result;
            }
            if (datas.size() > record) {
                final String email = StringUtil.isEmpty(command.getUserInfor().getEmail()) ? command.getEmail() : command.getUserInfor().getEmail();
                if (StringUtil.isEmpty(email)) {
                    result.setResultCode(ReturnCode.IMPORT_EMAIL_ISNOT_EMPTY.code());
                    result.setMessage(MessageFormat.format(ReturnCode.IMPORT_EMAIL_ISNOT_EMPTY.message(), record));
                    return result;
                }
                command.setEmail(email);
                terminalService.asyncImportTerminal(datas, command);
                result.setResultCode(ReturnCode.IMPORT_ASYNC_SUCCESS.code());
                result.setMessage(MessageFormat.format(ReturnCode.IMPORT_ASYNC_SUCCESS.message(), record, email));
            } else {
                result = terminalService.importTerminal(datas, command);
            }
        } catch (Exception e) {
            result.fillResult(ReturnCode.IMPORT_TERMINAL_FAILED);
            logger.error("===== import Terminal failed", e);
        }
        return result;
    }
}
