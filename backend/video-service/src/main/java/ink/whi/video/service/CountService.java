package ink.whi.video.service;

import ink.whi.video.model.dto.VideoStatisticDTO;

/**
 * @author: qing
 * @Date: 2023/10/26
 */
public interface CountService {
    void incrVideoReadCount(Long videoId);

    VideoStatisticDTO queryVideoStatisticInfo(Long videoId);
}
