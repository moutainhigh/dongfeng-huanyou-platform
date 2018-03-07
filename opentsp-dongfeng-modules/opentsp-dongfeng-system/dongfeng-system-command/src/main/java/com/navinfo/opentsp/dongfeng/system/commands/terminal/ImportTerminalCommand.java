package com.navinfo.opentsp.dongfeng.system.commands.terminal;

import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.validation.group.GroupCommand;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * @author tushenghong
 * @version 1.0
 * @date 2017-04-07
 * @modify
 * @copyright Navi Tsp
 */
public class ImportTerminalCommand extends BaseCommand<HttpCommandResultWithData> {
    @NotNull(message = "文件路径不能为空", groups = {GroupCommand.class})
    @NotBlank(message = "文件路径不能为空", groups = {GroupCommand.class})
    private String filePath;

    private String email;//接收导入结果的邮件

    private String importTime;//导入时间

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImportTime() {
        return importTime;
    }

    public void setImportTime(String importTime) {
        this.importTime = importTime;
    }

    @Override
    public Class<? extends HttpCommandResultWithData> getResultType() {
        return HttpCommandResultWithData.class;
    }
}