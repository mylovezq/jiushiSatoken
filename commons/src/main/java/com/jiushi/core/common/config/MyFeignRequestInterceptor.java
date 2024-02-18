package com.jiushi.core.common.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Objects;

/**
 * 自定义的Feign拦截器
 *
 * @author Saint
 */
@Slf4j
public class MyFeignRequestInterceptor implements RequestInterceptor {
    /**
     * 这里可以实现对请求的拦截，对请求添加一些额外信息之类的
     *
     * @param requestTemplate
     */
    @Override
    public void apply(RequestTemplate requestTemplate) {
        // 从header获取X-token
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (Objects.isNull(requestAttributes)) {
            return;
        }
        ServletRequestAttributes attr = (ServletRequestAttributes) requestAttributes;
        HttpServletRequest request = attr.getRequest();
        String token = request.getHeader("Authorization");//网关传过来的 token
        if (StringUtils.hasText(token)) {
            requestTemplate.header("Authorization", token);
        }
    }
}
