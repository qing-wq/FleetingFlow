package ink.whi.user.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serial;

/**
 * 用户主页信息
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserStatisticInfoDTO extends BaseUserInfoDTO {
    @Serial
    private static final long serialVersionUID = -7322294644978802578L;

    /**
     * 关注数
     */
    private Integer followCount;

    /**
     * 粉丝数
     */
    private Integer fansCount;

    /**
     * 已发布视频数
     */
    private Integer videoCount;

    /**
     * 点赞数
     */
    private Integer praiseCount;

    /**
     * 被收藏数
     */
    private Integer collectionCount;

    /**
     * 是否关注当前用户
     */
    private Boolean followed;
}
