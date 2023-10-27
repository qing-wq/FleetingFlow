package ink.whi.video.repo.video.converter;

import cn.hutool.core.bean.BeanUtil;
import ink.whi.common.vo.dto.SimpleUserInfoDTO;
import ink.whi.video.model.dto.VideoInfoDTO;
import ink.whi.video.model.video.CategoryDTO;
import ink.whi.video.repo.video.entity.CategoryDO;
import ink.whi.video.repo.video.entity.VideoDO;

import java.util.List;

/**
 * @author: qing
 * @Date: 2023/10/26
 */
public class VideoConverter {

    public static VideoInfoDTO toDto(VideoDO video) {
        VideoInfoDTO dto = new VideoInfoDTO();
        BeanUtil.copyProperties(video, dto);
        CategoryDTO category = new CategoryDTO();
        category.setCategoryId(video.getCategoryId());
        dto.setCategory(category);

        SimpleUserInfoDTO user = new SimpleUserInfoDTO();
        user.setUserId(video.getUserId());
        dto.setSimpleUserInfoDTO(user);
        return dto;
    }

    public static CategoryDTO toDto(CategoryDO category) {
        CategoryDTO dto = new CategoryDTO();
        BeanUtil.copyProperties(category, dto);
        return dto;
    }

    public static List<VideoInfoDTO> toDtoList(List<VideoDO> list) {
        return list.stream().map(VideoConverter::toDto).toList();
    }
}
