package ink.whi.user.repo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import ink.whi.common.vo.base.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 用户个人信息表
 *
 * @author qing
 * @date 2023/4/26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "user_info", autoResultMap = true)
public class UserInfoDO extends BaseDO {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
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

    /**
     * 0-普通用户，1-超级管理员
     */
    private Integer userRole;

    /**
     * 删除标记
     */
    private Integer deleted;

    /**
     * ip信息
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private IpInfo ip;

    public IpInfo getIp() {
        if (ip == null) {
            ip = new IpInfo();
        }
        return ip;
    }
}
