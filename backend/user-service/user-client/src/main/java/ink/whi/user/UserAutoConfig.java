package ink.whi.user;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author: qing
 * @Date: 2023/10/27
 */
@Configuration
@ComponentScan(basePackages = "ink.whi.user.client")
public class UserAutoConfig {
}
