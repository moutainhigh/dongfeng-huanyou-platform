package com.navinfo.opentsp.dongfeng.report.commands.market;

import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.validation.group.GroupCommand;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * ${DESCRIPTION}
 *
 * @author wt
 * @version 1.0
 * @date 2017-06-07
 * @modify
 * @copyright Navi Tsp
 */
public class ImportScanCodeCommand extends BaseCommand<HttpCommandResultWithData> {

    @NotNull(message = "文件路径不能为空", groups = {GroupCommand.class})
    @NotBlank(message = "文件路径不能为空", groups = {GroupCommand.class})
    private String filePath;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String toString() {
        return "ImportScanCodeCommand{" +
                "filePath='" + filePath + '\'' +
                '}';
    }

    @Override
    public Class<? extends HttpCommandResultWithData> getResultType() {
        return HttpCommandResultWithData.class;
    }
}