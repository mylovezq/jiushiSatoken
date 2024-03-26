package com.jiushi.order.service;



import com.baomidou.mybatisplus.extension.service.IService;
import com.jiushi.order.api.entity.SeckillProduct;
import com.jiushi.order.api.vo.SeckillProductVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author denmgmingyang
 * @since 2024-03-22
 */
public interface ISeckillProductService extends IService<SeckillProduct> {

    List<SeckillProductVo> selectTodayListByTimeFromRedis(Integer time);

    SeckillProductVo selectBySeckillId(Long seckillId);

    void updateStock(Integer time);
}
