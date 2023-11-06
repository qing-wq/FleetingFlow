package ink.whi.user.repo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import ink.whi.common.model.base.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;

/**
 * 用户足迹表
 *
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName("user_foot")
public class UserFootDO extends BaseDO {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 视频ID
     */
    private Long videoId;

    /**
     * 类型：1-video，2-评论
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
