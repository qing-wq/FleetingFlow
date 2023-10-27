package ink.whi.video.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author: qing
 * @Date: 2023/10/26
 */
@Configuration
public class RedisConfig {

    public RedisConfig(RedisTemplate<String, String> redisTemplate) {
        RedisClient.register(redisTemplate);
    }
}
