package ink.whi.video.controller;

import ink.whi.comment.client.CommentClient;
import ink.whi.common.base.BaseRestController;
import ink.whi.common.context.ReqInfoContext;
import ink.whi.common.exception.StatusEnum;
import ink.whi.common.utils.JsonUtil;
import ink.whi.common.utils.NumUtil;
import ink.whi.common.model.ResVo;
import ink.whi.common.model.page.PageListVo;
import ink.whi.common.model.page.PageParam;
import ink.whi.user.client.UserClient;
import ink.whi.video.dto.VideoInfoDTO;
import ink.whi.video.model.req.InteractionReq;
import ink.whi.video.model.req.VideoPostReq;
import ink.whi.video.model.vo.VideoDetailVO;
import ink.whi.video.service.QiNiuService;
import ink.whi.video.service.ScoreService;
import ink.whi.video.service.VideoService;
import ink.whi.video.utils.AIUtil;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * 视频接口
 *
 * @author: qing
 * @Date: 2023/10/26
 */
@Slf4j
@RestController
@RequestMapping(path = "video")
public class VideoRestController extends BaseRestController {

    @Autowired
    private VideoService videoService;

    @Resource
    private UserClient userClient;

    @Autowired
    private QiNiuService qiNiuService;

    @Resource
    private CommentClient commentClient;

    @Autowired
    private ScoreService scoreService;

    /**
     * 视频详情页
     *
     * @param videoId
     * @return
     */
    @GetMapping(path = "detail/{videoId}")
    public ResVo<VideoDetailVO> detail(@PathVariable(name = "videoId") Long videoId) {
        if (!NumUtil.upZero(videoId)) {
            return ResVo.fail(StatusEnum.ILLEGAL_ARGUMENTS_MIXED, "视频ID不合法: " + videoId);
        }
        VideoDetailVO vo = new VideoDetailVO();

        // 视频详情
        Long readUser = ReqInfoContext.getReqInfo().getUserId();
        VideoInfoDTO video = videoService.queryTotalVideoInfo(videoId, readUser);
        vo.setVideo(video);

        // 评论信息
        PageParam pageParam = PageParam.newPageInstance();
        commentClient.listVideoComment(videoId, pageParam.getPageNum(), pageParam.getPageSize());

        // 作者信息
        vo.setAuthor(userClient.querySimpleUserInfo(video.getUserId()));
        return ResVo.ok(vo);
    }

    /**
     * 获取视频流
     *
     * @param categoryId
     * @param page
     * @param size
     * @return
     */
    @GetMapping(path = "category/{category}")
    public ResVo<PageListVo<VideoInfoDTO>> feed(@PathVariable("category") Long categoryId,
                                                @RequestParam(name = "page") Long page,
                                                @RequestParam(name = "size", required = false) Long size) {
        PageParam pageParam = buildPageParam(page, size);
        PageListVo<VideoInfoDTO> list = videoService.queryVideosByCategory(categoryId, pageParam);
        return ResVo.ok(list);
    }

    /**
     * 上传视频
     * fixme: 分片上传，断点续传
     *
     * @param file 视频文件
     * @param json 其他参数的json封装
     * @return
     * @throws IOException
     */
    @PostMapping(path = "upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResVo<Long> upload(MultipartFile file, String json) throws IOException {
        if (file == null) {  // todo：判断文件是否是视频
            return ResVo.fail(StatusEnum.ILLEGAL_ARGUMENTS_MIXED, "请上传视频文件");
        }

        VideoPostReq videoPostReq = JsonUtil.toObj(json, VideoPostReq.class);
        log.info("接收到视频：" + videoPostReq);
        videoPostReq.setFile(file);
        Long videoId = videoService.saveVideo(videoPostReq);
        return ResVo.ok(videoId);
    }

    /**
     * 访问私有文件
     *
     * @param videoId
     * @return
     */
    @GetMapping(value = "/download/{videoId}")
    public ResVo<String> downloadFile(@PathVariable Long videoId) {
        String url = qiNiuService.download(videoService.queryBaseVideoInfo(videoId));
        return ResVo.ok(url);
    }

    /**
     * 创建/获取标签id
     *
     * @param tag
     * @return
     */
    @GetMapping(path = "tag")
    public ResVo<Long> getTagId(@RequestParam String tag) {
        return ResVo.ok(videoService.getTagId(tag));
    }

    /**
     * 上传图片
     * todo 图片上传到cdn
     *
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping(path = "image/upload")
    public ResVo<String> uploadImage(MultipartFile file) throws IOException {
        String key = qiNiuService.uploadImage(file);
        return ResVo.ok(key);
    }

    /**
     * 视频分享
     * @param videoId
     * @return
     */
    @GetMapping("share/{videoId}")
    public ResVo<String> share(@PathVariable Long videoId) {
        return null;
    }

    /**
     * 视频删除
     * @return
     */
    @GetMapping(path = "del/{videoId}")
    public ResVo<String> delete(@PathVariable Long videoId) {
        return null;
    }

    /**
     * 推荐权重埋点
     * @param req
     * @return
     */
    @PostMapping(path = "interaction")
    public ResVo<String> interaction(@RequestBody InteractionReq req) {
//        Score score = scoreService.queryScore(req.getVideoId(), ReqInfoContext.getReqInfo().getUserId());
//        Float nowScore = score.getScore();
//        float newScore = UserInteractionUtil.getScoreChange(req, nowScore);
//        score.setScore(newScore);
//        scoreService.updateScore(score);

        try {
            System.out.println(AIUtil.getCategoryByTitle("狗狗被猫咪欺负了#萌宠 #狗狗 #金太阳原创"));
            System.out.println(AIUtil.getVideoRecommendResults(3L, 3L));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return ResVo.ok("ok");
    }
}