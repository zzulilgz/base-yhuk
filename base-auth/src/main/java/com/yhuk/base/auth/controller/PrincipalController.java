package com.yhuk.base.auth.controller;

import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

/**
 * @Description TODO
 * @Author gaozhi.liu
 * @Date 2019/6/25 11:21
 * @Version 1.0
 **/
@RequestMapping("/principal")
public class PrincipalController {

    @RequestMapping("/login")
    public String login(){
      return "dd";
    }
}
