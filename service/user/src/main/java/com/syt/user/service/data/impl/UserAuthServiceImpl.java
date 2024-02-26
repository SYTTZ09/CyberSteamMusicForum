package com.syt.user.service.data.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.syt.model.user.dos.UserAuth;
import com.syt.user.mapper.data.UserAuthMapper;
import com.syt.user.service.data.UserAuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
public class UserAuthServiceImpl extends ServiceImpl<UserAuthMapper, UserAuth> implements UserAuthService {

}
