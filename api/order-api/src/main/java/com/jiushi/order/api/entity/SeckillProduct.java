package com.jiushi.order.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
@TableName("seckill_product")
public class SeckillProduct {

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @TableField("product_id")
    private Long productId;

    @TableField("seckill_price")
    private BigDecimal seckillPrice;

    @TableField("intergral")
    private BigDecimal intergral;

    @TableField("stock_count")
    private Integer stockCount;

    @TableField("start_date")
    private LocalDate startDate;

    @TableField("time")
    private Integer time;
}