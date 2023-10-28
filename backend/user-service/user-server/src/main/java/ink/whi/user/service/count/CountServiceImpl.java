package ink.whi.user.service.count;

import ink.whi.common.cache.RedisClient;
import ink.whi.common.statistic.constants.CountConstants;
import ink.whi.user.model.dto.UserStatisticInfoDTO;
import ink.whi.user.model.dto.VideoFootCountDTO;
import ink.whi.user.repo.dao.UserFootDao;
import ink.whi.user.service.CountService;
import org.springframework.stereotype.Service;

/**
 * 计数服务
 * todo: 计数相关后续使用redis来做
 *
 * @author qing
 * @date 2023/10/24
 */
@Service
public class CountServiceImpl implements CountService {

    private final UserFootDao userFootDao;

//    @Autowired
//    private CommentReadService commentReadService;

    public CountServiceImpl(UserFootDao userFootDao) {
        this.userFootDao = userFootDao;
    }

    @Override
    public VideoFootCountDTO queryVideoCountInfoByUserId(Long userId) {
        return userFootDao.countVideoByUserId(userId);
    }

    @Override
    public VideoFootCountDTO queryVideoCountInfoByVideoId(Long videoId) {
        VideoFootCountDTO res = userFootDao.countArticleByArticleId(videoId);
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

    /**
     * key: user_statistic_$userId
     * field:
     *  -  followCount: 关注数
     *  -  fansCount: 粉丝数
     *  -  articleCount: 已发布文章数
     *  -  praiseCount: 文章点赞数
     *  -  readCount: 文章被阅读数
     *  -  collectionCount: 文章被收藏数
     *
     * @param userId
     * @return
     */
    @Override
    public UserStatisticInfoDTO queryUserStatisticInfo(Long userId) {
        String key = CountConstants.USER_STATISTIC + userId;
        return (UserStatisticInfoDTO) RedisClient.hGetAll(key, UserStatisticInfoDTO.class);
    }
}
