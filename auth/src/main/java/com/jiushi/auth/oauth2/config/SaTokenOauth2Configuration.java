package com.jiushi.auth.oauth2.config;

import cn.dev33.satoken.oauth2.config.SaOAuth2Config;

import cn.dev33.satoken.util.SaResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * @author dengmingyang
 * @since 2023/7/10
 */
@Configuration
@Component
public class SaTokenOauth2Configuration implements WebMvcConfigurer {



    // Sa-OAuth2 定制化配置
    // ---------- 开放相关资源接口： Client端根据 Access-Token ，置换相关资源 ------------
    @Autowired
    public void setSaOAuth2Config(SaOAuth2Config cfg) {
        cfg.
                // 配置：未登录时返回的View
                        setNotLoginView(() -> SaResult.error("请先登录"))
                // 配置：登录处理函数
                       .setDoLoginHandle((name, pwd) -> SaResult.error("请先登录"))
                // 配置：确认授权时返回的View
                       .setConfirmView((clientId, scope) -> SaResult.data(clientId+scope))
        ;
    }
}
