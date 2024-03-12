package com.syt.music.service.data.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.syt.model.music.dos.MusicCollectState;
import com.syt.music.mapper.data.MusicCollectStateMapper;
import com.syt.music.service.data.MusicCollectStateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
public class MusicCollectStateServiceImpl extends ServiceImpl<MusicCollectStateMapper, MusicCollectState> implements MusicCollectStateService {
}
