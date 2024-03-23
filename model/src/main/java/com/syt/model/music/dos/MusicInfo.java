package com.syt.model.music.dos;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.google.common.base.Objects;
import com.syt.model.common.dos.DO;
import lombok.Data;

@Data
@TableName("music_info")
public class MusicInfo extends DO {

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MusicInfo musicInfo = (MusicInfo) o;
        return Objects.equal(this.id, musicInfo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
