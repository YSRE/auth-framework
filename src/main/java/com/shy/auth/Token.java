package com.shy.auth;

import lombok.Data;

/**
 * token实体
 * @author shy
 * @date 2023/3/27
 */
@Data
public class Token {

    /**
     * token令牌
     */
    private String accessToken;


    /**
     * 刷新token
     */
    private String refreshToken;
}
