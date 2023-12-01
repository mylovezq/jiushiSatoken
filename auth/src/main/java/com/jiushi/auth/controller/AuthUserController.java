package com.jiushi.auth.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;

import com.jiushi.user.api.fegin.UserInfoFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth/user")
@RestController
public class AuthUserController {
    @Autowired
    private UserInfoFeign userInfoFeign;
    @GetMapping("myUser")
    public SaResult myUser(){
        System.out.println(userInfoFeign.currentUser());
        return SaResult.data(StpUtil.getTokenValue());
    }
}
