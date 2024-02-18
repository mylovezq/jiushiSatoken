package com.jiushi.pay.controller;

import cn.hutool.core.bean.BeanUtil;
import com.jiushi.core.common.config.utils.redis.RedissonLockService;
import com.jiushi.core.common.config.utils.redis.RedissonUtils;
import com.jiushi.pay.api.dto.PayMsgDTO;
import com.jiushi.pay.api.fegin.PayMsgFeignClient;
import com.jiushi.pay.dao.PayMsg;
import com.jiushi.pay.service.PayMsgService;

import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pay")
public class PayMsgController implements PayMsgFeignClient {

    @Autowired
    private PayMsgService payMsgService;
    @Autowired
    private RedissonLockService redissonLockService;


    @Override
    public void saveOrUpdatePayMsg(@Validated PayMsgDTO payMsg) {
        //根据payMsg的id，作为redis的锁，防止重复消费
        redissonLockService.runRedisExclusiveLock.apply("payMsg:" + payMsg.getId(), () -> {
            payMsgService.saveOrUpdate(BeanUtil.copyProperties(payMsg, PayMsg.class));
            return null;
        });
    }
}
