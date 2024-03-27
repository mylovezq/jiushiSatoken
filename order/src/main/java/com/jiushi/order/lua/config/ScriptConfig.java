package com.jiushi.order.lua.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import cn.hutool.core.io.IoUtil;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Configuration
public class ScriptConfig {

    // 使用@Value注解加载资源文件
    @Value("classpath:seckillLua.lua")
    private Resource luaScriptResource;

    // 类的私有属性用于存放Lua脚本内容
    private String luaScriptContent;

    // Spring Bean初始化完成后执行的方法
    @PostConstruct
    public void initLuaScript() {
        try {
            // 从资源文件读取内容并赋值给类属性
            this.luaScriptContent = IoUtil.readUtf8(luaScriptResource.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException("Failed to load Lua script from resources", e);
        }
    }

    // 提供getter方法以便在其它地方使用luaScriptContent
    public String getLuaScriptContent() {
        return luaScriptContent;
    }
}