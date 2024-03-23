package com.syt.model.music.dos;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.syt.model.common.dos.DO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("music_collect_state")
public class MusicCollectState extends DO {

    /**
     * collect id
     */
    @TableField("collect_id")
    private Integer collectId;

    /**
     * 是否删除
     */
    @TableField("is_cancel")
    private Boolean isCancel;
}