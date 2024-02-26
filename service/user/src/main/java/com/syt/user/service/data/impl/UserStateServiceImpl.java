package com.syt.user.service.data.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.syt.model.user.dos.UserState;
import com.syt.user.mapper.data.UserStateMapper;
import com.syt.user.service.data.UserStateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
public class UserStateServiceImpl extends ServiceImpl<UserStateMapper, UserState> implements UserStateService {

}
