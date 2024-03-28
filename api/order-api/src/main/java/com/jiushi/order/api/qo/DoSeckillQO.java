package com.jiushi.order.api.qo;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class DoSeckillQO {
    @NotNull(message = "id不能为空")
    private Long id;//秒杀商品ID
}
