package com.jiushi.order.service.impl;

import cn.hutool.core.date.DateUtil;
import com.jiushi.order.api.entity.SeckillProduct;
import com.jiushi.order.api.vo.SeckillProductVo;

import com.jiushi.order.mapper.SeckillProductMapper;
import com.jiushi.order.service.ISeckillProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author denmgmingyang
 * @since 2024-03-22
 */
@Service
@CacheConfig(cacheNames = "seckill")
public class SeckillProductServiceImpl extends ServiceImpl<SeckillProductMapper, SeckillProduct> implements ISeckillProductService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Override
    @Cacheable(value = "today:seckill",key = "':products:'+#time")
    public List<SeckillProductVo> selectTodayListByTimeFromRedis(Integer time) {
        return baseMapper.selectTodayListByTime(time);
    }

    @Override
    @Cacheable(value = "today:seckill:",key = "'seckillId:'+ #seckillId")
    public SeckillProductVo selectBySeckillId(Long seckillId) {
        return baseMapper.selectBySeckillId(seckillId);
    }

    @Override
    public void updateStock(Integer time) {
        List<SeckillProductVo> seckillProductVos = this.selectTodayListByTimeFromRedis(time);
        seckillProductVos.parallelStream().forEach(seckillProductVo -> {
            stringRedisTemplate.opsForHash().put("today:seckill:stock:"+  DateUtil.format(new Date(), "yyyyMMdd"),seckillProductVo.getId()+"",seckillProductVo.getStockCount()+"");
        });
    }
}