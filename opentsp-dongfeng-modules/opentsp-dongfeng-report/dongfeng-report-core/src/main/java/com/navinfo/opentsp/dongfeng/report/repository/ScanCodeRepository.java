package com.navinfo.opentsp.dongfeng.report.repository;

import com.navinfo.opentsp.dongfeng.report.entity.market.ScanCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author wt
 * @version 1.0
 * @date 2017/9/6
 * @modify
 * @copyright
 */
@Repository
public interface ScanCodeRepository extends JpaRepository<ScanCodeEntity, String>, JpaSpecificationExecutor<ScanCodeEntity> {

    ScanCodeEntity findByChassisNum(String chassisNum);

}
