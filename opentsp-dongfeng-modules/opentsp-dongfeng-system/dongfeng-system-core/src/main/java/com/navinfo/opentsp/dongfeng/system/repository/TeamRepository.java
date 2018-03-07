package com.navinfo.opentsp.dongfeng.system.repository;

import com.navinfo.opentsp.dongfeng.system.entity.TeamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by Rex on 2017/3/13.
 */
public interface TeamRepository extends JpaRepository<TeamEntity, String>, JpaSpecificationExecutor<TeamEntity> {
    long countByDealerCodeAndDelFlag(String dealerCode,int delFlag);

    List<TeamEntity> findByTnameAndDelFlag(String team,int delFlag);

    List<TeamEntity> findByParentIdAndDelFlag(Long parentId,int delFlag);

    TeamEntity findByIdAndDelFlag(BigInteger id,int delFlag);

    long countByParentIdAndTnameAndDelFlag(Long parentId,String tname,int delFlag);

    /**
     * 验证经销商名称重复
     * @param tname  经销商名称
     * @param delFlag  删除标识
     * @return 数据总行数
     */
    long countByTnameAndDelFlag(String tname,int delFlag);
}
