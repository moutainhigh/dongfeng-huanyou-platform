package com.navinfo.opentsp.dongfeng.report.converter.oil;

import com.navinfo.opentsp.dongfeng.report.commands.oil.QueryOilCommand;
import com.navinfo.opentsp.dongfeng.report.commands.oil.QueryTireCommand;
import com.navinfo.opentsp.dongfeng.report.pojo.car.CarQueryParam;

/**
 * @author tushenghong
 * @version 1.0
 * @date 2017-03-30
 * @modify
 * @copyright Navi Tsp
 */
public class OilConverter {
    public static CarQueryParam oilParamToCarQueryParam(QueryOilCommand command) {
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
        return param;
    }

    public static CarQueryParam oilParamToCarTireQueryParam(QueryTireCommand command) {
        CarQueryParam param = new CarQueryParam();
        param.setAccountId(command.getUserInfor().getUserId() + "");
        param.setUserType(command.getUserInfor().getType());
        param.setSim(command.getCommunicationId());
        param.setPageNum(command.getPage_number());
        param.setPageSize(command.getPage_size());
        return param;
    }
}
