package ink.whi.video.client;


import ink.whi.common.vo.dto.SimpleVideoInfoDTO;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author: qing
 * @Date: 2023/10/29
 */
@FeignClient(value = "video-server", fallback = VideoClientResolver.class)
public interface VideoClient {
    SimpleVideoInfoDTO queryBasicVideoInfo(Long videoId);
}
