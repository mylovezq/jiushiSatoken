package com.jiushi.auth.api.vo;

import lombok.Data;

/**
 *
 * 开放平台
 *
 * @author dengmingyang
 * @since 2023/11/27 13:43
 */
@Data
public class SaTokenUserBaseVO {
    /**
     * openId
     */
    private String openId;

    /**
     * 用户名字
     */
    private String name;

    /**
     * 性别
     * 0,"男"
     * 1,"女"
     * 2,"保密"
     */
    private Integer gender;

}
