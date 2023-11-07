package ink.whi.video.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 算法系统配置
 * @author: qing
 * @Date: 2023/11/7
 */
@Data
@Component
@ConfigurationProperties(prefix = "ai.config")
public class AIConfigProperties {

    /**
     * 分类推荐系统
     */
    public static String video;

    /**
     * tag推荐系统
     */
    public static String tag;

    /**
     * 分类推荐系统
     */
    public static String category;
}
