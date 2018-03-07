package com.navinfo.opentsp.dongfeng.common.service.impl;

import com.navinfo.opentsp.dongfeng.common.dto.BaseData;
import com.navinfo.opentsp.dongfeng.common.service.BaseService;
import com.navinfo.opentsp.dongfeng.common.service.IBaseDataService;
import com.navinfo.opentsp.dongfeng.common.util.StringUtil;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by yaocy on 2017/03/11.
 * 数据字典Service
 */
@Service
public class BaseDataServiceImpl extends BaseService implements IBaseDataService {
    /**
     * Brand Service LOG
     */
    protected static final Logger logger = LoggerFactory.getLogger(BaseDataServiceImpl.class);

    @Override
    public List<BaseData> queryList (int type) {
        Map<String, Integer> map = new HashedMap();
        map.put("type",type);
        return dao.sqlFind("queryBasicDataList",map ,BaseData.class);
    }

	@Override
	public List<BaseData> queryList(int type, int dataCode) {
		
        Map<String, Integer> map = new HashedMap();
        map.put("type",type);
        map.put("dataCode", dataCode);
        return dao.sqlFind("queryBasicDataListWithCode",map ,BaseData.class);
	}

    @Override
    public BaseData getData(String code, int type) {
        Map<String, Object> map = new HashedMap();
        map.put("code", code);
        map.put("type",type);
        BaseData data = null;
        List<BaseData> list = dao.sqlFind("getBasicData",map ,BaseData.class);
        if (!StringUtil.isEmpty(list)) {
            data = list.get(0);
        }
        return data;
    }

}
