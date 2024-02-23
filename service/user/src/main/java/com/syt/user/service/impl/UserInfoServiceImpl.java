package com.syt.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.syt.model.user.dos.UserInfo;
import com.syt.user.mapper.UserInfoMapper;
import com.syt.user.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {
}
