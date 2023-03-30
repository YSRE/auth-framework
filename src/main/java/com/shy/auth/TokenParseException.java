package com.shy.auth;

/**
 * token解析异常
 * @author shy
 * @date 2023/3/27
 */
public class TokenParseException extends RuntimeException {
    public TokenParseException(Throwable cause) {
        super(cause);
    }
}
