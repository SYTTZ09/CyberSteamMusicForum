drop table if exists `music_info`;
create table `music_info`
(
    `id`           int auto_increment comment '主键',
    `name`         varchar(128) comment '歌名',
    `singer`       varchar(128) comment '歌手',
    `music_src`    varchar(512) comment '音乐',
    `image_src`    varchar(512) comment '封面',
    `introduction` varchar(1024) comment '介绍',
    `like_count`   int comment '喜欢次数',
    `play_count`   int comment '播放次数',
    `create_time`  datetime comment '创建时间',
    `update_time`  datetime comment '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
  ROW_FORMAT = DYNAMIC COMMENT = '音乐信息表';