package com.jiushi.order.api.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

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
@TableName("pay_log")
public class PayLog {

    @TableField("id")
    private Long id;

    @TableField("trade_no")
    private String tradeNo;

    @TableField("out_trade_no")
    private String outTradeNo;

    @TableField("notify_time")
    private String notifyTime;

    @TableField("total_amount")
    private String totalAmount;

    @TableField("pay_type")
    private String payType;
}