package com.jiushi.order.api.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author denmgmingyang
 * @since 2024-03-22
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("order_info")
public class OrderInfo {

    @TableId("order_no")
    private String orderNo;

    @TableField("user_id")
    private Long userId;

    @TableField("product_id")
    private Long productId;

    @TableField("product_img")
    private String productImg;

    @TableField("delivery_addr_id")
    private Long deliveryAddrId;

    @TableField("product_name")
    private String productName;

    @TableField("product_count")
    private Integer productCount;

    @TableField("product_price")
    private BigDecimal productPrice;

    @TableField("seckill_price")
    private BigDecimal seckillPrice;

    @TableField("status")
    private Integer status;

    @TableField("create_date")
    private LocalDateTime createDate;

    @TableField("pay_date")
    private LocalDateTime payDate;

    @TableField("seckill_date")
    private LocalDate seckillDate;

    @TableField("seckill_time")
    private Integer seckillTime;

    @TableField("intergral")
    private BigDecimal intergral;

    @TableField("seckill_id")
    private Long seckillId;

    @TableField("pay_type")
    private Integer payType;
}