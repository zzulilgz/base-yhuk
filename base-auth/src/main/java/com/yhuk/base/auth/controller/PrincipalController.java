package com.yhuk.base.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidClientException;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.exceptions.InvalidRequestException;
import org.springframework.security.oauth2.common.exceptions.UnsupportedGrantTypeException;
import org.springframework.security.oauth2.common.util.OAuth2Utils;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.util.StringUtils;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/**
 * @Description TODO
 * @Author gaozhi.liu
 * @Date 2019/6/25 11:21
 * @Version 1.0
 **/
@RestController
@RequestMapping("/principal")
public class PrincipalController {
    
    @Autowired
    private ClientDetailsService clientDetailsService;

    @Autowired
    TokenEndpoint tokenEndpoint;


    @PostMapping("/login")
    public ResponseEntity<OAuth2AccessToken> login(@RequestBody Map<String,String> request) throws HttpRequestMethodNotSupportedException {

        request.put("grant_type","password");

        return tokenEndpoint.postAccessToken(getAuthentication(request), request);
    }
    private Authentication getAuthentication(Map<String,String> request){
        Authentication authentication = new Authentication() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return null;
            }

            @Override
            public Object getCredentials() {
                return null;
            }

            @Override
            public Object getDetails() {
                return null;
            }

            @Override
            public Object getPrincipal() {
                return null;
            }

            @Override
            public boolean isAuthenticated() {
                return true;
            }

            @Override
            public void setAuthenticated(boolean b) throws IllegalArgumentException {

            }

            @Override
            public String getName() {
                return request.get(OAuth2Utils.CLIENT_ID);
            }
        };
        return authentication;
    }
}