package com.navinfo.opentsp.dongfeng.monitor.service.car.track;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.monitor.commands.car.track.ExportTrackVacuateCommand;
import com.navinfo.opentsp.dongfeng.monitor.commands.car.track.TrackVacuateCommand;
import com.navinfo.opentsp.dongfeng.monitor.pojo.car.QueryTracesPojo;
import net.sf.json.JSONObject;

import java.util.List;

/**
 * 轨迹抽稀
 * 
 * @author xltianc.zh
 *
 */
@SuppressWarnings("rawtypes")
public interface ITrackVacuateService {

	/**
	 * 轨迹抽稀
	 *
	 * @param command
	 * @throws Exception
	 */
	public HttpCommandResultWithData trackVacuate(TrackVacuateCommand command) throws Exception;

	/**
	 * 轨迹抽稀查询轨迹点FOR导出
	 * 
	 * @param command
	 * @return
	 */
	public List<QueryTracesPojo> queryTrackVacuateList(TrackVacuateCommand command);

	/**
	 * 轨迹抽稀导出
	 * 
	 * @param fservice
	 * @param command
	 * @param sourcePath
	 */
	public void asyncDonwload(String fservice, ExportTrackVacuateCommand command, String sourcePath,
			List<? extends Object> list);

	/**
	 * 轨迹抽稀导出
	 * 
	 * @param fservice
	 * @param list
	 * @param command
	 * @param sourcePath
	 * @return
	 */
	public JSONObject download(String fservice, List<? extends Object> list, ExportTrackVacuateCommand command,
			String sourcePath);
}
