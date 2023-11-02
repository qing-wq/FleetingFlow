package ink.whi.notify.listener;

import ink.whi.common.model.dto.UserFootDTO;
import ink.whi.notify.service.NotifyMsgService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static ink.whi.notify.constants.VideoMqConstants.*;


/**
 * @author qing
 * @Date 2023/11/2
 */
@Slf4j
@Component
public class VideoListener {
    public static final String VIDEO_PRAISE_QUEUE = "video.praise.queue";
    public static final String VIDEO_COLLECT_QUEUE = "video.collect.queue";
    public static final String VIDEO_CANCEL_PRAISE_QUEUE = "video.praise.cancel.queue";
    public static final String VIDEO_CANCEL_COLLECT_QUEUE = "video.collect.cancel.queue";
    public static final String SYSTEM_QUEUE = "system.queue";
    @Autowired
    private NotifyMsgService notifyService;

    /**
     * 视频点赞
     * @param foot
     */
    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange(name = VIDEO_TOPIC_EXCHANGE, type = ExchangeTypes.TOPIC),
            value = @Queue(name = VIDEO_PRAISE_QUEUE),
            key = VIDEO_PRAISE_KEY
    ))
    public void saveVideoPraise(UserFootDTO foot) {
        log.info("[INFO] 用户 {} 点赞了视频 {} ", foot.getUserId(), foot.getVideoId());
        notifyService.saveVideoPraise(foot);
    }

    /**
     * 视频收藏
     * @param foot
     */
    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange(name = VIDEO_TOPIC_EXCHANGE, type = ExchangeTypes.TOPIC),
            value = @Queue(name = VIDEO_COLLECT_QUEUE),
            key = VIDEO_COLLECT_KEY
    ))
    public void saveVideoCollect(UserFootDTO foot) {
        log.info("[INFO] 用户 {} 收藏了视频 {} ", foot.getUserId(), foot.getVideoId());
        notifyService.saveVideoCollect(foot);
    }

    /**
     * 取消视频点赞
     * @param foot
     */
    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange(name = VIDEO_TOPIC_EXCHANGE, type = ExchangeTypes.TOPIC),
            value = @Queue(name = VIDEO_CANCEL_PRAISE_QUEUE),
            key = VIDEO_CANCEL_PRAISE_KEY
    ))
    public void removeVideoPraise(UserFootDTO foot) {
        log.info("[INFO] 用户 {} 取消点赞 {} ", foot.getUserId(), foot.getVideoId());
        notifyService.removeVideoPraise(foot);
    }

    /**
     * 取消视频收藏
     * @param foot
     */
    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange(name = VIDEO_TOPIC_EXCHANGE, type = ExchangeTypes.TOPIC),
            value = @Queue(name = VIDEO_CANCEL_COLLECT_QUEUE),
            key = VIDEO_CANCEL_COLLECT_KEY
    ))
    public void removeVideoCollect(UserFootDTO foot) {
        log.info("[INFO] 用户 {} 取消收藏视频 {} ", foot.getUserId(), foot.getVideoId());
        notifyService.removeVideoCollect(foot);
    }

    /**
     * 用户注册
     * @param userId
     */
    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange(name = VIDEO_TOPIC_EXCHANGE, type = ExchangeTypes.TOPIC),
            value = @Queue(name = SYSTEM_QUEUE),
            key = SYSTEM_KEY
    ))
    public void saveRegisterSystemNotify(Long userId) {
        log.info("[INFO] 用户 {} 注册", userId);
        notifyService.saveRegisterSystemNotify(userId);
    }
}
