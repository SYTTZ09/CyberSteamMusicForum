package com.syt.model.common.dtos.req;

import com.syt.model.common.dtos.res.Response;
import lombok.Data;

import java.io.Serializable;

@Data
public class PageRequest {
    private Integer index;
    private Integer size;

    public void checkParam() {
        if (this.index == null || this.index < 0) {
            setIndex(1);
        }
        if (this.size == null || this.size < 0) {
            setSize(5);
        }
    }
}
