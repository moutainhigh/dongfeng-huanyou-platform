package com.navinfo.opentsp.dongfeng.openapi.commands.scan;

import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.validation.group.GroupCommand;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * 
 * @author xltianc.zh
 *
 */
@SuppressWarnings("rawtypes")
public class F9ReportInfoCommand extends BaseCommand<HttpCommandResultWithData> {

	@NotNull(message = "底盘号不能为空", groups = { GroupCommand.class })
	@NotBlank(message = "底盘号不能为空白", groups = { GroupCommand.class })
	@Length(message = "底盘号长度必须是八位数的字符串", min = 8, max = 8, groups = { GroupCommand.class })
	private String chassic;

	public String getChassic() {
		return chassic;
	}

	public void setChassic(String chassic) {
		this.chassic = chassic;
	}

	@Override
	public String toString() {
		return "F9ReportInfoCommand{chassic = " + chassic + "}";
	}

	@Override
	public Class<? extends HttpCommandResultWithData> getResultType() {
		return HttpCommandResultWithData.class;
	}

}
