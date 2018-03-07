package com.navinfo.opentsp.dongfeng.system.repository;

import com.navinfo.opentsp.dongfeng.system.entity.HyTerminalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by Sunyu on 2017/3/17.
 */
public interface TerminalRepository extends JpaRepository<HyTerminalEntity, String>, JpaSpecificationExecutor<HyTerminalEntity> {

    List<HyTerminalEntity> findByTTeamIdAndDelFlag(BigInteger tTeamId, Integer delFlag);
}