package com.jiushi.auth.oauth2.dao.entity;

import cn.dev33.satoken.oauth2.model.SaClientModel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * <p>
 * OAuth2 客户端
 * </p>
 *
 * @author denmgmingyang
 * @since 2023-12-18
 */
@Data
@TableName("oauth2_registered_client")
public class Oauth2RegisteredClientDO extends SaClientModel {

    /**
     * 自增id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;



    /**
     * 客户端名称
     */
    @TableField("client_name")
    private String clientName;

    /**
     * 接入方的logo图标url
     */
    @TableField("icon")
    private String icon;



    /**
     * 是否已删除
     */
    @TableField("enable")
    private Boolean enable;

    /**
     * 创建人者id
     */
    @TableField("create_user_id")
    private Long createUserId;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 创建人
     */
    @TableField("create_user_no")
    private String createUserNo;

    /**
     * 修改时间
     */
    @TableField("update_time")
    private LocalDateTime updateTime;

    /**
     * 修改人
     */
    @TableField("update_user_no")
    private String updateUserNo;
}