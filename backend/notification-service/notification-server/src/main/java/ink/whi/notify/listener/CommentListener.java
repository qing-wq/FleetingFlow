package ink.whi.notify.listener;

import ink.whi.comment.repo.entity.CommentDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static ink.whi.comment.listener.VideoMqConstants.*;


/**
 * @author qing
 * @Date 2023/8/10
 */
@Slf4j
@Component
public class CommentListener {
    public static final String VIDEO_PRAISE_QUEUE = "VIDEO.praise.queue";
    public static final String VIDEO_COLLECT_QUEUE = "VIDEO.collect.queue";
    public static final String VIDEO_COMMENT_QUEUE = "VIDEO.comment.queue";
    public static final String VIDEO_REPLY_QUEUE = "VIDEO.reply.queue";
    public static final String VIDEO_CANCEL_PRAISE_QUEUE = "VIDEO.praise.cancel.queue";
    public static final String VIDEO_CANCEL_COLLECT_QUEUE = "VIDEO.collect.cancel.queue";
    public static final String SYSTEM_QUEUE = "system.queue";

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
    public void saveCommentNotify(CommentDO comment) {
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
    public void saveReplyNotify(CommentDO comment) {
        log.info("[用户 {} 回复了视频 {} ] {}", comment.getUserId(), comment.getVideoId(), comment.getContent());
        notifyService.saveReplyNotify(comment);
    }

    /**
     * 视频点赞
     * @param foot
     */
    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange(name = VIDEO_TOPIC_EXCHANGE, type = ExchangeTypes.TOPIC),
            value = @Queue(name = VIDEO_PRAISE_QUEUE),
            key = VIDEO_PRAISE_KEY
    ))
    public void saveArticlePraise(UserFootDO foot) {
        log.info("[INFO] 用户 {} 点赞了视频 {} ", foot.getUserId(), foot.getVideoId());
        notifyService.saveArticlePraise(foot);
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
    public void saveArticleCollect(UserFootDO foot) {
        log.info("[INFO] 用户 {} 收藏了视频 {} ", foot.getUserId(), foot.getVideoId());
        notifyService.saveArticleCollect(foot);
    }
}