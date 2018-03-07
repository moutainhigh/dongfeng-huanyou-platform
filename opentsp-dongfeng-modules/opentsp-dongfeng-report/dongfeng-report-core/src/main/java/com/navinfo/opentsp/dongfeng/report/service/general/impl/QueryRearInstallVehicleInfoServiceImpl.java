package com.navinfo.opentsp.dongfeng.report.service.general.impl;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.PagingInfo;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.common.service.BaseService;
import com.navinfo.opentsp.dongfeng.common.util.DateUtils;
import com.navinfo.opentsp.dongfeng.report.commands.general.QueryRearInstallVehicleCommand;
import com.navinfo.opentsp.dongfeng.report.commands.general.QueryRearInstallVehicleDetailsCommand;
import com.navinfo.opentsp.dongfeng.report.converter.general.RearInstallVehicleDetailsDtoCvt;
import com.navinfo.opentsp.dongfeng.report.converter.general.RearInstallVehicleDtoCvt;
import com.navinfo.opentsp.dongfeng.report.pojo.general.RearInstallVehicleDetailsPojo;
import com.navinfo.opentsp.dongfeng.report.pojo.general.RearInstallVehiclePojo;
import com.navinfo.opentsp.dongfeng.report.service.general.IQueryRearInstallVehicleInfoService;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author wt
 * @version 1.0
 * @date 2017-06-05
 * @modify
 * @copyright Navi Tsp
 */
@Service
public class QueryRearInstallVehicleInfoServiceImpl extends BaseService implements IQueryRearInstallVehicleInfoService {

    @Override
    public HttpCommandResultWithData queryVehicleCounts(final QueryRearInstallVehicleCommand command) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("accountId", command.getUserInfor().getUserId());
        parameters.put("accountType", command.getUserInfor().getType());
        final long[] times = cvtTimeRanges(command);
        parameters.put("startTime", times[0]);
        parameters.put("endTime", times[1]);

        PagingInfo page = new PagingInfo<>();
        final List<RearInstallVehiclePojo> vehicles = dao.sqlFind("queryVehicleCounts", parameters, RearInstallVehiclePojo.class);
        page.setList(RearInstallVehicleDtoCvt.convert(vehicles));
        page.setTotal(vehicles.size());
        result.setData(page);
        result.fillResult(ReturnCode.OK);
        return result;
    }

    @Override
    public HttpCommandResultWithData queryVehicleDetails(final QueryRearInstallVehicleDetailsCommand command, final boolean isQueryAll) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        Map<String, Object> parameters = new HashMap<String, Object>();
        final long[] times = DateUtils.getDayBeginAndEndTime(command.getQueryTime(), DateUtils.ISO_DATE_FORMAT_DAY);
        parameters.put("startTime", times[0]);
        parameters.put("endTime", times[1]);
        parameters.put("accountId", command.getUserInfor().getUserId());
        parameters.put("accountType", command.getUserInfor().getType());

        PagingInfo<RearInstallVehicleDetailsPojo> datas = new PagingInfo<>();
        if (isQueryAll) {
            List<RearInstallVehicleDetailsPojo> vehicles = dao.sqlFind("queryVehicleDetails", parameters, RearInstallVehicleDetailsPojo.class);
            datas.setList(vehicles);
        } else {
            datas = dao.sqlPagelFind("queryVehicleDetails", parameters,
                    NumberUtils.toInt(command.getPage_number()), NumberUtils.toInt(command.getPage_size()), RearInstallVehicleDetailsPojo.class);
        }

        PagingInfo page = new PagingInfo();
        page.setList(RearInstallVehicleDetailsDtoCvt.convert(datas.getList()));
        page.setPage_total(datas.getPage_total());
        page.setTotal(datas.getTotal());
        result.setData(page);
        result.fillResult(ReturnCode.OK);

        return result;
    }

    private long[] cvtTimeRanges(final QueryRearInstallVehicleCommand command) {
        final long[] times = new long[2];
        times[0] = DateUtils.getFirstDayForMonth(command.getQueryTime(), DateUtils.ISO_DATE_FORMAT_MONTH);

        final long endTime = DateUtils.getLastDayForMonth(command.getQueryTime(), DateUtils.ISO_DATE_FORMAT_MONTH) + DateUtils.SECOND_PER_DAY;
        final long currentTime = DateUtils.getCurrentDayTimes(DateUtils.ISO_DATE_FORMAT_DAY) + DateUtils.SECOND_PER_DAY;
        times[1] = (currentTime < endTime) ? currentTime : endTime;
        return times;
    }
}
