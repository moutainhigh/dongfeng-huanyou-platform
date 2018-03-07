package com.navinfo.opentsp.dongfeng.authority.service;


import com.navinfo.opentsp.dongfeng.authority.pojo.MenuPojo;

import java.util.List;

/**
 * 目录服务
 *
 * @author tushenghong
 * @version 1.0
 * @date 2017-03-13
 * @modify
 * @copyright Navi Tsp
 */
public interface IMenuService {
    /**
     * 查询用户可见的目录结构
     *
     * @param userId   用户id
     * @param userType 用户类型
     * @return
     */
    List<MenuPojo> queryMenuByUserId(Long userId, Integer userType);

    /**
     * 根据编码查询目录
     *
     * @param codes 目录编码，多个用逗号隔开
     * @return
     */
    List<MenuPojo> queryMenuByCodes(String codes);

}
