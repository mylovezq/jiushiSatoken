package com.jiushi.auth.api.vo;


import lombok.Data;

/**
 * @author dengmingyang
 * @since 2023/11/28 14:21
 */
@Data
public class AccessTokenVO {


    private String accessToken;

    private String refreshToken;


    private int expiresIn;

    private int refreshExpiresIn;

    private String clientId;

    private String scope;

    private String openid;
}
