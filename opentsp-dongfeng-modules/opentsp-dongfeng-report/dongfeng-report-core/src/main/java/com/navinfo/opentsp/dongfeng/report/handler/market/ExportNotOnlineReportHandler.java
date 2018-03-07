package com.navinfo.opentsp.dongfeng.report.handler.market;

import com.navinfo.opentsp.dongfeng.common.constant.ReportConfigConstants;
import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.PagingInfo;
import com.navinfo.opentsp.dongfeng.common.service.IReportService;
import com.navinfo.opentsp.dongfeng.report.commands.market.ExportNotOnlineReportCommand;
import com.navinfo.opentsp.dongfeng.report.constant.PropertyConfig;
import com.navinfo.opentsp.dongfeng.report.dto.market.QueryNotOnlineReportDTo;
import com.navinfo.opentsp.dongfeng.report.service.market.INotOnlineReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 车辆在库未上线报表导出
 * 
 * @author xltianc.zh
 *
 */
@SuppressWarnings("rawtypes")
@Component
public class ExportNotOnlineReportHandler
		extends ValidateTokenAndReSetAbstracHandler<ExportNotOnlineReportCommand, HttpCommandResultWithData> {

	protected static final Logger logger = LoggerFactory.getLogger(ExportNotOnlineReportHandler.class);
	@Resource
	private INotOnlineReportService onlineReportService;

	@Autowired
	private IReportService reportService;

	@Autowired
	private PropertyConfig reportProperty;

	public ExportNotOnlineReportHandler() {
		super(ExportNotOnlineReportCommand.class, HttpCommandResultWithData.class);
	}

	protected ExportNotOnlineReportHandler(Class<ExportNotOnlineReportCommand> commandType,
			Class<HttpCommandResultWithData> resultType) {
		super(commandType, resultType);
	}

	@Override
	protected HttpCommandResultWithData businessHandle(ExportNotOnlineReportCommand command) {
		List<QueryNotOnlineReportDTo> QueryWsxReportDTos = new ArrayList<QueryNotOnlineReportDTo>();
		switch (command.getDownloadFlag()) {
		case ReportConfigConstants.EXPORT_CURRENT_FLAG: {
			PagingInfo<QueryNotOnlineReportDTo> datas = getPageList(command, false);
			QueryWsxReportDTos = datas.getList();
			break;
		}
		case ReportConfigConstants.EXPORT_ALL_FLAG: {
			PagingInfo<QueryNotOnlineReportDTo> datas = getPageList(command, true);
			QueryWsxReportDTos = datas.getList();
			break;
		}
		default:
		}

		return reportService.downLoad(QueryWsxReportDTos, command, ReportConfigConstants.wsxReport, reportProperty);
	}

	/**
	 * 获取当前或者全部结果集
	 * 
	 * @param command
	 * @param isExport
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private PagingInfo<QueryNotOnlineReportDTo> getPageList(ExportNotOnlineReportCommand command, boolean isExport) {
		HttpCommandResultWithData queryResult = onlineReportService.queryReportData(command, isExport);
		if (queryResult == null) {
			queryResult = new HttpCommandResultWithData();
			PagingInfo dataList = new PagingInfo();
			dataList.setList(Collections.emptyList());
			dataList.setTotal(0L);
			queryResult.setData(dataList);
		}
		PagingInfo<QueryNotOnlineReportDTo> info = (PagingInfo<QueryNotOnlineReportDTo>) queryResult.getData();
		if (info == null) {
			info = new PagingInfo();
			info.setList(new ArrayList<QueryNotOnlineReportDTo>());
			info.setTotal(0L);
			queryResult.setData(info);
		}
		for (QueryNotOnlineReportDTo dTo : info.getList()) {
			dTo.setLast_location(dTo.getLast_location().replace(",", ";"));
		}
		return info;
	}
}
