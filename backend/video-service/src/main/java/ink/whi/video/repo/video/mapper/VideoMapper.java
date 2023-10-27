package ink.whi.video.repo.video.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import ink.whi.common.vo.page.PageParam;
import ink.whi.video.model.dto.VideoInfoDTO;
import ink.whi.video.repo.video.entity.VideoDO;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author: qing
 * @Date: 2023/10/26
 */
@Component
public interface VideoMapper extends BaseMapper<VideoDO> {
//    List<VideoInfoDTO> listVideoWithResourceByCategory(Long categoryId, PageParam pageParam);
}
