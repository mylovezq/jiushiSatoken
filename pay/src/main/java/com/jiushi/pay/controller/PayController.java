package com.jiushi.pay.controller;

import cn.dev33.satoken.stp.StpUtil;
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


    @Resource
    private JiushiRocketmqProducer jiushiRocketmqProducer;

    @GetMapping(value = "/listenMessage")
    public String r1(){
      return "hello 邓明阳";
    }

    @GetMapping("/currentUser")
    public Object currentUser() {
        Object loginId = StpUtil.getTokenInfo().getLoginId();

        return loginId;
    }

}
