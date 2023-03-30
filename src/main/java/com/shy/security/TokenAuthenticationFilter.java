package com.shy.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

/**
 * 验证token的过滤器
 * @author shy
 * @date 2023/3/28
 */
public class TokenAuthenticationFilter extends GenericFilterBean {

    private final AuthenticationService authService;

    public TokenAuthenticationFilter(AuthenticationService authService){
        this.authService = authService;
    }
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        authService.auth(req);
        chain.doFilter(request,response);
    }
}
