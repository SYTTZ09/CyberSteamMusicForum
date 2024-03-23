package com.syt.model.music.dos;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.syt.model.common.dos.DO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("music_collection_state")
public class MusicCollectionState extends DO {

    /**
     * collection id
     */
    @TableField("collection_id")
    private Integer collectionId;

    /**
     * 是否为我喜欢
     */
    @TableField("is_my_like")
    private Boolean isMyLike;
}