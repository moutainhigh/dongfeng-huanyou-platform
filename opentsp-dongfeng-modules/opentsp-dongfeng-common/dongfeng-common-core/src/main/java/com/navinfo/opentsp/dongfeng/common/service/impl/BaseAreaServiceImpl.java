package com.navinfo.opentsp.dongfeng.common.service.impl;

import com.navinfo.opentsp.dongfeng.common.dto.BaseAreaDto;
import com.navinfo.opentsp.dongfeng.common.service.BaseService;
import com.navinfo.opentsp.dongfeng.common.service.IBaseAreaService;
import com.navinfo.opentsp.dongfeng.common.util.StringUtil;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author: tushenghong
 * @version: 1.0
 * @since: 2017-10-09
 **/
@Service
public class BaseAreaServiceImpl extends BaseService implements IBaseAreaService {
    @Override
    public List<BaseAreaDto> queryAreaByParentCode(String pCode) {
        Map<String, String> map = new HashedMap();
        map.put("pCode", pCode);
        return dao.sqlFind("queryAreaByParentCode", map, BaseAreaDto.class);
    }

    @Override
    public Map<Integer, BaseAreaDto> queryAreaInfoByAreaCode(List<Integer> codeList) {
        Map<String, Object> map = new HashedMap();
        StringBuffer codes = new StringBuffer();
        for (Integer c : codeList) {
            codes.append(c).append(",");
        }
        map.put("codes", codes.toString().substring(0, codes.toString().length() - 1));
        List<BaseAreaDto> govDatas = dao.sqlFind("queryAreaInfoByAreaCode", map, BaseAreaDto.class);
        Map<Integer, BaseAreaDto> resultMap = new HashedMap();
        if (govDatas != null && !govDatas.isEmpty()) {
            for (BaseAreaDto key : govDatas) {
                resultMap.put(StringUtil.toInteger(key.getGovCode()), key);
            }
        }
        return resultMap;
    }
}
