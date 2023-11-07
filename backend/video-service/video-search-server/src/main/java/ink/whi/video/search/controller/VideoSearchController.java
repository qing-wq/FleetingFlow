package ink.whi.video.search.controller;

import ink.whi.common.base.BaseRestController;
import ink.whi.common.model.ResVo;
import ink.whi.common.model.dto.SimpleUserInfoDTO;
import ink.whi.common.model.page.PageListVo;
import ink.whi.common.model.page.PageParam;
import ink.whi.user.client.UserClient;
import ink.whi.video.client.VideoClient;
import ink.whi.video.dto.VideoInfoDTO;
import ink.whi.video.search.repo.converter.VideoDocConverter;
import ink.whi.video.search.repo.entity.VideoDoc;
import ink.whi.video.search.service.VideoSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 视频搜索接口
 *
 * @author qing
 * @date 2023/11/4
 */
@RestController
@RequestMapping("search")
public class VideoSearchController extends BaseRestController {

    @Autowired
    private VideoSearchService videoSearchService;

    @Resource
    private UserClient userClient;

    @Resource
    private VideoClient videoClient;

    /**
     * 视频搜索
     *
     * @param key  关键词
     * @param page
     * @param size
     * @return
     */
    @GetMapping(path = "")
    public ResVo<PageListVo<VideoInfoDTO>> searchVideo(@RequestParam("key") String key,
                                                   @RequestParam(name = "page") Long page,
                                                   @RequestParam(name = "size", required = false) Long size) {
        PageParam pageParam = buildPageParam(page, size);
        List<VideoDoc> docs = videoSearchService.searchVideo(key, pageParam);
        if (CollectionUtils.isEmpty(docs)) {
            return ResVo.ok(null);
        }
        List<Long> videoIds = docs.stream().map(s -> s.getId().longValue()).toList();
        PageListVo<VideoInfoDTO> result = videoClient.listVideos(videoIds);
        // 替换高亮信息
//        Map<Integer, VideoDoc> docMap = docs.stream().collect(Collectors.toMap(VideoDoc::getId, t -> t));
//        Map<Long, VideoInfoDTO> dtoMap = result.getList().stream().collect(Collectors.toMap(VideoInfoDTO::getVideoId, t -> t));
//        List<VideoInfoDTO> list = new ArrayList<>();
//        videoIds.forEach(videoId -> {
//            VideoDoc videoDoc = docMap.get(videoId);
//            VideoInfoDTO dto = dtoMap.get(videoId.longValue());
//            dto.setTitle(videoDoc.getTitle());
//            dto.setThumbnail(videoDoc.getThumbnail());
//            list.add(dto);
//        });
//        result.setList(list);
        return ResVo.ok(result);
    }
}
