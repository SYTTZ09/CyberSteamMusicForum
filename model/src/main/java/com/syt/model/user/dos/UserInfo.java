package com.syt.model.user.dos;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("user_info")
public class UserInfo implements Serializable {

    private static final long serializableUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id")
    @ApiModelProperty(value = "主键", required = true)
    private Integer id;

    /**
     * 名字
     */
    @TableField("name")
    @ApiModelProperty(value = "名字", required = true)
    private String name;

    /**
     * 头像
     */
    @TableField("avatar")
    @ApiModelProperty(value = "头像", required = true)
    private String avatar;

    /**
     * 签名，介绍
     */
    @TableField("introduction")
    @ApiModelProperty(value = "介绍", required = true)
    private String introduction;
}
