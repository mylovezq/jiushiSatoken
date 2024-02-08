package com.jiushi.pay.service;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiushi.pay.dao.PayMsg;
import com.jiushi.pay.dao.mapper.PayMsgMapper;
import org.springframework.stereotype.Service;

@Service
public class PayMsgServiceImpl extends ServiceImpl<PayMsgMapper, PayMsg> implements PayMsgService {
    // 实现service接口中的业务方法
}