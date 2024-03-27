package com.jiushi.order.controller;


import cn.dev33.satoken.stp.StpUtil;
import com.jiushi.core.common.api.BusinessException;
import com.jiushi.core.common.api.ResultVO;
import com.jiushi.order.api.vo.SeckillProductVo;
import com.jiushi.order.service.ISeckillProductService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Collections;
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
    private StringRedisTemplate redisTemplate;
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Autowired
    private ISeckillProductService seckillProductService;

    private String decreaseLuaStr
        =  "local productJson = redis.call('GET', KEYS[1])\n" +
            "if not productJson then\n" +
            "return '商品已被秒杀完'\n" +
            "end\n" +

            "local product = cjson.decode(productJson)\n" +
            "local userLimitBuyOldCount = redis.call('GET', KEYS[2])\n" +
            "local userLimitBuyRealCount = (userLimitBuyOldCount == nil) and 0 or (tonumber(userLimitBuyOldCount) or 0)\n" +

            "if product and product.stockCount and product.stockCount < tonumber(ARGV[1]) then \n" +
            "return '商品已被秒杀完'\n" +
            "end\n"+

            "if userLimitBuyRealCount + tonumber(ARGV[1]) > tonumber(product.limitBuy) then\n" +
            "return '你的账号限购'..tostring(product.limitBuy)\n" +
            "end\n"+

            "    product.stockCount = product.stockCount - tonumber(ARGV[1])\n" +
            "    redis.call('SET', KEYS[2], userLimitBuyRealCount + tonumber(ARGV[1]))\n"+
            "    redis.call('SET', KEYS[1], cjson.encode(product))\n" +
            "    return 'success'\n";

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
        String productKey = "jiushiSeckil:today:seckillId:"+seckillId;
        String productUserKey = "jiushiSeckil:today:seckillId:userId:"+seckillId +":"+ StpUtil.getLoginId();
        RedisScript<String> decreaseStockScript = new DefaultRedisScript<>(decreaseLuaStr, String.class);
        // 秒杀id获取到秒杀商品对象

        // 执行Lua脚本，假设productId作为Key
        String result = redisTemplate.execute(decreaseStockScript, Arrays.asList(productKey,productUserKey), Integer.toString(1));

        if (!Objects.equals("success",result)){
            throw new BusinessException(result);
        }
        log.info("result:{}",result);

//        // 3. 判断时间是否大于开始时间 && 小于 开始时间+2小时
//        /*if (!DateUtil.isLegalTime(vo.getStartDate(), time)) {
//            throw new BusinessException(SeckillCodeMsg.OUT_OF_SECKILL_TIME_ERROR);
//        }*/
//
//
//        // 4. 判断用户是否重复下单
//        // 基于用户 + 秒杀 id + 场次查询订单, 如果存在订单, 说明用户已经下过单
////        int max = 1;
////        String userOrderCountKey = SeckillRedisKey.SECKILL_ORDER_HASH.join(seckillId + "");
////        Long increment = redisTemplate.opsForHash().increment(userOrderCountKey, userInfo.getPhone() + "", 1);
////        if (increment > max) {
////            throw new BusinessException(SeckillCodeMsg.REPEAT_SECKILL);
////        }
//
//        // 5. 通过 redis 库存预减控制访问人数
//        String stockCountKey = SeckillRedisKey.SECKILL_STOCK_COUNT_HASH.join(time + "");
//        // 对库存自减以后，会返回剩余库存数量
//        long remain = redisTemplate.opsForHash().increment(stockCountKey, seckillId + "", -1);
//        if (remain < 0) {
//            // 标记当前库存已经售完
//            throw new BusinessException(SeckillCodeMsg.SECKILL_STOCK_OVER);
//        }
//        // 6. 执行下单操作(减少库存, 创建订单)
//        // 修改为利用 RocketMQ 发送消息，实现异步下单
//        String orderNumber = generateOrderNumber();
//        rocketMQTemplate.asyncSend(MQConstant.ORDER_PENDING_TOPIC, new OrderMessage(time, seckillId, token, userInfo.getPhone(),orderNumber), new DefaultMQMessageCallback());
        return ResultVO.success("秒杀成功");


    }
}
