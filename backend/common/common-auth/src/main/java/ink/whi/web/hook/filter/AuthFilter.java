package ink.whi.web.hook.filter;

import ink.whi.common.context.ReqInfoContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 认证Filter
 *
 * @author: qing
 * @Date: 2023/11/1
 */
@Slf4j
@Component
@WebFilter(urlPatterns = "/*", filterName = "authFilter", asyncSupported = true)
public class AuthFilter implements Filter {

    public static final String USER_ID_HEADER = "user-id";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String userId = request.getHeader(USER_ID_HEADER);
        if (userId != null) {
            try {
                ReqInfoContext.ReqInfo reqInfo = new ReqInfoContext.ReqInfo();
                reqInfo.setUserId(Long.valueOf(userId));
                ReqInfoContext.addReqInfo(reqInfo);
                log.info("init reqInfo success");
            } catch (NumberFormatException e) {
                log.error("init reqInfo error: " + e.getMessage());
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
