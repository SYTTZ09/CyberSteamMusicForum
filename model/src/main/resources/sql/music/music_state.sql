drop table if exists `music_state`;
create table `music_state`
(
    `id`          int auto_increment comment '主键',
    `music_id`    int comment '音乐 id',
    `is_public`   boolean comment '是否公开',
    `create_time` datetime comment '创建时间',
    `update_time` datetime comment '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
  ROW_FORMAT = DYNAMIC COMMENT = '音乐状态表';