package com.navinfo.opentsp.dongfeng.report.converter.market;

import com.navinfo.opentsp.dongfeng.common.util.DateUtil;
import com.navinfo.opentsp.dongfeng.common.util.ListUtil;
import com.navinfo.opentsp.dongfeng.common.util.StringUtil;
import com.navinfo.opentsp.dongfeng.report.constant.Constant;
import com.navinfo.opentsp.dongfeng.report.dto.market.VehicleAuditDto;
import com.navinfo.opentsp.dongfeng.report.pojo.market.VehicleAuditPojo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author wt
 * @version 1.0
 * @date 2017/11/28
 * @modify
 * @copyright
 */
public class VehicleAuditCvt {

    private final static String OPERATION = "审核";

    public static List<VehicleAuditDto> convert(List<VehicleAuditPojo> datas) {
        if (ListUtil.isEmpty(datas)){
            return  Collections.emptyList();
        }

        List<VehicleAuditDto> results = new ArrayList<>();
        for (VehicleAuditPojo vehicle : datas) {
            VehicleAuditDto dto = new VehicleAuditDto();
            dto.setId(StringUtil.nvl(vehicle.getId()));
            Constant.AuditStatus status = Constant.AuditStatus.valueOf(vehicle.getStatus());
            dto.setStatus(status.getValue());
            if (status.equals(Constant.AuditStatus.PENDING)) {
                dto.setOperation(OPERATION);
            }
            dto.setChassisNum(StringUtil.nvl(vehicle.getChassisNum()));
            dto.setDealer(StringUtil.nvl(vehicle.getDealer()));
            dto.setOperator(StringUtil.nvl(vehicle.getOperator()));
            dto.setOperaDate(DateUtil.formatTime(vehicle.getOperaDate()));
            dto.setApplyReason(vehicle.getApplyReason());
            results.add(dto);
        }
        return  results;
    }
}
