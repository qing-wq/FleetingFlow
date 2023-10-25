package ink.whi.common.vo.dto;

import ink.whi.common.vo.base.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;

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
    private Integer categoryId;

    /**
     * 视频连接
     */
    private String url;

    /**
     * 状态：0-未发布,1-已发布,2-待审核
     */
    private Integer status;
}
