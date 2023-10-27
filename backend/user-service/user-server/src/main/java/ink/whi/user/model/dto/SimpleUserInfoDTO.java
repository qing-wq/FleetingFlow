package ink.whi.user.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * 基本用户信息
 *
 * @author: qing
 * @Date: 2023/4/27
 */

@Data
@Accessors(chain = true)
public class SimpleUserInfoDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 4802653694786272120L;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户名
     */
    private String name;

    /**
     * 头像
     */
    private String picture;

    /**
     * 简介
     */
    private String profile;
}
