package com.shy.auth;

import com.shy.auth.impl.LoginUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;

/**
 * 用户上下文
 * @author shy
 * @date 2023/3/28
 */
public class UserContext {

    public static LoginUser getUser(){
        // AuthenticationService::auth 方法负责认证,并将用户信息保存到SecurityContextHolder
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return null;
        }
        Object details = authentication.getDetails();
        if (details instanceof LoginUser) {
            return (LoginUser) details;
        }
        return null;
    }

    public static Long getUserId(){
        return Objects.requireNonNull(getUser()).getUid();
    }
}
