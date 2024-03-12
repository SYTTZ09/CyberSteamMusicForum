package com.syt.music.service.business.impl;

import com.syt.model.common.dtos.req.LoadMoreRequest;
import com.syt.model.common.dtos.res.Response;
import com.syt.model.common.enums.ResponseCode;
import com.syt.model.music.dos.MusicInfo;
import com.syt.model.music.vos.MusicVO;
import com.syt.music.mapper.business.IndexListMapper;
import com.syt.music.service.business.BehaviorService;
import com.syt.music.service.business.IndexListService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
@Slf4j
public class IndexListServiceImpl implements IndexListService {

    @Resource
    private IndexListMapper indexListMapper;
    
    @Resource
    private BehaviorService behaviorService;

    /**
     * 主页获取最近音乐
     *
     * @param request
     * @return
     */
    @Override
    public Response<List<MusicVO>> latestList(LoadMoreRequest request) {
        request.checkParam();
        // 获取参数
        Integer currentLength = request.getCurrentLength();
        Integer loadCount = request.getLoadCount();

        List<MusicInfo> musicInfoList = indexListMapper.selectLatestPublicMusic(currentLength, loadCount);

        return new Response<>(ResponseCode.SUCCESS.getCode(),
                "获取成功",
                behaviorService.checkLikeState(musicInfoList)
        );
    }

    /**
     * 主页获取日榜音乐
     *
     * @param request
     * @return
     */
    @Override
    public Response<List<MusicVO>> dayList(LoadMoreRequest request) {
        request.checkParam();
        // 获取参数
        Integer currentLength = request.getCurrentLength();
        Integer loadCount = request.getLoadCount();

        List<MusicInfo> musicInfoList = indexListMapper.selectDayPublicMusic(currentLength, loadCount);

        return new Response<>(ResponseCode.SUCCESS.getCode(),
                "获取成功",
                behaviorService.checkLikeState(musicInfoList)
        );
    }

    /**
     * 主页获取月榜音乐
     *
     * @param request
     * @return
     */
    @Override
    public Response<List<MusicVO>> monthList(LoadMoreRequest request) {
        request.checkParam();
        // 获取参数
        Integer currentLength = request.getCurrentLength();
        Integer loadCount = request.getLoadCount();

        List<MusicInfo> musicInfoList = indexListMapper.selectMonthPublicMusic(currentLength, loadCount);

        return new Response<>(ResponseCode.SUCCESS.getCode(),
                "获取成功",
                behaviorService.checkLikeState(musicInfoList)
        );
    }

    /**
     * 主页获取年榜音乐
     *
     * @param request
     * @return
     */
    @Override
    public Response<List<MusicVO>> yearList(LoadMoreRequest request) {
        request.checkParam();
        // 获取参数
        Integer currentLength = request.getCurrentLength();
        Integer loadCount = request.getLoadCount();

        List<MusicInfo> musicInfoList = indexListMapper.selectYearPublicMusic(currentLength, loadCount);

        return new Response<>(ResponseCode.SUCCESS.getCode(),
                "获取成功",
                behaviorService.checkLikeState(musicInfoList)
        );
    }

    /**
     * 主页获取总榜音乐
     *
     * @param request
     * @return
     */
    @Override
    public Response<List<MusicVO>> totalList(LoadMoreRequest request) {
        request.checkParam();
        // 获取参数
        Integer currentLength = request.getCurrentLength();
        Integer loadCount = request.getLoadCount();

        List<MusicInfo> musicInfoList = indexListMapper.selectTotalPublicMusic(currentLength, loadCount);

        return new Response<>(ResponseCode.SUCCESS.getCode(),
                "获取成功",
                behaviorService.checkLikeState(musicInfoList)
        );
    }
}