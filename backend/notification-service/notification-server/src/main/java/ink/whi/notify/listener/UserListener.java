package ink.whi.notify.listener;

import ink.whi.common.enums.FollowStateEnum;
import ink.whi.common.model.dto.UserRelationDTO;
import ink.whi.notify.service.NotifyMsgService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static ink.whi.notify.constants.UserMqConstants.*;


/**
 * 用户消息监听
 * @author qing
 * @Date 2023/10/31
 */
@Slf4j
@Component
public class UserListener {
    public static final String USER_FOLLOW_QUEUE = "user.follow.queue";
    public static final String USER_CANCEL_FOLLOW_QUEUE = "user.cancel.follow.queue";

    @Autowired
    private NotifyMsgService notifyService;

    /**
     * 用户关注
     *
     * @param relation
     */
    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange(name = USER_TOPIC_EXCHANGE, type = ExchangeTypes.TOPIC),
            value = @Queue(name = USER_FOLLOW_QUEUE),
            key = USER_FOLLOW_KEY
    ))
    public void saveFollowNotify(UserRelationDTO relation) {
        log.info("[INFO]  用户 {} 关注了用户 {} ", relation.getFollowUserId(), relation.getUserId());
        notifyService.saveFollowNotify(relation);
    }

    /**
     * 用户取关
     *
     * @param relation
     */
    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange(name = USER_TOPIC_EXCHANGE, type = ExchangeTypes.TOPIC),
            value = @Queue(name = USER_CANCEL_FOLLOW_QUEUE),
            key = USER_CANCEL_FOLLOW_KEY
    ))
    public void removeFollowNotify(UserRelationDTO relation) {
        if (Objects.equals(relation.getFollowState(), FollowStateEnum.FOLLOW.getCode())) {
            log.info("[INFO]  用户 {} 关注了用户 {} ", relation.getFollowUserId(), relation.getUserId());
        } else {
            log.info("[INFO]  用户 {} 取关了用户 {} ", relation.getFollowUserId(), relation.getUserId());
        }
        notifyService.removeFollowNotify(relation);
    }
}
