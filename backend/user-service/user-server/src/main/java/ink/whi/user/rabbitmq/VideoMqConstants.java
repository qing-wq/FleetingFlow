package ink.whi.user.rabbitmq;

/**
 * @author qing
 * @date 2023/10/29
 */
public class VideoMqConstants {

    /**
     * 交换机类型
     */
    public static final String VIDEO_TOPIC_EXCHANGE = "video.topic";

    public static final String VIDEO_COMMENT_KEY = "video.comment";
    public static final String VIDEO_REPLY_KEY = "video.reply";
    public static final String VIDEO_PRAISE_KEY = "video.praise";
    public static final String VIDEO_COLLECT_KEY = "video.collect";
    public static final String VIDEO_DELETE_COMMENT_KEY = "video.comment.delete";
    public static final String VIDEO_DELETE_REPLY_KEY = "video.reply.delete";
    public static final String VIDEO_CANCEL_PRAISE_KEY = "video.praise.cancel";
    public static final String VIDEO_CANCEL_COLLECT_KEY = "video.collect.cancel";
    public static final String SYSTEM_KEY = "system";
}