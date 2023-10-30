package ink.whi.video.client;

import ink.whi.common.vo.dto.SimpleVideoInfoDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author: qing
 * @Date: 2023/10/29
 */
@Slf4j
@Component
public class VideoClientResolver implements VideoClient{

    @Override
    public SimpleVideoInfoDTO queryBasicVideoInfo(Long videoId) {
        log.error("Video 服务异常：queryBasicVideoInfo 请求失败");
        return null;
    }

    @Override
    public Integer countVideoByUserId(Long userId) {
        log.error("Video 服务异常：countVideoByUserId 请求失败");
        return null;
    }
}
