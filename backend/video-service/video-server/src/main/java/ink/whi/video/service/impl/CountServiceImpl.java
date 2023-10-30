package ink.whi.video.service.impl;

import ink.whi.common.cache.RedisClient;
import ink.whi.common.statistic.constants.CountConstants;
import ink.whi.video.model.dto.VideoStatisticDTO;
import ink.whi.video.repo.video.dao.VideoDao;
import ink.whi.video.service.CountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;


/**
 * @author: qing
 * @Date: 2023/10/26
 */
@Service
public class CountServiceImpl implements CountService {

    @Autowired
    private VideoDao videoDao;

    @Override
    public void incrVideoReadCount(Long videoId, Long authorId) {
        // todo: 计数信息落到MySQL中
        // redis计数信息 +1
        RedisClient.pipelineAction()
                .add(CountConstants.VIDEO_STATISTIC + videoId, CountConstants.READ_COUNT,
                        (connection, key, value) -> connection.hIncrBy(key, value, 1))
                .add(CountConstants.USER_STATISTIC + authorId, CountConstants.READ_COUNT,
                        (connection, key, value) -> connection.hIncrBy(key, value, 1))
                .execute();
    }

    /**
     * 获取视频全部计数信息
     * @param videoId
     * @return
     */
    @Override
    public VideoStatisticDTO queryVideoStatisticInfo(Long videoId) {
        Map<String, Integer> ans = RedisClient.hGetAll(CountConstants.USER_STATISTIC + videoId, Integer.class);
        VideoStatisticDTO info = new VideoStatisticDTO();
        info.setForwardCount(ans.getOrDefault(CountConstants.FORWARD_COUNT, 0));
        info.setPraiseCount(ans.getOrDefault(CountConstants.PRAISE_COUNT, 0));
        info.setCollectionCount(ans.getOrDefault(CountConstants.COLLECTION_COUNT, 0));
        info.setViewCount(ans.getOrDefault(CountConstants.VIEW_COUNT, 0));
        info.setForwardCount(ans.getOrDefault(CountConstants.FANS_COUNT, 0));
        return info;
    }

    /**
     * 更新用户的统计信息
     *
     * @param userId
     */
    public void refreshUserStatisticInfo(Long userId) {
        // 用户的文章点赞数，收藏数，阅读计数
        ArticleFootCountDTO count = userFootDao.countArticleByUserId(userId);
        if (count == null) {
            count = new ArticleFootCountDTO();
        }

        // 获取关注数
        Long followCount = userRelationDao.queryUserFollowCount(userId);
        // 粉丝数
        Long fansCount = userRelationDao.queryUserFansCount(userId);

        // 查询用户发布的文章数
        Integer articleNum = articleDao.countArticleByUser(userId);

        String key = CountConstants.USER_STATISTIC_INFO + userId;
        RedisClient.hMSet(key, MapUtils.create(CountConstants.PRAISE_COUNT, count.getPraiseCount(),
                CountConstants.COLLECTION_COUNT, count.getCollectionCount(),
                CountConstants.READ_COUNT, count.getReadCount(),
                CountConstants.FANS_COUNT, fansCount,
                CountConstants.FOLLOW_COUNT, followCount,
                CountConstants.ARTICLE_COUNT, articleNum));

    }


    public void refreshArticleStatisticInfo(Long articleId) {
        ArticleFootCountDTO res = userFootDao.countArticleByArticleId(articleId);
        if (res == null) {
            res = new ArticleFootCountDTO();
        } else {
            res.setCommentCount(commentReadService.queryCommentCount(articleId));
        }

        RedisClient.hMSet(CountConstants.ARTICLE_STATISTIC_INFO + articleId,
                MapUtils.create(CountConstants.COLLECTION_COUNT, res.getCollectionCount(),
                        CountConstants.PRAISE_COUNT, res.getPraiseCount(),
                        CountConstants.READ_COUNT, res.getReadCount(),
                        CountConstants.COMMENT_COUNT, res.getCommentCount()
                )
        );
    }
}
