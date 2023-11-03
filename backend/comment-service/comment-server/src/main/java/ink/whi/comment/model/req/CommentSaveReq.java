package ink.whi.comment.model.req;

import lombok.Data;

/**
 * @author: qing
 * @Date: 2023/11/2
 */
@Data
public class CommentSaveReq {

    /**
     * 评论ID(非必须)
     */
    private Long commentId;

    /**
     * 视频ID
     */
    private Long videoId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 评论内容
     */
    private String commentContent;

    /**
     * 父评论ID
     */
    private Long parentCommentId;

    /**
     * 顶级评论ID
     */
    private Long topCommentId;
}
