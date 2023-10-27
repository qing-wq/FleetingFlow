package ink.whi.user.repo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import ink.whi.common.vo.base.BaseDO;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;

/**
 * @author: qing
 * @Date: 2023/10/16
 */
@Data
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
     * 第三方用户ID（用于微信登录）
     */
    private String thirdAccountId;

    /**
     * 登录方式: 0-微信登录，1-账号密码登录
     */
    private Integer loginType;

    /**
     * 登录密码
     */
    private String password;

    /**
     * 删除标记
     */
    private Integer deleted;
}
