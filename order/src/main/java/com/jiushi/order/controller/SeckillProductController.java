package com.jiushi.order.controller;


import cn.dev33.satoken.util.SaResult;
import com.jiushi.core.common.api.ResultVO;
import com.jiushi.order.api.vo.SeckillProductVo;
import com.jiushi.order.service.ISeckillProductService;
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
@RequestMapping("/seckillProduct")
public class SeckillProductController {

    @Resource
    private ISeckillProductService seckillProductService;

    @RequestMapping("/queryByTime")
    public ResultVO<List<SeckillProductVo>> queryByTime(Integer time) {
        return ResultVO.success(seckillProductService.selectTodayListByTimeFromRedis(time));
    }

    @RequestMapping("/find")
    public ResultVO<SeckillProductVo> findById(Long seckillId) {
        return ResultVO.success(seckillProductService.selectBySeckillId(seckillId));
    }

}
