package ink.whi.common.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: qing
 * @Date: 2023/11/2
 */
@Data
public class UserRelationDTO implements Serializable {

    /**
     * 主用户ID
     */
    private Long userId;

    /**
     * 粉丝用户ID
     */
    private Long followUserId;

    /**
     * 关注状态: 0-未关注，1-已关注，2-取消关注
     */
    private Integer followState;
}
