package com.jiushi.auth.api.qo;

import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;

@Data
public class PhoneAndPasswordQO {

    @NotBlank(message = "手机号不能为空")
    private String phoneNum;

    @NotBlank(message = "密码不能为空")
    private String password;
}
