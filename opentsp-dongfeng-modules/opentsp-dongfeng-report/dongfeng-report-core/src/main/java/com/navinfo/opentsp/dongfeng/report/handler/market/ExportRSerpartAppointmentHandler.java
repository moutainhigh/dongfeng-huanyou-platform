package com.navinfo.opentsp.dongfeng.report.handler.market;

import com.navinfo.opentsp.dongfeng.common.constant.ReportConfigConstants;
import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.PagingInfo;
import com.navinfo.opentsp.dongfeng.common.service.IReportService;
import com.navinfo.opentsp.dongfeng.report.commands.market.ExportRSerpartAppointmentCommand;
import com.navinfo.opentsp.dongfeng.report.commands.market.QueryRSerpartAppointmentCommand;
import com.navinfo.opentsp.dongfeng.report.constant.PropertyConfig;
import com.navinfo.opentsp.dongfeng.report.dto.market.QueryRSerpartAppointmentDTo;
import com.navinfo.opentsp.dongfeng.report.service.market.IRSerpartAppointmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 服务站服务配件预约导出
 * 
 * @author xltianc.zh
 *
 */
@SuppressWarnings("rawtypes")
@Component
public class ExportRSerpartAppointmentHandler
		extends ValidateTokenAndReSetAbstracHandler<ExportRSerpartAppointmentCommand, HttpCommandResultWithData> {

	protected static final Logger logger = LoggerFactory.getLogger(ExportRSerpartAppointmentHandler.class);
	@Resource
	private IRSerpartAppointmentService appointmentReportService;

	@Autowired
	private IReportService reportService;

	@Autowired
	private PropertyConfig reportProperty;

	public ExportRSerpartAppointmentHandler() {
		super(ExportRSerpartAppointmentCommand.class, HttpCommandResultWithData.class);
	}

	protected ExportRSerpartAppointmentHandler(Class<ExportRSerpartAppointmentCommand> commandType,
			Class<HttpCommandResultWithData> resultType) {
		super(commandType, resultType);
	}

	@Override
	protected HttpCommandResultWithData businessHandle(ExportRSerpartAppointmentCommand command) {
		List<QueryRSerpartAppointmentDTo> appointmentPojos = new ArrayList<QueryRSerpartAppointmentDTo>();
		switch (command.getDownloadFlag()) {
		case ReportConfigConstants.EXPORT_CURRENT_FLAG: {
			PagingInfo<QueryRSerpartAppointmentDTo> datas = getPageList(command, false);
			appointmentPojos = datas.getList();
			break;
		}
		case ReportConfigConstants.EXPORT_ALL_FLAG: {
			PagingInfo<QueryRSerpartAppointmentDTo> datas = getPageList(command, true);
			appointmentPojos = datas.getList();
			break;
		}
		default:
		}

		return reportService.downLoad(appointmentPojos, command, ReportConfigConstants.rSerpartAppointment,
				reportProperty);
	}

	/**
	 * 获取当前或者全部结果集
	 * 
	 * @param appointmentCommand
	 * @param isExport
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private PagingInfo<QueryRSerpartAppointmentDTo> getPageList(QueryRSerpartAppointmentCommand appointmentCommand,
			boolean isExport) {
		HttpCommandResultWithData queryResult = appointmentReportService.queryReportData(appointmentCommand, isExport);
		if (queryResult == null) {
			queryResult = new HttpCommandResultWithData();
			PagingInfo dataList = new PagingInfo();
			dataList.setList(Collections.emptyList());
			dataList.setTotal(0L);
			queryResult.setData(dataList);
		}
		PagingInfo<QueryRSerpartAppointmentDTo> info = (PagingInfo<QueryRSerpartAppointmentDTo>) queryResult.getData();
		if (info == null) {
			info = new PagingInfo();
			info.setList(new ArrayList<QueryRSerpartAppointmentDTo>());
			info.setTotal(0L);
			queryResult.setData(info);
		}
		for (QueryRSerpartAppointmentDTo dTo : info.getList()) {
			switch (dTo.getAppointmentStatus()) {
			case "0":
				dTo.setAppointmentStatus("预约待确认");
				break;
			case "1":
				dTo.setAppointmentStatus("已确认未服务");
				break;
			case "2":
				dTo.setAppointmentStatus("已取消");
				break;
			case "3":
				dTo.setAppointmentStatus("正在服务");
				break;
			case "4":
				dTo.setAppointmentStatus("完成服务待评价");
				break;
			case "5":
				dTo.setAppointmentStatus("完成评价");
				break;
			case "6":
				dTo.setAppointmentStatus("已过期");
				break;
			default:
				break;
			}
		}
		return info;
	}

}
