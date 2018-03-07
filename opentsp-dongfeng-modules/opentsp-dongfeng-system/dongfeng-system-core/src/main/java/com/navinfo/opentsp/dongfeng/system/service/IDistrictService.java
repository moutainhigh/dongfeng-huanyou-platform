package com.navinfo.opentsp.dongfeng.system.service;

import com.navinfo.opentsp.dongfeng.common.result.CommonResult;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.system.commands.district.*;

/**
 * 销售区域Service
 */
public interface IDistrictService {

    /**
     * 销售区域搜索 by Sunyu
     *
     * @param command
     * @return
     */
    HttpCommandResultWithData sqlPageDistrict(QueryDistrictCommand command);

    /**
     * 添加用户 by liht
     * @param command
     * @return
     */
    CommonResult addDistrict(AddDistrictCommand command) throws Exception;

    /**
     * 查询分组 by yaocy
     * @param command
     * @return
     */
    public HttpCommandResultWithData getTeam (GetTeamCommand command);

    /**
     * 修改区域 by liht
     * @param command
     * @return
     */
    CommonResult updateDistrict(UpdateDistrictCommand command) throws Exception;
    //查询某个销售区域
    public HttpCommandResultWithData getDistrict(GetDistrictCommand command);


}