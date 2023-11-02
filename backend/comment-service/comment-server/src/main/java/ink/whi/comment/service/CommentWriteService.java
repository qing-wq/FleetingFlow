package ink.whi.comment.service;


import ink.whi.comment.model.req.CommentSaveReq;

/**
 * @author: qing
 * @Date: 2023/5/2
 */
public interface CommentWriteService {
    Long saveComment(CommentSaveReq req);
}
