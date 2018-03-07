package com.navinfo.opentsp.dongfeng.report.repository;

import com.navinfo.opentsp.dongfeng.report.entity.HyPersonEntity;

import java.util.List;

/**
 * Created by zhangy on 2017/3/1.
 */

// @Repository
// public interface HyPersonRepository extends JpaRepository<HyPersonEntity, String>,
// JpaSpecificationExecutor<HyPersonEntity> {
interface HyPersonRepositor
{
    long countById(long id);
    
    public HyPersonEntity findByFirstNameAndLastName(String firstName, String lastName);
    
    public List<HyPersonEntity> findByFirstName(String firstName);
    
    public List<HyPersonEntity> findByLastName(String lastName);
    
}
