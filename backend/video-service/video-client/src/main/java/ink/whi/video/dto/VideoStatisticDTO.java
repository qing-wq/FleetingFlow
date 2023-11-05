package ink.whi.video.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: qing
 * @Date: 2023/10/27
 */
@Data
public class VideoStatisticDTO implements Serializable {

    /**
     * 视频点赞数
     */
    private Integer  praiseCount;

    /**
     * 视频播放数
     */
    private Integer viewCount;

    /**
     * 视频被收藏数
     */
    private Integer collectionCount;

    /**
     * 转发数
     */
    private Integer forwardCount;

    /**
     * 评论数
     */
    private Integer commentCount;

    public VideoStatisticDTO() {
        praiseCount = 0;
        viewCount = 0;
        collectionCount = 0;
        commentCount = 0;
    }
}
