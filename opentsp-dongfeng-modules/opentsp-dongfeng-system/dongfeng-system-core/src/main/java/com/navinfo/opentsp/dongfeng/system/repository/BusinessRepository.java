package com.navinfo.opentsp.dongfeng.system.repository;

import com.navinfo.opentsp.dongfeng.system.entity.BusinessEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

/**
 * Created by yaocy on 2017/03/14.
 */

@Repository
public interface BusinessRepository extends JpaRepository<BusinessEntity, BigInteger>, JpaSpecificationExecutor<BusinessEntity> {

}
