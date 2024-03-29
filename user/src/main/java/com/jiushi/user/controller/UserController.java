package com.jiushi.user.controller;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.jiushi.pay.api.dto.PayInfoDTO;

import com.jiushi.core.common.config.rocketMq.PayRocketmqSendCallback;

import com.jiushi.pay.api.fegin.PayMsgFeignClient;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author my deng
 * @since 2022/11/3 17:56
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private RocketMQTemplate rocketMQTemplate;
    @Resource
    private PayMsgFeignClient payMsgFeignClient;


    @GetMapping(value = "/sendPayMessage")
    public void r1() {

        PayInfoDTO payInfoDTO = new PayInfoDTO();
        payInfoDTO.setId(IdWorker.getId());
        payInfoDTO.setPayType("test");
        payInfoDTO.setPayUserId("99999");
        payInfoDTO.setPayStatus("已支付");
        //发送订单变更mq消息
        rocketMQTemplate.asyncSend("jiushiPayTopic:jiushiTag", payInfoDTO, new PayRocketmqSendCallback(payMsgFeignClient,payInfoDTO));

    }

}

