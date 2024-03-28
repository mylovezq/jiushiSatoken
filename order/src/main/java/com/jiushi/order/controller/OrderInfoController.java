package com.jiushi.order.controller;


import cn.hutool.core.util.RandomUtil;
import com.jiushi.core.common.api.BusinessException;
import com.jiushi.core.common.api.ResultVO;
import com.jiushi.order.lua.config.RedisLuaScriptConfig;
import com.jiushi.order.service.ISeckillProductService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Objects;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author denmgmingyang
 * @since 2024-03-22
 */
@RestController
@Slf4j
@RequestMapping("/orderInfo")
public class OrderInfoController {



    @Autowired
    private ISeckillProductService seckillProductService;



    /**
     * 优化前：
     * 测试数据：500 个用户，100 线程，执行 50 次
     * 测试情况：330 QPS
     * 优化后：
     * 测试数据：500 个用户，100 线程，执行 50 次
     * 测试情况：850 QPS
     */
    @GetMapping("/doSeckill")
    public ResultVO<String> doSeckill(Long seckillId) {
        seckillProductService.doSeckill(seckillId);


//        // 6. 执行下单操作(减少库存, 创建订单)
//        // 修改为利用 RocketMQ 发送消息，实现异步下单
//        String orderNumber = generateOrderNumber();
//        rocketMQTemplate.asyncSend(MQConstant.ORDER_PENDING_TOPIC, new OrderMessage(time, seckillId, token, userInfo.getPhone(),orderNumber), new DefaultMQMessageCallback());
        return ResultVO.success("秒杀成功");


    }
}
