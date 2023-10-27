package ink.whi.user.service.count;

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
    public VideoFootCountDTO queryArticleCountInfoByUserId(Long userId) {
        return userFootDao.countArticleByUserId(userId);
    }

    @Override
    public VideoFootCountDTO queryArticleCountInfoByArticleId(Long articleId) {
        VideoFootCountDTO res = userFootDao.countArticleByArticleId(articleId);
        if (res == null) {
            res = new VideoFootCountDTO();
        } else {
//            res.setCommentCount(commentReadService.queryCommentCount(articleId));
        }
        return res;
    }

    @Override
    public Integer queryCommentPraiseCount(Long commentId) {
        return userFootDao.countCommentPraise(commentId);
    }
}
