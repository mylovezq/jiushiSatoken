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
public class Product {

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @TableField("product_name")
    private String productName;

    @TableField("product_title")
    private String productTitle;

    @TableField("product_img")
    private String productImg;

    @TableField("product_detail")
    private String productDetail;

    @TableField("product_price")
    private BigDecimal productPrice;
}