package ink.whi.video.repo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import ink.whi.video.model.video.TagDTO;
import ink.whi.video.repo.entity.VideoTagDO;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author: qing
 * @Date: 2023/10/26
 */
@Component
public interface VideoTagMapper extends BaseMapper<VideoTagDO> {
    List<TagDTO> listVideoTagDetails(Long videoId);
}
