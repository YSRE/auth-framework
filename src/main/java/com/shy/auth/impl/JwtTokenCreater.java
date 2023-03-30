package com.shy.auth.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shy.auth.AuthUser;
import com.shy.auth.Token;
import com.shy.auth.TokenCreater;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT方式生成token
 * @author shy
 * @date 2023-03-27
 */
public final class JwtTokenCreater implements TokenCreater {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private String secret;

    private int accessTokenExp;

    private int refreshTokenExp;

    public JwtTokenCreater(String secret) {
        if(secret == null){
            throw new IllegalArgumentException("密钥不存在");
        }
        this.secret = secret;

        accessTokenExp = 60 * 60;

        refreshTokenExp = 60 * 60 * 48;
    }

    @Override
    public Token create(AuthUser user) {

        ObjectMapper oMapper = new ObjectMapper();
        //把用户信息打平
        Map<String, ?> userMap = oMapper.convertValue(user, HashMap.class);
        logger.info("签名Map:{}",userMap);
        //使用Calendar计算时间的加减，以免出现精度问题
        Calendar cal = Calendar.getInstance();

        //设置为当期日期
        cal.setTime(new Date());
        //由当前日期加上访问令牌的有效时长即为访问令牌失效时间
        cal.add(Calendar.SECOND, accessTokenExp);
        logger.info("Claims:{}",cal);
        String accessToken = Jwts.builder()
                .setClaims(userMap)
                .setSubject("user")
                .setExpiration(cal.getTime())
                .signWith(SignatureAlgorithm.HS512, secret.getBytes())
                .compact();

        //设置为当期日期
        cal.setTime(new Date());
        //由当前日期加上刷新令牌的有效时长即为刷新令牌失效时间
        cal.add(Calendar.SECOND, refreshTokenExp);
        String refreshToken = Jwts.builder()
                .setClaims(userMap)
                .setSubject("user")
                .setExpiration(cal.getTime())
                .signWith(SignatureAlgorithm.HS512, secret.getBytes())
                .compact();

        Token token = new Token();
        token.setAccessToken(accessToken);
        token.setRefreshToken(refreshToken);


        return token;
    }


    public JwtTokenCreater setSecret(String secret) {
        this.secret = secret;
        return this;
    }

    public JwtTokenCreater setAccessTokenExp(int accessTokenExp) {
        this.accessTokenExp = accessTokenExp;
        return this;
    }

    public JwtTokenCreater setRefreshTokenExp(int refreshTokenExp) {
        this.refreshTokenExp = refreshTokenExp;
        return this;
    }
}
