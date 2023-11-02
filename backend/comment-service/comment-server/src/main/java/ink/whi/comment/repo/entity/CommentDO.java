package ink.whi.comment.repo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import ink.whi.common.model.base.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * @author: qing
 * @Date: 2023/11/2
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("comment")
public class CommentDO extends BaseDO {
    @Serial
    private static final long serialVersionUID = 1615020948248956586L;

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
    private String content;

    /**
     * 父评论ID
     */
    private Long parentCommentId;

    /**
     * 顶级评论ID
     */
    private Long topCommentId;

    /**
     * 0未删除 1 已删除
     */
    private Integer deleted;
}