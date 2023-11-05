package ink.whi.gateway.config;

import ink.whi.gateway.handler.GlobalExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: qing
 * @Date: 2023/10/25
 */
@Configuration(proxyBeanMethods = false)
public class GatewayConfig {

    @Bean
    public GlobalExceptionHandler globalExceptionHandler() {
        return new GlobalExceptionHandler();
    }
}
