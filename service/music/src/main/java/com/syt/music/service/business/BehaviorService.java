package com.syt.music.service.business;

import com.syt.model.common.dtos.res.Response;

public interface BehaviorService {
    /**
     * 改变 喜欢状态
     * @param musicId
     * @return
     */
    Response<String> changeLikeState(Integer musicId);
}
