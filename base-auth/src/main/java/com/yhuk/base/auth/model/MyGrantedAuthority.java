package com.yhuk.base.auth.model;

import org.springframework.security.core.GrantedAuthority;

/**
 * @Description TODO
 * @Author gaozhi.liu
 * @Date 2019/5/21 17:43
 * @Version 1.0
 **/
public class MyGrantedAuthority implements GrantedAuthority {
    private String access_path;

    public MyGrantedAuthority(String access_path) {
        this.access_path = access_path;
    }

    @Override
    public String getAuthority() {
        return null;
    }
}
