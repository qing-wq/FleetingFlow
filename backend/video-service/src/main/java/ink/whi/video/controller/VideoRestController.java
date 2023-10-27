package ink.whi.video.controller;

import ink.whi.common.base.BaseRestController;
import ink.whi.common.context.ReqInfoContext;
import ink.whi.common.exception.StatusEnum;
import ink.whi.common.utils.NumUtil;
import ink.whi.common.vo.ResVo;
import ink.whi.common.vo.page.PageListVo;
import ink.whi.common.vo.page.PageParam;
import ink.whi.video.model.dto.VideoInfoDTO;
import ink.whi.video.model.vo.VideoDetailVO;
import ink.whi.video.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 视频接口
 * @author: qing
 * @Date: 2023/10/26
 */
@RestController
@RequestMapping(path = "video")
public class VideoRestController extends BaseRestController {

    @Autowired
    private VideoService videoService;

    /**
     * 视频详情页
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
        VideoInfoDTO video = videoService.queryTotalVideoInfo(videoId, ReqInfoContext.getReqInfo().getUserId());
        vo.setVideo(video);

        // 作者信息
        return null;
    }

    @GetMapping(path = "category/{category}")
    public ResVo<PageListVo<VideoInfoDTO>> categoryList(@PathVariable("category") Long categoryId,
                                              @RequestParam(name = "page") Long page,
                                              @RequestParam(name = "size", required = false) Long size) {
        PageParam pageParam = buildPageParam(page, size);
        PageListVo<VideoInfoDTO> list = videoService.queryVideosByCategory(categoryId, pageParam);
        return ResVo.ok(list);
    }
}
