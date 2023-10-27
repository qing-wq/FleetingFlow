package ink.whi.video.service.impl;

import com.qiniu.storage.model.DefaultPutRet;
import ink.whi.common.context.ReqInfoContext;
import ink.whi.common.enums.*;
import ink.whi.common.exception.BusinessException;
import ink.whi.common.exception.StatusEnum;
import ink.whi.common.permission.UserRole;
import ink.whi.common.utils.NumUtil;
import ink.whi.common.vo.dto.BaseUserDTO;
import ink.whi.common.vo.dto.UserFootDTO;
import ink.whi.common.vo.page.PageListVo;
import ink.whi.common.vo.page.PageParam;
import ink.whi.user.client.UserFootClient;
import ink.whi.video.cache.RedisClient;
import ink.whi.video.cache.RedisConstant;
import ink.whi.video.model.dto.VideoInfoDTO;
import ink.whi.video.model.req.VideoPostReq;
import ink.whi.video.repo.video.converter.VideoConverter;
import ink.whi.video.repo.video.dao.VideoDao;
import ink.whi.video.repo.video.dao.VideoTagDao;
import ink.whi.video.repo.video.entity.VideoDO;
import ink.whi.video.service.CountService;
import ink.whi.video.service.QiNiuService;
import ink.whi.video.service.VideoService;
import ink.whi.video.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

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

    @Autowired
    private UserFootClient userFootClient;

    @Autowired
    private QiNiuService qiNiuService;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Override
    public VideoInfoDTO queryBaseVideoInfo(Long videoId) {
        return videoDao.getVideoInfoById(videoId);
    }

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
            video.setFollowed(Objects.equals(foot.getCommentStat(), CommentStatEnum.COMMENT.getCode()));
            video.setCollected(Objects.equals(foot.getCollectionStat(), CollectionStatEnum.COLLECTION.getCode()));
        } else {
            // 未登录，全部设置为未处理
            video.setPraised(false);
            video.setFollowed(false);
            video.setCollected(false);
        }

        // 视频计数信息
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
        return buildVideoListVo(list, pageParam.getPageSize());
    }

    /**
     * 上传视频
     * @param videoPostReq
     * @return videoId
     * @throws IOException
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long upload(VideoPostReq videoPostReq) throws IOException {
        DefaultPutRet putKey = qiNiuService.upload(videoPostReq.getFile());
        String format = FileUtil.getExtensionName(videoPostReq.getFile().getOriginalFilename());
        VideoDO video = VideoConverter.toDo(videoPostReq, ReqInfoContext.getReqInfo().getUserId());
        return transactionTemplate.execute(new TransactionCallback<Long>() {
            //  video + video_tag + video_resource
            @Override
            public Long doInTransaction(TransactionStatus status) {
                if (!NumUtil.upZero(videoPostReq.getVideoId())) {
                    return insertVideo(video, videoPostReq.getTagIds(), putKey);
                } else {
                    // 更新视频信息
                    VideoDO record = videoDao.getById(video.getId());
                    if (!Objects.equals(record.getUserId(), video.getUserId())) {
                        throw BusinessException.newInstance(StatusEnum.FORBID_ERROR);
                    }
                    updateVideo(video, videoPostReq.getTagIds(), putKey);
                    return video.getId();
                }
            }
        });
    }

    private Long insertVideo(VideoDO video, Set<Long> tagIds, DefaultPutRet putKey) {
        // 是否开启审核
        video.setStatus(review() ? PushStatusEnum.REVIEW.getCode() : PushStatusEnum.ONLINE.getCode());
        videoDao.save(video);
        Long videoId = video.getId();

        // 保存视频标签
        videoTagDao.insertBatch(videoId, tagIds);

        // 保存视频资源
        videoDao.saveResource(video.getId(), putKey.key);
        return videoId;
    }

    private void updateVideo(VideoDO video, Set<Long> tagIds, DefaultPutRet putKey) {
        video.setStatus(review() ? PushStatusEnum.REVIEW.getCode() : PushStatusEnum.ONLINE.getCode());
        videoDao.updateById(video);
        Long videoId = video.getId();

        // 保存视频标签
        videoTagDao.updateTags(videoId, tagIds);
    }

    private boolean review() {
        return Boolean.parseBoolean(RedisClient.getStr(RedisConstant.REVIEW));
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

        // 标签
        dto.setTags(videoDao.listTagsDetail(videoId));
        // 阅读计数统计
        dto.setCount(countService.queryVideoStatisticInfo(videoId));
        // 视频资源列表
        dto.setResources(videoDao.listVideoResources(videoId));
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
}
