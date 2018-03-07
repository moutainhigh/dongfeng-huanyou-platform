package com.navinfo.opentsp.dongfeng.openapi.core.util;

import com.navinfo.opentsp.dongfeng.authority.commands.OpenApiLoginCommand;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.util.StringUtil;
import com.navinfo.opentsp.dongfeng.openapi.commands.base.BaseOpenApiCommand;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

public class OpenApiLoginUtil {

	private static OpenApiLoginUtil apiLoginUtil = null;

	public static OpenApiLoginUtil getInstance() {
		if (apiLoginUtil == null) {
			apiLoginUtil = new OpenApiLoginUtil();
		}
		return apiLoginUtil;
	}

	/**
	 * 获取登陆参数
	 * 
	 * @param command
	 * @return
	 */
	public OpenApiLoginCommand getOpenApiLoginCommand(BaseOpenApiCommand command) {
		OpenApiLoginCommand openApiLoginCommand = new OpenApiLoginCommand();
		openApiLoginCommand.setUserName(command.getUserName());
		openApiLoginCommand.setPassword(command.getPassword());
		return openApiLoginCommand;
	}

	/**
	 * 是否在查询时间窗之内
	 *
	 * @param time
	 * @return
	 */
	public boolean isInTimeWindow(Long time) {
		if (StringUtil.isEmpty(time)) {
			return false;
		}
		long beginTime = StringUtil.getCurrentTimeSeconds() - 60 * 60 * 24;
		long endTime = StringUtil.getCurrentTimeSeconds();
		if (time > beginTime && time < endTime) {
			return true;
		}
		return false;
	}

	/**
	 * @description 返回结果默认消息体
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public HttpCommandResultWithData resultData() {
		HttpCommandResultWithData result = new HttpCommandResultWithData();
		result.setResultCode(200);
		result.setMessage("成功");
		result.setData("");
		return result;
	}

	public boolean checkList(List<BigInteger> list) {
		return StringUtil.isEmpty(list) || list.get(0) == null;
	}

	public double formatNumber(int src, int validBit) throws Exception {
		BigDecimal b = new BigDecimal(src);
		double result = b.setScale(validBit, BigDecimal.ROUND_HALF_UP).doubleValue();
		return result;
	}

	public double formatNumber(double src, int validBit) throws Exception {
		BigDecimal b = new BigDecimal(src);
		double result = b.setScale(validBit, BigDecimal.ROUND_HALF_UP).doubleValue();
		return result;
	}
}
