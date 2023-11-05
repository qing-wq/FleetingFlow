package ink.whi.video.service.impl;

import ink.whi.cache.redis.RedisClient;
import ink.whi.common.statistic.constants.CountConstants;
import ink.whi.video.dto.VideoStatisticDTO;
import ink.whi.video.service.CountReadService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author: qing
 * @Date: 2023/11/3
 */
@Service
public class CountReadServiceImpl implements CountReadService {

    /**
     * 获取视频全部计数信息
     * @param videoId
     * @return
     */
    @Override
    public VideoStatisticDTO queryVideoStatisticInfo(Long videoId) {
        Map<String, Integer> ans = RedisClient.hGetAll(CountConstants.USER_STATISTIC + videoId, Integer.class);
        VideoStatisticDTO info = new VideoStatisticDTO();
        info.setForwardCount(ans.getOrDefault(CountConstants.FORWARD_COUNT, 0));
        info.setPraiseCount(ans.getOrDefault(CountConstants.PRAISE_COUNT, 0));
        info.setCollectionCount(ans.getOrDefault(CountConstants.COLLECTION_COUNT, 0));
        info.setViewCount(ans.getOrDefault(CountConstants.VIEW_COUNT, 0));
        info.setForwardCount(ans.getOrDefault(CountConstants.FANS_COUNT, 0));
        return info;
    }
}
