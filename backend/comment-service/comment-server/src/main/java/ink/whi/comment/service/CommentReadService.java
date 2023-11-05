package ink.whi.comment.service;


import ink.whi.comment.dto.TopCommentDTO;
import ink.whi.common.model.dto.CommentDTO;
import ink.whi.common.model.page.PageListVo;
import ink.whi.common.model.page.PageParam;

/**
 * 评论读接口
 * @author: qing
 * @Date: 2023/11/2
 */
public interface CommentReadService {
    Integer queryCommentCount(Long videoId);

    CommentDTO queryComment(Long commentId);

    PageListVo<TopCommentDTO> queryVideoComments(Long videoId, PageParam pageParam);
}
