package com.jiushi.order.api.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Setter
@Getter
public class SeckillProductVo  implements Serializable {
    private Long id;//秒杀商品ID
    private Long productId;//商品ID
    private BigDecimal seckillPrice;//秒杀价格
    private Long intergral;//积分
    private Integer stockCount;//库存总数
    private Date startDate;//秒杀日期
    private Integer time;//秒杀场次
    private String productName;
    private String productTitle;
    private String productImg;
    private String productDetail;
    private BigDecimal productPrice;
    private Integer currentCount;
}