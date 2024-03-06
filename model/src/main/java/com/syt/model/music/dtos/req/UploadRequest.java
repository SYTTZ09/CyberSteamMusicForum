package com.syt.model.music.dtos.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UploadRequest {
    @ApiModelProperty("歌名")
    private String name;

    @ApiModelProperty("歌手")
    private String singer;

    @ApiModelProperty("音乐")
    private String music;

    @ApiModelProperty("封面")
    private String image;

    @ApiModelProperty("介绍")
    private String introduction;

    @ApiModelProperty("是否公开")
    private Boolean isPublic;
}
