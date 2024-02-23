package com.syt.model.common.dtos.res;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class PageResponse<T> extends Response<T> implements Serializable {

    private static final long serializableUID = 1L;

    private Integer index;
    private Integer size;
    private Integer count;

    public PageResponse(Integer index, Integer size, Integer count) {
        super();
        this.index = index;
        this.size = size;
        this.count = count;
    }
}