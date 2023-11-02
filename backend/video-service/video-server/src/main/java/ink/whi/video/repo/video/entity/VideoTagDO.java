package ink.whi.video.repo.video.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import ink.whi.common.model.base.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author: qing
 * @Date: 2023/10/26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("video_tag")
public class VideoTagDO extends BaseDO {

    /**
     * 视频id
     */
    private Long videoId;

    /**
     * 标签id
     */
    private Long tagId;

    /**
     * 是否删除
     */
    private Integer deleted;
}
