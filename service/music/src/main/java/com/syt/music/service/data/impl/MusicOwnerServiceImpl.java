package com.syt.music.service.data.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.syt.model.music.dos.MusicOwner;
import com.syt.music.mapper.data.MusicOwnerMapper;
import com.syt.music.service.data.MusicOwnerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
public class MusicOwnerServiceImpl extends ServiceImpl<MusicOwnerMapper, MusicOwner> implements MusicOwnerService {
}
