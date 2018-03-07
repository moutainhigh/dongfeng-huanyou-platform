package com.navinfo.dongfeng.terminal.comm.common;

import java.util.HashMap;
import java.util.Map;

/**
 * 定义一个变量  用于存储抽析接口中对应的取值
 * @author gxq
 *
 */

public class CloudTrackConstant {
	private volatile static CloudTrackConstant cloudTrackConstant;// 缓存实例对象  
    //定义一个变量缓存   用于存储抽析接口对应的状态信息
	private static Map<String,String> statusMap = new HashMap<String,String>();
	//事件信息
	private static Map<String,String> dataMapEvent = new HashMap<String,String>();
	static{
		statusMap.put("00", "ACC 关");
		statusMap.put("01", "ACC 开");
		statusMap.put("10", "未定位");
		statusMap.put("11", "定位");
		statusMap.put("20", "北纬");
		statusMap.put("21", "南纬");
		statusMap.put("30", "东经");
		statusMap.put("31", "西经");
		statusMap.put("40", "运营状态");
		statusMap.put("41", "停运状态");
		statusMap.put("100", "车辆油路正常");
		statusMap.put("101", "车辆油路断开");
		statusMap.put("120", "车门解锁");
		statusMap.put("121", "车门加锁");
		statusMap.put("130", "关");
		statusMap.put("131", "开");
		statusMap.put("140", "关");
		statusMap.put("141", "开");
		statusMap.put("150", "关");
		statusMap.put("151", "开");
		statusMap.put("160", "关");
		statusMap.put("161", "开");
		statusMap.put("170", "关");
		statusMap.put("171", "开");
	}
    
	/**
	 *  Event事件
	 */
	static {
		dataMapEvent.put("0", "紧急报警(A)");
		dataMapEvent.put("1", "超速报警(B)");
		dataMapEvent.put("2", "疲劳驾驶(B)");
		dataMapEvent.put("3", "危险预警(A)");
		dataMapEvent.put("4", "GNSS模块发送故障(B)");
		dataMapEvent.put("5", "GNSS天线未接或被剪断(B)");
		dataMapEvent.put("6", "GNSS天线短路(B)");
		dataMapEvent.put("7", "主电源欠压(B)");
		dataMapEvent.put("8", "主电源掉电(B)");
		dataMapEvent.put("9", "终端LCD或显示器故障(B)");
		dataMapEvent.put("10", "TTS模块故障(B)");
		dataMapEvent.put("11", "摄像头故障(B)");
		dataMapEvent.put("12", "道路运输证IC卡模块故障(B)");
		dataMapEvent.put("13", "超速预警(B)");
		dataMapEvent.put("14", "疲劳驾驶预警(B)");
		dataMapEvent.put("18", "当天累计驾驶超时(B)");
		dataMapEvent.put("19", "超时停车(B)");
		dataMapEvent.put("20", "进出区域(A)");
		dataMapEvent.put("21", "进出路线(A)");
		dataMapEvent.put("22", "路段行驶时间不足/过长(A)");
		dataMapEvent.put("23", "路线偏离报警(B)");
		dataMapEvent.put("24", "车辆VSS故障(B)");
		dataMapEvent.put("25", "车辆油量异常(B)");
		dataMapEvent.put("26", "车辆被盗(B)");
		dataMapEvent.put("27", "车辆非法点火(A)");
		dataMapEvent.put("28", "车辆非法位移(A)");
		dataMapEvent.put("29", "碰撞预警(B)");
		dataMapEvent.put("30", "侧翻预警(B)");
		dataMapEvent.put("31", "非法开门报警(A)");
		dataMapEvent.put("32", "禁驾(B)");
		dataMapEvent.put("33", "关键点围栏");
		dataMapEvent.put("34", "区域滞留超时报警");
		dataMapEvent.put("35", "防拆报警");
		dataMapEvent.put("36", "空挡滑行");
		dataMapEvent.put("37", "急加速");
		dataMapEvent.put("38", "急减速");
		dataMapEvent.put("39", "急转弯");
		dataMapEvent.put("40", "低油量行驶");
		dataMapEvent.put("41", "猛踩油门");
		dataMapEvent.put("42", "制动气压低");
		dataMapEvent.put("43", "长时间刹车");
		dataMapEvent.put("44", "长时间离合");
		dataMapEvent.put("45", "停车立即熄火");
		dataMapEvent.put("46", "发动机冷启动");
		dataMapEvent.put("47", "绿区外驾驶");
		dataMapEvent.put("48", "ID不匹配");
		dataMapEvent.put("49", "北斗终端拆出");
		dataMapEvent.put("50", "未锁车");
	}
	public static String getStatusMap(String type){
		return statusMap.get(type);
	}
	public static String getEventMap(String type){
		return dataMapEvent.get(type);
	}
	public static void main(String[] args){
		String a= CloudTrackConstant.getStatusMap("131");
		String b =CloudTrackConstant.getEventMap("47");
		System.out.println(a+" "+b);
	}
}
