package com.navinfo.opentsp.dongfeng.common.service.impl;

import com.navinfo.opentsp.dongfeng.common.dto.GovData;
import com.navinfo.opentsp.dongfeng.common.service.BaseService;
import com.navinfo.opentsp.dongfeng.common.service.IGovDataService;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 查询省市编码表
 *
 * @wenya
 * @create 2017-03-28 15:01
 **/
@Service
public class GovDataServiceImpl extends BaseService implements IGovDataService{

    @Override
    public List<GovData> queryParList(int pCode) {
        Map<String, Integer> map = new HashedMap();
        map.put("pCode",pCode);
        return dao.sqlFind("queryGovDataList",map ,GovData.class);
    }
    @Override
    public List<GovData> queryParListNotCounty(int pCode) {
        Map<String, Integer> map = new HashedMap();
        map.put("pCode",pCode);
        return dao.sqlFind("queryGovDataListNotCounty",map ,GovData.class);
    }

    @Override
    public GovData getGovData(int code) {
        Map<String, Integer> map = new HashedMap();
        map.put("code",code);
        GovData govData = (GovData)dao.sqlFindObject("getGovData",map ,GovData.class);
        return govData;
    }

    @Override
    public  Map<Integer,GovData> queryDatas(List<Integer> codeList) {
        Map<String, Object> map = new HashedMap();
        String codes = "";
        for(Integer c:codeList){
            codes += c + ",";
        }
        map.put("codes",codes.substring(0,codes.length()-1));
        List<GovData> govDatas = dao.sqlFind("getGovDataList",map ,GovData.class);
        Map<Integer,GovData> resultMap = new HashedMap();
        if(govDatas!=null&&!govDatas.isEmpty()){
            for(GovData key:govDatas){
                resultMap.put(key.getGovCode(), key);
            }
        }
        return resultMap;
    }
}
