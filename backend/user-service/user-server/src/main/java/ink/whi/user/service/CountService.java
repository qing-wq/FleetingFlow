package ink.whi.user.service;


import ink.whi.user.model.dto.VideoFootCountDTO;

/**
 * @author: qing
 * @Date: 2023/10/31
 */
public interface CountService {

    void initUserCache();

    void initVideoCache();

    /**
     * 查询作者全部视频计数的统计
     * @param userId
     * @return
     */
    VideoFootCountDTO queryVideoCountInfoByUserId(Long userId);

    /**
     * 视频点赞、阅读、评论、收藏
     * @param videoId
     * @return
     */
    VideoFootCountDTO queryVideoCountInfoByVideoId(Long videoId);

    /**
     * 查询点赞数
     * @param commentId
     * @return
     */
    Integer queryCommentPraiseCount(Long commentId);
}
