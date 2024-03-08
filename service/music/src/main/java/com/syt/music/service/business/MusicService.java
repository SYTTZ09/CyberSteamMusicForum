package com.syt.music.service.business;

import com.syt.model.common.dtos.res.Response;
import com.syt.model.music.dtos.req.UploadRequest;

public interface MusicService {
    /**
     * 上传音乐
     * @param request
     * @return
     */
    public Response<String> upload(UploadRequest request);

}
