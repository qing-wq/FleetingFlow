package ink.whi.video.model.dto;

import lombok.Data;

/**
 * @author: qing
 * @Date: 2023/10/27
 */
@Data
public class VideoStatisticDTO {

    /**
     * 视频点赞数
     */
    private Integer  praiseCount;

    /**
     * 视频播放数
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

    public VideoStatisticDTO() {
        praiseCount = 0;
        readCount = 0;
        collectionCount = 0;
        commentCount = 0;
    }
}
