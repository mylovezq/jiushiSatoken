package com.jiushi.order.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.jiushi.core.common.api.BusinessException;
import com.jiushi.order.api.entity.SeckillProduct;
import com.jiushi.order.api.qo.SeckillProductQO;
import com.jiushi.order.api.vo.SeckillProductVo;

import com.jiushi.order.lua.config.RedisLuaScriptConfig;
import com.jiushi.order.mapper.SeckillProductMapper;
import com.jiushi.order.service.ISeckillProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author denmgmingyang
 * @since 2024-03-22
 */
@Service
@Slf4j
@CacheConfig(cacheNames = {"jiushiSeckil"})
public class SeckillProductServiceImpl extends ServiceImpl<SeckillProductMapper, SeckillProduct> implements ISeckillProductService {
    @Resource
    private StringRedisTemplate redisTemplate;
    @Resource
    private RocketMQTemplate rocketMQTemplate;
    @Resource
    private RedisLuaScriptConfig scriptConfig;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Override
    @Cacheable(key = "'today:products:'+#time")
    public List<SeckillProductVo> selectTodayListByTimeFromRedis(Integer time) {
        return baseMapper.selectTodayListByTime(time);
    }

    @Override
    @Cacheable(key = "'today:seckillId:'+#seckillId")
    public SeckillProductVo selectBySeckillId(Long seckillId) {
        return baseMapper.selectBySeckillId(seckillId);
    }

    @Override
    @CacheEvict(value = "today:seckillId",key = "#seckillId")
    public void deleteBySeckillId(Long seckillId) {

    }

    @Override
    public String doSeckill(Long seckillId) {
        String productKey = "jiushiSeckil:today:seckillId:"+seckillId;
        String productUserKey = "jiushiSeckil:today:seckillId:userId:"+seckillId +":"+ RandomUtil.randomInt(1, 100);;
        RedisScript<String> decreaseStockScript = new DefaultRedisScript<>(scriptConfig.getSeckillAndStockScript(), String.class);
        // 秒杀id获取到秒杀商品对象

        // 执行Lua脚本，假设productId作为Key
        String result = redisTemplate.execute(decreaseStockScript, Arrays.asList(productKey,productUserKey), Integer.toString(1));

        if (!Objects.equals("success",result)){
            throw new BusinessException(result);
        }
        log.info("result:{}",result);
        return result;
    }


}
