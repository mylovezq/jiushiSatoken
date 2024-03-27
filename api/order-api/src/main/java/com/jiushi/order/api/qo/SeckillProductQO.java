package com.jiushi.order.api.qo;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
public class SeckillProductQO  implements Serializable {
    @NotNull(message = "id不能为空")
    private Long id;//秒杀商品ID

    private Integer limitBuy;

    private BigDecimal seckillPrice;//秒杀价格

    private Integer stockCount;//库存总数

    private LocalDateTime startDate;//秒杀日期

    private Integer time;//秒杀场次

    private BigDecimal productPrice;
}