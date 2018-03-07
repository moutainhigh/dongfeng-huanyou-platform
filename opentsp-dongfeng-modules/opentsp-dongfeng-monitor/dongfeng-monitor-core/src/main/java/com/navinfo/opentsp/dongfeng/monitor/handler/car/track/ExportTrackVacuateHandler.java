package com.navinfo.opentsp.dongfeng.monitor.handler.car.track;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.common.util.DateUtil;
import com.navinfo.opentsp.dongfeng.common.util.StringUtil;
import com.navinfo.opentsp.dongfeng.monitor.commands.car.track.ExportTrackVacuateCommand;
import com.navinfo.opentsp.dongfeng.monitor.pojo.car.QueryTracesPojo;
import com.navinfo.opentsp.dongfeng.monitor.service.car.track.ITrackVacuateService;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.List;

/**
 * 轨迹回放:轨迹点handler
 *
 * @author fengwm
 * @version 1.0
 * @date 2017-03-09
 * @modify
 * @copyright Navi Tsp
 */

@SuppressWarnings("rawtypes")
@Component
public class ExportTrackVacuateHandler
		extends ValidateTokenAndReSetAbstracHandler<ExportTrackVacuateCommand, HttpCommandResultWithData> {

	@Autowired
	private ITrackVacuateService trackVacuateService;
	@Value("${report.module.path}")
	private String path;

	@Value("${fservice.url}")
	private String fservice;

	@Value("${download.record.size:3000}")
	private String records;
	protected static final Logger logger = LoggerFactory.getLogger(ExportTrackVacuateHandler.class);

	public ExportTrackVacuateHandler() {
		super(ExportTrackVacuateCommand.class, HttpCommandResultWithData.class);
	}

	protected ExportTrackVacuateHandler(Class<ExportTrackVacuateCommand> commandType,
			Class<HttpCommandResultWithData> resultType) {
		super(commandType, resultType);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected HttpCommandResultWithData businessHandle(ExportTrackVacuateCommand command) {
		HttpCommandResultWithData result = new HttpCommandResultWithData();
		//COMMAND已经验空
		if(DateUtil.strTimeChangeLong(command.getBeginDate())>DateUtil.strTimeChangeLong(command.getEndDate())){
			result = result.fillResult(ReturnCode.TIME_BEGIN_AND_END_CHECK);
			return result;
		}
		List<QueryTracesPojo> list = trackVacuateService.queryTrackVacuateList(command);
		int number = Integer.valueOf(records);
		if (list.size() >= number) {

			if (StringUtil.isNull(command.getEmail()) && StringUtil.isNull(command.getUserInfor().getEmail())) {
				result.setResultCode(ReturnCode.EXPORT_EMAIL_ISNOT_EMPTY.code());
				result.setMessage(ReturnCode.EXPORT_EMAIL_ISNOT_EMPTY.message());
				return result;
			} else {
				if (!StringUtil.isNull(command.getUserInfor().getEmail())) {
					command.setEmail(command.getUserInfor().getEmail());
				}
			}
			// path表示模板在文件服务器的位置.
			trackVacuateService.asyncDonwload(fservice, command, path, list);

			result = new HttpCommandResultWithData();
			result.setResultCode(ReturnCode.OK.code());
			result.setMessage(
					MessageFormat.format(ReturnCode.EXPORT_ASYNC_SUCCESS.message(), records, command.getEmail()));
		} else {
			JSONObject uploadResult = trackVacuateService.download(fservice, list, command, path);
			result = new HttpCommandResultWithData();
			result.setResultCode(ReturnCode.OK.code());
			result.setMessage("文件下载成功！");
			result.setData(uploadResult.getJSONObject("data").getString("fullPath"));
		}
		return result;
	}
}