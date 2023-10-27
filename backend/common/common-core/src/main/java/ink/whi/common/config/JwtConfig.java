package ink.whi.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "security.jwt")
public class JwtConfig {

	/**
	 * 密钥KEY
	 */
	public static String key;

	public void setKey(String secret) {
		JwtConfig.key = secret;
	}

}
