package com.navinfo.opentsp.dongfeng.system.service.impl.business.user;

import com.navinfo.opentsp.dongfeng.common.constant.UserTypeConstant;
import com.navinfo.opentsp.dongfeng.system.service.impl.business.user.factory.BaseOperator;

/**
 * 用户管理工厂
 *
 * @author wt
 * @version 1.0
 * @date 2017-04-11
 * @modify
 * @copyright Navi Tsp
 */
public interface IUserFactory {
    BaseOperator build(final UserTypeConstant userType);
}
