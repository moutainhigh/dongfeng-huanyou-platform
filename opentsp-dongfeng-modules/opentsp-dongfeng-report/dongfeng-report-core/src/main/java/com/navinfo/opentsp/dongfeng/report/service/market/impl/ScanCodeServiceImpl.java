package com.navinfo.opentsp.dongfeng.report.service.market.impl;

import com.navinfo.opentsp.dongfeng.common.constant.UserTypeConstant;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.PagingInfo;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.common.service.BaseService;
import com.navinfo.opentsp.dongfeng.common.util.DateUtil;
import com.navinfo.opentsp.dongfeng.common.util.StringUtil;
import com.navinfo.opentsp.dongfeng.report.commands.market.QueryScanCodeCommand;
import com.navinfo.opentsp.dongfeng.report.converter.market.ScanCodeToDto;
import com.navinfo.opentsp.dongfeng.report.pojo.market.ScanCodePojo;
import com.navinfo.opentsp.dongfeng.report.service.market.IScanCodeService;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ScanCodeServiceImpl extends BaseService implements IScanCodeService {

    @Override
    public HttpCommandResultWithData queryVehicleScanCodeInfos(final QueryScanCodeCommand command, final boolean isQueryAll) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        if (!checkAuthority(command)) {
            result.fillResult(ReturnCode.NO_AUTHORITY_VIEW_SCANREPORT);
        }

        Map<String, Object> parameters = cvtQueryParas(command);
        PagingInfo<ScanCodePojo> datas = new PagingInfo<>();
        if (isQueryAll){
            List<ScanCodePojo> vehicles = dao.sqlFind("queryVehicleScanCodeInfos", parameters, ScanCodePojo.class);
            datas.setList(vehicles);
        } else {
            datas = dao.sqlPagelFind("queryVehicleScanCodeInfos", parameters,
                    NumberUtils.toInt(command.getPage_number()), NumberUtils.toInt(command.getPage_size()), ScanCodePojo.class);
        }

        PagingInfo page = new PagingInfo();
        page.setList(ScanCodeToDto.convert(datas.getList()));
        page.setPage_total(datas.getPage_total());
        page.setTotal(datas.getTotal());
        result.setData(page);
        result.fillResult(ReturnCode.OK);
        return result;
    }

    private Map<String, Object> cvtQueryParas(final QueryScanCodeCommand command) {
        Map<String, Object> parameters = new HashMap<String , Object>();
        if (StringUtils.isNotEmpty(command.getOperaDate())) {
            parameters.put("startTime", command.getOperaDate());
            parameters.put("endTime", DateUtil.formatDate(DateUtil.addDay(DateUtil.parseDate(command.getOperaDate()), 1)));
        }

        parameters.put("chassisNum", command.getChassisNum());
        parameters.put("scanOperaNum", StringUtil.nvl(command.getScanOpera()));
        parameters.put("dealer", command.getDealer());
        parameters.put("accountId", command.getUserInfor().getUserId());
        parameters.put("accountType", command.getUserInfor().getType());
        parameters.put("jobType", command.getUserInfor().getJobType());
        return parameters;
    }

    private boolean checkAuthority(final QueryScanCodeCommand command){
        return command.getUserInfor().getType() == UserTypeConstant.SYSTEM_ADMIN.getCode()
                || command.getUserInfor().getType() == UserTypeConstant.CAR_FACTORY.getCode();
    }
}
