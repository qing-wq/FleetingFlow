import ink.whi.common.cache.RedisClient;
import ink.whi.user.service.CountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author: qing
 * @Date: 2023/10/30
 */
public class MyTest extends BasicTest{

    @Autowired
    CountService countService;

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    @Test
    void test() {
        RedisClient.register(redisTemplate);
        countService.initUserCache();
        countService.initViewCache();
    }
}
