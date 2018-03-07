package com.navinfo.dongfeng.terminal.comm.mapper.basicdata;

/**
 * 数据字典管理dao层接口
 *
 * @author baitao 2017/7/14
 *
 */

import com.navinfo.dongfeng.terminal.comm.model.basicdata.HyBasicdata;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface HyBasicdataMapper
{
    /**
     * 根据主键(多个)删除
     *
     * @param basicdataIds
     * @return
     */
    int deleteByPrimaryKey(List<String> dataId);
    
    /**
     * 新增数据字典
     * 
     * @param record
     * @return
     */
    int insert(HyBasicdata record);
    
    /**
     * 根据数据字典ID查询数据字典对象
     * 
     * @param basicdataId
     * @return
     */
    HyBasicdata selectByPrimaryKey(Long dataId);
    
    /**
     * 修改数据字典对象
     * 
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(HyBasicdata record);
    
    /**
     * 分页查询数据字典类型数据列表
     * 
     * @param map
     * @return
     */
    List<HyBasicdata> selectBasicdataByPage(Map<String, Object> map);
    
    /**
     * 分页查询字典类型据列表总数
     * 
     * @param map
     * @return
     */
    int selectCountByPage(Map<String, Object> map);
    
    /**
     * 根据分类查询数据字典列表
     * 
     * @param dataId
     * @return
     */
    List<HyBasicdata> selectByDatatype(int dataType);
    
    /**
     * 查询所有数据字典 -- 缓存
     * 
     * @param basicdata
     * @param page
     * @return
     */
    public List<HyBasicdata> findBasicdataAll();
    
    /**
     * 根据数据字典CODE查询数据字典对象
     * 
     * @param basicdatacode
     * @return
     */
    HyBasicdata selectByCode(String dataCode);
    
    /**
     * 根据数据字典CODE查询数据字典对象
     * 
     * @param basicdatacode
     * @return
     */
    HyBasicdata selectByTypeCode(Map<String, Object> map);
}