package ink.whi.common.properties;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


/**
 * 七牛云对象存储配置类
 *
 * @author qing
 * @date 2023/10/25
 */
@Data
@Component
@ConfigurationProperties(prefix = "qiniu.config")
public class QiniuConfigProperties {

    /**
     * accessKey
     */
    public String accessKey;

    /**
     * secretKey
     */
    public String secretKey;

    /**
     * Bucket
     */
    public String bucket;

    /**
     * 地域
     */
    public String zone;

    /**
     * 外链域名
     */
    public String host;

    /**
     * 空间类型：公开/私有
     */
    public String type = "公开";

    public String buildUrl(String url) {
        if (!url.startsWith(host)) {
            return host + "/" + url;
        }
        return url;
    }
}
