drop table if exists `user_activate`;
create table `user_activate`
(
    `id`       int auto_increment comment '主键',
    `user_auth_id` int comment '用户认证表主键',
    `code` varchar(32) comment '验证码',
    `deadline` datetime comment '截止时间',
    `create_time` datetime comment '创建时间',
    `update_time` datetime comment '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
  ROW_FORMAT = DYNAMIC COMMENT = '用户激活表';