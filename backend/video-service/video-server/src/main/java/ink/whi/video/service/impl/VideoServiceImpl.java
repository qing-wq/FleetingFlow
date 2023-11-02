package ink.whi.video.service.impl;

import ink.whi.common.context.ReqInfoContext;
import ink.whi.common.enums.*;
import ink.whi.common.exception.BusinessException;
import ink.whi.common.exception.StatusEnum;
import ink.whi.common.permission.UserRole;
import ink.whi.common.utils.NumUtil;
import ink.whi.common.model.dto.BaseUserDTO;
import ink.whi.common.model.dto.SimpleVideoInfoDTO;
import ink.whi.common.model.dto.UserFootDTO;
import ink.whi.common.model.page.PageListVo;
import ink.whi.common.model.page.PageParam;
import ink.whi.user.client.UserClient;
import ink.whi.cache.redis.RedisClient;
import ink.whi.common.statistic.constants.SettingsConstant;
import ink.whi.video.model.dto.VideoInfoDTO;
import ink.whi.video.model.req.TagReq;
import ink.whi.video.model.req.VideoPostReq;
import ink.whi.video.model.video.TagDTO;
import ink.whi.video.repo.video.converter.VideoConverter;
import ink.whi.video.repo.video.dao.VideoDao;
import ink.whi.video.repo.video.dao.VideoTagDao;
import ink.whi.video.repo.video.entity.TagDO;
import ink.whi.video.repo.video.entity.VideoDO;
import ink.whi.video.service.CountService;
import ink.whi.video.service.QiNiuService;
import ink.whi.video.service.VideoService;
import ink.whi.video.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * @author: qing
 * @Date: 2023/10/26
 */
@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideoDao videoDao;

    @Autowired
    private VideoTagDao videoTagDao;

    @Autowired
    private CountService countService;

    @Resource
    private UserClient userClient;

    @Autowired
    private QiNiuService qiNiuService;


    @Override
    public VideoInfoDTO queryBaseVideoInfo(Long videoId) {
        return videoDao.getVideoInfoById(videoId);
    }

    @Override
    public VideoInfoDTO queryTotalVideoInfo(Long videoId, Long readUser) {
        VideoInfoDTO video = queryDetailVideoInfo(videoId);

        // 视频浏览数 +1
        countService.incrVideoViewCount(videoId, video.getUserId());

        // 用户交互信息
        if (readUser != null) {
            UserFootDTO foot  = userClient.saveUserFoot(VideoTypeEnum.VIDEO.getCode(), videoId,
                    video.getUserId(), readUser, OperateTypeEnum.READ.getCode());
            video.setPraised(Objects.equals(foot.getPraiseStat(), PraiseStatEnum.PRAISE.getCode()));
            video.setFollowed(Objects.equals(foot.getCommentStat(), CommentStatEnum.COMMENT.getCode()));
            video.setCollected(Objects.equals(foot.getCollectionStat(), CollectionStatEnum.COLLECTION.getCode()));
        } else {
            // 未登录，全部设置为未处理
            video.setPraised(false);
            video.setFollowed(false);
            video.setCollected(false);
        }

        // 视频计数
        video.setCount(countService.queryVideoStatisticInfo(videoId));
        return video;
    }

    @Override
    public VideoInfoDTO queryDetailVideoInfo(Long videoId) {
        VideoInfoDTO video = videoDao.getVideoInfoById(videoId);
        if (video == null) {
            throw BusinessException.newInstance(StatusEnum.ILLEGAL_ARGUMENTS_MIXED, "视频不存在：" + videoId);
        }

        if (!showContent(video)) {
            throw BusinessException.newInstance(StatusEnum.ILLEGAL_OPERATE, "视频审核中，请稍后再看");
        }

        // 标签
        video.setTags(videoDao.listTagsDetail(videoId));
        return video;
    }

    @Override
    public PageListVo<VideoInfoDTO> queryVideosByCategory(Long categoryId, PageParam pageParam) {
        List<VideoDO> list = videoDao.listVideosByCategory(categoryId, pageParam);
        String domain = "s34mqjagr.hn-bkt.clouddn.com/";
        list.forEach(s -> {
            s.setUrl(domain + s.getUrl());
        });
        return buildVideoListVo(list, pageParam.getPageSize());
    }

    @Override
    public PageListVo<TagDTO> queryTagsList(Long categoryId, PageParam pageParam) {
        List<TagDTO> list = videoDao.listTagsByCategory(categoryId, pageParam);
        return PageListVo.newVo(list, pageParam.getPageSize());
    }

    /**
     * 查询用户已发布的视频信息列表
     * @param userId
     * @param pageParam
     * @return
     */
    @Override
    public PageListVo<SimpleVideoInfoDTO> queryUserVideoList(Long userId, PageParam pageParam) {
        List<VideoDO> videos = videoDao.listVideoByUserId(userId, pageParam);
        List<SimpleVideoInfoDTO> result = VideoConverter.toSimpleVideoDTOList(videos);
        return PageListVo.newVo(result, pageParam.getPageSize());
    }

    /**
     * 上传视频
     *
     * @param videoPostReq
     * @return videoId
     * @throws IOException
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long upload(VideoPostReq videoPostReq) throws IOException {
        String key = qiNiuService.upload(videoPostReq.getFile());
        long size = videoPostReq.getFile().getSize();
        String format = FileUtil.getExtensionName(videoPostReq.getFile().getOriginalFilename());
        VideoDO video = VideoConverter.toDo(videoPostReq, ReqInfoContext.getReqInfo().getUserId());
        video.setFormat(format);
        video.setSize(FileUtil.getSize(size));
        video.setUrl(key);
        // todo: 获取视频编码格式、分辨率

        //  video + video_tag + video_resource
        if (!NumUtil.upZero(videoPostReq.getVideoId())) {
            return insertVideo(video, videoPostReq.getTagIds());
        } else {
            // 更新视频信息
            VideoDO record = videoDao.getById(video.getId());
            if (!Objects.equals(record.getUserId(), video.getUserId())) {
                throw BusinessException.newInstance(StatusEnum.FORBID_ERROR);
            }
            updateVideo(video, videoPostReq.getTagIds());
            return video.getId();
        }
    }

    private Long insertVideo(VideoDO video, Set<Long> tagIds) {
        // 是否开启审核
        video.setStatus(review() ? PushStatusEnum.REVIEW.getCode() : PushStatusEnum.ONLINE.getCode());
        videoDao.save(video);
        Long videoId = video.getId();

        // 保存视频标签
        videoTagDao.insertBatch(videoId, tagIds);

        // 保存视频资源
//        videoDao.saveResource(video.getId(), putKey.key);
        return videoId;
    }

    private void updateVideo(VideoDO video, Set<Long> tagIds) {
        video.setStatus(review() ? PushStatusEnum.REVIEW.getCode() : PushStatusEnum.ONLINE.getCode());
        videoDao.updateById(video);
        Long videoId = video.getId();

        // 保存视频标签
        videoTagDao.updateTags(videoId, tagIds);
    }

    private boolean review() {
        return Boolean.parseBoolean(RedisClient.getStr(SettingsConstant.REVIEW));
    }

    private PageListVo<VideoInfoDTO> buildVideoListVo(List<VideoDO> list, long pageSize) {
        List<VideoInfoDTO> result = list.stream().map(this::fillVideoInfo).toList();
        return PageListVo.newVo(result, pageSize);
    }

    /**
     * 补全视频的计数、分类、标签、视频资源列表等信息
     *
     * @param record
     * @return
     */
    public VideoInfoDTO fillVideoInfo(VideoDO record) {
        VideoInfoDTO dto = VideoConverter.toDto(record);
        Long videoId = record.getId();

        // 标签
        dto.setTags(videoDao.listTagsDetail(videoId));
        // 阅读计数统计
        dto.setCount(countService.queryVideoStatisticInfo(videoId));
        // 视频资源列表
//        dto.setResources(videoDao.listVideoResources(videoId));
        return dto;
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
    public Long saveTag(TagReq req) {
        TagDO tag = VideoConverter.toDo(req);
        return videoDao.saveTag(tag);
    }
}