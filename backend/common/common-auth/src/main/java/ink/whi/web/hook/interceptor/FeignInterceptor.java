package ink.whi.web.hook.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Feign 远程调用拦截器，用于传递用户身份信息
 * @author: qing
 * @Date: 2023/11/7
 */
@Configuration
public class FeignInterceptor implements RequestInterceptor {

    public static final String USER_ID_HEADER = "user-id";

    @Override
    public void apply(RequestTemplate requestTemplate) {
        ServletRequestAttributes attributes = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes());
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            requestTemplate.header(USER_ID_HEADER, request.getHeader(USER_ID_HEADER));
        }
    }
}