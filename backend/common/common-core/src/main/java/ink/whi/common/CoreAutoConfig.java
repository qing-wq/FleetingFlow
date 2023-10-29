package ink.whi.common;

import ink.whi.common.cache.RedisClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author qing
 * @date 2023/4/27
 */
@Configuration
@ComponentScan("ink.whi.common")
public class CoreAutoConfig {
    public CoreAutoConfig(RedisTemplate<String, String> redisTemplate) {
        RedisClient.register(redisTemplate);
    }
}
