drop table if exists `music_collection`;
create table `music_collection`
(
    `id`          int auto_increment comment '主键',
    `user_id`     int comment '用户 id',
    `name`        varchar(128) comment '名字',
    `create_time` datetime comment '创建时间',
    `update_time` datetime comment '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
  ROW_FORMAT = DYNAMIC COMMENT = '音乐收藏夹表';