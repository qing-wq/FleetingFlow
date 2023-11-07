package ink.whi.video.service.impl;

import ink.whi.common.context.ReqInfoContext;
import ink.whi.common.enums.*;
import ink.whi.common.exception.BusinessException;
import ink.whi.common.exception.StatusEnum;
import ink.whi.common.properties.QiniuConfigProperties;
import ink.whi.common.utils.MapUtils;
import ink.whi.video.service.*;
import ink.whi.common.utils.NumUtil;
import ink.whi.common.model.dto.UserFootDTO;
import ink.whi.common.model.page.PageListVo;
import ink.whi.common.model.page.PageParam;
import ink.whi.user.client.UserClient;
import ink.whi.cache.redis.RedisClient;
import ink.whi.common.statistic.constants.SettingsConstant;
import ink.whi.video.dto.VideoInfoDTO;
import ink.whi.video.model.req.VideoPostReq;
import ink.whi.video.dto.TagDTO;
import ink.whi.video.repo.converter.VideoConverter;
import ink.whi.video.repo.dao.VideoDao;
import ink.whi.video.repo.dao.VideoTagDao;
import ink.whi.video.repo.entity.VideoDO;
import ink.whi.video.utils.AIUtil;
import ink.whi.video.utils.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;

/**
 * @author: qing
 * @Date: 2023/10/26
 */
@Slf4j
@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideoDao videoDao;

    @Autowired
    private VideoTagDao videoTagDao;

    @Autowired
    private CountService countService;

    @Autowired
    private CountReadService countReadService;

    @Autowired
    private CategoryService categoryService;

    @Resource
    private UserClient userClient;

    @Autowired
    private QiNiuService qiNiuService;

    @Autowired
    private QiniuConfigProperties config;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Override
    public VideoInfoDTO queryBaseVideoInfo(Long videoId) {
        return videoDao.getVideoInfoById(videoId);
    }

    /**
     * video + tag + interact + count
     * @param videoId
     * @param readUser
     * @return
     */
    @Override
    public VideoInfoDTO queryTotalVideoInfo(Long videoId, Long readUser) {
        VideoInfoDTO video = queryDetailVideoInfo(videoId);

        // 视频浏览数 +1
        countService.incrVideoViewCount(videoId, video.getUserId());

        // 用户交互信息
        setUserInteract(video, readUser, videoId);

        // 视频计数
        video.setCount(countReadService.queryVideoStatisticInfo(videoId));
        video.setUrl(config.buildUrl(video.getUrl()));
        return video;
    }

    /**
     * video + tag
     * @param videoId
     * @return
     */
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
    public PageListVo<VideoInfoDTO> queryVideosByCategory(Long userId, Long categoryId, Integer pageSize) {
        userId = userId == null ? -1 : userId;
        categoryId = categoryId == null ? -1 : categoryId;
        List<Long> videoRecommendResults = null;
        try {
            videoRecommendResults = AIUtil.getVideoRecommendResults(userId, categoryId);
        } catch (JSONException e) {
            log.error("获取视频推荐列表失败：{}", e.getMessage());
            e.printStackTrace();
        }
        List<VideoDO> list = videoDao.listVideoByIds(videoRecommendResults);
        return buildVideoListVo(list, pageSize);
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
    public PageListVo<VideoInfoDTO> queryUserVideoList(Long userId, PageParam pageParam) {
        List<VideoDO> videos = videoDao.listVideoByUserId(userId, pageParam);
        return buildVideoListVo(videos, pageParam.getPageSize());
    }

    /**
     * 保存视频
     *
     * @param videoPostReq
     * @return videoId
     * @throws IOException
     */
    @Override
//    @Transactional(rollbackFor = Exception.class)
    public Long saveVideo(VideoPostReq videoPostReq) throws IOException {
        String key = qiNiuService.upload(videoPostReq.getFile());
        long size = videoPostReq.getFile().getSize();
        String filename = videoPostReq.getFile().getOriginalFilename();
        if (!FileUtil.hasVideoExtension(filename)) {
            throw BusinessException.newInstance(StatusEnum.ILLEGAL_ARGUMENTS, "请上传视频文件");
        }

        String format = FileUtil.getExtensionName(filename);
        Long userId = ReqInfoContext.getReqInfo().getUserId();
        VideoDO video = VideoConverter.toDo(videoPostReq, userId);
        video.setFormat(format);
        video.setSize(FileUtil.getSize(size));
        video.setUrl(key);

        // 对标题进行自然语言处理，自动获取分类
        Long categoryId = null;
        try {
            String category = AIUtil.getCategoryByTitle(videoPostReq.getTitle() + videoPostReq.getThumbnail());
            categoryId = categoryService.queryCategoryId(category);
        } catch (JSONException e) {
            log.error("获取视频分类失败：{}", e.getMessage());
        }
        // 默认分类 娱乐
        video.setCategoryId(categoryId == null ? 5 : categoryId);

        return transactionTemplate.execute(new TransactionCallback<Long>() {
            @Override
            public Long doInTransaction(TransactionStatus status) {
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
        });
    }

    private Long insertVideo(VideoDO video, Set<Long> tagIds) {
        // fixme 是否开启审核
        video.setStatus(PushStatusEnum.ONLINE.getCode());
        videoDao.save(video);
        Long videoId = video.getId();

        // 保存视频标签
        videoTagDao.insertBatch(videoId, tagIds);
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
     * video + tag + count + user + interact
     *
     * @param record
     * @return
     */
    public VideoInfoDTO fillVideoInfo(VideoDO record) {
        VideoInfoDTO dto = VideoConverter.toDto(record);
        Long videoId = record.getId();
        dto.setUrl(config.buildUrl(dto.getUrl()));

        // 标签
        dto.setTags(videoDao.listTagsDetail(videoId));
        // 阅读计数统计
        dto.setCount(countReadService.queryVideoStatisticInfo(videoId));
        // 用户信息
        dto.setAuthor(userClient.querySimpleUserInfo(record.getUserId()));
        // 用户交互信息
        Long readUser = ReqInfoContext.getReqInfo().getUserId();
        setUserInteract(dto, readUser, record.getId());
        return dto;
    }

    private void setUserInteract(VideoInfoDTO dto, Long readUser, Long videoId) {
        if (readUser != null) {
            UserFootDTO foot  = userClient.saveUserFoot(VideoTypeEnum.VIDEO.getCode(), videoId,
                    dto.getUserId(), readUser, OperateTypeEnum.READ.getCode());
            dto.setPraised(Objects.equals(foot.getPraiseStat(), PraiseStatEnum.PRAISE.getCode()));
            dto.setFollowed(Objects.equals(foot.getCommentStat(), CommentStatEnum.COMMENT.getCode()));
            dto.setCollected(Objects.equals(foot.getCollectionStat(), CollectionStatEnum.COLLECTION.getCode()));
        } else {
            // 未登录，全部设置为未处理
            dto.setPraised(false);
            dto.setFollowed(false);
            dto.setCollected(false);
        }
    }


    public static boolean showContent(VideoInfoDTO video) {
        // 只能查看已上线的视频
        if (video.getStatus() == PushStatusEnum.ONLINE.getCode()) {
            return true;
        }

        Long userId = ReqInfoContext.getReqInfo().getUserId();
        if (userId == null) {
            return false;
        }
        // 作者本人可以看到审核内容
        return userId.equals(video.getUserId()) ;
    }

    /**
     * 获取标签ID
     * @param tag tagName
     * @return tagId
     */
    @Override
    public Long getTagId(String tag) {
        return videoDao.getTagId(tag);
    }

    @Override
    public PageListVo<VideoInfoDTO> listVideos(List<Long> videoIds) {
        List<VideoDO> videoDOS = videoDao.listVideoByIds(videoIds);
        return buildVideoListVo(videoDOS, PageParam.DEFAULT_PAGE_SIZE);
    }

    /**
     * 根据首页分类查询视频信息
     * @param userId
     * @param pageParam
     * @param code
     * @return
     */
    @Override
    public PageListVo<VideoInfoDTO> queryVideosByUserAndType(Long userId, PageParam pageParam, String code) {
        List<VideoDO> records = null;
        HomeSelectEnum select = HomeSelectEnum.fromCode(code);
        if (select == HomeSelectEnum.WORKS) {
            // 用户的文章列表
            records = videoDao.listVideoByUserId(userId, pageParam);
        } else if (select == HomeSelectEnum.HISTORY) {
            // 用户的阅读记录
            List<Long> articleIds = userClient.queryUserReadVideoList(userId, pageParam);
            records = CollectionUtils.isEmpty(articleIds) ? Collections.emptyList() : videoDao.listByIds(articleIds);
            records = sortByIds(articleIds, records);
        } else if (select == HomeSelectEnum.COLLECTION) {
            // 用户的收藏列表
            List<Long> articleIds = userClient.queryUserCollectionVideoList(userId, pageParam);
            records = CollectionUtils.isEmpty(articleIds) ? Collections.emptyList() : videoDao.listByIds(articleIds);
            records = sortByIds(articleIds, records);
        } else if (select == HomeSelectEnum.LIKE) {
            // 用户的点赞列表
            List<Long> articleIds = userClient.queryUserPraiseVideoList(userId, pageParam);
            records = CollectionUtils.isEmpty(articleIds) ? Collections.emptyList() : videoDao.listByIds(articleIds);
            records = sortByIds(articleIds, records);
        }

        if (CollectionUtils.isEmpty(records)) {
            return PageListVo.emptyVo();
        }
        return buildVideoListVo(records, pageParam.getPageSize());
    }

    /**
     * 排序
     *
     * @param videoIds
     * @param records
     * @return
     */
    private List<VideoDO> sortByIds(List<Long> videoIds, List<VideoDO> records) {
        if (CollectionUtils.isEmpty(videoIds)) {
            return null;
        }
        List<VideoDO> articleDOS = new ArrayList<>();
        Map<Long, VideoDO> articleDOMap = MapUtils.toMap(records, VideoDO::getId, r -> r);
        videoIds.forEach(articleId -> {
            VideoDO article = articleDOMap.get(articleId);
            if (article != null && article.getDeleted() == YesOrNoEnum.NO.getCode()) {
                articleDOS.add(article);
            }
        });
        return articleDOS;
    }
}