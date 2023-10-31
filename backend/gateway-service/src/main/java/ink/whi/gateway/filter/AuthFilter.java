package ink.whi.gateway.filter;

import ink.whi.common.context.ReqInfoContext;
import ink.whi.common.exception.StatusEnum;
import ink.whi.common.vo.ResVo;
import ink.whi.gateway.config.SecurityConfig;
import ink.whi.gateway.util.CrossUtil;
import ink.whi.gateway.util.MonoUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;

/**
 * 认证Filter
 * @author: qing
 * @Date: 2023/10/31
 */
@Slf4j
@Component
public class AuthFilter implements GlobalFilter, Ordered {
    private static final Logger REQ_LOG = LoggerFactory.getLogger("req");

    @Autowired
    private GlobalInitHelper globalInitService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        long start = System.currentTimeMillis();
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        // 未经过网关就存在 user-id 的情况下，认为用户恶意操作，直接拦截
        if (getUserId(request) != null) {
            return MonoUtils.buildMonoWrap(exchange.getResponse(), ResVo.fail(StatusEnum.ILLEGAL_OPERATE), HttpStatus.BAD_REQUEST);
        }
        HttpServletRequest req = null;
        try {
            req = initReqInfo((HttpServletRequest) request);
            CrossUtil.buildCors(req, (HttpServletResponse) response);
            Long userId = globalInitService.initUserInfo();
            if (userId != null) {
                // 传递用户信息
                ServerHttpRequest build = request.mutate().header("user-id", userId.toString()).build();
                exchange = exchange.mutate().request(build).build();
            }
            return chain.filter(exchange);
        }finally {
            buildRequestLog(ReqInfoContext.getReqInfo(), req, System.currentTimeMillis() - start);
            ReqInfoContext.clear();
        }
    }

    @Override
    public int getOrder() {
        return -100;
    }

    private HttpServletRequest initReqInfo(HttpServletRequest request) {
        if (staticURI(request)) {
            // 静态资源直接放行
            return request;
        }

        try {
            // 校验token
            Long userId = globalInitService.initUserInfo();
        } catch (Exception e) {
            log.info("init reqInfo error: " + e.getMessage());
        }
        return request;
    }

    private HttpServletRequest wrapperRequest(HttpServletRequest request, ReqInfoContext.ReqInfo reqInfo) {
        BodyReaderHttpServletRequestWrapper requestWrapper = new BodyReaderHttpServletRequestWrapper(request);
        reqInfo.setPayload(requestWrapper.getBodyString());
        return requestWrapper;
    }

    /**
     * 打印日志
     * @param req
     * @param request
     * @param costTime
     */
    private void buildRequestLog(ReqInfoContext.ReqInfo req, HttpServletRequest request, long costTime) {
        if (req == null || staticURI(request)) {
            return;
        }

        StringBuilder msg = new StringBuilder();
        msg.append("method=").append(request.getMethod()).append("; ");
        if (StringUtils.isNotBlank(req.getReferer())) {
            msg.append("referer=").append(URLDecoder.decode(req.getReferer())).append("; ");
        }
        msg.append("; agent=").append(req.getUserAgent());

        if (req.getUserId() != null) {
            // 打印用户信息
            msg.append("; user=").append(req.getUserId());
        }

        msg.append("; uri=").append(request.getRequestURI());
        if (StringUtils.isNotBlank(request.getQueryString())) {
            msg.append('?').append(URLDecoder.decode(request.getQueryString()));
        }

        msg.append("; payload=").append(req.getPayload());
        msg.append("; cost=").append(costTime);
        REQ_LOG.info("{}", msg);

    }

    private boolean staticURI(HttpServletRequest request) {
        return request == null
                || request.getRequestURI().endsWith("css")
                || request.getRequestURI().endsWith("js")
                || request.getRequestURI().endsWith("png")
                || request.getRequestURI().endsWith("ico")
                || request.getRequestURI().endsWith("svg");
    }

    /**
     * 获取request中的用户信息
     *
     * @param request 请求体
     * @return 用户id
     */
    public static Long getUserId(ServerHttpRequest request) {
        Long res = null;
        String identity = request.getHeaders().getFirst(SecurityConfig.identityHeader);
        if (identity != null) {
            try {
                res = Long.parseLong(identity);
            } catch (NumberFormatException ignored) {
            }
        }
        return res;
    }
}
