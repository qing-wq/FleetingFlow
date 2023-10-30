package ink.whi.video.controller;

import ink.whi.common.vo.dto.SimpleVideoInfoDTO;
import ink.whi.video.model.dto.VideoInfoDTO;
import ink.whi.video.service.CountService;
import ink.whi.video.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 视频远程调用接口
 * @author: qing
 * @Date: 2023/10/30
 */
@RestController
@RequestMapping(path = "client")
public class VideoClientRestController {

    @Autowired
    private VideoService videoService;

    @Autowired
    private CountService countService;

    /**
     * 查询视频信息
     * @param videoId
     * @return
     */
    @GetMapping(path = "video/{videoId}")
    SimpleVideoInfoDTO queryBasicVideoInfo(@PathVariable Long videoId) {
        VideoInfoDTO video = videoService.queryBaseVideoInfo(videoId);
        SimpleVideoInfoDTO dto = new SimpleVideoInfoDTO();
        dto.setVideoId(videoId);
        dto.setUserId(video.getUserId());
        dto.setUrl(video.getUrl());
        return dto;
    }

    /**
     * 获取用户已发布视频数
     * @param userId
     * @return
     */
    @GetMapping(path = "count/{userId}")
    Integer countVideoByUserId(@PathVariable Long userId) {
        return countService.countVideoByUserId(userId);
    }
}
