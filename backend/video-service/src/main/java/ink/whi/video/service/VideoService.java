package ink.whi.video.service;

import ink.whi.video.model.dto.VideoInfoDTO;

/**
 * @author: qing
 * @Date: 2023/10/26
 */
public interface VideoService {
    VideoInfoDTO queryTotalVideoInfo(Long videoId, Long userId);
}
