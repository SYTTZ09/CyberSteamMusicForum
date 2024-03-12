drop table if exists `music_collect_state`;
create table `music_collect_state`
(
    `id`          int auto_increment comment '主键',
    `collect_id`  int comment '收藏 id',
    `is_cancel`  boolean comment '是否取消',
    `create_time` datetime comment '创建时间',
    `update_time` datetime comment '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
  ROW_FORMAT = DYNAMIC COMMENT = '音乐收藏状态表';