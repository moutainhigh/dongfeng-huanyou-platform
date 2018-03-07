package com.navinfo.dongfeng.terminal.comm.mapper.system.instructlayin;

import com.navinfo.dongfeng.terminal.comm.model.system.instructlayin.HyInstructLayin;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HyInstructLayinMapper
{
    
    /****
     * 新增
     ****/
    int insertSelective(HyInstructLayin record);
    
    /****
     * 根据主键更新记录
     ****/
    int updateByPrimaryKeySelective(HyInstructLayin record);
}