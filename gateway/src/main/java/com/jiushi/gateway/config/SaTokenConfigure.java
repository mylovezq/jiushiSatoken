package com.jiushi.gateway.config;

import cn.dev33.satoken.reactor.filter.SaReactorFilter;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * [Sa-Token 权限认证] 全局配置类 
 */
@Configuration
@Slf4j
@EnableConfigurationProperties({AuthProperties.class})
public class SaTokenConfigure {
    /**
     * 注册 [Sa-Token全局过滤器] 
     */
    @Bean
    public SaReactorFilter getSaReactorFilter(AuthProperties authProperties) {
        return new SaReactorFilter()
                // 指定 [拦截路由]
                .addInclude("/**")    /* 拦截所有path */
                // 指定 [放行路由]
                .addExclude("/favicon.ico")

                .addExclude(authProperties.getSkipUrl().toArray(new String[]{}))
                // 指定[认证函数]: 每次请求执行
                .setAuth(obj -> {
                   log.error("sa全局token拦截开始" + obj);
                    SaRouter.match("/**", () -> StpUtil.checkLogin());
                })
                // 指定[异常处理函数]：每次[认证函数]发生异常时执行此函数 
                .setError(e -> {
                   log.error("sa拦截异常",e);
                    return SaResult.error(e.getMessage());
                })
                ;
    }
}
