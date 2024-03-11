package com.syt.music.service.business;

import com.syt.model.common.dtos.req.LoadMoreRequest;
import com.syt.model.common.dtos.res.Response;
import com.syt.model.music.dos.MusicInfo;

import java.util.List;

public interface IndexListService {

    /**
     * 主页获取最近音乐
     * @param request
     * @return
     */
    Response<List<MusicInfo>> latestList(LoadMoreRequest request);

    /**
     * 主页获取日榜音乐
     * @param request
     * @return
     */
    Response<List<MusicInfo>> dayList(LoadMoreRequest request);

    /**
     * 主页获取月榜音乐
     * @param request
     * @return
     */
    Response<List<MusicInfo>> monthList(LoadMoreRequest request);

    /**
     * 主页获取年榜音乐
     * @param request
     * @return
     */
    Response<List<MusicInfo>> yearList(LoadMoreRequest request);

    /**
     * 主页获取总榜音乐
     * @param request
     * @return
     */
    Response<List<MusicInfo>> totalList(LoadMoreRequest request);
}
