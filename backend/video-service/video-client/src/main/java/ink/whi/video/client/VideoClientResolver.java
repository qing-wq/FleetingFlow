package ink.whi.video.client;

import ink.whi.common.exception.BusinessException;
import ink.whi.common.exception.StatusEnum;
import ink.whi.common.model.dto.SimpleVideoInfoDTO;
import ink.whi.common.model.page.PageListVo;
import ink.whi.common.model.page.PageParam;
import ink.whi.video.dto.VideoInfoDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author: qing
 * @Date: 2023/10/29
 */
@Slf4j
@Component
public class VideoClientResolver implements VideoClient{

    @Override
    public SimpleVideoInfoDTO queryBasicVideoInfo(@PathVariable Long videoId) {
        log.error("Video 服务异常：queryBasicVideoInfo 请求失败");
        return null;
    }

    @Override
    public Integer countVideoByUserId(Long userId) {
        log.error("Video 服务异常：countVideoByUserId 请求失败");
        return null;
    }

    @Override
    public PageListVo<VideoInfoDTO> listVideosByUserId(@PathVariable Long userId, PageParam pageParam) {
        log.error("Video 服务异常：listVideosByUserId 请求失败");
        throw BusinessException.newInstance(StatusEnum.UNEXPECT_ERROR, " video-server 服务异常");
//        return null;
    }

    @Override
    public PageListVo<VideoInfoDTO> listVideos(List<Long> videoIds) {
        log.error("Video 服务异常：listVideos 请求失败");
        return null;
    }

    @Override
    public PageListVo<VideoInfoDTO> queryVideosByUserAndType(Long userId, PageParam pageParam, String code) {
        log.error("Video 服务异常：queryVideosByUserAndType 请求失败");
        return null;
    }
}