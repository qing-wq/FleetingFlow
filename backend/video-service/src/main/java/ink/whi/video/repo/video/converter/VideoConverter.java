package ink.whi.video.repo.video.converter;

import cn.hutool.core.bean.BeanUtil;
import ink.whi.video.model.dto.VideoInfoDTO;
import ink.whi.video.model.video.CategoryDTO;
import ink.whi.video.repo.video.entity.CategoryDO;
import ink.whi.video.repo.video.entity.VideoDO;

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
        return dto;
    }

    public static CategoryDTO toDto(CategoryDO category) {
        CategoryDTO dto = new CategoryDTO();
        BeanUtil.copyProperties(category, dto);
        return dto;
    }
}
