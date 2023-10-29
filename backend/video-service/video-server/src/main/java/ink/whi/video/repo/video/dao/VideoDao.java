package ink.whi.video.repo.video.dao;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.ChainWrappers;
import ink.whi.common.enums.FileTypeEnum;
import ink.whi.common.enums.PushStatusEnum;
import ink.whi.common.enums.VideoTypeEnum;
import ink.whi.common.enums.YesOrNoEnum;
import ink.whi.common.vo.base.BaseDO;
import ink.whi.common.vo.page.PageParam;
import ink.whi.video.model.dto.VideoInfoDTO;
import ink.whi.video.model.video.CategoryDTO;
import ink.whi.video.model.video.TagDTO;
import ink.whi.video.repo.video.converter.VideoConverter;
import ink.whi.video.repo.video.entity.CategoryDO;
import ink.whi.video.repo.video.entity.TagDO;
import ink.whi.video.repo.video.entity.VideoDO;
import ink.whi.video.repo.video.entity.VideoResource;
import ink.whi.video.repo.video.mapper.*;
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

    @Autowired
    private VideoResourceMapper videoResourceMapper;

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


    public List<VideoResource> listVideoResources(Long videoId) {
        LambdaQueryChainWrapper<VideoResource> wrapper = ChainWrappers.lambdaQueryChain(videoResourceMapper);
        return wrapper.eq(VideoResource::getVideoId, videoId)
                .list();
    }

    public void saveResource(Long videoId, String key) {
        VideoResource resource = new VideoResource();
        resource.setVideoId(videoId);
        resource.setUrl(key);

    }

    public List<TagDTO> listTagsByCategory(Long categoryId, PageParam pageParam) {
        LambdaQueryChainWrapper<TagDO> wrapper = ChainWrappers.lambdaQueryChain(tagMapper);
        List<TagDO> list = wrapper.eq(TagDO::getCategoryId, categoryId)
                .eq(TagDO::getStatus, PushStatusEnum.ONLINE.getCode())
                .eq(TagDO::getDeleted, YesOrNoEnum.NO.getCode())
                .last(PageParam.getLimitSql(pageParam))
                .list();
        return VideoConverter.toTagDtoList(list);
    }
}
