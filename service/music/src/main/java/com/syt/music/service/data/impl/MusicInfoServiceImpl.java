package com.syt.music.service.data.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.syt.model.music.dos.MusicInfo;
import com.syt.music.mapper.data.MusicInfoMapper;
import com.syt.music.service.data.MusicInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
public class MusicInfoServiceImpl extends ServiceImpl<MusicInfoMapper, MusicInfo> implements MusicInfoService {
}
