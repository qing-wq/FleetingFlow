package ink.whi.user.model.req;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author: qing
 * @Date: 2023/7/7
 */
@Data
public class UserSaveReq implements Serializable {
    @Serial
    private static final long serialVersionUID = 34489978092087873L;

    private Long userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 电话
     */
    private String phone;
}
