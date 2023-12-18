package com.jiushi.auth.api.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 *
 * 授权时页面回显的内容，
 *
 * @author dengmingyang
 * @since 2023/11/24 17:44
 */
@Data
public class ConfirmScopeViewVO {



    private String clientId;

    //授权原始范围字符串
    private String scope;

    //申请授权方的icon url
    private String icon;
    //申请授权方的名称
    private String clientName;

    //根据授权原始范围 回显 给用户看得懂的范围描述
    private List<ScopeVo> scopeDescList;

    /**
     * 通用权限回显对象
     * 比如专病就是
     *
     * 姓名：医生范包包
     *
     * 账户：185****234
     *
     * 身份证信息：5130****4720
     */
    @Data
    public static class ScopeVo{

        private String label;

        private String value;
    }
}
