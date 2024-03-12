package com.syt.music.service.business.impl;

import com.syt.model.common.dtos.req.LoadMoreRequest;
import com.syt.model.common.dtos.res.Response;
import com.syt.model.common.enums.ResponseCode;
import com.syt.model.music.dos.MusicCollection;
import com.syt.model.music.dos.MusicInfo;
import com.syt.model.music.vos.MusicVO;
import com.syt.music.mapper.business.BehaviorMapper;
import com.syt.music.mapper.business.UserListMapper;
import com.syt.music.service.business.BehaviorService;
import com.syt.music.service.business.UserListService;
import com.syt.util.thread.UserIdThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@Slf4j
public class UserListServiceImpl implements UserListService {

    @Resource
    private UserListMapper userListMapper;

    @Resource
    private BehaviorMapper behaviorMapper;

    @Resource
    private BehaviorService behaviorService;

    private Integer getUserId(Integer id) {
        return !id.equals(0) ? id : UserIdThreadLocalUtil.getUserId();
    }

    /**
     * 用户页面获取全部音乐
     *
     * @param id
     * @param request
     * @return
     */
    @Override
    public Response<List<MusicVO>> allList(Integer id, LoadMoreRequest request) {
        // 校验参数
        if (id == null) {
            return new Response<List<MusicVO>>(ResponseCode.PARAM_REQUIRE.getCode(),
                    "id 不能为空",
                    new ArrayList<>()
            );
        }
        request.checkParam();
        // 获取参数
        Integer currentLength = request.getCurrentLength();
        Integer loadCount = request.getLoadCount();
        Integer userId = getUserId(id);

        List<MusicInfo> musicInfoList;
        // 是否是自己
        if (userId.equals(UserIdThreadLocalUtil.getUserId())) {
            musicInfoList = userListMapper.selectAllMusicByUserId(userId, currentLength, loadCount);
        } else {
            musicInfoList = userListMapper.selectPublicMusicByUserId(userId, currentLength, loadCount);
        }

        return new Response<>(ResponseCode.SUCCESS.getCode(),
                "获取成功",
                behaviorService.checkLikeState(musicInfoList)
        );
    }

    /**
     * 用户页面获取喜欢音乐
     *
     * @param id
     * @param request
     * @return
     */
    @Override
    public Response<List<MusicVO>> likeList(Integer id, LoadMoreRequest request) {
        List<MusicInfo> musicInfoList = new ArrayList<>();
        // 校验参数
        if (id == null) {
            return new Response<List<MusicVO>>(ResponseCode.PARAM_REQUIRE.getCode(),
                    "id 不能为空",
                    behaviorService.checkLikeState(musicInfoList)
            );
        }
        request.checkParam();
        // 获取参数
        Integer currentLength = request.getCurrentLength();
        Integer loadCount = request.getLoadCount();
        Integer userId = getUserId(id);

        // 是否是自己
        if (userId.equals(UserIdThreadLocalUtil.getUserId())) {
            MusicCollection myLikeMusicCollection = behaviorMapper.selectMyLikeMusicCollectionByUserId(userId);
            musicInfoList = userListMapper.selectLikeMusicByCollectionId(myLikeMusicCollection.getId(), currentLength, loadCount);
        } else {
            return new Response<>(ResponseCode.NO_OPERATOR_AUTH.getCode(),
                    "很抱歉，您没有权限查看",
                    behaviorService.checkLikeState(musicInfoList)
            );
        }

        return new Response<>(ResponseCode.SUCCESS.getCode(),
                "获取成功",
                behaviorService.checkLikeState(musicInfoList)
        );
    }

    /**
     * 用户页面获取公开音乐
     *
     * @param id
     * @param request
     * @return
     */
    @Override
    public Response<List<MusicVO>> publicList(Integer id, LoadMoreRequest request) {
        List<MusicInfo> musicInfoList = new ArrayList<>();
        // 校验参数
        if (id == null) {
            return new Response<List<MusicVO>>(ResponseCode.PARAM_REQUIRE.getCode(),
                    "id 不能为空",
                    behaviorService.checkLikeState(musicInfoList)
            );
        }
        request.checkParam();
        // 获取参数
        Integer currentLength = request.getCurrentLength();
        Integer loadCount = request.getLoadCount();
        Integer userId = getUserId(id);

        musicInfoList = userListMapper.selectPublicMusicByUserId(userId, currentLength, loadCount);

        return new Response<>(ResponseCode.SUCCESS.getCode(),
                "获取成功",
                behaviorService.checkLikeState(musicInfoList)
        );
    }

    /**
     * 用户页面获取私密音乐
     *
     * @param id
     * @param request
     * @return
     */
    @Override
    public Response<List<MusicVO>> privateList(Integer id, LoadMoreRequest request) {
        List<MusicInfo> musicInfoList = new ArrayList<>();
        // 校验参数
        if (id == null) {
            return new Response<List<MusicVO>>(ResponseCode.PARAM_REQUIRE.getCode(),
                    "id 不能为空",
                    behaviorService.checkLikeState(musicInfoList)
            );
        }
        request.checkParam();
        // 获取参数
        Integer currentLength = request.getCurrentLength();
        Integer loadCount = request.getLoadCount();
        Integer userId = getUserId(id);

        // 是否是自己
        if (userId.equals(UserIdThreadLocalUtil.getUserId())) {
            musicInfoList = userListMapper.selectPrivateMusicByUserId(userId, currentLength, loadCount);
        } else {
            return new Response<>(ResponseCode.NO_OPERATOR_AUTH.getCode(),
                    "很抱歉，您没有权限查看",
                    behaviorService.checkLikeState(musicInfoList)
                    );
        }

        return new Response<>(ResponseCode.SUCCESS.getCode(),
                "获取成功",
                behaviorService.checkLikeState(musicInfoList)
        );
    }
}