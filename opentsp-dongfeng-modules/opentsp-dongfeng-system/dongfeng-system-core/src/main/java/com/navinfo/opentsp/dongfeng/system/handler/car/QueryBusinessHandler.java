package com.navinfo.opentsp.dongfeng.system.handler.car;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.PagingInfo;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.system.commands.car.QueryBusinessCommand;
import com.navinfo.opentsp.dongfeng.system.commands.dto.inDto.BusinessIndto;
import com.navinfo.opentsp.dongfeng.system.service.ICarService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class QueryBusinessHandler extends ValidateTokenAndReSetAbstracHandler<QueryBusinessCommand, HttpCommandResultWithData> {

	@Resource
	private ICarService carService;
	
	public QueryBusinessHandler() {
		super(QueryBusinessCommand.class , HttpCommandResultWithData.class);
	}
	
	protected QueryBusinessHandler(Class<QueryBusinessCommand> commandType,
			Class<HttpCommandResultWithData> resultType) {
		super(commandType, resultType);
	}

	@Override
	protected HttpCommandResultWithData businessHandle(QueryBusinessCommand command) {
		
		HttpCommandResultWithData result = null;
		PagingInfo<BusinessIndto> page = null;

		page = carService.sqlPageBusiness(command , null);
		result = new HttpCommandResultWithData();
		result.setResultCode(ReturnCode.OK.code());
		result.setData(page);
		
		return result ;
	}

}
