package com.syt.music.service.data.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.syt.model.music.dos.MusicCollect;
import com.syt.music.mapper.data.MusicCollectMapper;
import com.syt.music.service.data.MusicCollectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
public class MusicCollectServiceImpl extends ServiceImpl<MusicCollectMapper, MusicCollect> implements MusicCollectService {
}
