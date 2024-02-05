package com.jiushi.core.common.config.rocketMq;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;

import java.util.concurrent.atomic.AtomicLong;

@Slf4j
public class RocketmqSendCallback implements SendCallback {
    private AtomicLong atomicLong = new AtomicLong();

    @Override
    public void onSuccess(SendResult sendResult) {
        log.info(atomicLong.incrementAndGet() + "async onSuccess SendResult={}" , sendResult);
    }

    @Override
    public void onException(Throwable throwable) {
        log.error("async onException Throwable", throwable);
    }
}
