package com.navinfo.opentsp.dongfeng.kafka.repository;

import com.navinfo.opentsp.dongfeng.kafka.entity.HyCarEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

/**
 * @author tushenghong
 * @version 1.0
 * @date 2017-05-17
 * @modify
 * @copyright Navi Tsp
 */
@Repository
public interface CarRepository extends JpaRepository<HyCarEntity, String>, JpaSpecificationExecutor<HyCarEntity> {
    HyCarEntity findByCarId(BigInteger id);
}