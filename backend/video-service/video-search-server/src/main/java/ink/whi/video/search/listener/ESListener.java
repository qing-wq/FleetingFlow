package ink.whi.video.search.listener;


import ink.whi.video.search.repo.entity.VideoDoc;
import ink.whi.video.search.repo.mapper.VideoSearchRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import java.util.List;

import static ink.whi.notify.constants.VideoMqConstants.*;

/**
 * ES数据同步监听器
 *
 * @author qing
 * @date 2023/11/4
 */
@Slf4j
@Component
public class ESListener {

    /**
     * 视频保存队列
     */
    private static final String VIDEO_SAVE_QUEUE = "video.save.es.queue";

    /**
     * ES刷新队列
     */
    private static final String VIDEO_REFRESH_QUEUE = "video.refresh.es.queue";

    /**
     * 视频删除队列
     */
    private static final String VIDEO_DELETE_QUEUE = "video.delete.es.queue";

    @Resource
    private VideoSearchRepository videoSearchRepository;

    /**
     * 视频插入或更新
     * @param videoDoc
     */
    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange(name = VIDEO_TOPIC_EXCHANGE, type = ExchangeTypes.TOPIC),
            value = @Queue(name = VIDEO_SAVE_QUEUE),
            key = {VIDEO_INSERT_KEY, VIDEO_UPDATE_KEY}
    ))
    public void saveListener(VideoDoc videoDoc) {
        log.info("[INFO] es save video: {}", videoDoc);
        videoSearchRepository.save(videoDoc);
    }

    /**
     * 视频删除
     * @param videoId
     */
    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange(name = VIDEO_TOPIC_EXCHANGE, type = ExchangeTypes.TOPIC),
            value = @Queue(name = VIDEO_DELETE_QUEUE),
            key = VIDEO_DELETE_KEY
    ))
    public void deleteListener(Long videoId) {
        log.info("[INFO] es delete video: {}", videoId);
        videoSearchRepository.deleteById(videoId);
    }

    /**
     * MySQL同步ES
     * @param videoDocList
     */
    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange(name = VIDEO_TOPIC_EXCHANGE, type = ExchangeTypes.TOPIC),
            value = @Queue(name = VIDEO_REFRESH_QUEUE),
            key = VIDEO_REFRESH_ES_KEY
    ))
    public void refreshES(List<VideoDoc> videoDocList) {
        long now = System.currentTimeMillis();
        log.info("[INFO] 开始同步，数据量：{}", videoDocList.size());
        videoSearchRepository.saveAll(videoDocList);
        log.info("[INFO] 同步完成，耗时：{}", System.currentTimeMillis() - now);
    }
}
