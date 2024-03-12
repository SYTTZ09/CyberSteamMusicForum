package com.syt.music.mapper.business;

import com.syt.model.music.dos.MusicCollection;
import com.syt.model.music.dos.MusicInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BehaviorMapper {
    MusicCollection selectMyLikeMusicCollectionByUserId(Integer userId);

    List<MusicInfo> selectLikeMusicByCollectionId(Integer id);

    void changeLikeStateById(Integer id);

    void addLikeCountById(Integer id);

    void subtractLikeCountById(Integer id);
}
