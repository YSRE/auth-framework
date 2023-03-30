package com.shy.auth;

/**
 * token生成器
 * @author shy
 * @date 2023/3/27
 */
public interface TokenCreater {

    /**
     * 创建token
     * @param user 用户
     * @return token
     */
    Token create(AuthUser user);
}
