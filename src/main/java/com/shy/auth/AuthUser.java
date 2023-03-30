package com.shy.auth;

import java.util.List;

/**
 * 认证的用户
 * @author shy
 * @date 2023-03-27
 */
public interface AuthUser {

    List<String> getRoles();

    void setRoles(List<String> roles);

    List<String> getPermissions();

    void setPermissions(List<String> permissions);
}