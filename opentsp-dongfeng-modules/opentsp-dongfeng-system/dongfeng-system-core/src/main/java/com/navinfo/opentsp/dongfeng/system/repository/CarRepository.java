package com.navinfo.opentsp.dongfeng.system.repository;

import com.navinfo.opentsp.dongfeng.system.entity.CarEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Created by Sunyu on 2017/3/17.
 */
public interface CarRepository extends JpaRepository<CarEntity, String>, JpaSpecificationExecutor<CarEntity> {

    List<CarEntity> findByDealerIdAndDelFlag(Long carTeamId, Integer delFlag);
    CarEntity findByCarId(Long carId);
}