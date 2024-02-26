package com.syt.model.user.dos;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("user_info")
public class UserInfo implements Serializable {

    private static final long serializableUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "主键")
    private Integer id;

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

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("create_time")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("update_time")
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
}
