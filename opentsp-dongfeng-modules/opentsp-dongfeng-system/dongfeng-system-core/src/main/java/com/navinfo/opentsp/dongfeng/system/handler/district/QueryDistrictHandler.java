package com.navinfo.opentsp.dongfeng.system.handler.district;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.system.commands.district.QueryDistrictCommand;
import com.navinfo.opentsp.dongfeng.system.service.IDistrictService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 销售区域搜索Handler
 *
 * @author Sunyu
 */
@Component
public class QueryDistrictHandler extends ValidateTokenAndReSetAbstracHandler<QueryDistrictCommand, HttpCommandResultWithData> {

	@Resource
	private IDistrictService districtService;

	public QueryDistrictHandler() {
		super(QueryDistrictCommand.class, HttpCommandResultWithData.class);
	}

	protected QueryDistrictHandler(Class<QueryDistrictCommand> commandType, Class<HttpCommandResultWithData> resultType) {
		super(commandType, resultType);
	}

	@Override
	protected HttpCommandResultWithData businessHandle(QueryDistrictCommand command) {
		HttpCommandResultWithData result = districtService.sqlPageDistrict(command);
		return result;
	}
}