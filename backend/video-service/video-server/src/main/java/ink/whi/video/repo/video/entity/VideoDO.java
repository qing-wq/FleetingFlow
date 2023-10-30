package ink.whi.video.repo.video.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import ink.whi.common.vo.base.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * @author: qing
 * @Date: 2023/10/24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("video")
public class VideoDO extends BaseDO {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 视频标题
     */
    private String title;

    /**
     * 视频介绍
     */
    private String thumbnail;

    /**
     * 视频头图
     */
    private String picture;

    /**
     * 分类
     */
    private Long categoryId;

    /**
     * 状态：0-未发布,1-已发布,2-待审核
     */
    private Integer status;

    /**
     * 空间名
     */
    private String bucket;

    /**
     * 大小
     */
    private String size;

    /**
     * 视频类型：公开/私有
     */
    private Integer type;

    /**
     * 视频地址
     */
    private String url;

    /**
     * 编码格式
     */
    private String format;

    /**
     * 分辨率
     */
    private String resolution;

    /**
     * 是否删除
     */
    private Integer deleted;
}
