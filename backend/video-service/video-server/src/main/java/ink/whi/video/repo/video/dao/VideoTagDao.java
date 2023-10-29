package ink.whi.video.repo.video.dao;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ink.whi.common.enums.YesOrNoEnum;
import ink.whi.common.vo.base.BaseDO;
import ink.whi.video.repo.video.mapper.VideoTagMapper;
import ink.whi.video.repo.video.entity.VideoTagDO;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author: qing
 * @Date: 2023/10/27
 */
@Repository
public class VideoTagDao extends ServiceImpl<VideoTagMapper, VideoTagDO> {

    /**
     * 插入标签
     * @param videoId
     * @param tagIds
     */
    public void insertBatch(Long videoId, Set<Long> tagIds) {
        if (CollectionUtils.isEmpty(tagIds)) {
            return;
        }

        List<VideoTagDO> list = new ArrayList<>(tagIds.size());
        tagIds.forEach(s -> {
            VideoTagDO tag = new VideoTagDO();
            tag.setVideoId(videoId);
            tag.setTagId(s);
            tag.setDeleted(YesOrNoEnum.NO.getCode());
            list.add(tag);
        });
        saveBatch(list);
    }

    /**
     * 更新标签
     * @param videoId
     * @param newTags
     */
    public void updateTags(Long videoId, Set<Long> newTags) {
        if (newTags == null) {
            return;
        }

        List<VideoTagDO> oldTags = listVideoTags(videoId);
        List<VideoTagDO> delete = new ArrayList<>();
        oldTags.forEach(s -> {
            if (newTags.contains(s.getTagId())) {
                newTags.remove(s.getTagId());
            } else {
                delete.add(s);
            }
        });

        insertBatch(videoId, newTags);
        if (!CollectionUtils.isEmpty(delete)) {
            List<Long> ids = delete.stream().map(BaseDO::getId).toList();
            baseMapper.deleteBatchIds(ids);
        }
    }

    private List<VideoTagDO> listVideoTags(Long videoId) {
        return lambdaQuery().eq(VideoTagDO::getVideoId, videoId)
                .eq(VideoTagDO::getDeleted, YesOrNoEnum.NO.getCode())
                .list();
    }
}
