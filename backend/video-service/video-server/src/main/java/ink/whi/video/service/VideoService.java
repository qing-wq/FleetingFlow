package ink.whi.video.service;

import ink.whi.common.vo.dto.SimpleVideoInfoDTO;
import ink.whi.common.vo.page.PageListVo;
import ink.whi.common.vo.page.PageParam;
import ink.whi.video.model.dto.VideoInfoDTO;
import ink.whi.video.model.req.VideoPostReq;
import ink.whi.video.model.video.TagDTO;

import java.io.IOException;

/**
 * @author: qing
 * @Date: 2023/10/26
 */
public interface VideoService {
    VideoInfoDTO queryTotalVideoInfo(Long videoId, Long userId);

    VideoInfoDTO queryDetailVideoInfo(Long videoId);

    PageListVo<VideoInfoDTO> queryVideosByCategory(Long categoryId, PageParam pageParam);

    Long upload(VideoPostReq videoPostReq) throws IOException;

    VideoInfoDTO queryBaseVideoInfo(Long videoId);

    PageListVo<TagDTO> queryTagsList(Long categoryId, PageParam pageParam);

    PageListVo<SimpleVideoInfoDTO> queryUserVideoList(Long userId, PageParam pageParam);
}
