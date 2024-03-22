package com.jiushi.core.common.utils.redis;


import cn.dev33.satoken.exception.SaTokenException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.function.BiFunction;


/**
 * @author dengmingyang
 * @since 2023/11/28 16:08
 */
@Component
@Slf4j
@Configuration
@EnableConfigurationProperties(RedisConfigureProperties.class)
public class RedissonLockService {
    @Resource
    private RedisConfigureProperties redisConfigureProperties;
    @Resource
    private ObjectMapper objectMapper;
    @Bean
    public RedissonClient redisson() {
        String redissonAddr = "redis://" + redisConfigureProperties.getHost() + ":" + redisConfigureProperties.getPort();
        Config config = new Config();
        config.setCodec(new JsonJacksonCodec(objectMapper));
        config.useSingleServer().setAddress(redissonAddr).setPassword(redisConfigureProperties.getPassword());
        return Redisson.create(config);
    }
    @Resource
    private RedissonClient redissonClient;

    /**
     *
     * 排他锁，其他线程拿锁会抛错
     *
     * redisson通用锁方法 拿到锁就执行  没拿到直接抛错
     * lockKey  要锁的key
     * runWithRedissonLockMethod  具体执行的方法
     */
    public BiFunction<String, RunWithRedissonLockMethod, Object> runRedisExclusiveLock = (lockKey, runWithRedissonLockMethod) -> {
        RLock redissonLock = redissonClient.getLock(lockKey);
        try {
            if (redissonLock.tryLock()) {
                return runWithRedissonLockMethod.runMethod();
            }else {
                throw new SaTokenException("请勿重复操作");
            }
        } catch (Exception e) {
            log.error("操作异常", e);
            throw e;
        } finally {
            if (null != redissonLock && redissonLock.isHeldByCurrentThread()) {
                redissonLock.unlock();
            }
        }
    };


}
