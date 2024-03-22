package com.jiushi.order.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jiushi.order.api.entity.SeckillProduct;
import com.jiushi.order.api.vo.SeckillProductVo;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author denmgmingyang
 * @since 2024-03-22
 */
public interface SeckillProductMapper extends BaseMapper<SeckillProduct> {

    List<SeckillProductVo> selectTodayListByTime(Integer time);

    SeckillProductVo selectBySeckillId(Long seckillId);
}

