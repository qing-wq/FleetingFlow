package ink.whi.user.service;

import ink.whi.common.enums.DocumentTypeEnum;
import ink.whi.common.vo.page.PageParam;
import ink.whi.user.model.dto.SimpleUserInfoDTO;
import ink.whi.user.repo.entity.UserFootDO;

import java.util.List;

/**
 * @author: qing
 * @Date: 2023/10/24
 */
public interface UserFootService {
//    UserFootDO saveOrUpdateUserFoot(DocumentTypeEnum article, Long articleId, Long author, Long userId, OperateTypeEnum read);

    UserFootDO queryUserFoot(Long commentId, Integer code, Long userId);

    List<Long> queryUserReadArticleList(Long userId, PageParam pageParam);

    List<Long> queryUserCollectionArticleList(Long userId, PageParam pageParam);

//    void saveCommentFoot(CommentDO comment, Long userId, Long parentCommentId);
}
