package com.navinfo.opentsp.dongfeng.authority.repository;

import com.navinfo.opentsp.dongfeng.authority.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;


/**
 * Created by zhangy on 2017/03/10.
 */

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String>, JpaSpecificationExecutor<UserEntity>
{
    
    /**
     * 按照用户姓名和删除区分查询
     *
     * @param accountName
     * @param delFlag
     * @return
     */
    public UserEntity findByAccountNameAndDelFlag(String accountName, Integer delFlag);
    
    /**
     * 按照用户Id和删除区分查询
     *
     * @param accountId
     * @param delFlag
     * @return
     */
    public UserEntity findByAccountIdAndDelFlag(BigInteger accountId, Integer delFlag);
}
