package com.syt.model.music.dos;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("music_info")
public class MusicInfo implements Serializable {

    private static final long serializableUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 歌名
     */
    @TableField("name")
    private String name;

    /**
     * 歌手
     */
    @TableField("singer")
    private String singer;

    /**
     * 音乐
     */
    @TableField("music_src")
    private String musicSrc;

    /**
     * 封面
     */
    @TableField("image_src")
    private String imageSrc;

    /**
     * 介绍
     */
    @TableField("introduction")
    private String introduction;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy年MM月dd日")
    @TableField("create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField("update_time")
    private Date updateTime;
}
