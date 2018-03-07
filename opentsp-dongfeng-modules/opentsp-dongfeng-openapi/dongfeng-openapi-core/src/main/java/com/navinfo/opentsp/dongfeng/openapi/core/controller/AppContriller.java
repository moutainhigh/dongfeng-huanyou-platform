package com.navinfo.opentsp.dongfeng.openapi.core.controller;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.openapi.commands.scan.F9ReportInfoCommand;
import com.navinfo.opentsp.dongfeng.openapi.commands.scan.F9ScanResultCommand;
import com.navinfo.opentsp.dongfeng.openapi.core.service.IF9ScanBusinessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhangyu on 2017/8/3.
 */
@Component(value = "appContriller")
public class AppContriller {
	@Autowired
	private IF9ScanBusinessService scanBusinessService;

	private static final Logger logger = LoggerFactory.getLogger(AppContriller.class);

	/**
	 * @param command
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Map f9ReportInfo(F9ReportInfoCommand command) {
		logger.info("===== f9ReportInfo start =====");
		Map<String, Object> reMap = new HashMap<String, Object>();
		try {
			HttpCommandResultWithData result = scanBusinessService.reportInfo(command);
			reMap.put("resultCode", result.getResultCode());
			reMap.put("message", result.getMessage());
			reMap.put("data", result.getData());
		} catch (Exception e) {
			reMap.put("resultCode", 506);
			reMap.put("message", "Server Error");
			logger.error("#AppContriller#f9ReportInfo#error", e);
		}
		logger.info("===== f9ReportInfo end =====");
		return reMap;
	}

	/**
	 * @param command
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Map f9ScanResultInfo(F9ScanResultCommand command) {
		logger.info("===== f9ScanResultInfo start =====");
		Map<String, Object> reMap = new HashMap<String, Object>();
		try {
			HttpCommandResultWithData result = scanBusinessService.scanResult(command);
			reMap.put("resultCode", result.getResultCode());
			reMap.put("message", result.getMessage());
			reMap.put("data", result.getData());
		} catch (Exception e) {
			reMap.put("resultCode", 506);
			reMap.put("message", "Server Error");
			logger.error("#AppContriller#f9ScanResultInfo#error", e);
		}
		logger.info("===== f9ScanResultInfo end =====");
		return reMap;
	}

}
