package com.jiushi.order.controller;


import com.jiushi.core.common.api.ResultVO;
import com.jiushi.order.api.vo.SeckillProductVo;
import com.jiushi.order.service.ISeckillProductService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author denmgmingyang
 * @since 2024-03-22
 */
@RestController
@RequestMapping("/manageSeckillProduct")
public class ManageProductController {

    @Resource
    private ISeckillProductService seckillProductService;

    @PostMapping("/updateStock")
    public ResultVO updateStock(Integer time) {
        seckillProductService.updateStock(time);
        return ResultVO.success("");
    }

}
