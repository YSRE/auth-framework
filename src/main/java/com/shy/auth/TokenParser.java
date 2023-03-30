package com.shy.auth;

/**
 * token解析器
 * @author shy
 * @date 2023/3/27
 */
public interface TokenParser {

    /**
     * 解析token
     * @param clz 业务认证实体类型
     * @param token
     * @return
     * @param <T>
     * @throws TokenParseException
     */
    <T extends AuthUser>  T parse(Class<T> clz, String token) throws TokenParseException;
}
