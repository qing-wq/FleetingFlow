package ink.whi.user.model.req;

import lombok.Data;

/**
 * @author: qing
 * @Date: 2023/7/22
 */
@Data
public class UserInfoSaveReq {

    /**
     * 用户ID (不需要填)
     */
    private Long userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户头像
     */
    private String picture;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 个人简介
     */
    private String profile;
}
