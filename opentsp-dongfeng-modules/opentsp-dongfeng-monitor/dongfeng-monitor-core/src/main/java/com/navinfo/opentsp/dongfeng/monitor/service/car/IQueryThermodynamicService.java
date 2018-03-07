package com.navinfo.opentsp.dongfeng.monitor.service.car;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.monitor.commands.car.QueryThermodynamicCommand;

public interface IQueryThermodynamicService {
    /**
     * 查询地图热力图
     *
     * @param command
     */
    HttpCommandResultWithData queryThermodynamic(QueryThermodynamicCommand command);
    int[] getTile(long latitude, long longitude, int zoom);
    long xyzToTileId(int z, int x, int y);
}
