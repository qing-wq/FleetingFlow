package ink.whi.user.model.dto;

import lombok.Data;

/**
 * @author: qing
 * @Date: 2023/10/27
 */
@Data
public class VideoFootCountDTO {

    /**
     * 视频点赞数
     */
    private Integer  praiseCount;

    /**
     * 视频被阅读数
     */
    private Integer  readCount;

    /**
     * 视频被收藏数
     */
    private Integer  collectionCount;

    /**
     * 评论数
     */
    private Integer commentCount;

    public VideoFootCountDTO() {
        praiseCount = 0;
        readCount = 0;
        collectionCount = 0;
        commentCount = 0;
    }
}
