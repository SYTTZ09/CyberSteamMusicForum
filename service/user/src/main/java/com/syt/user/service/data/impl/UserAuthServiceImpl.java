package com.syt.user.service.data.impl;

import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.syt.model.common.dtos.res.Response;
import com.syt.model.common.enums.HttpCode;
import com.syt.model.user.dos.UserAuth;
import com.syt.model.user.dos.UserInfo;
import com.syt.model.user.dtos.req.LoginRequest;
import com.syt.model.user.dtos.res.LoginResponse;
import com.syt.user.mapper.UserAuthMapper;
import com.syt.user.service.data.UserAuthService;
import com.syt.user.service.data.UserInfoService;
import com.syt.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
@Slf4j
public class UserAuthServiceImpl extends ServiceImpl<UserAuthMapper, UserAuth> implements UserAuthService {

}
