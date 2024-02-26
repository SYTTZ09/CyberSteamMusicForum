drop table if exists `user_state`;
create table `user_state`
(
    `id`           int auto_increment comment '主键',
    `user_auth_id` int comment '用户认证表主键',
    `is_activated` boolean default false comment '用户激活状态',
    `create_time`  datetime comment '创建时间',
    `update_time`  datetime comment '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
  ROW_FORMAT = DYNAMIC COMMENT = '用户状态表';