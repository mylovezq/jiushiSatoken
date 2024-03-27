package com.jiushi.order.controller;


import com.jiushi.core.common.api.ResultVO;
import com.jiushi.order.api.qo.SeckillProductQO;
import com.jiushi.order.api.vo.SeckillProductVo;
import com.jiushi.order.service.IManageProductService;
import com.jiushi.order.service.ISeckillProductService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 *  manageSeckillProduct
 *
 * @author denmgmingyang
 * @since 2024-03-22
 */
@RestController
@RequestMapping("/manageSeckillProduct")
public class ManageProductController {

    @Resource
    private IManageProductService manageProductService;

    @PostMapping("/updateStock")
    public ResultVO updateStock(@RequestBody @Validated SeckillProductQO seckillProductQO) {
        manageProductService.updateStock(seckillProductQO);
        return ResultVO.success("");
    }

}
