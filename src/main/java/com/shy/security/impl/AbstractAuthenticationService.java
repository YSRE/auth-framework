package com.shy.security.impl;

import com.alibaba.fastjson2.JSONObject;
import com.shy.security.TokenManager;
import com.shy.security.config.SecurityProperties;
import jakarta.servlet.http.HttpServletRequest;
import com.shy.auth.AuthUser;
import com.shy.security.AuthenticationService;
import com.shy.security.config.TokenConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shy
 * @date 2023/3/28
 */
public abstract class AbstractAuthenticationService implements AuthenticationService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected TokenManager tokenManager;

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public void auth(HttpServletRequest req) {
        String token = this.getToken(req);
        if (StringUtils.isEmpty(token)) {
            return;
        }
        logger.info("auth. path:{}", req.getServletPath());
        Authentication authentication = getAuthentication(token);
        if (authentication != null) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
    }

    protected Authentication getAuthentication(String token) {
        try {
            AuthUser user = null;
            if(securityProperties.getMock().isEnable()){
                user = securityProperties.getMock().getUser();
                logger.warn("mock 用户信息. user:{}", JSONObject.toJSONString(user));
            }else{
                user = parseToken(token);
                if (user == null) {
                    logger.debug("token失效或不存在");
                    return null;
                }
            }

            List<GrantedAuthority> auths = new ArrayList<>();
            List<String> roles = user.getRoles();
            for (String role : roles) {
                auths.add(new SimpleGrantedAuthority("ROLE_" + role));
            }
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken("user", null, auths);
            authentication.setDetails(user);
            return authentication;
        } catch (Exception e) {
            logger.warn("JWT解析失败,认证异常 ex:", e);
            return null;
        }
    }

    /**
     * 根据具体业务 解析token
     *
     * @param token
     * @return
     */
    protected abstract AuthUser parseToken(String token);

    protected String getToken(HttpServletRequest req) {
        String token = req.getHeader(TokenConstant.HEADER_STRING);
        if (!StringUtils.isEmpty(token)) {
            token = token.replaceAll(TokenConstant.TOKEN_PREFIX, "").trim();
        }
        return token;
    }
}
