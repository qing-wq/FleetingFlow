package ink.whi.notify.listener;

import ink.whi.common.model.dto.CommentDTO;
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
 * @Date 2023/8/10
 */
@Slf4j
@Component
public class CommentListener {
    public static final String VIDEO_PRAISE_QUEUE = "video.praise.queue";
    public static final String VIDEO_COLLECT_QUEUE = "video.collect.queue";
    public static final String VIDEO_COMMENT_QUEUE = "video.comment.queue";
    public static final String VIDEO_REPLY_QUEUE = "video.reply.queue";

    @Autowired
    private NotifyMsgService notifyService;

    /**
     * 用户评论
     * @param comment
     */
    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange(name = VIDEO_TOPIC_EXCHANGE, type = ExchangeTypes.TOPIC),
            value = @Queue(name = VIDEO_COMMENT_QUEUE),
            key = VIDEO_COMMENT_KEY
    ))
    public void saveCommentNotify(CommentDTO comment) {
        log.info("[INFO] 用户 {} 评论了视频 {}:{}", comment.getUserId(), comment.getVideoId(), comment.getContent());
        notifyService.saveCommentNotify(comment);
    }

    /**
     * 用户回复
     * @param comment
     */
    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange(name = VIDEO_TOPIC_EXCHANGE, type = ExchangeTypes.TOPIC),
            value = @Queue(name = VIDEO_REPLY_QUEUE),
            key = VIDEO_REPLY_KEY
    ))
    public void saveReplyNotify(CommentDTO comment) {
        log.info("[用户 {} 回复了视频 {} ] {}", comment.getUserId(), comment.getVideoId(), comment.getContent());
        notifyService.saveReplyNotify(comment);
    }
}
