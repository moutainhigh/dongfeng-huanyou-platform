package com.navinfo.opentsp.dongfeng.report.service.general.impl;

import com.google.protobuf.InvalidProtocolBufferException;
import com.lc.core.protocol.webservice.statisticsdata.LCFaultCodeRecords;
import com.lc.core.protocol.webservice.statisticsdata.entity.LCFaultCode;
import com.navinfo.opentsp.dongfeng.common.configuration.CloudDataRmiClientConfiguration;
import com.navinfo.opentsp.dongfeng.common.pojo.HyBasicdataPojo;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.PagingInfo;
import com.navinfo.opentsp.dongfeng.common.rmi.DataAnalysisWebService;
import com.navinfo.opentsp.dongfeng.common.rmi.module.CommonParameterSerializer;
import com.navinfo.opentsp.dongfeng.common.service.BaseService;
import com.navinfo.opentsp.dongfeng.common.util.DateUtils;
import com.navinfo.opentsp.dongfeng.common.util.ListPageUtil;
import com.navinfo.opentsp.dongfeng.report.commands.general.QueryFaultSummaryCommand;
import com.navinfo.opentsp.dongfeng.report.converter.general.FaultSummaryDto;
import com.navinfo.opentsp.dongfeng.report.dto.general.QueryFaultSummaryDto;
import com.navinfo.opentsp.dongfeng.report.pojo.general.QueryFaultSummaryPojo;
import com.navinfo.opentsp.dongfeng.report.service.general.IFaultSummaryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 故障汇总报表
 * 
 * @Date 2017/3/27
 */
@SuppressWarnings("rawtypes")
@Service
public class FaultSummaryServiceImpl extends BaseService implements IFaultSummaryService {

	private static final Logger logger = LoggerFactory.getLogger(FaultSummaryServiceImpl.class);

	@Resource
	private CloudDataRmiClientConfiguration cloudDataRmiClientConfiguration;

	@SuppressWarnings("unchecked")
	@Override
	public HttpCommandResultWithData queryFaultSummary(QueryFaultSummaryCommand command, boolean isExport) {
		HttpCommandResultWithData result = new HttpCommandResultWithData();
		if (command.getDateStr() != null && !command.getDateStr().isEmpty()) {
			command.setBeginTime(setTimeValue(command.getDateStr().substring(0, 10) + " 00:00:00"));
			command.setEndTime(setTimeValue(command.getDateStr().substring(13, 23) + " 23:59:59"));
		}
		if (command.getUserInfor().getType() == 1) {
			command.getUserInfor().setUsername("_1");
		}
		PagingInfo page = new PagingInfo();
		List<QueryFaultSummaryDto> summaryDtos = FaultSummaryDto.convert(queryCloud(command));
		long count = summaryDtos.size();
		int pageSize = Integer.parseInt(command.getPage_size());
		if (!isExport) {
			page = getResult(summaryDtos, command);
		} else {
			page.setList(summaryDtos);
		}
		result.setData(page);
		page.setTotal(count);
		page.setPage_total((count + pageSize - 1) / pageSize);
		return result;
	}

	@SuppressWarnings("unchecked")
	private PagingInfo<QueryFaultSummaryDto> getResult(List<QueryFaultSummaryDto> summaryDtos,
			QueryFaultSummaryCommand command) {
		int pageNumber = Integer.parseInt(command.getPage_number());
		int pageSize = Integer.parseInt(command.getPage_size());
		return ListPageUtil.getPagingInfo(pageNumber, pageSize, summaryDtos);
	}

	/**
	 * 数据库查询
	 * 
	 * @param command
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private List<QueryFaultSummaryPojo> queryFaultSummaryInfo(QueryFaultSummaryCommand command) throws Exception {
		Map<String, Object> conMaps = new HashMap<String, Object>();
		conMaps.put("isTer", new String("1"));// 是否绑定终端
		conMaps.put("accountType", command.getUserInfor().getType());
		conMaps.put("accountId", command.getUserInfor().getUserId());

		conMaps.put("terid", command.getTerId());// 一体机过滤
		conMaps.put("carTerid", command.getCarTerId());// 防拆盒过滤
		conMaps.put("companyName", command.getCompanyName());
		conMaps.put("carType", command.getCarType());
		conMaps.put("chassisNum", command.getChassisNum());
		conMaps.put("carCph", command.getPlateNum());
		conMaps.put("engineType", command.getEngineType());
		conMaps.put("accountName", command.getUserInfor().getUsername());
		conMaps.put("structureNum", command.getStructureNum());
		conMaps.put("carOwnerStr", command.getCarOwner());

		return dao.sqlFind("queryFaultSummary", conMaps, QueryFaultSummaryPojo.class);
	}

	/**
	 * 查询云信息
	 * 
	 * @param condition
	 * @return
	 */
	private List<QueryFaultSummaryPojo> queryCloud(QueryFaultSummaryCommand condition) {
		List<QueryFaultSummaryPojo> dataList = new ArrayList<QueryFaultSummaryPojo>();
		if (condition.getBeginTime() != null && condition.getEndTime() != null) {
			try {
				List<QueryFaultSummaryPojo> faultSummaryPojos = queryFaultSummaryInfo(condition);

				if (faultSummaryPojos != null && !faultSummaryPojos.isEmpty()) {
					// 封装通讯号信息数据
					Map<Long, QueryFaultSummaryPojo> dataMaps = new HashMap<Long, QueryFaultSummaryPojo>();
					List<Long> communicationIds = setDataMaps(faultSummaryPojos, dataMaps);
					// 从位置云拿数据并解析
					if (communicationIds != null && !communicationIds.isEmpty()) {

						DataAnalysisWebService service = cloudDataRmiClientConfiguration.getDataAnalysisWebService();
						// 条件筛选：（SPN： FMI： 故障描述：）
						CommonParameterSerializer parameter = new CommonParameterSerializer();
						parameter.setMultipage(true);
						parameter.setSort(false);
						int spn = (condition.getSpn() == null || Integer.parseInt(condition.getSpn()) < 0) ? -1
								: Integer.parseInt(condition.getSpn().trim());
						int fmi = (condition.getFmi() == null || Integer.parseInt(condition.getFmi()) < 0) ? -1
								: Integer.parseInt(condition.getFmi().trim());
						byte[] data = service.getFaultCodeRecords(communicationIds, spn, fmi, condition.getBeginTime(),
								condition.getEndTime(), parameter);
						logger.info("FaultSummaryServiceImpl:cloudDataLength:" + (data == null ? "null" : data.length));
						if (null != data) {
							LCFaultCodeRecords.FaultCodeRecords builder = LCFaultCodeRecords.FaultCodeRecords.parseFrom(data);
							List<LCFaultCode.FaultCode> list = builder.getDataListList();
							Map<String, String> sfMap = new HashMap<String, String>();
							if (list != null && !list.isEmpty()) {
								for (LCFaultCode.FaultCode vo : list) {
									setCloudInfo(dataList, dataMaps, sfMap, vo);
								}
							}
						}
					}
				}
			} catch (InvalidProtocolBufferException e) {
				logger.info("轨迹数据获取或转换异常", e);
			} catch (Exception e) {
				logger.info("车辆获取或转换异常", e);
			}
		}
		return dataList;
	}

	private void setCloudInfo(List<QueryFaultSummaryPojo> dataList, Map<Long, QueryFaultSummaryPojo> dataMaps,
			Map<String, String> sfMap, LCFaultCode.FaultCode vo) throws CloneNotSupportedException {
		Long tid = vo.getTerminalId();
		QueryFaultSummaryPojo bean = dataMaps.get(tid);
		QueryFaultSummaryPojo rBean = (QueryFaultSummaryPojo) bean.clone();
		rBean.setSpn(vo.getSpn() + "");
		rBean.setFmi(vo.getFmi() + "");
		String spn_fmi = vo.getSpn() + "_" + vo.getFmi();
		if (rBean != null && rBean.geteType() != null && !rBean.geteType().isEmpty()) {
			String eType = rBean.geteType();
			String mapKey = spn_fmi + "_" + eType;
			if (sfMap.containsKey(mapKey)) {
				rBean.setBreakdownDis(sfMap.get(mapKey));
			} else {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("dataType", eType);
				map.put("dataCode", spn_fmi);
				@SuppressWarnings("unchecked")
				HyBasicdataPojo discribe = (HyBasicdataPojo) dao.sqlFindObject("queryHyBasicMapping", map,
						HyBasicdataPojo.class);
				if (discribe != null) {
					sfMap.put(mapKey, null == discribe.getDataValue() ? "" : discribe.getDataValue());
				} else {
					sfMap.put(mapKey, "未知");
				}
				rBean.setBreakdownDis(sfMap.get(mapKey));
			}
		} else {
			rBean.setBreakdownDis("未知");
		}
		rBean.setOccurDate(DateUtils.formatDate(vo.getBeginDate() * 1000));
		rBean.setDuration(formatLongToTimeString(vo.getContinuousTime()));
		rBean.setbLoction(vo.getBeginLon() * 0.000001 + "," + vo.getBeginLat() * 0.000001);
		rBean.seteLoction(vo.getEndLon() * 0.000001 + "," + vo.getEndLat() * 0.000001);

		dataList.add(rBean);
	}

	private List<Long> setDataMaps(List<QueryFaultSummaryPojo> faultSummaryPojos,
			Map<Long, QueryFaultSummaryPojo> dataMaps) {
		List<Long> communicationIds = new ArrayList<Long>();
		for (int index = 0; index < faultSummaryPojos.size(); index++) {
			if (faultSummaryPojos.get(index) != null && faultSummaryPojos.get(index).getCommId() != null) {
				Long commId = faultSummaryPojos.get(index).getCommId().longValue();
				if (commId != null && commId.longValue() != 0) {
					communicationIds.add(commId);
					dataMaps.put(commId, faultSummaryPojos.get(index));
				}
			}
		}
		return communicationIds;
	}

	/**
	 * 将秒转换为时分秒格式
	 * 
	 * @param time
	 * @return
	 */
	private String formatLongToTimeString(int time) {
		long second = new Long(time);
		long hour = 0;
		long minute = 0;

		if (second >= 60) {
			minute = second / 60;
			second = second % 60;
		}
		if (minute >= 60) {
			hour = minute / 60;
			minute = minute % 60;
		}
		return (hour + "小时" + minute + "分钟" + second + "秒");
	}

	private Long setTimeValue(String value) {// 秒级别
		Long date = 0L;
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			date = df.parse(value).getTime() / 1000;
		} catch (ParseException e) {
			logger.error("异常", e);
		}
		return date;
	}

}
