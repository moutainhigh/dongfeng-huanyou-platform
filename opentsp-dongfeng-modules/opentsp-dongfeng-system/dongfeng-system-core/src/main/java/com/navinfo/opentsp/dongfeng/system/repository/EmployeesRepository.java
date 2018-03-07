package com.navinfo.opentsp.dongfeng.system.repository;

import com.navinfo.opentsp.dongfeng.system.entity.EmployeesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Created by Sunyu on 2017/3/17.
 */
public interface EmployeesRepository extends JpaRepository<EmployeesEntity, String>, JpaSpecificationExecutor<EmployeesEntity> {

    List<EmployeesEntity> findByTeamId(Long teamId);
}