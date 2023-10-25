package ink.whi.common.permission;

import java.lang.annotation.*;

/**
 * @author: qing
 * @Date: 2023/4/26
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Permission {
    UserRole role() default UserRole.ALL;
}
