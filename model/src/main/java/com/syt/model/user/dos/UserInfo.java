package com.syt.model.user.dos;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.syt.model.common.dos.DO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("user_info")
public class UserInfo extends DO {

    /**
     * 用户认证表主键
     */
    @TableField("user_auth_id")
    private Integer userAuthId;

    /**
     * 名字
     */
    @TableField("name")
    @ApiModelProperty(value = "名字")
    private String name;

    /**
     * 头像
     */
    @TableField("avatar")
    @ApiModelProperty(value = "头像")
    private String avatar;

    /**
     * 签名，介绍
     */
    @TableField("introduction")
    @ApiModelProperty(value = "介绍")
    private String introduction;
}
