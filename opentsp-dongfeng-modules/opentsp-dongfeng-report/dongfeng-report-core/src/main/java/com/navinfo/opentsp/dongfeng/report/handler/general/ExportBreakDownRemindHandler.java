package com.navinfo.opentsp.dongfeng.report.handler.general;

import com.navinfo.opentsp.dongfeng.common.constant.ReportConfigConstants;
import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.PagingInfo;
import com.navinfo.opentsp.dongfeng.common.service.IReportService;
import com.navinfo.opentsp.dongfeng.report.commands.general.ExportBreakDownRemindCommand;
import com.navinfo.opentsp.dongfeng.report.constant.PropertyConfig;
import com.navinfo.opentsp.dongfeng.report.converter.general.BreakDownRemindDto;
import com.navinfo.opentsp.dongfeng.report.service.general.IBreakDownRemindService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("rawtypes")
@Component
public class ExportBreakDownRemindHandler extends ValidateTokenAndReSetAbstracHandler<ExportBreakDownRemindCommand, HttpCommandResultWithData> {

	protected static final Logger logger = LoggerFactory.getLogger(ExportBreakDownRemindHandler.class);

	@Autowired
	private IBreakDownRemindService breakDownRemindService;

	@Autowired
	private IReportService reportService;

	@Autowired
	private PropertyConfig reportProperty;

	public ExportBreakDownRemindHandler() {
		super(ExportBreakDownRemindCommand.class, HttpCommandResultWithData.class);
	}

	protected ExportBreakDownRemindHandler(Class<ExportBreakDownRemindCommand> commandType,
			Class<HttpCommandResultWithData> resultType) {
		super(commandType, resultType);
	}

	@Override
	protected HttpCommandResultWithData businessHandle(ExportBreakDownRemindCommand command) {
		List<BreakDownRemindDto> nsoodleReportPojos = new ArrayList<BreakDownRemindDto>();
		switch (command.getDownloadFlag()) {
		case ReportConfigConstants.EXPORT_CURRENT_FLAG: {
			PagingInfo<BreakDownRemindDto> datas = getPageList(command, false);
			nsoodleReportPojos = datas.getList();
			break;
		}
		case ReportConfigConstants.EXPORT_ALL_FLAG: {
			PagingInfo<BreakDownRemindDto> datas = getPageList(command, true);
			nsoodleReportPojos = datas.getList();
			break;
		}
		default:
		}

		return reportService.downLoad(nsoodleReportPojos, command, ReportConfigConstants.breakdown, reportProperty);
	}

	/**
	 * 获取当前或者全部结果集
	 * 
	 * @param command
	 * @param isExport
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private PagingInfo<BreakDownRemindDto> getPageList(ExportBreakDownRemindCommand command, boolean isExport) {
		HttpCommandResultWithData queryResult = breakDownRemindService.queryBreakDownRemind(command, isExport);
		if (queryResult == null) {
			queryResult = new HttpCommandResultWithData();
			PagingInfo dataList = new PagingInfo();
			dataList.setList(Collections.emptyList());
			dataList.setTotal(0L);
			queryResult.setData(dataList);
		}
		PagingInfo<BreakDownRemindDto> info = (PagingInfo<BreakDownRemindDto>) queryResult.getData();
		if (info == null) {
			info = new PagingInfo();
			info.setList(new ArrayList<BreakDownRemindDto>());
			info.setTotal(0L);
			queryResult.setData(info);
		}
		return info;
	}
}
