package ink.whi.video.search.repo.converter;

import ink.whi.video.dto.VideoInfoDTO;
import ink.whi.video.search.repo.entity.VideoDoc;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * @author: qing
 * @Date: 2023/11/7
 */
public class VideoDocConverter {

    public static List<VideoInfoDTO> toDtoList(List<VideoDoc> list) {
        return list.stream().map(VideoDocConverter::toDto).toList();
    }

    public static VideoInfoDTO toDto(VideoDoc doc) {
        VideoInfoDTO dto = new VideoInfoDTO();
        BeanUtils.copyProperties(doc, dto);
        dto.setUserId(doc.getAuthorId().longValue());
        return dto;
    }
}
