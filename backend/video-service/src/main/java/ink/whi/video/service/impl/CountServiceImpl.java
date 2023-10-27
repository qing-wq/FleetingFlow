package ink.whi.video.service.impl;

import ink.whi.video.cache.RedisClient;
import ink.whi.video.model.dto.VideoStatisticDTO;
import ink.whi.video.service.CountService;
import org.springframework.stereotype.Service;

/**
 * @author: qing
 * @Date: 2023/10/26
 */
@Service
public class CountServiceImpl implements CountService {

    private static final String USER_STATISTIC = "user_statistic";
    private static final String USER_CACHE_PREFIX = "user_statistic_";

    private static final String VIDEO_STATISTIC = "video_statistic";
    public static final String VIDEO_CACHE_PREFIX = "video_statistic_";


    @Override
    public void incrVideoReadCount(Long videoId) {
        String field = VIDEO_CACHE_PREFIX + videoId;
        RedisClient.hIncr(VIDEO_STATISTIC, field, 1);
    }

    @Override
    public VideoStatisticDTO queryVideoStatisticInfo(Long videoId) {
        String field = VIDEO_CACHE_PREFIX + videoId;
        return RedisClient.hGet(VIDEO_STATISTIC, field, VideoStatisticDTO.class);
    }
}
