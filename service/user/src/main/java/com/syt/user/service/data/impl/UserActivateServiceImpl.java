package com.syt.user.service.data.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.syt.model.user.dos.UserActivate;
import com.syt.user.mapper.data.UserActivateMapper;
import com.syt.user.service.data.UserActivateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
public class UserActivateServiceImpl extends ServiceImpl<UserActivateMapper, UserActivate> implements UserActivateService {

}
