package ink.whi.common.vo.dto;

import ink.whi.common.vo.base.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 用户足迹
 *
 * @author qing
 * @date 2023/10/27
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserFootDTO extends BaseDTO {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 文档ID（视频/评论）
     */
    private Long documentId;

    /**
     * 文档类型：1-视频，2-评论
     */
    private Integer documentType;
    /**
     * 发布该文档的用户ID
     */
    private Long documentUserId;
    /**
     * 收藏状态: 0-未收藏，1-已收藏
     */
    private Integer collectionStat;

    /**
     * 阅读状态: 0-未读，1-已读
     */
    private Integer readStat;

    /**
     * 评论状态: 0-未评论，1-已评论
     */
    private Integer commentStat;

    /**
     * 点赞状态: 0-未点赞，1-已点赞
     */
    private Integer praiseStat;
}
