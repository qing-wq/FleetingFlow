package ink.whi.video.repo.video.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import ink.whi.common.enums.VideoQualityEnum;
import lombok.Data;

/**
 * 视频资源对象
 *
 * @author: qing
 * @Date: 2023/10/27
 */
@Data
@TableName("video_resource")
public class VideoResource {

    /**
     * 视频id
     */
    private Long videoId;

    /**
     * 视频地址
     */
    private String url;

    /**
     * 格式
     */
    private String format;

    /**
     * 清晰度
     */
    private VideoQualityEnum quality;
}
