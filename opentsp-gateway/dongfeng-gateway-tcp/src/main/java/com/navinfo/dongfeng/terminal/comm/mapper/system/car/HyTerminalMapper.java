package com.navinfo.dongfeng.terminal.comm.mapper.system.car;

import com.navinfo.dongfeng.terminal.comm.model.system.vehicle.HyTerminal;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface HyTerminalMapper
{
    List<HyTerminal> selectAllForCache();
    List<String> selectAll();
}