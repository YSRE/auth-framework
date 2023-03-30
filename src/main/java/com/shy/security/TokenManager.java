package com.shy.security;

import com.shy.auth.AuthUser;
import com.shy.auth.Token;
import com.shy.auth.TokenParseException;

/**
 * token管理器
 * @author shy
 * @date 2023/3/28
 */
public interface TokenManager {

    /**
     * 创建token
     * @param user
     * @return
     */
    Token create(AuthUser user);

    /**
     * 解析token
     *
     * @param token
     * @return 用户对象
     */
    <T extends AuthUser> T parse(Class<T> clz, String token) throws TokenParseException;


    /**
     * 创建token
     * @param user
     * @param tokenOutTime token超时时间
     * @param refreshTokenOutTime  refreshToken超时时间
     * @return
     */
    Token create(AuthUser user,Integer tokenOutTime,Integer refreshTokenOutTime);
}
