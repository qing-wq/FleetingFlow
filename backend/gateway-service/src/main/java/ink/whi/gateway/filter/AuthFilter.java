package ink.whi.gateway.filter;

import ink.whi.common.context.ReqInfoContext;
import ink.whi.common.exception.StatusEnum;
import ink.whi.common.model.ResVo;
import ink.whi.gateway.config.SecurityConfig;
import ink.whi.gateway.util.MonoUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

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
        try {
            Long userId = initReqInfo(request, response);
            if (userId != null) {
                // 将用户id放到request header中，传递用户信息
                ServerHttpRequest build = request.mutate().header("user-id", userId.toString()).build();
                exchange = exchange.mutate().request(build).build();
            }
            return chain.filter(exchange);
        }finally {
            buildRequestLog(ReqInfoContext.getReqInfo(), request, System.currentTimeMillis() - start);
            ReqInfoContext.clear();
        }
    }

    @Override
    public int getOrder() {
        return -100;
    }

    private Long initReqInfo(ServerHttpRequest request, ServerHttpResponse response) {
        if (staticURI(request)) {
            // 静态资源直接放行
            return null;
        }

        Long userId = null;
        try {
            ReqInfoContext.ReqInfo reqInfo = new ReqInfoContext.ReqInfo();
            HttpHeaders headers = request.getHeaders();
            reqInfo.setHost(headers.getFirst(HttpHeaders.HOST));
            reqInfo.setPath(request.getPath().value());
            reqInfo.setReferer(headers.getFirst(HttpHeaders.REFERER));
            reqInfo.setUserAgent(headers.getFirst(HttpHeaders.USER_AGENT));
            // 校验token
            userId = globalInitService.initUserInfo(request, response);
            ReqInfoContext.addReqInfo(reqInfo);
        } catch (Exception e) {
            log.info("init reqInfo error: " + e.getMessage());
        }
        return userId;
    }

    /**
     * 打印日志
     * @param reqInfo
     * @param request
     * @param costTime
     */
    private void buildRequestLog(ReqInfoContext.ReqInfo reqInfo, ServerHttpRequest request, long costTime) {
        if (reqInfo == null || staticURI(request)) {
            return;
        }

        StringBuilder msg = new StringBuilder();
        msg.append("method=").append(request.getMethod()).append("; ");
        if (StringUtils.isNotBlank(reqInfo.getReferer())) {
            msg.append("referer=").append(URLDecoder.decode(reqInfo.getReferer())).append("; ");
        }
        msg.append("agent=").append(reqInfo.getUserAgent());

        if (reqInfo.getUserId() != null) {
            // 打印用户信息
            msg.append("; user=").append(reqInfo.getUserId());
        }

        msg.append("; uri=").append(request.getURI());
        if (StringUtils.isNotBlank(request.getURI().getQuery())) {
            msg.append('?').append(URLDecoder.decode(request.getURI().getQuery()));
        }

        // fixme：post参数封装
//        msg.append("; payload=").append(reqInfo.getPayload());
        msg.append("; cost=").append(costTime);
        REQ_LOG.info("{}", msg);
    }

    private boolean staticURI(ServerHttpRequest request) {
        String uri = request.getURI().toString();
        return uri == null
                || uri.endsWith("css")
                || uri.endsWith("js")
                || uri.endsWith("png")
                || uri.endsWith("ico")
                || uri.endsWith("svg");
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
