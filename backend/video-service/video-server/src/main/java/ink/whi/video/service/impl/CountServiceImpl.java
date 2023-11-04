package ink.whi.video.service.impl;

import ink.whi.cache.redis.RedisClient;
import ink.whi.common.statistic.constants.CountConstants;
import ink.whi.video.model.dto.VideoStatisticDTO;
import ink.whi.video.repo.dao.ReadCountDao;
import ink.whi.video.repo.dao.VideoDao;
import ink.whi.video.service.CountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author: qing
 * @Date: 2023/10/26
 */
@Service
public class CountServiceImpl implements CountService {

    @Autowired
    private VideoDao videoDao;

    @Autowired
    private ReadCountDao readCountDao;

    @Override
    public void incrVideoViewCount(Long videoId, Long authorId) {
        // todo: 计数信息落到MySQL中
        readCountDao.incrViewCount(videoId);
        // redis计数信息 +1
        RedisClient.pipelineAction()
                .add(CountConstants.VIDEO_STATISTIC + videoId, CountConstants.VIEW_COUNT,
                        (connection, key, value) -> connection.hIncrBy(key, value, 1))
                .add(CountConstants.USER_STATISTIC + authorId, CountConstants.VIEW_COUNT,
                        (connection, key, value) -> connection.hIncrBy(key, value, 1))
                .execute();
    }

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

    /**
     * 获取用户已发布视频数
     * @param userId 用户ID
     * @return 已发布视频数
     */
    @Override
    public Integer countVideoByUserId(Long userId) {
        return videoDao.countVideo(userId);
    }
}
