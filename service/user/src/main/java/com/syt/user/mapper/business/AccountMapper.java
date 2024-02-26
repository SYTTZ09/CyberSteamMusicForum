package com.syt.user.mapper.business;

import com.syt.model.user.dos.UserAuth;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AccountMapper {
    /**
     * 通过邮件查询已激活的账号
     * @param email
     * @return
     */
    UserAuth selectActivatedAccountByEmail(String email);
    /**
     * 通过邮件查询未激活的账号
     * @param email
     * @return
     */
    UserAuth selectUnActivatedAccountByEmail(String email);
}
