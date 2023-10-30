drop database if exists `qiniu`;

create database `qiniu`;

use qiniu;

create table category
(
    id            int unsigned auto_increment comment '主键ID'
        primary key,
    category_name varchar(64) default ''                  not null comment '类目名称',
    status        tinyint     default 0                   not null comment '状态：0-未发布，1-已发布',
    `rank`        tinyint     default 0                   not null comment '排序',
    deleted       tinyint     default 0                   not null comment '是否删除',
    create_time   timestamp   default current_timestamp() not null comment '创建时间',
    update_time   timestamp   default current_timestamp() not null on update current_timestamp() comment '最后更新时间'
)
    comment '类目管理表' charset = utf8mb4;

create table comment
(
    id                int unsigned auto_increment comment '主键ID'
        primary key,
    video_id          int unsigned default 0                   not null comment '视频ID',
    user_id           int unsigned default 0                   not null comment '用户ID',
    content           varchar(300) default ''                  not null comment '评论内容',
    top_comment_id    int          default 0                   not null comment '顶级评论ID',
    parent_comment_id int unsigned default 0                   not null comment '父评论ID',
    deleted           tinyint      default 0                   not null comment '是否删除',
    create_time       timestamp    default current_timestamp() not null comment '创建时间',
    update_time       timestamp    default current_timestamp() not null on update current_timestamp() comment '最后更新时间'
)
    comment '评论表' charset = utf8mb4;

create index idx_article_id
    on comment (video_id);

create index idx_user_id
    on comment (user_id);

create table notify_msg
(
    id              int unsigned auto_increment comment '主键ID'
        primary key,
    related_id      int unsigned     default 0                   not null comment '关联的主键',
    notify_user_id  int unsigned     default 0                   not null comment '通知的用户id',
    operate_user_id int unsigned     default 0                   not null comment '触发这个通知的用户id',
    msg             varchar(1024)    default ''                  not null comment '消息内容',
    type            tinyint unsigned default 0                   not null comment '类型: 0-默认，1-评论，2-回复 3-点赞 4-收藏 5-关注 6-系统',
    state           tinyint unsigned default 0                   not null comment '阅读状态: 0-未读，1-已读',
    create_time     timestamp        default current_timestamp() not null comment '创建时间',
    update_time     timestamp        default current_timestamp() not null on update current_timestamp() comment '最后更新时间'
)
    comment '消息通知列表' charset = utf8mb4;

create index key_notify_user_id_type_state
    on notify_msg (notify_user_id, type, state);

create table read_count
(
    id          int unsigned auto_increment comment '主键ID'
        primary key,
    video_id    int unsigned default 0                   not null comment '视频ID',
    cnt         int unsigned default 0                   not null comment '访问计数',
    create_time timestamp    default current_timestamp() not null comment '创建时间',
    update_time timestamp    default current_timestamp() not null on update current_timestamp() comment '最后更新时间',
    constraint idx_document_id_type
        unique (video_id)
)
    comment '计数表' charset = utf8mb4;

create table tag
(
    id          int unsigned auto_increment comment '主键ID'
        primary key,
    tag_name    varchar(120)                             not null comment '标签名称',
    category_id int unsigned default 0                   not null comment '分类ID',
    status      tinyint      default 0                   not null comment '状态：0-未发布，1-已发布',
    deleted     tinyint      default 0                   not null comment '是否删除',
    create_time timestamp    default current_timestamp() not null comment '创建时间',
    update_time timestamp    default current_timestamp() not null on update current_timestamp() comment '最后更新时间'
)
    comment '标签管理表' charset = utf8mb4;

create index idx_category_id
    on tag (category_id);

create table tool_local_storage
(
    storage_id  bigint auto_increment comment 'ID'
        primary key,
    real_name   varchar(255) null comment '文件真实的名称',
    name        varchar(255) null comment '文件名',
    suffix      varchar(255) null comment '后缀',
    path        varchar(255) null comment '路径',
    type        varchar(255) null comment '类型',
    size        varchar(100) null comment '大小',
    create_by   varchar(255) null comment '创建者',
    update_by   varchar(255) null comment '更新者',
    create_time datetime     null comment '创建日期',
    update_time datetime     null comment '更新时间'
)
    comment '本地存储';

create table tool_qiniu_config
(
    id         bigint       not null comment 'ID'
        primary key,
    access_key text         null comment 'accessKey',
    bucket     varchar(255) null comment 'Bucket 识别符',
    host       varchar(255) not null comment '外链域名',
    secret_key text         null comment 'secretKey',
    type       varchar(255) null comment '空间类型',
    zone       varchar(255) null comment '机房'
)
    comment '七牛云配置';

create table tool_qiniu_content
(
    id          bigint auto_increment comment 'ID'
        primary key,
    name        varchar(180)                          null comment '文件名称',
    bucket      varchar(255)                          null comment 'Bucket 识别符',
    size        varchar(255)                          null comment '文件大小',
    type        varchar(255)                          null comment '文件类型：私有或公开',
    url         varchar(255)                          null comment '文件url',
    suffix      varchar(255)                          null comment '文件后缀',
    update_time timestamp default current_timestamp() null comment '上传或同步的时间',
    constraint uniq_name
        unique (name)
)
    comment '七牛云文件存储';

create table user
(
    id               int unsigned auto_increment comment '主键ID'
        primary key,
    third_account_id varchar(128) default ''                  not null comment '第三方用户ID',
    user_name        varchar(64)  default ''                  not null comment '用户名',
    password         varchar(128) default ''                  not null comment '密码',
    login_type       tinyint      default 0                   not null comment '登录方式: 0-微信登录，1-账号密码登录',
    deleted          tinyint      default 0                   not null comment '是否删除',
    create_time      timestamp    default current_timestamp() not null comment '创建时间',
    update_time      timestamp    default current_timestamp() not null on update current_timestamp() comment '最后更新时间'
)
    comment '用户登录表' charset = utf8mb4;

create index key_third_account_id
    on user (third_account_id);

create table user_foot
(
    id              int unsigned auto_increment comment '主键ID'
        primary key,
    user_id         int unsigned     default 0                   not null comment '用户ID',
    video_id        int unsigned     default 0                   not null comment '视频ID',
    type            tinyint          default 1                   not null comment '类型：1-视频，2-评论',
    video_user_id   int unsigned     default 0                   not null comment '发布该视频的用户ID',
    collection_stat tinyint unsigned default 0                   not null comment '收藏状态: 0-未收藏，1-已收藏，2-取消收藏',
    read_stat       tinyint unsigned default 0                   not null comment '阅读状态: 0-未读，1-已读',
    comment_stat    tinyint unsigned default 0                   not null comment '评论状态: 0-未评论，1-已评论，2-删除评论',
    praise_stat     tinyint unsigned default 0                   not null comment '点赞状态: 0-未点赞，1-已点赞，2-取消点赞',
    create_time     timestamp        default current_timestamp() not null comment '创建时间',
    update_time     timestamp        default current_timestamp() not null on update current_timestamp() comment '最后更新时间',
    constraint idx_user_video
        unique (user_id, video_id, type)
)
    comment '用户足迹表' charset = utf8mb4;

create index idx_video_id
    on user_foot (video_id);

create table user_info
(
    id          int unsigned auto_increment comment '主键ID'
        primary key,
    user_id     int unsigned default 0                   not null comment '用户ID',
    user_name   varchar(50)  default ''                  not null comment '用户名',
    picture     varchar(128) default ''                  not null comment '用户图像',
    email       varchar(100) default ''                  not null comment '电子邮件',
    phone       varchar(100) default ''                  not null comment '手机号码',
    profile     varchar(225) default ''                  not null comment '个人简介',
    user_role   int          default 0                   not null comment '0-普通用户 1-超管',
    ip          varchar(300) default ''                  not null comment '用户的ip信息',
    deleted     tinyint      default 0                   not null comment '是否删除',
    create_time timestamp    default current_timestamp() not null comment '创建时间',
    update_time timestamp    default current_timestamp() not null on update current_timestamp() comment '最后更新时间'
)
    comment '用户个人信息表' charset = utf8mb4;

create index key_user_id
    on user_info (user_id);

create table user_relation
(
    id             int unsigned auto_increment comment '主键ID'
        primary key,
    user_id        int unsigned     default 0                   not null comment '用户ID',
    follow_user_id int unsigned                                 not null comment '关注userId的用户id，即粉丝userId',
    follow_state   tinyint unsigned default 0                   not null comment '阅读状态: 0-未关注，1-已关注，2-取消关注',
    create_time    timestamp        default current_timestamp() not null comment '创建时间',
    update_time    timestamp        default current_timestamp() not null on update current_timestamp() comment '最后更新时间',
    constraint uk_user_follow
        unique (user_id, follow_user_id)
)
    comment '用户关系表' charset = utf8mb4;

create index key_follow_user_id
    on user_relation (follow_user_id);

create table video
(
    id          int unsigned auto_increment comment '主键ID'
        primary key,
    user_id     int unsigned default 0                   not null comment '用户ID',
    title       varchar(120) default ''                  not null comment '视频标题',
    thumbnail   varchar(300) default ''                  not null comment '视频介绍',
    picture     varchar(128) default ''                  not null comment '视频头图',
    category_id int unsigned default 0                   not null comment '分类',
    status      tinyint      default 0                   not null comment '状态：0-未发布，1-已发布，2-待审核',
    bucket      varchar(180) default ''                  not null comment '空间名',
    size        varchar(50)  default ''                  not null comment '大小',
    type        tinyint      default 0                   not null comment '文件类型：0-公开，1-私有',
    url         varchar(500) default ''                  not null comment '视频地址',
    format      varchar(50)  default ''                  not null comment '编码格式',
    resolution  varchar(10)  default ''                  null comment '分辨率',
    deleted     tinyint      default 0                   not null comment '是否删除',
    create_time timestamp    default current_timestamp() not null comment '创建时间',
    update_time timestamp    default current_timestamp() not null on update current_timestamp() comment '最后更新时间'
)
    comment '视频信息表' charset = utf8mb4;

create index idx_category_id
    on video (category_id);

create table video_resource
(
    id       int auto_increment comment '业务id'
        primary key,
    video_id int          default 0  not null comment '视频id',
    url      varchar(300) default '' not null comment '视频地址',
    format   varchar(50)  default '' not null comment '视频格式',
    quality  int          default 0  not null comment '视频清晰度'
)
    comment '视频资源表';

create table video_tag
(
    id          int unsigned auto_increment
        primary key,
    video_id    int unsigned null,
    tag_id      int          null comment '标签id',
    deleted     tinyint      null,
    create_time timestamp    null,
    update_time timestamp    null
);

create index idx_tag_id
    on video_tag (tag_id);

