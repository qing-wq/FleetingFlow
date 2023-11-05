package ink.whi.video.dto;

import ink.whi.common.enums.FileTypeEnum;
import ink.whi.common.model.dto.SimpleUserInfoDTO;
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
    private Long categoryId;

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
    private Boolean followed;

    /**
     * 表示当前用户是否收藏过
     */
    private Boolean collected;

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
     * 文件类型
     * {@link FileTypeEnum}
     */
    private Integer type;

    /**
     * 作者信息
     */
    private SimpleUserInfoDTO author;

    /**
     * 视频对应的统计计数
     */
    private VideoStatisticDTO count;
}
