package com.jiushi.user.controller;

import cn.dev33.satoken.stp.StpUtil;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author my deng
 * @since 2022/11/3 17:56
 */
@RestController
@RequestMapping("/user")
public class UserController {



    @GetMapping(value = "/hello")
    public String r1(){
      return "hello 邓明阳";
    }

    @GetMapping("/currentUser")
    public Object currentUser() {
        Object loginId = StpUtil.getTokenInfo().getLoginId();
        return loginId;
    }

}
