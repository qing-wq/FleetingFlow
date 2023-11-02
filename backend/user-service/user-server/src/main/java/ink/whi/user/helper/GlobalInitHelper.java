package ink.whi.user.helper;

import ink.whi.common.context.ReqInfoContext;
import ink.whi.common.exception.BusinessException;
import ink.whi.common.exception.StatusEnum;
import ink.whi.common.utils.JwtUtil;
import ink.whi.common.model.dto.BaseUserDTO;
import ink.whi.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.apache.http.nio.reactor.ssl.SSLIOSession.SESSION_KEY;


/**
 * @author: qing
 * @Date: 2023/10/24
 */
@Slf4j
@Component
public class GlobalInitHelper {

    @Autowired
    private UserService userService;

    /**
     * 初始化用户信息
     * @param reqInfo
     */
    public void initUserInfo(ReqInfoContext.ReqInfo reqInfo) {
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpServletResponse response =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();
        if (request.getCookies() == null) {
            return;
        }
        for (Cookie cookie : request.getCookies()) {
            if (SESSION_KEY.equalsIgnoreCase(cookie.getName())) {
                BaseUserDTO user = VerifyToken(cookie.getValue(), response);
                if (user != null) {
                    reqInfo.setUserId(user.getUserId());
                    reqInfo.setUser(user);
                }
            }
        }
    }

    /**
     * 校验token
     *
     * @param token
     * @param response
     */
    private BaseUserDTO VerifyToken(String token, HttpServletResponse response) {
        if (StringUtils.isBlank(token)) {
            return null;
        }
        Long userId = JwtUtil.isVerify(token);
        BaseUserDTO user = userService.queryBasicUser(userId);
        if (user == null) {
            throw BusinessException.newInstance(StatusEnum.JWT_VERIFY_EXISTS);
        }

        // 检查token是否需要更新
        if (JwtUtil.isNeedUpdate(token)) {
            token = JwtUtil.createToken(userId);
            response.addCookie(new Cookie(SESSION_KEY, token));
        }
        return user;
    }
}
