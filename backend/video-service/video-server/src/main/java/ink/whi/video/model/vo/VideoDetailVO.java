package ink.whi.video.model.vo;

import ink.whi.comment.dto.TopCommentDTO;
import ink.whi.common.model.dto.SimpleUserInfoDTO;
import ink.whi.video.dto.VideoInfoDTO;
import lombok.Data;

import java.util.List;

/**
 * @author: qing
 * @Date: 2023/10/26
 */
@Data
public class VideoDetailVO {
    /**
     * 视频信息
     */
    private VideoInfoDTO video;

    /**
     * 评论信息
     */
    private List<TopCommentDTO> comments;

    /**
     * 作者相关信息
     */
    private SimpleUserInfoDTO author;
}
