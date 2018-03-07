package com.navinfo.opentsp.dongfeng.report.converter.market;

import com.navinfo.opentsp.dongfeng.common.util.DateUtil;
import com.navinfo.opentsp.dongfeng.common.util.ListUtil;
import com.navinfo.opentsp.dongfeng.common.util.StringUtil;
import com.navinfo.opentsp.dongfeng.report.constant.Constant;
import com.navinfo.opentsp.dongfeng.report.dto.market.QueryScanCodeDto;
import com.navinfo.opentsp.dongfeng.report.pojo.market.ScanCodePojo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ScanCodeToDto {

    public static List<QueryScanCodeDto> convert(List<ScanCodePojo> datas) {
        if (ListUtil.isEmpty(datas)){
            return  Collections.emptyList();
        }

        List<QueryScanCodeDto> results = new ArrayList<>();
        for (ScanCodePojo scanCode : datas) {
            QueryScanCodeDto dto = new QueryScanCodeDto();
            dto.setChassisNum(scanCode.getChassisNum());
            dto.setPlateNum(scanCode.getPlateNum());
            dto.setTerminalId(scanCode.getTerminalId());
            dto.setSim(scanCode.getSim());
            dto.setOperaDate(DateUtil.formatTime(scanCode.getOperaDate()));
            dto.setScanOpera(scanCode.getScanOpera());
            dto.setOperator(scanCode.getOperator());
            dto.setDealer(scanCode.getDealer());
            if(StringUtil.isNotEmpty(scanCode.getLocation())){
                String[] locations = StringUtil.split(scanCode.getLocation(), ",");
                if (locations.length > 1) {
                    dto.setLon(locations[0]);
                    dto.setLat(locations[1]);
                }
            }
            dto.setRemark(scanCode.getRemark());
            if(scanCode.getSyncState() != null){
                dto.setSyncState(scanCode.getSyncState().intValue());
            }
            if (scanCode.getSyncState() != null){
                dto.setSyncStateDesc(Constant.SyncStatus.getValue(scanCode.getSyncState().intValue()));
            }
            results.add(dto);
        }
        return  results;
    }
}
