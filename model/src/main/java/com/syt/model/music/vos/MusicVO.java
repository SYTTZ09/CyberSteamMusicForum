package com.syt.model.music.vos;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class MusicVO implements Serializable {

    private static final long serializableUID = 1L;

    @ApiModelProperty("id")
    private Integer id;

    @ApiModelProperty("名字")
    private String name;

    @ApiModelProperty("歌手")
    private String singer;

    @ApiModelProperty("音乐")
    private String musicSrc;

    @ApiModelProperty("封面")
    private String imageSrc;

    @ApiModelProperty("介绍")
    private String introduction;

    @ApiModelProperty("Like")
    private Integer likeCount;

    @ApiModelProperty("是否时我喜欢")
    private Boolean isMyLike;

    @JsonFormat(pattern = "yyyy年MM月dd日")
    @ApiModelProperty("创建时间")
    private Date createTime;
}
