package com.navinfo.opentsp.dongfeng.monitor.handler.car.track;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.monitor.commands.car.track.TrackVacuateCommand;
import com.navinfo.opentsp.dongfeng.monitor.service.car.track.ITrackVacuateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@SuppressWarnings("rawtypes")
public class QueryTrackVacuateHandler
		extends ValidateTokenAndReSetAbstracHandler<TrackVacuateCommand, HttpCommandResultWithData> {
	@Autowired
	private ITrackVacuateService trackVacuateService;

	protected QueryTrackVacuateHandler(Class<TrackVacuateCommand> commandType,
			Class<HttpCommandResultWithData> resultType) {
		super(commandType, resultType);
	}

	public QueryTrackVacuateHandler() {
		super(TrackVacuateCommand.class, HttpCommandResultWithData.class);
	}

	@Override
	protected HttpCommandResultWithData businessHandle(TrackVacuateCommand command) {
		HttpCommandResultWithData result = new HttpCommandResultWithData();
		try {
			result = trackVacuateService.trackVacuate(command);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.fillResult(ReturnCode.SERVER_ERROR);
			result.setMessage("轨迹抽稀异常");
		}
		return result;
	}

}
