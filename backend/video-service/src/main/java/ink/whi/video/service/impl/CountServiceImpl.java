package ink.whi.video.service.impl;

import ink.whi.common.cache.RedisClient;
import ink.whi.common.statistic.constants.CountConstants;
import ink.whi.video.model.dto.VideoStatisticDTO;
import ink.whi.video.repo.video.dao.VideoDao;
import ink.whi.video.service.CountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author: qing
 * @Date: 2023/10/26
 */
@Service
public class CountServiceImpl implements CountService {

    @Autowired
    private VideoDao videoDao;

    @Override
    public void incrVideoReadCount(Long videoId, Long authorId) {
        // todo: 计数信息落到MySQL中
        // redis计数信息 +1
        RedisClient.pipelineAction()
                .add(CountConstants.VIDEO_STATISTIC + videoId, CountConstants.READ_COUNT,
                        (connection, key, value) -> connection.hIncrBy(key, value, 1))
                .add(CountConstants.USER_STATISTIC + authorId, CountConstants.READ_COUNT,
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
        String key = CountConstants.VIDEO_STATISTIC + videoId;
        return (VideoStatisticDTO) RedisClient.hGetAll(key, VideoStatisticDTO.class);
    }
}
