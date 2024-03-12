package com.syt.music.service.data.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.syt.model.music.dos.MusicCollection;
import com.syt.music.mapper.data.MusicCollectionMapper;
import com.syt.music.service.data.MusicCollectionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
public class MusicCollectionServiceImpl extends ServiceImpl<MusicCollectionMapper, MusicCollection> implements MusicCollectionService {
}
