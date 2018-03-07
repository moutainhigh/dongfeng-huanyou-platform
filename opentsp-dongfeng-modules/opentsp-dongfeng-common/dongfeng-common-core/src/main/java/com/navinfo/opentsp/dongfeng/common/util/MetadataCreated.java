package com.navinfo.opentsp.dongfeng.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MetadataCreated {
	
	public static final Map<String, Map<String, List<String>>> reportMetadata = new HashMap<String, Map<String, List<String>>>();
	
	private static MetadataCreated metadataInstance;
	
	private MetadataCreated() {
		createCarBean();
	}
	
	public static synchronized MetadataCreated getMetadataInstance() {
		
		if (null == metadataInstance) {
			metadataInstance = new MetadataCreated();
		}
		return metadataInstance;
	}
	
	public Map<String, Map<String, List<String>>> getReportMetadata() {
		return reportMetadata;
	}
	
	private void createCarBean() {
		List<String> headers = new ArrayList<String>();
		headers.add("序号");
		headers.add("底盘号");
		headers.add("车牌号");
		headers.add("北斗一体机ID");
		headers.add("北斗一体机SIM卡");
		
		headers.add("FK控制器ID");
		headers.add("FK控制器SIM卡");
		
		headers.add("所属经销商");
		headers.add("经销商代码");
		headers.add("所属客户");
		
		headers.add("车辆型号");
		headers.add("发动机型号");
		headers.add("发动机类型");
		
		headers.add("注册时间");
		headers.add("销售时间");
		headers.add("销售状态");
		headers.add("录入方式");
		headers.add("出库时间");
		headers.add("首次上线时间");
//		headers.add("首次位置");
//		headers.add("末次上线时间");
//		headers.add("末次位置");
		Map<String, List<String>> excel = new HashMap<String, List<String>>();
		excel.put("车辆信息报表", headers);
		List<String> props = new ArrayList<String>();
		
		//底盘号
		props.add("chassisNum");
		
		//车牌号
		props.add("cph");
		
		//北斗一体机Code
		props.add("terminalCode");
		//北斗一体机SIM
		props.add("terminalSim");
		
		//FK控制器编号
		props.add("fkCode");
		//FK控制器SIM
		props.add("fkSim");
		
		//所属经销商名称
		props.add("companyName");
		//经销商编码
		props.add("dealerCode");
		//所属客户
		props.add("businessName");
		
		//车辆型号
		props.add("CAR_TYPE_VALUE");
		//发动机型号
		props.add("engineNumber");
		//发动机类型
		props.add("ENGINE_TYPE_VALUE");
		//注册时间
		props.add("registerTimeStr");
		//销售时间
		props.add("salesDateStr");
		//销售状态
		props.add("salesStatusValue");
		//录入方式
		props.add("autoFlagStr");
		//出库时间
		props.add("removalTimeStr");
		//首次上线时间
		props.add("firstGpsTimeStr");
		//首次位置
//		props.add("nettingLog");
		
//		props.add("lastCarDateStr");
//		props.add("lastLon");
		Map<String, List<String>> entity = new HashMap<String, List<String>>();
		entity.put("carInfo", props);
		reportMetadata.put("1_excel", excel);
		reportMetadata.put("1_entity", entity);
	}
	
	public String getExcelName(Long reportId) {
		String entityName = "";
		Map<String, List<String>> entity = MetadataCreated.reportMetadata.get(reportId.intValue() + "_entity");
		if(null != entity){
			for(String key : entity.keySet()){
				entityName = (null == key || key=="") ? "" : key;
			}
		}
		return entityName;
	}
	
	/**
	 * 获取报表名称
	 */
	public String getSheetName(Long reportId) {
		String reportName = "";
		Map<String, List<String>> excel = MetadataCreated.reportMetadata.get(reportId.intValue() + "_excel");
		if(null != excel){
			for(String key : excel.keySet()){
				reportName = (null == key || key=="") ? "" : key;
			}
		}
		return reportName;
	}
	
	/**
	 * 获取报表列头
	 */
	public List<String> getHeaders(Long reportId) {
		List<String> headers = new ArrayList<String>();
		Map<String, List<String>> excel = MetadataCreated.reportMetadata.get(reportId.intValue() + "_excel");
		if(null != excel){
			headers = excel.get(getSheetName(reportId));
		}
		return headers;
	}
	
	/**
	 * 获取报表实体类属性名
	 */
	public List<String> getProps(Long reportId) {
		List<String> props = new ArrayList<String>();
		Map<String, List<String>> entity = MetadataCreated.reportMetadata.get(reportId + "_entity");
		if(null != entity){
			props = entity.get(getExcelName(reportId));
		}
		return props;
	}
}
