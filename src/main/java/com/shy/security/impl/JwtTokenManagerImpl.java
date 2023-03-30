package com.shy.security.impl;

import com.shy.auth.Token;
import com.shy.auth.TokenParseException;
import com.shy.security.TokenManager;
import com.shy.security.config.SecurityProperties;
import com.shy.auth.AuthUser;
import com.shy.auth.impl.JwtTokenCreater;
import com.shy.auth.impl.JwtTokenParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author shy
 * @date 2023/3/28
 */
@Service
public class JwtTokenManagerImpl implements TokenManager {

    @Autowired
    private SecurityProperties securityProperties;

    public JwtTokenManagerImpl(){
    }
    @Override
    public Token create(AuthUser user) {
        JwtTokenCreater tokenCreater = new JwtTokenCreater(securityProperties.getTokenSecret());
        tokenCreater.setAccessTokenExp(securityProperties.getAccessTokenTimeout());
        tokenCreater.setRefreshTokenExp(securityProperties.getRefreshTokenTimeout());
        return tokenCreater.create(user);
    }

    @Override
    public <T extends AuthUser> T parse(Class<T> clz, String token) throws TokenParseException {
        JwtTokenParser tokenParser = new JwtTokenParser(securityProperties.getTokenSecret());
        return tokenParser.parse(clz, token);
    }

    @Override
    public Token create(AuthUser user, Integer tokenOutTime, Integer refreshTokenOutTime) {
        JwtTokenCreater tokenCreater = new JwtTokenCreater(securityProperties.getTokenSecret());
        if (null == tokenOutTime){
            tokenCreater.setAccessTokenExp(securityProperties.getAccessTokenTimeout());
        }else{
            tokenCreater.setAccessTokenExp(tokenOutTime);
        }
        if (null == refreshTokenOutTime){
            tokenCreater.setRefreshTokenExp(securityProperties.getRefreshTokenTimeout());
        }else{
            tokenCreater.setRefreshTokenExp(refreshTokenOutTime);
        }
        return tokenCreater.create(user);
    }
}
