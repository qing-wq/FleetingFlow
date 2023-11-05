package ink.whi.user.model.req;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

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

    /**
     * 用户名
     */
    @NotNull
    private String username;

    /**
     * 密码
     */
    @NotNull
    private String password;

    /**
     * 邮箱
     */
    @NotNull
    private String email;

    /**
     * 验证码
     */
    @NotNull
    private String code;

    /**
     * 电话
     */
    private String phone;
}
