package com.syt.music.mapper.business;

import com.syt.model.music.dos.MusicInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserListMapper {
    List<MusicInfo> selectAllMusicByUserId(@Param("id") Integer id, @Param("offset") Integer offset, @Param("limit") Integer limit);

    List<MusicInfo> selectPublicMusicByUserId(@Param("id") Integer id, @Param("offset") Integer offset, @Param("limit") Integer limit);

    List<MusicInfo> selectPrivateMusicByUserId(@Param("id") Integer id, @Param("offset") Integer offset, @Param("limit") Integer limit);
}
