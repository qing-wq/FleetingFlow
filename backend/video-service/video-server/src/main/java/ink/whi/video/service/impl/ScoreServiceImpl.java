package ink.whi.video.service.impl;

import ink.whi.video.repo.dao.ScoreDao;
import ink.whi.video.repo.entity.Score;
import ink.whi.video.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: qing
 * @Date: 2023/11/3
 */
@Service
public class ScoreServiceImpl implements ScoreService {

    @Autowired
    private ScoreDao scoreDao;

    @Override
    public Score queryScore(Long videoId, Long userId) {
        return scoreDao.getScoreByVideoId(videoId, userId);
    }

    @Override
    public void updateScore(Score score) {
        scoreDao.updateById(score);
    }
}
