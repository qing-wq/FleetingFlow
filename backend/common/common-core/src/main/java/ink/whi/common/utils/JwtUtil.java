package ink.whi.common.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import ink.whi.common.properties.JwtConfigProperties;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;


/**
 * JWT工具类
 * @author: qing
 * @Date: 2023/11/7
 */
@Slf4j
@Data
public class JwtUtil {
    private static final String TOKEN_PREFIX = "Bearer ";
    public static final String Authorization = "Authorization";
    private static final long ONE_MONTH = 30 * 24 * 60 * 60 * 1000L;

    /**
     * 生成token
     *
     * @param userId
     * @return
     */
    public static String createToken(Long userId) {
        Algorithm algorithm = Algorithm.HMAC256(JwtConfigProperties.key);
        return JWT.create()
                .withSubject(Long.toString(userId))
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis() + ONE_MONTH))
                .sign(algorithm);
    }

    /**
     * 校验token
     *
     * @param token
     * @return userId
     */
    public static Long isVerify(String token) {
        Algorithm algorithm = Algorithm.HMAC256(JwtConfigProperties.key);
        String userId = JWT.require(algorithm)
                .build()
                .verify(token.replace(TOKEN_PREFIX, ""))
                .getSubject();
        return Long.parseLong(userId);
    }

    /**
     * 检验token是否需要更新
     *
     * @param token
     * @return
     */
    public static boolean isNeedUpdate(String token) {
        Date expiresAt = JWT.require(Algorithm.HMAC256(JwtConfigProperties.key))
                .build()
                .verify(token.replace(TOKEN_PREFIX, ""))
                .getExpiresAt();
        // 小于半个月更新
        return (expiresAt.getTime() - System.currentTimeMillis()) < (ONE_MONTH >> 1);
    }

}
