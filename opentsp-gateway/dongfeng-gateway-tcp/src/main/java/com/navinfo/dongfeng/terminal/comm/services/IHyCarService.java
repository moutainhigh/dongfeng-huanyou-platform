package com.navinfo.dongfeng.terminal.comm.services;

import com.navinfo.dongfeng.terminal.comm.model.system.vehicle.HyCar;

import java.util.Map;

public interface IHyCarService
{
	HyCar selectCarByComId(Map<String,Object> comMap);
	Integer updateCarByPrimaryKey(Map<String,Object> comMap);
}