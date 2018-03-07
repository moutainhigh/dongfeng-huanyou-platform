package com.navinfo.opentsp.dongfeng.common.service;

import com.navinfo.opentsp.dongfeng.common.dto.BaseAreaDto;

import java.util.List;
import java.util.Map;

/**
 * 图吧区域字典表
 *
 * @author: tushenghong
 * @version: 1.0
 * @since: 2017-10-09
 **/
public interface IBaseAreaService {
    /**
     * @param pCode 父级区域code
     * @return
     */
    List<BaseAreaDto> queryAreaByParentCode(String pCode);

    /**
     * @param codes 区域code
     * @return
     */
    Map<Integer, BaseAreaDto> queryAreaInfoByAreaCode(List<Integer> codes);
}
