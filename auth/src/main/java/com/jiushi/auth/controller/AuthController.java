package com.jiushi.auth.controller;

import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;

import com.jiushi.auth.api.qo.PhoneAndPasswordQO;
import com.jiushi.user.api.fegin.UserInfoFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDateTime;



@RequestMapping("/auth/login")
@RestController
public class AuthController {


    @Autowired
    private UserInfoFeign userInfoFeign;

    @PostMapping("loginByPhoneAndPassword")
    public SaResult loginByPhoneAndPassword(@RequestBody @Validated PhoneAndPasswordQO phoneAndPasswordQO ){
        SaLoginModel model = new SaLoginModel();
        model.setExtra("currTime", LocalDateTime.now());
        model.setDevice("MINI_PRO");
        model.setExtra("device","mini_pro");
        StpUtil.login(phoneAndPasswordQO.getPhoneNum(),model);
       System.out.println(userInfoFeign.currentUser());
        return SaResult.data(StpUtil.getTokenValue());
    }


}
