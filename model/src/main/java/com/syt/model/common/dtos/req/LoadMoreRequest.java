package com.syt.model.common.dtos.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LoadMoreRequest {
    @ApiModelProperty("当前长度")
    private Integer currentLength;
    @ApiModelProperty("加载个数")
    private Integer loadCount;

    public void checkParam() {
        if (this.currentLength == null || this.currentLength < 0) {
            setCurrentLength(0);
        }
        if (this.loadCount == null || this.loadCount < 0) {
            setLoadCount(10);
        }
    }
}