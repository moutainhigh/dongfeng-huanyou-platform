package com.navinfo.opentsp.dongfeng.report.converter.general;

import com.navinfo.opentsp.dongfeng.common.util.StringUtil;
import com.navinfo.opentsp.dongfeng.report.dto.general.QueryRearInstallVehicleDto;
import com.navinfo.opentsp.dongfeng.report.pojo.general.RearInstallVehiclePojo;
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
public class RearInstallVehicleDtoCvt {
    private static final Logger logger = LoggerFactory.getLogger(RearInstallVehicleDtoCvt.class);

    public static List<QueryRearInstallVehicleDto> convert(final List<RearInstallVehiclePojo> datas) {
        List<QueryRearInstallVehicleDto> results = new ArrayList<>();
        if (CollectionUtils.isEmpty(datas)) {
            logger.warn("RearInstallVehicelDtoCvt data is empty");
            return results;
        }

        for (RearInstallVehiclePojo data : datas) {
            QueryRearInstallVehicleDto dto = new QueryRearInstallVehicleDto();
            dto.setDate(data.getDate());
            dto.setCount(StringUtil.nvl(data.getCount()));
            results.add(dto);
        }
        return results;
    }
}
