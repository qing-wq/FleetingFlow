package ink.whi.video.client;

import ink.whi.common.model.dto.SimpleVideoInfoDTO;
import ink.whi.common.model.page.PageListVo;
import ink.whi.common.model.page.PageParam;
import ink.whi.video.dto.VideoInfoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author: qing
 * @Date: 2023/10/29
 */
@FeignClient(value = "video-service", fallback = VideoClientResolver.class)
public interface VideoClient {

    /**
     * 查询视频信息
     *
     * @param videoId
     * @return
     */
    @GetMapping(path = "client/video/{videoId}")
    SimpleVideoInfoDTO queryBasicVideoInfo(@PathVariable Long videoId);

    /**
     * 获取用户已发布视频数
     *
     * @param userId
     * @return
     */
    @GetMapping(path = "client/count/{userId}")
    Integer countVideoByUserId(@PathVariable Long userId);

    /**
     * 获取用户发布的视频列表
     *
     * @param userId
     * @return
     */
    @GetMapping(path = "client/user/{userId}")
    PageListVo<VideoInfoDTO> listVideosByUserId(@PathVariable Long userId, @SpringQueryMap PageParam pageParam);

    @PostMapping(path = "client/list")
    PageListVo<VideoInfoDTO> listVideos(@RequestParam List<Long> videoIds);

    @GetMapping(path = "client/user/type")
    PageListVo<VideoInfoDTO> queryVideosByUserAndType(@RequestParam Long userId, @SpringQueryMap PageParam pageParam,
                                                      @RequestParam String code);
}
