package ink.whi.user.service;

import ink.whi.common.enums.OperateTypeEnum;
import ink.whi.common.enums.VideoTypeEnum;
import ink.whi.common.model.dto.CommentDTO;
import ink.whi.common.model.dto.UserFootDTO;
import ink.whi.common.model.page.PageParam;
import ink.whi.user.repo.entity.UserFootDO;

import java.util.List;

/**
 * @author: qing
 * @Date: 2023/10/24
 */
public interface UserFootService {
    UserFootDO saveOrUpdateUserFoot(VideoTypeEnum typeEnum, Long video, Long author, Long userId, OperateTypeEnum read);

    UserFootDTO queryUserFoot(Long commentId, Integer code, Long userId);

    List<Long> queryUserReadVideoList(Long userId, PageParam pageParam);

    List<Long> queryUserCollectionVideoList(Long userId, PageParam pageParam);

    void saveCommentFoot(CommentDTO comment, Long userId, Long parentCommentUser);
}
