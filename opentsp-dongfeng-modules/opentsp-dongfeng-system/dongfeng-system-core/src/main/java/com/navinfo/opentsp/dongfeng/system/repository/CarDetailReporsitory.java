package com.navinfo.opentsp.dongfeng.system.repository;

import com.navinfo.opentsp.dongfeng.system.entity.CarDetailEntity;
import com.navinfo.opentsp.dongfeng.system.entity.CarEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CarDetailReporsitory extends JpaRepository<CarDetailEntity, String>, JpaSpecificationExecutor<CarEntity> {
	
	CarDetailEntity findById(Long carId);
}
