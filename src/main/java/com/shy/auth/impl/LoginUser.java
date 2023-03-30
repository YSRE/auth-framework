package com.shy.auth.impl;

import lombok.Data;
import com.shy.auth.AuthUser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 基本登录信息
 * @author shy
 * @date 2023-03-28
 */
@Data
public class LoginUser implements AuthUser {


    /**
     * 用户名id
     */
    private Long uid;

    /**
     * 用户名
     */
    private String username;

    /**
     * 登录时间
     */
    private Long loginTime;

    /**
     * 过期时间
     */
    private Long expireTime;

    /**
     * 登录IP地址
     */
    private String ipaddr;

    /**
     * 权限列表
     */
    private List<String> permissions;

    /**
     * 角色列表
     */
    private List<String> roles;

    public LoginUser() {
        this.roles = new ArrayList<>();
        this.permissions = new ArrayList<>();
    }

    public void addRoles(String... roles) {
        this.roles.addAll(Arrays.asList(roles));
    }

    public void addPermissions(String permissions){
        this.permissions.addAll(Arrays.asList(permissions));
    }


}
