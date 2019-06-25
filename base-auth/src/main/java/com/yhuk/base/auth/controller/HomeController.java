package com.yhuk.base.auth.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @Description TODO
 * @Author gaozhi.liu
 * @Date 2019/6/24 13:53
 * @Version 1.0
 **/
@RestController
@RequestMapping("/")
public class HomeController {

    @RequestMapping("/")
    public String home(){
        return "success";
    }
}
