package com.navinfo.opentsp.dongfeng.openapi.core.handler.abstracts;

import com.navinfo.opentsp.dongfeng.authority.commands.OpenApiLoginCommand;
import com.navinfo.opentsp.dongfeng.common.client.AuthorityClient;
import com.navinfo.opentsp.dongfeng.common.handler.NotValidateTokenAbstractHandler;
import com.navinfo.opentsp.dongfeng.common.result.CommonResult;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.openapi.commands.base.BaseOpenApiCommand;
import com.navinfo.opentsp.dongfeng.openapi.core.util.OpenApiLoginUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 使用用户自定义方法校验抽象处理类
 *
 * @author tushenghong
 * @version 1.0
 * @date 2017-07-18
 * @modify
 * @copyright Navi Tsp
 */
public abstract class ValidateUsingUserDefinedMethodAbstractHandler<C extends BaseOpenApiCommand, CR extends CommonResult> extends NotValidateTokenAbstractHandler<C, CR> {

    protected static final Logger logger = LoggerFactory.getLogger(ValidateUsingUserDefinedMethodAbstractHandler.class);

    protected ValidateUsingUserDefinedMethodAbstractHandler(Class<C> commandType, Class<CR> resultType) {
        super(commandType, resultType);
    }

    @Autowired
    AuthorityClient authorityClient;


    @Override
    protected CR businessHandle(C command) {
        // 获取登陆参数
        OpenApiLoginCommand openApiLoginCommand = OpenApiLoginUtil.getInstance().getOpenApiLoginCommand(command);
        // 获取返回结果
        CommonResult loginResult = authorityClient.openApilogin(openApiLoginCommand);
        // 登陆失败
        if (loginResult == null || loginResult.getResultCode() != ReturnCode.OK.code()) {
            return (CR) loginResult;
        }
        // 登陆成功
        return openApiHandle(command);
    }

    /**
     * api 处理逻辑
     *
     * @param command
     * @return
     */
    protected abstract CR openApiHandle(C command);

}
