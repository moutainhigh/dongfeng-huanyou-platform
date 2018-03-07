package com.navinfo.opentsp.dongfeng.common.service;

import com.navinfo.opentsp.dongfeng.common.dto.GovData;

import java.util.List;
import java.util.Map;

/**
 * 查询省市编码表 hy_gov_org
 * Created by wenya on 2017/3/28.
 */
public interface IGovDataService {

    public List<GovData> queryParList (int pCode);

    public List<GovData> queryParListNotCounty(int pCode);

    public GovData getGovData(int code);

    public Map<Integer,GovData> queryDatas(List<Integer> codes);
}
