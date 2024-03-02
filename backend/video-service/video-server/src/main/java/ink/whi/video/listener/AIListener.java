package ink.whi.video.listener;

import ink.whi.common.utils.AsyncUtil;
import ink.whi.video.utils.AIUtil;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static ink.whi.notify.constants.VideoMqConstants.*;

/**
 * @author: qing
 * @Date: 2024/3/2
 */
@Slf4j
@Component
public class AIListener {
    public static final String AI_VIDEO_RECOMMEND_QUEUE = "ai.video.recommend.queue";
    public static final String AI_TAG_RECOMMEND_QUEUE = "ai.tag.recommend.queue";
    public static final String AI_CATEGORY_QUEUE = "ai.category.queue";

    /**
     * 视频推荐
     */
    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange(name = VIDEO_TOPIC_EXCHANGE, type = ExchangeTypes.TOPIC),
            value = @Queue(name = AI_VIDEO_RECOMMEND_QUEUE),
            key = AI_VIDEO_RECOMMEND_KEY
    ))
    public List<Long> getVideoRecommendResults(Long userId, Long categoryId) throws JSONException, ExecutionException, InterruptedException {
        Future<List<Long>> future = AsyncUtil.submit(() -> AIUtil.getVideoRecommendResults(userId, categoryId));
        return future.get();
    }
}
