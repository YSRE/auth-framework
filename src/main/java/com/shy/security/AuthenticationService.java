package com.shy.security;

import jakarta.servlet.http.HttpServletRequest;

/**
 * @author shy
 * @date 2023/3/28
 */
public interface AuthenticationService {

    void auth(HttpServletRequest req);
}
