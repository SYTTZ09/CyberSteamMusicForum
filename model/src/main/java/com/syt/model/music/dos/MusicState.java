package com.syt.model.music.dos;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.syt.model.common.dos.DO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("music_state")
public class MusicState  extends DO {

    /**
     * 音乐 id
     */
    @TableField("music_id")
    private Integer musicId;

    /**
     * 是否公开
     */
    @TableField("is_public")
    private Boolean isPublic;
}
