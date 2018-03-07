package com.navinfo.opentsp.dongfeng.report.converter.online;

import com.navinfo.opentsp.dongfeng.common.util.StringUtil;
import com.navinfo.opentsp.dongfeng.report.commands.online.QueryOnlineCommand;
import com.navinfo.opentsp.dongfeng.report.pojo.car.CarQueryParam;

/**
 * @author tushenghong
 * @version 1.0
 * @date 2017-04-05
 * @modify
 * @copyright Navi Tsp
 */
public class OnlineConverter {

    public static CarQueryParam onlineParamToCarQueryParam(QueryOnlineCommand command) {
        CarQueryParam param = new CarQueryParam();
        param.setAccountId(command.getUserInfor().getUserId() + "");
        param.setUserType(command.getUserInfor().getType());
        param.setChassis(command.getChassisNum());
        param.setCph(command.getCarNo());
        param.setCarType(command.getCarType());
        param.setEngineType(command.getEngineType());
        param.setCompanyName(command.getTeamNme());
        param.setBusinessName(command.getCarOwnerName());
        param.setTcode(command.getTerminalId());
        param.setFkCode(command.getFkID());
        param.setPageNum(command.getPage_number());
        param.setPageSize(command.getPage_size());
        if (!StringUtil.isEmpty(command.getSyncDateStr())) {
            param.setbSyncDate(command.getSyncDateStr().substring(0, 10) + " 00:00:00");
            param.seteSyncDate(command.getSyncDateStr().substring(13, 23) + " 23:59:59");
        }
        if (!StringUtil.isEmpty(command.getFirstNettingDateStr())) {
            param.setbNettingDate(command.getFirstNettingDateStr().substring(0, 10) + " 00:00:00");
            param.seteNettingDate(command.getFirstNettingDateStr().substring(13, 23) + " 23:59:59");
        }
        if (!StringUtil.isEmpty(command.getProduceDateStr())) {
            param.setbProdOfflineDate(command.getProduceDateStr().substring(0, 10) + " 00:00:00");
            param.seteProdOfflineDate(command.getProduceDateStr().substring(13, 23) + " 23:59:59");
        }
        param.setSaleStatus(command.getSaleStatus());
        return param;
    }

}