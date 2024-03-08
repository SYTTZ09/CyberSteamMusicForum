package com.syt.music.service.business;

import com.syt.model.common.dtos.req.LoadMoreRequest;
import com.syt.model.common.dtos.res.Response;
import com.syt.model.music.dos.MusicInfo;

import java.util.List;

public interface UserListService{

    /**
     * 用户页面获取全部音乐
     * @param id
     * @param request
     * @return
     */
    Response<List<MusicInfo>> allList(Integer id, LoadMoreRequest request);

    /**
     * 用户页面获取喜欢音乐
     * @param id
     * @param request
     * @return
     */
    Response<List<MusicInfo>> likeList(Integer id, LoadMoreRequest request);

    /**
     * 用户页面获取公开音乐
     * @param id
     * @param request
     * @return
     */
    Response<List<MusicInfo>> publicList(Integer id, LoadMoreRequest request);

    /**
     * 用户页面获取私密音乐
     * @param id
     * @param request
     * @return
     */
    Response<List<MusicInfo>> privateList(Integer id, LoadMoreRequest request);
}
