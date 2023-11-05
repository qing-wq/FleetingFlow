package ink.whi.web.hook.filter;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import ink.whi.common.context.ReqInfoContext;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URLDecoder;

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

    private static Logger REQ_LOG = LoggerFactory.getLogger("req");

    public static final String USER_ID_HEADER = "user-id";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        long start = System.currentTimeMillis();
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        ReqInfoContext.ReqInfo reqInfo = new ReqInfoContext.ReqInfo();
        String userId = request.getHeader(USER_ID_HEADER);
        reqInfo.setHost(request.getHeader(HttpHeaders.HOST));
        reqInfo.setPath(request.getPathInfo());
        try {
            if (userId != null) {
                reqInfo.setUserId(Long.valueOf(userId));
            }
            // 封装request
            request = this.wrapperRequest(request, reqInfo);
            ReqInfoContext.addReqInfo(reqInfo);
            filterChain.doFilter(request, servletResponse);
        } catch (NumberFormatException e) {
            log.error("init reqInfo error: " + e.getMessage());
        } finally {
            buildRequestLog(ReqInfoContext.getReqInfo(), request, System.currentTimeMillis() - start);
            ReqInfoContext.clear();
        }
    }

    private HttpServletRequest wrapperRequest(HttpServletRequest request, ReqInfoContext.ReqInfo reqInfo) {
        if (!HttpMethod.POST.name().equalsIgnoreCase(request.getMethod())) {
            return request;
        }

        BodyReaderHttpServletRequestWrapper requestWrapper = new BodyReaderHttpServletRequestWrapper(request);
        reqInfo.setPayload(requestWrapper.getBodyString());
        return requestWrapper;
    }

    private void buildRequestLog(ReqInfoContext.ReqInfo req, HttpServletRequest request, long costTime) {
        if (req == null || isStaticURI(request)) {
            return;
        }

        StringBuilder msg = new StringBuilder();
        msg.append("method=").append(request.getMethod()).append("; ");
        if (StringUtils.isNotBlank(req.getReferer())) {
            msg.append("referer=").append(URLDecoder.decode(req.getReferer())).append("; ");
        }
        msg.append("agent=").append(request.getHeader(HttpHeaders.USER_AGENT));

        if (req.getUserId() != null) {
            // 打印用户信息
            msg.append("; user=").append(req.getUserId());
        }

        msg.append("; uri=").append(request.getRequestURI());
        if (StringUtils.isNotBlank(request.getQueryString())) {
            msg.append('?').append(URLDecoder.decode(request.getQueryString()));
        }

        msg.append("; payload= ").append(req.getPayload());
        msg.append("; cost=").append(costTime);
        REQ_LOG.info("{}", msg);
    }

    private boolean isStaticURI(HttpServletRequest request) {
        return request == null
                || request.getRequestURI().endsWith("css")
                || request.getRequestURI().endsWith("js")
                || request.getRequestURI().endsWith("png")
                || request.getRequestURI().endsWith("ico")
                || request.getRequestURI().endsWith("svg")
                || request.getRequestURI().endsWith("min.js.map")
                || request.getRequestURI().endsWith("min.css.map");
    }
}
