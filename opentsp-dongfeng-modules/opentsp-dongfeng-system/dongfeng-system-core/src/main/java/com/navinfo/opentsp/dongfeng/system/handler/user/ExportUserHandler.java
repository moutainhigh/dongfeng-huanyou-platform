package com.navinfo.opentsp.dongfeng.system.handler.user;

import com.navinfo.opentsp.dongfeng.common.constant.ReportConfigConstants;
import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.PagingInfo;
import com.navinfo.opentsp.dongfeng.common.service.IReportService;
import com.navinfo.opentsp.dongfeng.system.commands.dto.outDto.UserListOutdto;
import com.navinfo.opentsp.dongfeng.system.commands.user.ExportUserCommand;
import com.navinfo.opentsp.dongfeng.system.constant.PropertyConfig;
import com.navinfo.opentsp.dongfeng.system.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * ${DESCRIPTION}
 *
 * @author wt
 * @version 1.0
 * @date 2017-07-24
 * @modify
 * @copyright Navi Tsp
 */
@Component
public class ExportUserHandler extends ValidateTokenAndReSetAbstracHandler<ExportUserCommand, HttpCommandResultWithData> {

    @Autowired
    private IUserService userService;

    @Autowired
    private IReportService reportService;

    @Autowired
    private PropertyConfig reportProperty;

    public ExportUserHandler() {
        super(ExportUserCommand.class, HttpCommandResultWithData.class);
    }

    protected ExportUserHandler(Class<ExportUserCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Override
    protected HttpCommandResultWithData businessHandle(ExportUserCommand command) {
        boolean isQueryAll = (command.getDownloadFlag() == ReportConfigConstants.EXPORT_ALL_FLAG);
        PagingInfo<UserListOutdto> datas = (PagingInfo<UserListOutdto>) userService.queryUserList(command, isQueryAll).getData();
        return reportService.downLoad(datas.getList(), command, ReportConfigConstants.usersConfig, reportProperty);
    }
}
