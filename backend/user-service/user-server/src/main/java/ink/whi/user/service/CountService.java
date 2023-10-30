package ink.whi.user.service;


import ink.whi.user.model.dto.UserStatisticInfoDTO;
import ink.whi.user.model.dto.VideoFootCountDTO;

/**
 * @author: qing
 * @Date: 2023/4/27
 */
public interface CountService {
    //    @PostConstruct
    void initCache();

    /**
     * 查询作者全部视频计数的统计
     * @param userId
     * @return
     */
    VideoFootCountDTO queryVideoCountInfoByUserId(Long userId);

    /**
     * 视频点赞、阅读、评论、收藏
     * @param articleId
     * @return
     */
    VideoFootCountDTO queryVideoCountInfoByVideoId(Long articleId);

    /**
     * 查询点赞数
     * @param commentId
     * @return
     */
    Integer queryCommentPraiseCount(Long commentId);

    UserStatisticInfoDTO queryUserStatisticInfo(Long userId);
}
