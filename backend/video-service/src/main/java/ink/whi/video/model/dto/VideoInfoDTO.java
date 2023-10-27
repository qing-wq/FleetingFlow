package ink.whi.video.model.dto;

import ink.whi.video.model.video.CategoryDTO;
import ink.whi.video.model.video.TagDTO;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * @author: qing
 * @Date: 2023/10/24
 */
@Data
public class VideoInfoDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 视频id
     */
    private Long videoId;

    /**
     * 作者id
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
    private CategoryDTO category;

    /**
     * 视频连接
     */
    private String url;

    /**
     * 状态：0-未发布,1-已发布,2-待审核
     */
    private Integer status;

    /**
     * 标签
     */
    private List<TagDTO> tags;

    /**
     * 表示当前查看的用户是否已经点赞过
     */
    private Boolean praised;

    /**
     * 表示当用户是否评论过
     */
    private Boolean commented;

    /**
     * 表示当前用户是否收藏过
     */
    private Boolean collected;

    /**
     * 视频对应的统计计数
     */
    private VideoStatisticDTO count;
}
