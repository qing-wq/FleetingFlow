package ink.whi.video.repo.video.entity;

import com.baomidou.mybatisplus.annotation.TableId;
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

    @TableId
    private Long id;

    /**
     * 视频id
     */
    private Long videoId;

    /**
     * 视频地址
     */
    private String url;

    /**
     * 清晰度
     */
    private VideoQualityEnum quality;
}
