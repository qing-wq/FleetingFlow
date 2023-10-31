package ink.whi.video.client;

import ink.whi.common.exception.BusinessException;
import ink.whi.common.exception.StatusEnum;
import ink.whi.common.vo.dto.SimpleVideoInfoDTO;
import ink.whi.common.vo.page.PageListVo;
import ink.whi.common.vo.page.PageParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

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

    @Override
    public PageListVo<SimpleVideoInfoDTO> listVideosByUserId(Long userId, PageParam pageParam) {
        log.error("Video 服务异常：listVideosByUserId 请求失败");
        throw BusinessException.newInstance(StatusEnum.UNEXPECT_ERROR, " video-server 服务异常");
//        return null;
    }
}