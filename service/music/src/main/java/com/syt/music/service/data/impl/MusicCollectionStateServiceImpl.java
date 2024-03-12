package com.syt.music.service.data.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.syt.model.music.dos.MusicCollectionState;
import com.syt.music.mapper.data.MusicCollectionStateMapper;
import com.syt.music.service.data.MusicCollectionStateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
public class MusicCollectionStateServiceImpl extends ServiceImpl<MusicCollectionStateMapper, MusicCollectionState> implements MusicCollectionStateService {

}
