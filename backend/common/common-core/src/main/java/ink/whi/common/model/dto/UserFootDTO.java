package ink.whi.common.model.dto;

import lombok.Data;
import java.io.Serial;
import java.io.Serializable;

/**
 * 用户足迹
 *
 * @author qing
 * @date 2023/10/27
 */
@Data
public class UserFootDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 视频/评论ID
     */
    private Long videoId;

    /**
     * 文档类型：1-视频，2-评论
     * {@link ink.whi.common.enums.VideoTypeEnum}
     */
    private Integer type;

    /**
     * 发布该文档的用户ID
     */
    private Long videoUserId;
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
