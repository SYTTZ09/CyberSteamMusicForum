package com.syt.model.common.dos;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public abstract class DO  implements Serializable {
    protected static final long serializableUID = 1L;
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    protected Integer id;

    /**
     * 创建时间
     */
    @TableField("create_time")
    protected Date createTime;

    /**
     * 更新时间
     */
    @TableField("update_time")
    protected Date updateTime;
}
