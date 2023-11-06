package ink.whi.video.service;

import ink.whi.video.repo.entity.Score;

/**
 * @author: qing
 * @Date: 2023/11/3
 */
public interface ScoreService {
    Score queryScore(Long videoId, Long userId);

    void updateScore(Score score);
}
