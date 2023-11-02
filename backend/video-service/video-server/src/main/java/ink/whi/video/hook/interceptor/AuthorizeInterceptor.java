package ink.whi.video.hook.interceptor;

import ink.whi.common.context.ReqInfoContext;
import ink.whi.common.exception.StatusEnum;
import ink.whi.common.permission.Permission;
import ink.whi.common.permission.UserRole;
import ink.whi.common.utils.JsonUtil;
import ink.whi.common.vo.ResVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 授权拦截器
 * @author: qing
 * @Date: 2023/11/1
 */
@Slf4j
@Component
public class AuthorizeInterceptor implements AsyncHandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod handlerMethod) {
            Permission permission = handlerMethod.getMethod().getAnnotation(Permission.class);
            if (permission == null) {
                permission = handlerMethod.getBeanType().getAnnotation(Permission.class);
            }

            // 直接放行
            if (permission == null || permission.role() == UserRole.ALL) {
                return true;
            }

            // 登录
            if (permission.role() == UserRole.LOGIN && ReqInfoContext.getReqInfo().getUserId() == null) {
                response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
                response.setStatus(HttpStatus.FORBIDDEN.value());
                response.getWriter().println(JsonUtil.toStr(ResVo.fail(StatusEnum.FORBID_ERROR_MIXED, "未登录")));
                response.getWriter().flush();
                return false;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        ReqInfoContext.clear();
    }
}
