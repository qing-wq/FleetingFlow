package ink.whi.user.service.count;

import ink.whi.cache.redis.RedisClient;
import ink.whi.comment.client.CommentClient;
import ink.whi.common.statistic.constants.CountConstants;
import ink.whi.common.utils.MapUtils;
import ink.whi.user.model.dto.VideoFootCountDTO;
import ink.whi.user.repo.dao.UserDao;
import ink.whi.user.repo.dao.UserFootDao;
import ink.whi.user.repo.dao.UserRelationDao;
import ink.whi.user.service.CountService;
import ink.whi.video.client.VideoClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 计数服务
 *
 * @author qing
 * @date 2023/10/30
 */
@Slf4j
@Service
public class CountServiceImpl implements CountService {

    @Autowired
    private UserDao userDao;

    private final UserFootDao userFootDao;

    private final UserRelationDao userRelationDao;

    @Resource
    private VideoClient videoClient;

    @Autowired
    private CommentClient commentClient;

    public CountServiceImpl(UserFootDao userFootDao, UserRelationDao userRelationDao) {
        this.userFootDao = userFootDao;
        this.userRelationDao = userRelationDao;
    }

    @Override
    public VideoFootCountDTO queryVideoCountInfoByUserId(Long userId) {
        return userFootDao.countVideoStatisticByUserId(userId);
    }

    @Override
    public VideoFootCountDTO queryVideoCountInfoByVideoId(Long videoId) {
        VideoFootCountDTO res = userFootDao.countVideoStatisticByVideoId(videoId);
        if (res == null) {
            res = new VideoFootCountDTO();
        } else {
            res.setCommentCount(commentClient.queryCommentCount(videoId));
        }
        return res;
    }

    /**
     * 获取评论点赞数量
     *
     * @param commentId
     * @return
     */
    @Override
    public Integer queryCommentPraiseCount(Long commentId) {
        return userFootDao.countCommentPraise(commentId);
    }

    /**
     * 初始化用户缓存
     */
    @Override
    public void initUserCache() {
        long now = System.currentTimeMillis();
        log.info("开始自动刷新用户统计信息");
        Long userId = 0L;
        int batchSize = 20;
        while (true) {
            List<Long> userIds = userDao.scanUserId(userId, batchSize);
            userIds.forEach(this::refreshUserStatisticInfo);
            if (userIds.size() < batchSize) {
                userId = userIds.get(userIds.size() - 1);
                break;
            } else {
                userId = userIds.get(batchSize - 1);
            }
        }
        log.info("结束自动刷新用户统计信息，共耗时: {}ms, maxUserId: {}", System.currentTimeMillis() - now, userId);
    }

    /**
     * 初始化视频缓存
     */
    @Override
    public void initVideoCache() {
        long now = System.currentTimeMillis();
        log.info("开始自动刷新视频统计信息");
        Long videoId = 0L;
        int batchSize = 20;
        while (true) {
            List<Long> userIds = userDao.scanUserId(videoId, batchSize);
            userIds.forEach(this::refreshVideoStatisticInfo);
            if (userIds.size() < batchSize) {
                videoId = userIds.get(userIds.size() - 1);
                break;
            } else {
                videoId = userIds.get(batchSize - 1);
            }
        }
        log.info("结束自动刷新用户统计信息，共耗时: {}ms, maxUserId: {}", System.currentTimeMillis() - now, videoId);
    }

    /**
     * 刷新用户的统计信息
     *
     * @param userId
     */
    public void refreshUserStatisticInfo(Long userId) {
        VideoFootCountDTO count = userFootDao.countVideoStatisticByVideoId(userId);
        if (count == null) {
            count = new VideoFootCountDTO();
        }

        // 获取关注数
        Long followCount = userRelationDao.queryUserFollowsCount(userId);
        // 粉丝数
        Long fansCount = userRelationDao.queryUserFansCount(userId);

        // 查询用户发布的视频数
        Integer videoNum = videoClient.countVideoByUserId(userId);

        String key = CountConstants.USER_STATISTIC + userId;
        RedisClient.hMSet(key, MapUtils.create(CountConstants.PRAISE_COUNT, count.getPraiseCount(),
                CountConstants.COLLECTION_COUNT, count.getCollectionCount(),
                CountConstants.VIEW_COUNT, count.getViewCount(),
                CountConstants.FANS_COUNT, fansCount,
                CountConstants.FOLLOW_COUNT, followCount,
                CountConstants.VIDEO_COUNT, videoNum));
    }

    /**
     * 刷新视频的统计信息
     *
     * @param videoId
     */
    public void refreshVideoStatisticInfo(Long videoId) {
        VideoFootCountDTO res = userFootDao.countVideoStatisticByVideoId(videoId);
        if (res == null) {
            res = new VideoFootCountDTO();
        } else {
            res.setCommentCount(commentClient.queryCommentCount(videoId));
        }
        RedisClient.hMSet(CountConstants.VIDEO_STATISTIC + videoId,
                MapUtils.create(CountConstants.COLLECTION_COUNT, res.getCollectionCount(),
                        CountConstants.PRAISE_COUNT, res.getPraiseCount(),
                        CountConstants.VIEW_COUNT, res.getViewCount(),
                        CountConstants.COMMENT_COUNT, res.getCommentCount()
                )
        );
    }
}
