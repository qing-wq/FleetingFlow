package ink.whi.user.service;


import ink.whi.common.enums.OperateTypeEnum;

/**
 * @author: qing
 * @Date: 2023/10/31
 */
public interface CountService {

    void initUserCache();

    void initVideoCache();

    Integer queryCommentPraiseCount(Long commentId);

    void incrVideoStatisticCount(Long videoId, Long authorId, OperateTypeEnum type);
}
