package ink.whi.gateway.filter;

import ink.whi.common.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpCookie;
import org.springframework.http.ResponseCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import java.util.List;

/**
 * @author: qing
 * @Date: 2023/10/24
 */
@Slf4j
@Component
public class GlobalInitHelper {

    public static final String SESSION_KEY = "ff-session";

    /**
     * 获取token中的用户信息
     */
    public Long initUserInfo(ServerHttpRequest request, ServerHttpResponse response) {
        MultiValueMap<String, HttpCookie> cookies = request.getCookies();
        List<HttpCookie> token = cookies.get("token");
        if (token == null) {
            return null;
        }
        for (HttpCookie cookie : token) {
            if (SESSION_KEY.equalsIgnoreCase(cookie.getName())) {
                return VerifyToken(cookie.getValue(), response);
            }
        }
        return null;
    }

    /**
     * 校验token
     * @param token
     * @param response
     * @return userId
     */
    private Long VerifyToken(String token, ServerHttpResponse response) {
        if (StringUtils.isBlank(token)) {
            return null;
        }
        Long userId = JwtUtil.isVerify(token);

        // 检查token是否需要更新
        if (JwtUtil.isNeedUpdate(token)) {
            token = JwtUtil.createToken(userId);
            response.addCookie(ResponseCookie.from(SESSION_KEY, token).build());
        }
        return userId;
    }
}
