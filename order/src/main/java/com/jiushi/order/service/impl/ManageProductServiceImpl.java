package com.jiushi.order.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.jiushi.order.api.entity.SeckillProduct;
import com.jiushi.order.api.qo.SeckillProductQO;
import com.jiushi.order.service.IManageProductService;
import com.jiushi.order.service.ISeckillProductService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ManageProductServiceImpl implements IManageProductService {
    @Resource
    private ISeckillProductService seckillProductService;
    @Override
    public void updateStock(SeckillProductQO seckillProductQO) {

        SeckillProduct seckillProduct = BeanUtil.copyProperties(seckillProductQO, SeckillProduct.class);
        seckillProductService.updateById(seckillProduct);
        seckillProductService.deleteBySeckillId(seckillProductQO.getId());
    }
}
