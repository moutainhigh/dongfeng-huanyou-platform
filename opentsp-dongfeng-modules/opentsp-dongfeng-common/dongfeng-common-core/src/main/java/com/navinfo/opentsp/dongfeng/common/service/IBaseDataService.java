package com.navinfo.opentsp.dongfeng.common.service;


import com.navinfo.opentsp.dongfeng.common.dto.BaseData;

import java.util.List;

/**
 * Created by yaocy on 2017/03/11.
 * 查询数据字典Service
 */

public interface IBaseDataService {

    public List<BaseData> queryList (int type);
    
	public List<BaseData> queryList(int type , int dataCode);

    public BaseData getData (String code, int type);
}
