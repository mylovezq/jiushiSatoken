package com.jiushi.auth.controller;

import cn.dev33.satoken.util.SaResult;
import com.jiushi.user.api.vo.PhoneAndPasswordQO;
import com.jiushi.auth.service.IAuthUserService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@RequestMapping("/auth/login")
@RestController
public class AuthController {

    @Resource
    private IAuthUserService authUserService;

    @PostMapping("/loginByPhoneAndPassword")
    public SaResult loginByPhoneAndPassword(@RequestBody @Validated PhoneAndPasswordQO phoneAndPasswordQO ){
        return SaResult.data(authUserService.loginByPhoneAndPassword(phoneAndPasswordQO));
    }


}
