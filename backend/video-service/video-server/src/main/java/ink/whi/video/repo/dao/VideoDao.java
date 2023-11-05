package ink.whi.video.repo.dao;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.ChainWrappers;
import ink.whi.common.enums.FileTypeEnum;
import ink.whi.common.enums.PushStatusEnum;
import ink.whi.common.enums.VideoTypeEnum;
import ink.whi.common.enums.YesOrNoEnum;
import ink.whi.common.model.base.BaseDO;
import ink.whi.common.model.page.PageParam;
import ink.whi.video.dto.VideoInfoDTO;
import ink.whi.video.dto.CategoryDTO;
import ink.whi.video.dto.TagDTO;
import ink.whi.video.repo.converter.VideoConverter;
import ink.whi.video.repo.entity.CategoryDO;
import ink.whi.video.repo.entity.TagDO;
import ink.whi.video.repo.entity.VideoDO;
import ink.whi.video.repo.mapper.CategoryMapper;
import ink.whi.video.repo.mapper.TagMapper;
import ink.whi.video.repo.mapper.VideoMapper;
import ink.whi.video.repo.mapper.VideoTagMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: qing
 * @Date: 2023/10/26
 */
@Repository
public class VideoDao extends ServiceImpl<VideoMapper, VideoDO> {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private VideoTagMapper videoTagMapper;

    @Autowired
    private TagMapper tagMapper;

    public VideoInfoDTO getVideoInfoById(Long videoId) {
        VideoDO video = lambdaQuery().eq(VideoDO::getDeleted, YesOrNoEnum.NO.getCode())
                .eq(BaseDO::getId, videoId)
                .one();
        return VideoConverter.toDto(video);
    }

    public CategoryDTO getCategoryById(Long categoryId) {
        LambdaQueryChainWrapper<CategoryDO> wrapper = ChainWrappers.lambdaQueryChain(categoryMapper);
        CategoryDO category = wrapper.eq(BaseDO::getId, categoryId)
                .eq(CategoryDO::getDeleted, YesOrNoEnum.NO.getCode())
                .one();
        return VideoConverter.toDto(category);
    }

    public List<TagDTO> listTagsDetail(Long videoId) {
        return videoTagMapper.listVideoTagDetails(videoId);
    }

    public List<VideoDO> listVideosByCategory(Long categoryId, PageParam pageParam) {
        return lambdaQuery().eq(VideoDO::getCategoryId, categoryId)
                .eq(VideoDO::getDeleted, YesOrNoEnum.NO.getCode())
                .eq(VideoDO::getType, FileTypeEnum.PUBLIC.getCode())
                .eq(VideoDO::getStatus, VideoTypeEnum.VIDEO.getCode())
                .last(PageParam.getLimitSql(pageParam))
                .orderByDesc(BaseDO::getCreateTime)
                .list();
    }

    public List<TagDTO> listTagsByCategory(Long categoryId, PageParam pageParam) {
        LambdaQueryChainWrapper<TagDO> wrapper = ChainWrappers.lambdaQueryChain(tagMapper);
        List<TagDO> list = wrapper.eq(TagDO::getDeleted, YesOrNoEnum.NO.getCode())
                .last(PageParam.getLimitSql(pageParam))
                .list();
        return VideoConverter.toTagDtoList(list);
    }

    /**
     * 查询用户发布视频数
     * @param userId
     * @return
     */
    public Integer countVideo(Long userId) {
        return lambdaQuery().eq(VideoDO::getUserId, userId)
                .eq(VideoDO::getDeleted, YesOrNoEnum.NO.getCode())
                .eq(VideoDO::getStatus, PushStatusEnum.ONLINE.getCode())
                .eq(VideoDO::getType, FileTypeEnum.PUBLIC.getCode())
                .count().intValue();
    }

    public List<VideoDO> listVideoByUserId(Long userId, PageParam pageParam) {
        return lambdaQuery().eq(VideoDO::getUserId, userId)
                .eq(VideoDO::getDeleted, YesOrNoEnum.NO.getCode())
                .eq(VideoDO::getStatus, PushStatusEnum.ONLINE.getCode())
                .eq(VideoDO::getType, FileTypeEnum.PUBLIC.getCode())
                .last(PageParam.getLimitSql(pageParam))
                .orderByDesc(BaseDO::getCreateTime)
                .list();
    }

    public Long getTagId(String tag) {
        LambdaQueryChainWrapper<TagDO> wrapper = ChainWrappers.lambdaQueryChain(tagMapper);
        TagDO record = wrapper.eq(TagDO::getTagName, tag)
                .eq(TagDO::getDeleted, YesOrNoEnum.NO.getCode())
                .one();
        if (record == null) {
            record = new TagDO();
            record.setTagName(tag);
            tagMapper.insert(record);
        }
        return record.getId();
    }
}