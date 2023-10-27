package ink.whi.video.repo.video.entity;

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
public class VideoDO extends BaseDO {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    private Integer userId;

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
     * 视频地址
     */
    private String url;

    /**
     * 状态：0-未发布,1-已发布,2-待审核
     */
    private Integer status;

    private Integer deleted;

    /**
     * 空间名
     */
    private String bucket;

    /**
     * 大小
     */
    private String size;

    /**
     * 文件类型
     */
    private String suffix;

    /**
     * 空间类型：公开/私有
     */
    private String type = "公开";
}
