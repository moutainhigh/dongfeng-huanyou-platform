package com.navinfo.dongfeng.terminal.comm.mapper.system.car;

import com.navinfo.dongfeng.terminal.comm.model.system.vehicle.HyCar;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface HyCarMapper
{
    HyCar selectCarByComId(Map<String,Object> comMap);
    Integer updateCarByPrimaryKey(Map<String,Object> comMap);
}