package ink.whi.user.controller;

import ink.whi.common.context.ReqInfoContext;
import ink.whi.common.exception.StatusEnum;
import ink.whi.common.limit.Limit;
import ink.whi.common.limit.LimitType;
import ink.whi.common.utils.JwtUtil;
import ink.whi.common.model.ResVo;
import ink.whi.user.model.dto.BaseUserInfoDTO;
import ink.whi.user.model.req.UserSaveReq;
import ink.whi.user.service.UserService;
import ink.whi.user.service.help.LoginHelper;
import ink.whi.web.auth.Permission;
import ink.whi.web.auth.UserRole;
import ink.whi.web.utils.SessionUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.nio.reactor.ssl.SSLIOSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

import static ink.whi.web.utils.SessionUtil.SESSION_KEY;


/**
 * 登录接口
 *
 * @author: qing
 * @Date: 2023/10/24
 */
@RestController
@RequestMapping
public class LoginRestController {

    @Autowired
    private UserService userService;

    @Autowired
    private LoginHelper loginHelper;

    /**
     * 账号密码登录
     *
     * @param username 用户名/邮箱
     * @param password
     * @return
     */
    @PostMapping(path = "login")
    public ResVo<BaseUserInfoDTO> login(@RequestParam(name = "username") String username,
                                        @RequestParam(name = "password") String password,
                                        HttpServletResponse response) {
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            return ResVo.fail(StatusEnum.ILLEGAL_ARGUMENTS_MIXED, "用户名或密码不能为空");
        }
        BaseUserInfoDTO info = userService.passwordLogin(username, password);
        // 签发token
        String token = JwtUtil.createToken(info.getUserId());
        if (StringUtils.isNotBlank(token)) {
            response.addCookie(SessionUtil.newCookie(SESSION_KEY, token));
            return ResVo.ok(info);
        } else {
            return ResVo.fail(StatusEnum.LOGIN_FAILED_MIXED, "登录失败，请重试");
        }
    }

    /**
     * 登出接口
     *
     * @param response
     * @return
     */
    @GetMapping(path = "logout")
    @Permission(role = UserRole.LOGIN)
    public ResVo<String> logout(HttpServletResponse response) {
        response.addCookie(SessionUtil.delCookie(SESSION_KEY));
        return ResVo.ok("ok");
    }

    /**
     * 用户注册
     *
     * @param req
     * @return
     */
    @Limit(key = "register", period = 60, count = 1, limitType = LimitType.IP)
    @PostMapping(path = "register")
    public ResVo<Long> register(@Validated @RequestBody UserSaveReq req, HttpServletResponse response) {
        if (StringUtils.isBlank(req.getUsername()) || StringUtils.isBlank(req.getPassword())) {
            return ResVo.fail(StatusEnum.ILLEGAL_ARGUMENTS_MIXED, "账号密码不能为空");
        }

        loginHelper.verifyEmail(req.getEmail(), req.getCode());

        Long userId = userService.saveUser(req);
        // 签发token
        String token = JwtUtil.createToken(userId);
        if (StringUtils.isBlank(token)) {
            return ResVo.fail(StatusEnum.TOKEN_NOT_EXISTS);
        }
        response.addCookie(SessionUtil.newCookie(SESSION_KEY, token));
        return ResVo.ok(userId);
    }

    /**
     * 获取验证码
     *
     * @return
     */
    @PostMapping(path = "code")
    public ResVo<String> code(@RequestParam("email") String email) {
        String code = loginHelper.subscribe(email);
        return ResVo.ok(code);
    }
}
