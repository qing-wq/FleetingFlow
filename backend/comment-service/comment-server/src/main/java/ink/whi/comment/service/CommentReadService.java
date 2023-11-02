package ink.whi.comment.service;


import ink.whi.comment.model.dto.TopCommentDTO;
import ink.whi.comment.repo.entity.CommentDO;
import ink.whi.common.model.page.PageParam;

import java.util.List;

/**
 * 评论读接口
 * @author: qing
 * @Date: 2023/11/2
 */
public interface CommentReadService {
    Integer queryCommentCount(Long articleId);

    List<TopCommentDTO> getVideoComments(Long articleId, PageParam newPageInstance);

    CommentDO queryComment(Long commentId);
}
