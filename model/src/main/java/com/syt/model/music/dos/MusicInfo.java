package com.syt.model.music.dos;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.google.common.base.Objects;
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
     * Like
     */
    @TableField("like_count")
    private Integer likeCount;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField("update_time")
    private Date updateTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MusicInfo musicInfo = (MusicInfo) o;
        return Objects.equal(id, musicInfo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
