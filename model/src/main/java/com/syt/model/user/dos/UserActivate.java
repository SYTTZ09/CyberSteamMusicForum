package com.syt.model.user.dos;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.syt.model.common.dos.DO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("user_activate")
public class UserActivate extends DO {

    /**
     * 用户认证表主键
     */
    @TableField("user_auth_id")
    private Integer userAuthId;

    /**
     * 激活码
     */
    @TableField("code")
    private String code;

    /**
     * 激活截至时间
     */
    @TableField("deadline")
    private Date deadline;
}