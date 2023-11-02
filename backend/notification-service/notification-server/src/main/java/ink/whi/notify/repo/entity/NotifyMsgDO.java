package ink.whi.notify.repo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import ink.whi.common.enums.NotifyTypeEnum;
import ink.whi.common.model.base.BaseDO;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.experimental.Tolerate;

import java.io.Serial;

/**
 * @author: qing
 * @Date: 2023/10/31
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@Accessors(chain = true)
@TableName("notify_msg")
public class NotifyMsgDO extends BaseDO {
    @Serial
    private static final long serialVersionUID = -4043774744889659100L;

    @Tolerate
    public NotifyMsgDO() {
    }

    /**
     * 消息关联的主体
     * - 如视频收藏、评论、回复评论、点赞消息，这里存视频ID；
     * - 如系统通知消息时，这里存的是系统通知消息正文主键，也可以是0
     * - 如关注，这里就是0
     */
    private Long relatedId;

    /**
     * 消息内容
     */
    private String msg;

    /**
     * 消息通知的用户id
     */
    private Long notifyUserId;

    /**
     * 触发这个消息的用户id
     */
    private Long operateUserId;

    /**
     * 消息类型
     *
     * @see NotifyTypeEnum#getType()
     */
    private Integer type;

    /**
     * 0 未查看 1 已查看
     */
    private Integer state;
}

