package com.jiushi.auth.dao;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jiushi.core.common.config.EntityDO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 
 * </p>
 *
 * @author denmgmingyang
 * @since 2023-12-01
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("base_auth_user")
public class BaseAuthUserDO extends EntityDO {

    @TableField("phone")
    private String phone;

    @TableField("client_type")
    private Integer clientType;

    @TableField("role_list")
    private String roleList;

    @TableField("user_name")
    private String userName;

    @TableField("password")
    private String password;


}