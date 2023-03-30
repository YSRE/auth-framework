package com.shy.security.impl;

import com.alibaba.fastjson2.JSONObject;
import com.shy.auth.AuthUser;
import com.shy.auth.impl.LoginUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 使用LoginUser承载用户信息的时候,可以直接使用该服务做解析
 * @author shy
 * @date 2023/3/29
 */
public class DefaultLoginUserAuthenticationService extends AbstractAuthenticationService{

    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Override
    protected AuthUser parseToken(String token) {
        LoginUser loginUser = tokenManager.parse(LoginUser.class,token);
        if(loginUser.getExpireTime() == null || loginUser.getExpireTime() < System.currentTimeMillis()){
            logger.debug("用户登录信息过期. user:{}", JSONObject.toJSONString(loginUser));
            return null;
        }
        return loginUser;
    }
}
