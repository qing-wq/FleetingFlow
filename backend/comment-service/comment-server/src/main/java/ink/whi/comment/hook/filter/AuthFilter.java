package ink.whi.comment.hook.filter;

import ink.whi.common.context.ReqInfoContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * 认证Filter
 *
 * @author: qing
 * @Date: 2023/11/3
 */
@Slf4j
@WebFilter(urlPatterns = "/*", filterName = "authFilter", asyncSupported = true)
public class AuthFilter implements Filter {

    public static final String USER_ID_HEADER = "user-id";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        ReqInfoContext.ReqInfo reqInfo = new ReqInfoContext.ReqInfo();
        ServletRequestAttributes attribute = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attribute != null) {
            String userId = attribute.getRequest().getHeader(USER_ID_HEADER);
            if (userId != null) {
                try {
                    reqInfo.setUserId(Long.valueOf(userId));
                    log.info("init reqInfo success!");
                } catch (NumberFormatException e) {
                    log.info("init reqInfo error: " + e.getMessage());
                }
            }
        }
        ReqInfoContext.addReqInfo(reqInfo);
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
