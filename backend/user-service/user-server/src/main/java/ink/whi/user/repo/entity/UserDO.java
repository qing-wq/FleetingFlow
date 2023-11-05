package ink.whi.user.repo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import ink.whi.common.model.base.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;

/**
 * @author: qing
 * @Date: 2023/10/16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("user")
public class UserDO extends BaseDO {
    @Serial
    private static final long serialVersionUID = 4704149522920373163L;

    /**
     * 登录用户名
     */
    private String userName;

    /**
     * 登录密码
     */
    private String password;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 登录方式: 0-微信登录，1-账号密码登录
     */
    private Integer loginType;

    /**
     * 第三方用户ID（用于微信登录）
     */
    private String thirdAccountId;

    /**
     * 删除标记
     */
    private Integer deleted;
}
