package ink.whi.video.service;

import ink.whi.video.dto.VideoStatisticDTO;

/**
 * @author: qing
 * @Date: 2023/10/26
 */
public interface CountService {
    /**
     * 视频浏览数 +1
     * @param videoId
     * @param authorId
     */
    void incrVideoViewCount(Long videoId, Long authorId);

    Integer countVideoByUserId(Long userId);
}
