package ink.whi.video.repo.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ink.whi.video.repo.entity.Score;
import ink.whi.video.repo.mapper.ScoreMapper;
import org.springframework.stereotype.Repository;

/**
 * @author: qing
 * @Date: 2023/11/3
 */
@Repository
public class ScoreDao extends ServiceImpl<ScoreMapper, Score> {
    public Score getScoreByVideoId(Long videoId, Long userId) {
        return lambdaQuery().eq(Score::getVideoId, videoId)
                .eq(Score::getUserId, userId)
                .one();
    }
}
