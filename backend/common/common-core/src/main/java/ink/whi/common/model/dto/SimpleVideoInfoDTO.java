package ink.whi.common.model.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author: qing
 * @Date: 2023/10/24
 */
@Data
public class SimpleVideoInfoDTO implements Serializable {
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
     * 视频头图
     */
    private String picture;

    /**
     * 视频地址
     */
    private String url;

    /**
     * 状态：0-未发布,1-已发布,2-待审核
     */
    private Integer status;

    /**
     * 表示当前查看的用户是否已经点赞过
     */
    private Boolean praised;

    /**
     * 视频点赞数
     */
    private Long priseCount;
}
