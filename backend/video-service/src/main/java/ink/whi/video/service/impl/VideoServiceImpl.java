package ink.whi.video.service.impl;

import ink.whi.common.context.ReqInfoContext;
import ink.whi.common.enums.*;
import ink.whi.common.exception.BusinessException;
import ink.whi.common.exception.StatusEnum;
import ink.whi.common.permission.UserRole;
import ink.whi.common.vo.dto.BaseUserDTO;
import ink.whi.common.vo.dto.UserFootDTO;
import ink.whi.common.vo.page.PageListVo;
import ink.whi.common.vo.page.PageParam;
import ink.whi.user.client.UserFootClient;
import ink.whi.video.model.dto.VideoInfoDTO;
import ink.whi.video.repo.video.converter.VideoConverter;
import ink.whi.video.repo.video.dao.VideoDao;
import ink.whi.video.repo.video.entity.VideoDO;
import ink.whi.video.service.CountService;
import ink.whi.video.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author: qing
 * @Date: 2023/10/26
 */
@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideoDao videoDao;

    @Autowired
    private CountService countService;

    @Autowired
    private UserFootClient userFootClient;

    @Override
    public VideoInfoDTO queryTotalVideoInfo(Long videoId, Long readUser) {
        VideoInfoDTO video = queryDetailVideoInfo(videoId);

        // 视频浏览数 +1
        countService.incrVideoReadCount(videoId);

        // 文章的操作标记
        if (readUser != null) {
            // 更新用于足迹，并判断是否点赞、评论、收藏
            UserFootDTO foot = userFootClient.saveUserFoot(VideoTypeEnum.ARTICLE, videoId,
                    video.getUserId(), readUser, OperateTypeEnum.READ);
            video.setPraised(Objects.equals(foot.getPraiseStat(), PraiseStatEnum.PRAISE.getCode()));
            video.setCommented(Objects.equals(foot.getCommentStat(), CommentStatEnum.COMMENT.getCode()));
            video.setCollected(Objects.equals(foot.getCollectionStat(), CollectionStatEnum.COLLECTION.getCode()));
        } else {
            // 未登录，全部设置为未处理
            video.setPraised(false);
            video.setCommented(false);
            video.setCollected(false);
        }

        // 视频计数信息
        return video;
    }


    public VideoInfoDTO queryDetailVideoInfo(Long videoId) {
        VideoInfoDTO video = videoDao.queryBaseVideoInfo(videoId);
        if (video == null) {
            throw BusinessException.newInstance(StatusEnum.ILLEGAL_ARGUMENTS_MIXED, "视频不存在：" + videoId);
        }

        if (!showContent(video)) {
            throw BusinessException.newInstance(StatusEnum.ILLEGAL_OPERATE, "视频审核中，请稍后再看");
        }

        // 设置分类信息
        video.setCategory(videoDao.getCategoryById(video.getCategory().getCategoryId()));

        // 标签
        video.setTags(videoDao.listTagsDetail(videoId));
        return video;
    }

    public static boolean showContent(VideoInfoDTO video) {
        // 只能查看已上线的视频
        if (video.getStatus() == PushStatusEnum.ONLINE.getCode()) {
            return true;
        }

        BaseUserDTO user = ReqInfoContext.getReqInfo().getUser();
        if (user == null) {
            return false;
        }

        // 作者本人和超管可以看到审核内容
        return user.getUserId().equals(video.getUserId()) || (user.getRole() != null && user.getRole().equalsIgnoreCase(UserRole.ADMIN.name()));
    }

    @Override
    public PageListVo<VideoInfoDTO> queryVideosByCategory(Long categoryId, PageParam pageParam) {
        List<VideoDO> list = videoDao.listVideosByCategory(categoryId, pageParam);
        return buildVideoListVo(list, pageParam.getPageSize());
    }

    private PageListVo<VideoInfoDTO> buildVideoListVo(List<VideoDO> list, long pageSize) {
        List<VideoInfoDTO> result = list.stream().map(this::fillVideoInfo).toList();
        return PageListVo.newVo(result, pageSize);
    }

    /**
     * 补全视频的计数、分类、标签、视频资源列表等信息
     * @param record
     * @return
     */
    public VideoInfoDTO fillVideoInfo(VideoDO record) {
        VideoInfoDTO dto = VideoConverter.toDto(record);
        Long videoId = record.getId();

        // 设置分类信息
        dto.setCategory(videoDao.getCategoryById(dto.getCategory().getCategoryId()));
        // 标签
        dto.setTags(videoDao.listTagsDetail(videoId));
        // 阅读计数统计
        dto.setCount(countService.queryVideoStatisticInfo(videoId));
        // 视频资源列表
        dto.setResources(videoDao.listVideoResources(videoId));
        return dto;
    }
}
