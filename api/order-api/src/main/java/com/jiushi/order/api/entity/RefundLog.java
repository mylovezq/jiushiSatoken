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
@TableName("refund_log")
public class RefundLog {

    @TableId("out_trade_no")
    private String outTradeNo;

    @TableField("refund_time")
    private String refundTime;

    @TableField("refund_amount")
    private String refundAmount;

    @TableField("refund_reason")
    private String refundReason;

    @TableField("refund_type")
    private Integer refundType;
}