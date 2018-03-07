package com.navinfo.opentsp.dongfeng.system.repository;

import com.navinfo.opentsp.dongfeng.system.entity.SecdteamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Created by Sunyu on 2017/3/17.
 */
public interface SecdteamRepository extends JpaRepository<SecdteamEntity, String>, JpaSpecificationExecutor<SecdteamEntity> {

    List<SecdteamEntity> findByDealerId(Long teamId);

}