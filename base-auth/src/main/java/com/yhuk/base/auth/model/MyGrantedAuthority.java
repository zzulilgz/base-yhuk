package com.yhuk.base.auth.model;

import org.springframework.security.core.GrantedAuthority;

/**
 * @Description TODO
 * @Author gaozhi.liu
 * @Date 2019/5/21 17:43
 * @Version 1.0
 **/
public class MyGrantedAuthority implements GrantedAuthority {
    private String roleId;
    private String name;
    public MyGrantedAuthority(String roleId,String name) {
        this.roleId = roleId;
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return this.roleId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
