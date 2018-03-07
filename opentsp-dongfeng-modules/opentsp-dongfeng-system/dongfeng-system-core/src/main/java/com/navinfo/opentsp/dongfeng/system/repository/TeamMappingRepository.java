package com.navinfo.opentsp.dongfeng.system.repository;

import com.navinfo.opentsp.dongfeng.system.entity.AccountTeamMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by yaocy on 2017/03/10.
 */

@Repository
public interface TeamMappingRepository extends JpaRepository<AccountTeamMapping, String>, JpaSpecificationExecutor<AccountTeamMapping> {

    public List<AccountTeamMapping> findByAccountId(BigInteger accountId);

    public List<AccountTeamMapping> findByCarTeamIdAndAtcType(BigInteger carTeamId,int atcType);

    Integer deleteByCarTeamIdAndAtcType(BigInteger carTeamId, int atcType);
}
