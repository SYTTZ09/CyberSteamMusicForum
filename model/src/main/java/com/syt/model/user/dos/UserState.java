package com.syt.model.user.dos;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.syt.model.common.dos.DO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("user_state")
public class UserState extends DO {

    /**
     * 用户认证表主键
     */
    @TableField("user_auth_id")
    private Integer userAuthId;

    /**
     * 是否激活    0 未激活   1 已激活
     */
    @TableField("is_activated")
    private Boolean isActivated;
}
