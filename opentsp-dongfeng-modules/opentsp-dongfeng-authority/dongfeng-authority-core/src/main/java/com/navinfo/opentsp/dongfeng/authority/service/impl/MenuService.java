package com.navinfo.opentsp.dongfeng.authority.service.impl;

import com.navinfo.opentsp.dongfeng.authority.pojo.MenuPojo;
import com.navinfo.opentsp.dongfeng.authority.service.IMenuService;
import com.navinfo.opentsp.dongfeng.common.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author tushenghong
 * @version 1.0
 * @date 2017-03-14
 * @modify
 * @copyright Navi Tsp
 */
@Service
public class MenuService extends BaseService implements IMenuService {

    @Override
    public List<MenuPojo> queryMenuByUserId(Long userId, Integer userType) {
        logger.info("query menu,userId:{}", userId);
        Map<String, Object> param = new HashMap<>();
        param.put("userId", userId);
        param.put("userType", userType);
        return dao.sqlFind("queryMenu", param, MenuPojo.class);
    }

    @Override
    public List<MenuPojo> queryMenuByCodes(String codes) {
        logger.info("query menu,codes:{}", codes);
        Map<String, Object> param = new HashMap<>();
        param.put("codes", codes);
        return dao.sqlFind("queryMenuByCodes", param, MenuPojo.class);
    }
}
