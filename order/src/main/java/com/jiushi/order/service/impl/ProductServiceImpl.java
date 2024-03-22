package com.jiushi.order.service.impl;


import com.jiushi.order.api.entity.Product;
import com.jiushi.order.mapper.ProductMapper;
import com.jiushi.order.service.IProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author denmgmingyang
 * @since 2024-03-22
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {

}
