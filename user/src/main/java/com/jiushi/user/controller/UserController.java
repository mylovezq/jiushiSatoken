package com.jiushi.user.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.json.JSONUtil;
import com.jiushi.core.common.config.rocketMq.RocketmqSendCallback;
import com.jiushi.pay.api.dto.PayInfoDTO;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author my deng
 * @since 2022/11/3 17:56
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private RocketMQTemplate rocketMQTemplate;


    @GetMapping(value = "/sendPayMessage")
    public void r1() {

        PayInfoDTO payInfoDTO = new PayInfoDTO();
        payInfoDTO.setId(10000L);
        payInfoDTO.setPayType("test");
        payInfoDTO.setPayUserId("99999");
        //发送订单变更mq消息
        rocketMQTemplate.asyncSend("jiushiPayTopic:jiushiTag", JSONUtil.toJsonStr(payInfoDTO), new RocketmqSendCallback());

    }

}

