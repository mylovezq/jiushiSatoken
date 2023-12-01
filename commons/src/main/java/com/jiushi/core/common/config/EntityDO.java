package com.jiushi.core.common.config;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EntityDO {

    @TableId("id")
    private Long id;

    @TableField("create_time")
    private LocalDateTime createTime;

    @TableField("creator_id")
    private Long creatorId;

    @TableField("update_time")
    private LocalDateTime updateTime;

    @TableField("updater_id")
    private Long updaterId;
}
