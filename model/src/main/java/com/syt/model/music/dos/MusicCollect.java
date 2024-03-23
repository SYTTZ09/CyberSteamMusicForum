package com.syt.model.music.dos;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.syt.model.common.dos.DO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("music_collect")
public class MusicCollect extends DO {

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * collection id
     */
    @TableField("collection_id")
    private Integer collectionId;

    /**
     * music id
     */
    @TableField("music_id")
    private Integer musicId;
}