package ink.whi.video.repo.converter;

import cn.hutool.core.bean.BeanUtil;
import ink.whi.common.model.dto.SimpleUserInfoDTO;
import ink.whi.common.model.dto.SimpleVideoInfoDTO;
import ink.whi.video.dto.VideoInfoDTO;
import ink.whi.video.model.req.TagReq;
import ink.whi.video.model.req.VideoPostReq;
import ink.whi.video.dto.CategoryDTO;
import ink.whi.video.dto.TagDTO;
import ink.whi.video.repo.entity.CategoryDO;
import ink.whi.video.repo.entity.VideoDO;
import ink.whi.video.repo.entity.TagDO;

import java.util.List;

/**
 * @author: qing
 * @Date: 2023/10/26
 */
public class VideoConverter {

    public static VideoInfoDTO toDto(VideoDO video) {
        if (video == null) {
            return null;
        }
        VideoInfoDTO dto = new VideoInfoDTO();
        BeanUtil.copyProperties(video, dto);
        dto.setVideoId(video.getId());

        SimpleUserInfoDTO user = new SimpleUserInfoDTO();
        user.setUserId(video.getUserId());
        dto.setAuthor(user);
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

    public static VideoDO toDo(VideoPostReq req, Long userId) {
        VideoDO video = new VideoDO();
        BeanUtil.copyProperties(req, video);
        video.setUserId(userId);
        return video;
    }

    public static List<CategoryDTO> toCategoryDtoList(List<CategoryDO> list) {
        return list.stream().map(VideoConverter::toDto).toList();
    }

    public static List<TagDTO> toTagDtoList(List<TagDO> list) {
        return list.stream().map(VideoConverter::toDto).toList();
    }

    public static TagDTO toDto(TagDO tag) {
        TagDTO dto = new TagDTO();
        dto.setTagId(tag.getId());
        dto.setTag(tag.getTagName());
        return dto;
    }

    public static SimpleVideoInfoDTO toSimpleVideoDTO(VideoDO video) {
        SimpleVideoInfoDTO dto = new SimpleVideoInfoDTO();
        BeanUtil.copyProperties(video, dto);
        dto.setVideoId(video.getId());
        return dto;
    }

    public static List<SimpleVideoInfoDTO> toSimpleVideoDTOList(List<VideoDO> list) {
        return list.stream().map(VideoConverter::toSimpleVideoDTO).toList();
    }

    public static TagDO toDo(TagReq tagReq) {
        if (tagReq == null) {
            return null;
        }
        TagDO tagDO = new TagDO();
        tagDO.setTagName(tagReq.getTag());
        return tagDO;
    }
}