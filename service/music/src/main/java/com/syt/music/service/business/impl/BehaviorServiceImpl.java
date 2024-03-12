package com.syt.music.service.business.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.syt.model.common.dtos.res.Response;
import com.syt.model.common.enums.ResponseCode;
import com.syt.model.music.dos.*;
import com.syt.model.music.vos.MusicVO;
import com.syt.music.mapper.business.BehaviorMapper;
import com.syt.music.service.business.BehaviorService;
import com.syt.music.service.data.MusicCollectService;
import com.syt.music.service.data.MusicCollectStateService;
import com.syt.music.service.data.MusicCollectionService;
import com.syt.music.service.data.MusicCollectionStateService;
import com.syt.util.thread.UserIdThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service
@Transactional
@Slf4j
public class BehaviorServiceImpl implements BehaviorService {
    @Resource
    private BehaviorMapper behaviorMapper;

    @Resource
    private MusicCollectService musicCollectService;

    @Resource
    private MusicCollectStateService musicCollectStateService;

    @Resource
    private MusicCollectionService musicCollectionService;

    @Resource
    private MusicCollectionStateService musicCollectionStateService;


    /**
     * 改变 喜欢状态
     *
     * @param musicId
     * @return
     */
    @Override
    public Response<String> changeLikeState(Integer musicId) {
        // 获取 music id 与 user id
        Integer userId = UserIdThreadLocalUtil.getUserId();

        // 校验
        if (musicId == null) {
            return new Response<>(ResponseCode.PARAM_REQUIRE.getCode(),
                    "id 不能为空"
            );
        }
        if (musicId <= 0) {
            return new Response<>(ResponseCode.Fail.getCode(),
                    "无法喜欢该音乐",
                    "fail"
            );
        }

        // 查看是否有我喜欢收藏夹
        MusicCollection dbMusicCollection = behaviorMapper.selectMyLikeMusicCollectionByUserId(userId);
        Date currentTime = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentTime);
        calendar.set(Calendar.MILLISECOND, 0);
        currentTime = calendar.getTime();
        if (dbMusicCollection == null) {
            // 没有就构建一个我喜欢收藏夹
            MusicCollection newMusicCollection = new MusicCollection();
            newMusicCollection.setName("我喜欢");
            newMusicCollection.setUserId(userId);
            newMusicCollection.setCreateTime(currentTime);
            newMusicCollection.setUpdateTime(currentTime);
            musicCollectionService.save(newMusicCollection);

            // 获取 newMusicCollection
            dbMusicCollection = musicCollectionService.getOne(Wrappers.<MusicCollection>lambdaQuery()
                    .eq(MusicCollection::getCreateTime, currentTime)
                    .eq(MusicCollection::getUpdateTime, currentTime)
            );

            // 将 newMusicCollection 设置为 我喜欢收藏夹
            MusicCollectionState newMusicCollectionState = new MusicCollectionState();
            newMusicCollectionState.setCollectionId(dbMusicCollection.getId());
            newMusicCollectionState.setIsMyLike(true);
            newMusicCollectionState.setCreateTime(currentTime);
            newMusicCollectionState.setUpdateTime(currentTime);
            musicCollectionStateService.save(newMusicCollectionState);
        }

        // 查看我喜欢收藏夹中是否有该音乐
        MusicCollect dbMusicCollect = musicCollectService.getOne(Wrappers.<MusicCollect>lambdaQuery()
                .eq(MusicCollect::getMusicId, musicId)
                .eq(MusicCollect::getCollectionId, dbMusicCollection.getId())
        );
        // 没有就添加
        if (dbMusicCollect == null) {
            // 构建 newMusicCollect
            MusicCollect newMusicCollect = new MusicCollect();
            newMusicCollect.setMusicId(musicId);
            newMusicCollect.setCollectionId(dbMusicCollection.getId());
            newMusicCollect.setCreateTime(currentTime);
            newMusicCollect.setUpdateTime(currentTime);
            musicCollectService.save(newMusicCollect);

            // 获取 newMusicCollect
            dbMusicCollect = musicCollectService.getOne(Wrappers.<MusicCollect>lambdaQuery()
                    .eq(MusicCollect::getCreateTime, currentTime)
                    .eq(MusicCollect::getUpdateTime, currentTime)
            );

            // 构建 newMusicCollectState
            MusicCollectState newMusicCollectState = new MusicCollectState();
            newMusicCollectState.setCollectId(dbMusicCollect.getId());
            newMusicCollectState.setIsCancel(false);
            newMusicCollectState.setCreateTime(currentTime);
            newMusicCollectState.setUpdateTime(currentTime);
            musicCollectStateService.save(newMusicCollectState);

            // 修改 MusicInfo Like Count
            // musicLikeCount++
            behaviorMapper.addLikeCountById(musicId);
        } else {
            // 查看状态
            MusicCollectState dbMusicCollectState = musicCollectStateService.getOne(Wrappers.<MusicCollectState>lambdaQuery()
                    .eq(MusicCollectState::getCollectId, dbMusicCollect.getId())
            );

            if (dbMusicCollectState.getIsCancel().equals(false)) {
                // 取消收藏
                dbMusicCollectState.setIsCancel(true);
                // musicLikeCount--
                behaviorMapper.subtractLikeCountById(musicId);
            } else {
                // 收藏
                dbMusicCollectState.setIsCancel(false);
                // musicLikeCount++
                behaviorMapper.addLikeCountById(musicId);
            }
            dbMusicCollectState.setUpdateTime(currentTime);
            musicCollectStateService.updateById(dbMusicCollectState);
        }

        return new Response<>(ResponseCode.SUCCESS.getCode(),
                "音乐喜欢状态改变成功",
                "success"
        );
    }

    /**
     * 检查 喜欢状态
     *
     * @param musicInfoList
     * @return
     */
    @Override
    public List<MusicVO> checkLikeState(List<MusicInfo> musicInfoList) {
        ArrayList<MusicVO> musicVOList = new ArrayList<>();
        Integer userId = UserIdThreadLocalUtil.getUserId();
        if (userId != null) {
            MusicCollection myLikeMusicCollection = behaviorMapper.selectMyLikeMusicCollectionByUserId(userId);
            if (myLikeMusicCollection != null) {
                List<MusicInfo> likeMusicList = behaviorMapper.selectLikeMusicByCollectionId(myLikeMusicCollection.getId());
                Set<MusicInfo> likeMusicSet = new HashSet<>(likeMusicList);
                for (MusicInfo musicInfo : musicInfoList) {
                    MusicVO musicVO = new MusicVO();
                    BeanUtil.copyProperties(musicInfo, musicVO);
                    musicVO.setIsMyLike(likeMusicSet.contains(musicInfo));
                    musicVOList.add(musicVO);
                }
                return musicVOList;
            }
        }
        for (MusicInfo musicInfo : musicInfoList) {
            MusicVO musicVO = new MusicVO();
            BeanUtil.copyProperties(musicInfo, musicVO);
            musicVOList.add(musicVO);
        }
        return musicVOList;
    }
}
