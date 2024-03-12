package com.syt.music.service.business;

import com.syt.model.common.dtos.res.Response;
import com.syt.model.music.dos.MusicInfo;
import com.syt.model.music.vos.MusicVO;

import java.util.List;

public interface BehaviorService {
    /**
     * 改变 喜欢状态
     * @param musicId
     * @return
     */
    Response<String> changeLikeState(Integer musicId);

    /**
     * 检查 喜欢状态
     * @param musicInfoList
     * @return
     */
    List<MusicVO> checkLikeState(List<MusicInfo> musicInfoList);
}
