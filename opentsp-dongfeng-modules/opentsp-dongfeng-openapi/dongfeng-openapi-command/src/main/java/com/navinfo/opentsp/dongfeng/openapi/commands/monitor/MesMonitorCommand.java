package com.navinfo.opentsp.dongfeng.openapi.commands.monitor;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.validation.group.GroupCommand;
import com.navinfo.opentsp.dongfeng.openapi.commands.base.BaseOpenApiCommand;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * 
 * @author xltianc.zh
 *
 */
@SuppressWarnings("rawtypes")
public class MesMonitorCommand extends BaseOpenApiCommand {

	@NotNull(message = "步骤名不能为空", groups = { GroupCommand.class })
	@NotBlank(message = "步骤名不能为空白", groups = { GroupCommand.class })
	private String stepName;

	@NotNull(message = "查询条数不能为空", groups = { GroupCommand.class })
	@NotBlank(message = "查询条数不能为空白", groups = { GroupCommand.class })
	private String rows;

	@NotNull(message = "间隔时间不能为空", groups = { GroupCommand.class })
	@NotBlank(message = "间隔时间不能为空白", groups = { GroupCommand.class })
	private String intervalTime;

	public String getStepName() {
		return stepName;
	}

	public String getRows() {
		return rows;
	}

	public void setStepName(String stepName) {
		this.stepName = stepName;
	}

	public void setRows(String rows) {
		this.rows = rows;
	}

	public String getIntervalTime() {
		return intervalTime;
	}

	public void setIntervalTime(String intervalTime) {
		this.intervalTime = intervalTime;
	}

	@Override
	public String toString() {
		return "MesMonitorCommand{" +
				"stepName='" + stepName + '\'' +
				", rows='" + rows + '\'' +
				", intervalTime=" + intervalTime +
				'}';
	}

	@Override
	public Class<? extends HttpCommandResultWithData> getResultType() {
		return HttpCommandResultWithData.class;
	}

}
