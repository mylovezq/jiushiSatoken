package com.jiushi.pay.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author my deng
 * @since 2022/11/3 17:56
 */
@RestController
@RequestMapping("/pay")
public class PayController {



    @GetMapping("/currentUser")
    public Object currentUser() {

        return StpUtil.getTokenInfo().getLoginId();
    }

}
