package ink.whi.video.client;


import ink.whi.common.vo.dto.SimpleVideoInfoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author: qing
 * @Date: 2023/10/29
 */
@FeignClient(value = "video-service", fallback = VideoClientResolver.class)
public interface VideoClient {

    /**
     * 查询视频信息
     * @param videoId
     * @return
     */
    @GetMapping(path = "client/video/{videoId}")
    SimpleVideoInfoDTO queryBasicVideoInfo(@PathVariable Long videoId);

    /**
     * 获取用户已发布视频数
     * @param userId
     * @return
     */
    @GetMapping(path = "client/count/{userId}")
    Integer countVideoByUserId(@PathVariable Long userId);
}
