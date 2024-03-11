package com.syt.music.mapper.business;

import com.syt.model.music.dos.MusicInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IndexListMapper {
    List<MusicInfo> selectLatestPublicMusic(@Param("offset") Integer offset, @Param("limit") Integer limit);

    List<MusicInfo> selectDayPublicMusic(@Param("offset") Integer offset, @Param("limit") Integer limit);

    List<MusicInfo> selectMonthPublicMusic(@Param("offset") Integer offset, @Param("limit") Integer limit);

    List<MusicInfo> selectYearPublicMusic(@Param("offset") Integer offset, @Param("limit") Integer limit);

    List<MusicInfo> selectTotalPublicMusic(@Param("offset") Integer offset, @Param("limit") Integer limit);
}
