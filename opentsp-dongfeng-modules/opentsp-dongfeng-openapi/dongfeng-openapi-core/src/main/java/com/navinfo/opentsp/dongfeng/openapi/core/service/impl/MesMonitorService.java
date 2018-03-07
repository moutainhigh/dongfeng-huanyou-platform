package com.navinfo.opentsp.dongfeng.openapi.core.service.impl;

import com.navinfo.opentsp.dongfeng.authority.commands.OpenApiLoginCommand;
import com.navinfo.opentsp.dongfeng.common.client.AuthorityClient;
import com.navinfo.opentsp.dongfeng.common.result.CommonResult;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.common.service.BaseService;
import com.navinfo.opentsp.dongfeng.openapi.commands.monitor.MesMonitorCommand;
import com.navinfo.opentsp.dongfeng.openapi.core.converter.monitor.MesMonitorToDto;
import com.navinfo.opentsp.dongfeng.openapi.core.pojo.MesMonitorPojo;
import com.navinfo.opentsp.dongfeng.openapi.core.service.IMesMonitorService;
import com.navinfo.opentsp.dongfeng.openapi.core.util.OpenApiLoginUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author xltianc.zh
 *
 */
@Service
@SuppressWarnings({ "unchecked", "rawtypes" })
public class MesMonitorService extends BaseService implements IMesMonitorService {

	@Autowired
	private AuthorityClient authorityClient;

	@Override
	public HttpCommandResultWithData monitor(MesMonitorCommand command) {
		HttpCommandResultWithData result = OpenApiLoginUtil.getInstance().resultData();
		// 获取登陆参数
		OpenApiLoginCommand openApiLoginCommand = OpenApiLoginUtil.getInstance().getOpenApiLoginCommand(command);
		// 获取返回结果
		CommonResult openApiloginResult = authorityClient.openApilogin(openApiLoginCommand);
		// 登陆成功
		if (openApiloginResult != null && openApiloginResult.getResultCode() == ReturnCode.OK.code()) {
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("stepName", command.getStepName());
			parameters.put("rows", Integer.parseInt(command.getRows()));
			parameters.put("intervalTime", Integer.parseInt(command.getIntervalTime()));
			List<MesMonitorPojo> pageInfo = dao.sqlFind("queryMesMonitorInfo", parameters, MesMonitorPojo.class);
			result.setData(MesMonitorToDto.convert(pageInfo));
		} else {
			// 登陆失败
			result.setMessage(openApiloginResult.getMessage());
			result.setResultCode(openApiloginResult.getResultCode());
		}
		return result;
	}

}
