drop table if exists `user_info`;
create table `user_info`
(
    `id`           int auto_increment comment '主键',
    `user_auth_id` int comment '用户认证表主键',
    `name`         varchar(32) comment '名字',
    `avatar`       varchar(32) comment '头像',
    `introduction` varchar(128) comment '介绍',
    `create_time`  datetime comment '创建时间',
    `update_time`  datetime comment '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
  ROW_FORMAT = DYNAMIC COMMENT = '用户信息表';