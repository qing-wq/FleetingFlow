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
     * key: user_statistic_$userId
     * field:
     *  -  followCount: 关注数
     *  -  fansCount: 粉丝数
     *  -  articleCount: 已发布文章数
     *  -  praiseCount: 文章点赞数
     *  -  readCount: 文章被阅读数
     *  -  collectionCount: 文章被收藏数
     */
    String USER_STATISTIC = "user_statistic_";
    /**
     * 文章相关统计信息
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
     * 已发布文章数
     */
    String ARTICLE_COUNT = "articleCount";

    /**
     * 文章点赞数
     */
    String PRAISE_COUNT = "praiseCount";

    /**
     * 文章被阅读数
     */
    String READ_COUNT = "readCount";

    /**
     * 文章被收藏数
     */
    String COLLECTION_COUNT = "collectionCount";

    /**
     * 评论数
     */
    String COMMENT_COUNT = "commentCount";
}
