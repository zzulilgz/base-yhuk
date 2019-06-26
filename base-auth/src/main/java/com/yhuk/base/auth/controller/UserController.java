package com.yhuk.base.auth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @Description TODO
 * @Author gaozhi.liu
 * @Date 2019/6/26 17:50
 * @Version 1.0
 **/
@RestController
public class UserController {

    @GetMapping("/user")
    public Principal user(Principal user){
        return user;
    }
}
