package ink.whi.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * JWT配置
 */
@Data
@Component
@ConfigurationProperties(prefix = "security.jwt")
public class JwtConfigProperties {

	/**
	 * 密钥KEY
	 */
	public static String key;

	public void setKey(String secret) {
		JwtConfigProperties.key = secret;
	}
}
