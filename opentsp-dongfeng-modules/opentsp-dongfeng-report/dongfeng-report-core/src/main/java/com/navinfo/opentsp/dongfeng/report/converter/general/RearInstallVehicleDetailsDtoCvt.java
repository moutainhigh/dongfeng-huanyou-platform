package com.navinfo.opentsp.dongfeng.report.converter.general;

import com.navinfo.opentsp.dongfeng.common.util.StringUtil;
import com.navinfo.opentsp.dongfeng.report.dto.general.QueryRearInstallVehicleDetailsDto;
import com.navinfo.opentsp.dongfeng.report.pojo.general.RearInstallVehicleDetailsPojo;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author wt
 * @version 1.0
 * @date 2017-06-05
 * @modify
 * @copyright Navi Tsp
 */
public class RearInstallVehicleDetailsDtoCvt {
    private static final Logger logger = LoggerFactory.getLogger(RearInstallVehicleDetailsDtoCvt.class);

    public static List<QueryRearInstallVehicleDetailsDto> convert(final List<RearInstallVehicleDetailsPojo> datas) {
        List<QueryRearInstallVehicleDetailsDto> results = new ArrayList<>();
        if (CollectionUtils.isEmpty(datas)) {
            logger.warn("RearInstallVehicelDetailsDtoCvt data is empty");
            return results;
        }

        for (RearInstallVehicleDetailsPojo data : datas) {
            QueryRearInstallVehicleDetailsDto dto = new QueryRearInstallVehicleDetailsDto();
            dto.setServiceStation(data.getServiceStation());
            dto.setServiceStationCode(data.getServiceStationCode());
            dto.setLon(StringUtil.nvl(data.getLon()));
            dto.setLat(StringUtil.nvl(data.getLat()));
            dto.setDate(data.getDate());
            dto.setCount(StringUtil.nvl(data.getCount()));
            results.add(dto);
        }
        return results;
    }
}
