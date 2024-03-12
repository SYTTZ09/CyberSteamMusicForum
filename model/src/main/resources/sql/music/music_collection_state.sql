drop table if exists `music_collection_state`;
create table `music_collection_state`
(
    `id`            int auto_increment comment '主键',
    `collection_id` int comment '收藏夹 id',
    `is_my_like`    boolean comment '是否为我喜欢收藏夹',
    `create_time`   datetime comment '创建时间',
    `update_time`   datetime comment '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
  ROW_FORMAT = DYNAMIC COMMENT = '音乐收藏夹状态表';