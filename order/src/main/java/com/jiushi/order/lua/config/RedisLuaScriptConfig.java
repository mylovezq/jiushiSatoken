package com.jiushi.order.lua.config;

import com.jiushi.core.common.api.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import cn.hutool.core.io.IoUtil;

import javax.annotation.PostConstruct;

@Configuration
@Slf4j
public class RedisLuaScriptConfig {

    // 使用@Value注解加载资源文件
    @Value("classpath:seckillLua.lua")
    private Resource seckillLuaResource;

    // 类的私有属性用于存放Lua脚本内容
    private String seckillAndStockScript;

    // Spring Bean初始化完成后执行的方法
    @PostConstruct
    public void initLuaScript() {
        try {
            // 从资源文件读取内容并赋值给类属性
            this.seckillAndStockScript = IoUtil.readUtf8(seckillLuaResource.getInputStream());
        } catch (Exception e) {
            log.error("加载lua脚本异常",e);
            throw new BusinessException("加载lua脚本异常");
        }
    }

    // 提供getter方法以便在其它地方使用luaScriptContent
    public String getSeckillAndStockScript() {
        return seckillAndStockScript;
    }
}