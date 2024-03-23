package com.syt.model.music.dos;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.syt.model.common.dos.DO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("music_collection")
public class MusicCollection extends DO {

    /**
     * user id
     */
    @TableField("user_id")
    private Integer userId;

    /**
     * name
     */
    @TableField("name")
    private String name;
}