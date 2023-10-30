package ink.whi.user.service.count;

import ink.whi.common.cache.RedisClient;
import ink.whi.common.statistic.constants.CountConstants;
import ink.whi.common.utils.MapUtils;
import ink.whi.user.model.dto.UserStatisticInfoDTO;
import ink.whi.user.model.dto.VideoFootCountDTO;
import ink.whi.user.repo.dao.UserDao;
import ink.whi.user.repo.dao.UserFootDao;
import ink.whi.user.repo.dao.UserRelationDao;
import ink.whi.user.service.CountService;
import ink.whi.video.client.VideoClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
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

    @Autowired
    private VideoClient videoClient;

//    @Autowired
//    private CommentReadService commentReadService;

    public CountServiceImpl(UserFootDao userFootDao, UserRelationDao userRelationDao) {
        this.userFootDao = userFootDao;
        this.userRelationDao = userRelationDao;
    }

    @PostConstruct
    public void initCache() {
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
//            res.setCommentCount(commentReadService.queryCommentCount(articleId));
        }
        return res;
    }

    /**
     * 获取评论点赞数量
     * @param commentId
     * @return
     */
    @Override
    public Integer queryCommentPraiseCount(Long commentId) {
        return userFootDao.countCommentPraise(commentId);
    }


    @Override
    public UserStatisticInfoDTO queryUserStatisticInfo(Long userId) {
        String key = CountConstants.USER_STATISTIC + userId;
        return (UserStatisticInfoDTO) RedisClient.hGetAll(key, UserStatisticInfoDTO.class);
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
        Integer articleNum = videoClient.countVideoByUserId(userId);

        String key = CountConstants.USER_STATISTIC + userId;
        RedisClient.hMSet(key, MapUtils.create(CountConstants.PRAISE_COUNT, count.getPraiseCount(),
                CountConstants.COLLECTION_COUNT, count.getCollectionCount(),
                CountConstants.VIEW_COUNT, count.getReadCount(),
                CountConstants.FANS_COUNT, fansCount,
                CountConstants.FOLLOW_COUNT, followCount,
                CountConstants.VIDEO_COUNT, articleNum));

    }

    /**
     * 刷新视频的统计信息
     *
     * @param videoId
     */
    public void refreshArticleStatisticInfo(Long videoId) {
        VideoFootCountDTO res = userFootDao.countVideoStatisticByVideoId(videoId);
        if (res == null) {
            res = new VideoFootCountDTO();
        } else {
//            res.setCommentCount(commentReadService.queryCommentCount(videoId));
        }
        RedisClient.hMSet(CountConstants.VIDEO_STATISTIC + videoId,
                MapUtils.create(CountConstants.COLLECTION_COUNT, res.getCollectionCount(),
                        CountConstants.PRAISE_COUNT, res.getPraiseCount(),
                        CountConstants.VIEW_COUNT, res.getReadCount(),
                        CountConstants.COMMENT_COUNT, res.getCommentCount()
                )
        );
    }
}
