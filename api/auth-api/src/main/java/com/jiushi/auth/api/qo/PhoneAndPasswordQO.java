package com.jiushi.auth.api.qo;

import com.jiushi.auth.api.constant.ClientTypeEnum;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class PhoneAndPasswordQO {

    @NotBlank(message = "手机号不能为空")
    private String phone;

    @NotBlank(message = "密码不能为空")
    private String password;

    @NotNull(message = "clientType不能为空")
    private ClientTypeEnum clientType;
}
