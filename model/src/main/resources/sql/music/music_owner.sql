drop table if exists `music_owner`;
create table `music_owner`
(
    `id`          int auto_increment comment '主键',
    `music_id`    int comment '音乐 id',
    `user_id`     int comment '用户 id',
    `create_time` datetime comment '创建时间',
    `update_time` datetime comment '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
  ROW_FORMAT = DYNAMIC COMMENT = '音乐用户表';