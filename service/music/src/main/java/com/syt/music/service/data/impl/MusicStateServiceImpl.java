package com.syt.music.service.data.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.syt.model.music.dos.MusicState;
import com.syt.music.mapper.data.MusicStateMapper;
import com.syt.music.service.data.MusicStateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
public class MusicStateServiceImpl extends ServiceImpl<MusicStateMapper, MusicState> implements MusicStateService {
}
