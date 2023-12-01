package com.jiushi.auth.controller;

import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;

import com.jiushi.auth.api.constant.DeviceType;
import com.jiushi.auth.api.qo.PhoneAndPasswordQO;
import com.jiushi.auth.service.IBaseAuthUserService;
import com.jiushi.user.api.fegin.UserInfoFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import javax.annotation.Resource;
import java.time.LocalDateTime;

import static com.jiushi.auth.api.constant.LoginConstant.device;


@RequestMapping("/auth/login")
@RestController
public class AuthController {

    @Resource
    private IBaseAuthUserService baseAuthUserService;

    @PostMapping("/loginByPhoneAndPassword")
    public SaResult loginByPhoneAndPassword(@RequestBody @Validated PhoneAndPasswordQO phoneAndPasswordQO ){
        return baseAuthUserService.loginByPhoneAndPassword(phoneAndPasswordQO);
    }


}
