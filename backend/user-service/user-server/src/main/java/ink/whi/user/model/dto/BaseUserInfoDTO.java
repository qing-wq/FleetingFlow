package ink.whi.user.model.dto;

import ink.whi.common.model.base.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;

/**
 * @author: qing
 * @Date: 2023/4/26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class BaseUserInfoDTO extends BaseDTO {
    @Serial
    private static final long serialVersionUID = -2426438424647735636L;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户角色 必须是String
     */
    private String role;

    /**
     * 用户头像
     */
    private String picture;

    /**
     * 个人简介
     */
    private String profile;

    /**
     * 邮箱
     */
    private String email;
}
