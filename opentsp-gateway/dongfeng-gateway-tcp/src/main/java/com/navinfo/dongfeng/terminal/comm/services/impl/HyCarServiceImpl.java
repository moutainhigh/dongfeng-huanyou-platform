package com.navinfo.dongfeng.terminal.comm.services.impl;

import com.navinfo.dongfeng.terminal.comm.mapper.system.car.HyCarMapper;
import com.navinfo.dongfeng.terminal.comm.model.system.vehicle.HyCar;
import com.navinfo.dongfeng.terminal.comm.services.IHyCarService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class HyCarServiceImpl implements IHyCarService
{

    @Resource
    private HyCarMapper hyCarMapper;

    @Override
    public HyCar selectCarByComId(Map<String, Object> comMap) {
        return hyCarMapper.selectCarByComId(comMap);
    }

    @Override
    public Integer updateCarByPrimaryKey(Map<String, Object> comMap) {
        return hyCarMapper.updateCarByPrimaryKey(comMap);
    }
}