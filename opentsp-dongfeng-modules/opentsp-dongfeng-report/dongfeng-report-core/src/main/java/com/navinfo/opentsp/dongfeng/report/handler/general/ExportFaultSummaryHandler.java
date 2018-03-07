package com.navinfo.opentsp.dongfeng.report.handler.general;

import com.navinfo.opentsp.dongfeng.common.constant.ReportConfigConstants;
import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.PagingInfo;
import com.navinfo.opentsp.dongfeng.common.service.IReportService;
import com.navinfo.opentsp.dongfeng.report.commands.general.ExportFaultSummaryCommand;
import com.navinfo.opentsp.dongfeng.report.constant.PropertyConfig;
import com.navinfo.opentsp.dongfeng.report.dto.general.QueryFaultSummaryDto;
import com.navinfo.opentsp.dongfeng.report.service.general.IFaultSummaryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("rawtypes")
@Component
public class ExportFaultSummaryHandler extends ValidateTokenAndReSetAbstracHandler<ExportFaultSummaryCommand, HttpCommandResultWithData> {

	protected static final Logger logger = LoggerFactory.getLogger(ExportFaultSummaryHandler.class);

	@Autowired
	private IFaultSummaryService faultSummaryService;

	@Autowired
	private IReportService reportService;

	@Autowired
	private PropertyConfig reportProperty;

	public ExportFaultSummaryHandler() {
		super(ExportFaultSummaryCommand.class, HttpCommandResultWithData.class);
	}

	protected ExportFaultSummaryHandler(Class<ExportFaultSummaryCommand> commandType,
			Class<HttpCommandResultWithData> resultType) {
		super(commandType, resultType);
	}

	@Override
	protected HttpCommandResultWithData businessHandle(ExportFaultSummaryCommand command) {
		List<QueryFaultSummaryDto> nsoodleReportPojos = new ArrayList<QueryFaultSummaryDto>();
		switch (command.getDownloadFlag()) {
		case ReportConfigConstants.EXPORT_CURRENT_FLAG: {
			PagingInfo<QueryFaultSummaryDto> datas = getPageList(command, false);
			nsoodleReportPojos = datas.getList();
			break;
		}
		case ReportConfigConstants.EXPORT_ALL_FLAG: {
			PagingInfo<QueryFaultSummaryDto> datas = getPageList(command, true);
			nsoodleReportPojos = datas.getList();
			break;
		}
		default:
		}

		return reportService.downLoad(nsoodleReportPojos, command, ReportConfigConstants.faultSummary, reportProperty);
	}

	/**
	 * 获取当前或者全部结果集
	 * 
	 * @param command
	 * @param isExport
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private PagingInfo<QueryFaultSummaryDto> getPageList(ExportFaultSummaryCommand command, boolean isExport) {
		HttpCommandResultWithData queryResult = faultSummaryService.queryFaultSummary(command, isExport);
		if (queryResult == null) {
			queryResult = new HttpCommandResultWithData();
			PagingInfo dataList = new PagingInfo();
			dataList.setList(Collections.emptyList());
			dataList.setTotal(0L);
			queryResult.setData(dataList);
		}
		PagingInfo<QueryFaultSummaryDto> info = (PagingInfo<QueryFaultSummaryDto>) queryResult.getData();
		if (info == null) {
			info = new PagingInfo();
			info.setList(new ArrayList<QueryFaultSummaryDto>());
			info.setTotal(0L);
			queryResult.setData(info);
		}
		for (QueryFaultSummaryDto dTo : info.getList()) {
			dTo.setbLoction(dTo.getbLoction().replace(",", ";"));
			dTo.seteLoction(dTo.geteLoction().replace(",", ";"));
		}
		return info;
	}
}
