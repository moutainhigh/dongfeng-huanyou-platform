package com.navinfo.opentsp.dongfeng.report.commands.market;

import com.navinfo.opentsp.dongfeng.common.command.BaseReportCommand;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;

@SuppressWarnings("rawtypes")
public class QueryNsoodleReportCommand extends BaseReportCommand {
	
	private String teamDealerCode;
	private String teamName;
	private String outOfLibraryTimeStr;
	
	public String getTeamDealerCode() {
		return teamDealerCode;
	}

	public void setTeamDealerCode(String teamDealerCode) {
		this.teamDealerCode = teamDealerCode;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getOutOfLibraryTimeStr() {
		return outOfLibraryTimeStr;
	}

	public void setOutOfLibraryTimeStr(String outOfLibraryTimeStr) {
		this.outOfLibraryTimeStr = outOfLibraryTimeStr;
	}

	@Override
	public Class<? extends HttpCommandResultWithData> getResultType() {
		return HttpCommandResultWithData.class;
	}

	@Override
	public String toString() {
		return "QueryNsoodleReportCommand{" +
                "teamDealerCode='" + teamDealerCode + '\'' +
                ", teamName='" + teamName + '\'' +
                 ", outOfLibraryTimeStr='" + outOfLibraryTimeStr + '\'' +
                '}';
	}

}
