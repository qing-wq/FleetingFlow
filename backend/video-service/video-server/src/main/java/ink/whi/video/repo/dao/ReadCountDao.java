package ink.whi.video.repo.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ink.whi.video.repo.entity.ReadCountDO;
import ink.whi.video.repo.mapper.ReadCountMapper;
import org.springframework.stereotype.Repository;

/**
 * @author: qing
 * @Date: 2023/10/31
 */
@Repository
public class ReadCountDao extends ServiceImpl<ReadCountMapper, ReadCountDO> {

    /**
     * 浏览计数 +1
     * @param videoId
     */
    public void incrViewCount(Long videoId) {
        ReadCountDO record = lambdaQuery().eq(ReadCountDO::getVideoId, videoId).one();
        if (record == null) {
            record = new ReadCountDO()
                    .setVideoId(videoId)
                    .setCnt(1);
            save(record);
            return;
        }
        record.setCnt(record.getCnt() + 1);
        updateById(record);
    }
}
