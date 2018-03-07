package com.navinfo.opentsp.dongfeng.report.service.market.impl;

import com.navinfo.opentsp.dongfeng.common.pojo.HyBasicdataPojo;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.PagingInfo;
import com.navinfo.opentsp.dongfeng.common.service.BaseService;
import com.navinfo.opentsp.dongfeng.common.util.ServiceTypeCvt;
import com.navinfo.opentsp.dongfeng.common.util.ServiceTypeGroup;
import com.navinfo.opentsp.dongfeng.common.util.ServiceTypeItem;
import com.navinfo.opentsp.dongfeng.report.commands.market.QueryRSerpartAppointmentCommand;
import com.navinfo.opentsp.dongfeng.report.converter.market.RSerpartAppointmentDto;
import com.navinfo.opentsp.dongfeng.report.pojo.market.RSerpartAppointmentPojo;
import com.navinfo.opentsp.dongfeng.report.service.market.IRSerpartAppointmentService;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RSerpartAppointmentServiceImpl extends BaseService implements IRSerpartAppointmentService {

	protected static final Logger logger = LoggerFactory.getLogger(RSerpartAppointmentServiceImpl.class);

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public HttpCommandResultWithData queryReportData(QueryRSerpartAppointmentCommand command, boolean isExport) {
		HttpCommandResultWithData result = new HttpCommandResultWithData();
		String date = command.getDateStr();
		if (date != null && !date.isEmpty()) {
			command.setBDate(command.getDateStr().substring(0, 10) + " 00:00:00");
			command.setEDate(command.getDateStr().substring(13, 23) + " 23:59:59");
		}
		PagingInfo<RSerpartAppointmentPojo> datas = queryPagingInfo(command, isExport);
		PagingInfo page = new PagingInfo();
		page.setList(RSerpartAppointmentDto.convert(datas.getList()));
		page.setPage_total(datas.getPage_total());
		result.setData(page);
		return result;
	}

	@SuppressWarnings({ "unchecked" })
	public PagingInfo<RSerpartAppointmentPojo> queryPagingInfo(QueryRSerpartAppointmentCommand command,
			boolean isExport) {
		PagingInfo<RSerpartAppointmentPojo> dataList = new PagingInfo<RSerpartAppointmentPojo>();
		int aType = command.getUserInfor().getType();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("accountId", command.getUserInfor().getUserId());
		String accountName = command.getUserInfor().getUsername();
		if (aType == 1) {
			accountName = "_1";
		}
		paramMap.put("accountName", accountName);
		paramMap.put("accountType", command.getUserInfor().getType());
		if ((aType != 1 && aType != 2 && aType != 3) || command.getChassisNum() != null || command.getTerId() != null
				|| command.getCarTerId() != null || command.getCarCph() != null || command.getCompanyName() != null
				|| command.getCarOwner() != null || command.getCarType() != null || command.getEngineType() != null) {
			paramMap.put("isTer", new String("1"));
			paramMap.put("terid", command.getTerId());// 一体机过滤
			paramMap.put("carTerid", command.getCarTerId());// 防拆盒过滤
			paramMap.put("appoType", command.getAppoType());
			paramMap.put("carType", command.getCarType());
			paramMap.put("chassisNum", command.getChassisNum());
			paramMap.put("carCph", command.getCarCph());
			paramMap.put("engineType", command.getEngineType());
			paramMap.put("companyName", command.getCompanyName());
			paramMap.put("carOwnerStr", command.getCarOwner());
		}
		if (aType != 1 || command.getCityCode() != null || command.getStationName() != null
				|| command.getPovinceCode() != null) {
			if (null != command.getCityCode()) {
				command.setPovinceCode(command.getCityCode() - command.getCityCode() % 10000);
			}
			if (command.getCityCode() == null || command.getCityCode() % 10000 <= 0) {
				command.setCityCode(null);
			}
			paramMap.put("stationName", command.getStationName());
			paramMap.put("provinceCode", command.getPovinceCode());
			paramMap.put("cityCode", command.getCityCode());
		}
		// 预约服务类型
		if (command.getAppoType() != null && command.getAppoType().intValue() != -1) {
			paramMap.put("appoType", command.getAppoType());
		}
		if (command.getAppointmentNum() != null) { // 预约流水号
			paramMap.put("appointmentNum", Long.parseLong(command.getAppointmentNum()));
		}
		paramMap.put("bDate", command.getBDate());// 开始时间
		paramMap.put("eDate", command.getEDate());// 结束时间
		try {
			if (isExport) {
				List<RSerpartAppointmentPojo> appointmentPojos = dao.sqlFind("queryRSerpartAppointmentList", paramMap,
						RSerpartAppointmentPojo.class);
				dataList.setList(appointmentPojos);
				dataList.setTotal(appointmentPojos.size());
			} else {
				dataList = dao.sqlPagelFind("queryRSerpartAppointmentList", paramMap,
						NumberUtils.toInt(command.getPage_number()), NumberUtils.toInt(command.getPage_size()),
						RSerpartAppointmentPojo.class);
			}
			// 服务，配件内容转换
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("dataType", 41);
			List<HyBasicdataPojo> basicdatasby41 = dao.sqlFind("queryHyBasicMapping", map, HyBasicdataPojo.class);
			map.put("dataType", 59);
			List<HyBasicdataPojo> basicdatasby59 = dao.sqlFind("queryHyBasicMapping", map, HyBasicdataPojo.class);
			map.put("dataType", 42);
			List<HyBasicdataPojo> basicdatasby42 = dao.sqlFind("queryHyBasicMapping", map, HyBasicdataPojo.class);
			for (RSerpartAppointmentPojo temp : dataList.getList()) {
				temp = switchPojo(temp, basicdatasby41, basicdatasby59, basicdatasby42);
			}
		} catch (Exception e) {
			logger.error("RSerpartAppointmentServiceImpl异常", e);
			throw e;
		}
		return dataList;
	}

	private RSerpartAppointmentPojo switchPojo(RSerpartAppointmentPojo temp, List<HyBasicdataPojo> basicdatasby41,
			List<HyBasicdataPojo> basicdatasby59, List<HyBasicdataPojo> basicdatasby42) {
		int appoType = temp.getAppointmentType();
		switch (appoType) {
		case 0:
			ServiceTypeGroup serverg = ServiceTypeCvt.transSPTContent(41, "服务", temp.getAppoService(), basicdatasby41);
			ServiceTypeGroup toolg = ServiceTypeCvt.transAppoSPTContent(59, "提升工具", temp.getAppoTool(), basicdatasby59);

			StringBuffer service = new StringBuffer();
			if (serverg.getItems() != null && serverg.getItems().size() > 0) {
				for (ServiceTypeItem item : serverg.getItems()) {
					service.append(item.getName() + ";");
				}
			}
			StringBuffer tool = new StringBuffer();
			if (toolg.getItems() != null && toolg.getItems().size() > 0) {
				for (ServiceTypeItem item : toolg.getItems()) {
					tool.append(item.getName() + "" + item.getCheckedCount() + "个;");
				}
			}
			if (service.length() > 0) {
				temp.setAppoService(service.substring(0, service.lastIndexOf(";")));
			}
			temp.setAppoParts("");
			if (tool.length() > 0) {
				temp.setAppoTool(tool.substring(0, tool.lastIndexOf(";")));
			}
			break;
		case 1:
			ServiceTypeGroup partsg = ServiceTypeCvt.transAppoSPTContent(42, "备件", temp.getAppoParts(), basicdatasby42);

			StringBuffer parts = new StringBuffer();
			if (partsg.getItems() != null && partsg.getItems().size() > 0) {
				for (ServiceTypeItem item : partsg.getItems()) {
					parts.append(item.getName() + "" + item.getCheckedCount() + "个;");
				}
			}
			temp.setAppoService("");
			temp.setAppoTool("");
			if (parts.length() > 0) {
				temp.setAppoParts(parts.substring(0, parts.lastIndexOf(";")));
			}
			break;
		case 2:
			ServiceTypeGroup serverg1 = ServiceTypeCvt.transSPTContent(41, "服务", temp.getAppoService(), basicdatasby41);
			ServiceTypeGroup partsg1 = ServiceTypeCvt.transAppoSPTContent(42, "备件", temp.getAppoParts(),
					basicdatasby42);
			ServiceTypeGroup toolg1 = ServiceTypeCvt.transAppoSPTContent(59, "提升工具", temp.getAppoTool(),
					basicdatasby59);

			StringBuffer service1 = new StringBuffer();
			if (serverg1.getItems() != null && serverg1.getItems().size() > 0) {
				for (ServiceTypeItem item : serverg1.getItems()) {
					service1.append(item.getName() + ";");
				}
			}
			StringBuffer parts1 = new StringBuffer();
			if (partsg1.getItems() != null && partsg1.getItems().size() > 0) {
				for (ServiceTypeItem item : partsg1.getItems()) {
					parts1.append(item.getName() + "" + item.getCheckedCount() + "个;");
				}
			}
			StringBuffer tool1 = new StringBuffer();
			if (toolg1.getItems() != null && toolg1.getItems().size() > 0) {
				for (ServiceTypeItem item : toolg1.getItems()) {
					tool1.append(item.getName() + "" + item.getCheckedCount() + "个;");
				}
			}
			if (service1.length() > 0) {
				temp.setAppoService(service1.substring(0, service1.lastIndexOf(";")));
			}
			if (parts1.length() > 0) {
				temp.setAppoParts(parts1.substring(0, parts1.lastIndexOf(";")));
			}
			if (tool1.length() > 0) {
				temp.setAppoTool(tool1.substring(0, tool1.lastIndexOf(";")));
			}
			break;
		}
		return temp;
	}
}
