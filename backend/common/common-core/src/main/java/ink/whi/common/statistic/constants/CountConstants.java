package ink.whi.common.statistic.constants;

/**
 * 用户相关的常量信息
 *
 * @author qing
 * @date 2023/10/28
 */
public interface CountConstants {

    /**
     * 用户相关统计信息
     *
     * key: user_statistic_${userId}
     * field:
     *  -  followCount: 关注数
     *  -  fansCount: 粉丝数
     *  -  videoCount: 已发布视频数
     *  -  forwardCount: 视频分享数
     *  -  praiseCount: 视频点赞数
     *  -  viewCount: 视频播放数
     *  -  collectionCount: 视频被收藏数
     *  -  commentCount: 评论数
     */
    String USER_STATISTIC = "user_statistic_";
    /**
     * 视频相关统计信息
     */
    String VIDEO_STATISTIC = "video_statistic_";

    /**
     * 关注数
     */
    String FOLLOW_COUNT = "followCount";

    /**
     * 粉丝数
     */
    String FANS_COUNT = "fansCount";

    /**
     * 视频发布数
     */
    String VIDEO_COUNT = "videoCount";

    /**
     * 视频分享数
     */
    String FORWARD_COUNT = "forwardCount";

    /**
     * 视频点赞数
     */
    String PRAISE_COUNT = "praiseCount";

    /**
     * 视频播放数
     */
    String VIEW_COUNT = "viewCount";

    /**
     * 视频被收藏数
     */
    String COLLECTION_COUNT = "collectionCount";

    /**
     * 评论数
     */
    String COMMENT_COUNT = "commentCount";
}
