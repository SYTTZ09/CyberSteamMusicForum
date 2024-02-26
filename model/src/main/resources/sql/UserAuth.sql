drop table if exists `user_auth`;
create table `user_auth`
(
    `id`       int auto_increment comment '主键',
    `email`    varchar(32) comment '邮箱',
    `password` varchar(16) comment '密码',
    `salt`     varchar(32) comment '盐',
    `create_time` datetime comment '创建时间',
    `update_time` datetime comment '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
  ROW_FORMAT = DYNAMIC COMMENT = '用户认证表';