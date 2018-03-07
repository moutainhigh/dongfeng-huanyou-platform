package com.navinfo.opentsp.dongfeng.system.service.impl;

import com.navinfo.opentsp.dongfeng.common.constant.UserTypeConstant;
import com.navinfo.opentsp.dongfeng.common.service.BaseService;
import com.navinfo.opentsp.dongfeng.system.repository.TeamMappingRepository;
import com.navinfo.opentsp.dongfeng.system.repository.UserRepository;
import com.navinfo.opentsp.dongfeng.system.service.impl.business.user.IUserFactory;
import com.navinfo.opentsp.dongfeng.system.service.impl.business.user.factory.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户管理工厂
 *
 * @author wt
 * @version 1.0
 * @date 2017-04-11
 * @modify
 * @copyright Navi Tsp
 */

@Service
public class UserFactory extends BaseService implements IUserFactory {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TeamMappingRepository teamRepository;

    @Override
    public BaseOperator build(final UserTypeConstant userType) {
        BaseOperator operator = null;
        switch (userType) {
            case SYSTEM_ADMIN:
                operator = new SystemAdmin(userRepository, teamRepository, dao);
                break;
            case CAR_FACTORY:
                operator = new VehicleFactory(userRepository, teamRepository, dao);
                break;
            case SERVICE_STATION:
                operator = new ServiceStation(userRepository, teamRepository, dao);
                break;
            case TRANSPORT:
                operator = new TransPort(userRepository, teamRepository, dao);
                break;
            case BUSINESS:
                operator = new Business(userRepository, teamRepository, dao);
                break;
            case FINANCE_COMPANY:
//                operator = new FinanceCompany(userRepository, teamRepository, dao);
                break;
            default:
                // do nothing
        }

        return operator;
    }
}
