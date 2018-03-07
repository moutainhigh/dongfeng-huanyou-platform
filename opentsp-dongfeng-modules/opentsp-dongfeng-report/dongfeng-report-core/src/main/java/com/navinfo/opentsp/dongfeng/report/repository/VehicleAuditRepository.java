package com.navinfo.opentsp.dongfeng.report.repository;

import com.navinfo.opentsp.dongfeng.report.entity.market.VehicleAuditEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

/**
 * @author wt
 * @version 1.0
 * @date 2017/11/28
 * @modify
 * @copyright
 */
@Repository
public interface VehicleAuditRepository extends JpaRepository<VehicleAuditEntity, String>, JpaSpecificationExecutor<VehicleAuditEntity> {
    VehicleAuditEntity findById(BigInteger id);
}
