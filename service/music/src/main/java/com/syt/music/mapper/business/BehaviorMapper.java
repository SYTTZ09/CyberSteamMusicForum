package com.syt.music.mapper.business;

import com.syt.model.music.dos.MusicCollection;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BehaviorMapper {
    MusicCollection selectMyLikeMusicCollectionByUserId(Integer userId);

    void changeLikeState(Integer collectId);
}
