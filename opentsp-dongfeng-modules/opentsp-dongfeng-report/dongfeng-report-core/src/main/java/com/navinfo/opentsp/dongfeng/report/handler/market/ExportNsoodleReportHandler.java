package com.navinfo.opentsp.dongfeng.report.handler.market;

import com.navinfo.opentsp.dongfeng.common.constant.ReportConfigConstants;
import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.PagingInfo;
import com.navinfo.opentsp.dongfeng.common.service.IReportService;
import com.navinfo.opentsp.dongfeng.common.util.StringUtil;
import com.navinfo.opentsp.dongfeng.report.commands.market.ExportNsoodleReportCommand;
import com.navinfo.opentsp.dongfeng.report.constant.PropertyConfig;
import com.navinfo.opentsp.dongfeng.report.dto.market.QueryNsoodleReportDTo;
import com.navinfo.opentsp.dongfeng.report.service.market.INsoodleReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 未售车辆出经销商库异常报表导出
 * 
 * @author xltianc.zh
 */
@SuppressWarnings("rawtypes")
@Component
public class ExportNsoodleReportHandler extends ValidateTokenAndReSetAbstracHandler<ExportNsoodleReportCommand, HttpCommandResultWithData> {

	protected static final Logger logger = LoggerFactory.getLogger(ExportNsoodleReportHandler.class);
	@Resource
	private INsoodleReportService soodleReportService;

	@Autowired
	private IReportService reportService;

	@Autowired
	private PropertyConfig reportProperty;

	public ExportNsoodleReportHandler() {
		super(ExportNsoodleReportCommand.class, HttpCommandResultWithData.class);
	}

	protected ExportNsoodleReportHandler(Class<ExportNsoodleReportCommand> commandType,
			Class<HttpCommandResultWithData> resultType) {
		super(commandType, resultType);
	}

	@Override
	protected HttpCommandResultWithData businessHandle(ExportNsoodleReportCommand command) {
		List<QueryNsoodleReportDTo> nsoodleReportPojos = new ArrayList<QueryNsoodleReportDTo>();
		switch (command.getDownloadFlag()) {
		case ReportConfigConstants.EXPORT_CURRENT_FLAG: {
			PagingInfo<QueryNsoodleReportDTo> datas = getPageList(command, false);
			nsoodleReportPojos = datas.getList();
			break;
		}
		case ReportConfigConstants.EXPORT_ALL_FLAG: {
			PagingInfo<QueryNsoodleReportDTo> datas = getPageList(command, true);
			nsoodleReportPojos = datas.getList();
			break;
		}
		default:
		}

		return reportService.downLoad(nsoodleReportPojos, command, ReportConfigConstants.nsoodleReport, reportProperty);
	}

	/**
	 * 获取当前或者全部结果集
	 * 
	 * @param command
	 * @param isExport
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private PagingInfo<QueryNsoodleReportDTo> getPageList(ExportNsoodleReportCommand command, boolean isExport) {
		HttpCommandResultWithData queryResult = soodleReportService.queryReportData(command, isExport);
		if (queryResult == null) {
			queryResult = new HttpCommandResultWithData();
			PagingInfo dataList = new PagingInfo();
			dataList.setList(Collections.emptyList());
			dataList.setTotal(0L);
			queryResult.setData(dataList);
		}
		PagingInfo<QueryNsoodleReportDTo> info = (PagingInfo<QueryNsoodleReportDTo>) queryResult.getData();
		if (info == null) {
			info = new PagingInfo();
			info.setList(new ArrayList<QueryNsoodleReportDTo>());
			info.setTotal(0L);
			queryResult.setData(info);
		}
		for (QueryNsoodleReportDTo dTo : info.getList()) {
			if(dTo != null && StringUtil.isNotEmpty(dTo.getLastLoction())){
				dTo.setLastLoction(dTo.getLastLoction().replace(",", ";"));
			}
		}
		return info;
	}

}
