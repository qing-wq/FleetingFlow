package ink.whi.user.controller;

import ink.whi.common.exception.StatusEnum;
import ink.whi.common.utils.JwtUtil;
import ink.whi.common.vo.ResVo;
import ink.whi.user.model.dto.BaseUserInfoDTO;
import ink.whi.user.service.UserService;
import ink.whi.utils.SessionUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static ink.whi.utils.SessionUtil.SESSION_KEY;


/**
 * 登录接口
 * @author: qing
 * @Date: 2023/10/24
 */
@RestController
@RequestMapping(path = "login")
public class LoginRestController {

    @Autowired
    private UserService userService;

    /**
     * 账号密码登录
     * @param request
     * @param response
     * @return
     */
    @PostMapping(path = {""})
    public ResVo<BaseUserInfoDTO> login(HttpServletRequest request,
                                    HttpServletResponse response) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
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

}
