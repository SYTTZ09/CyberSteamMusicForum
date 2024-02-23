drop table if exists `user_info`;
create table `user_info`
(
    `id`       int comment '主键',
    `name`    varchar(32) comment '名字',
    `avatar` varchar(32) comment '头像',
    `introduction`     varchar(128) comment '介绍',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
  ROW_FORMAT = DYNAMIC COMMENT = '用户信息表';