package com.navinfo.opentsp.dongfeng.system.repository;

import com.navinfo.opentsp.dongfeng.system.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

/**
 * Created by yaocy on 2017/03/10.
 */

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String>, JpaSpecificationExecutor<UserEntity> {

    public UserEntity findByAccountId(BigInteger accountId);

    /**
     * 按照用户姓名和删除区分查询
     *
     * @param accountName
     * @param delFlag
     * @return
     */
    UserEntity findByAccountNameAndDelFlag(String accountName, Integer delFlag);

    /**
     * 按照用户姓名
     *
     * @param accountName
     * @return
     */
    UserEntity findByAccountName(String accountName);
}
