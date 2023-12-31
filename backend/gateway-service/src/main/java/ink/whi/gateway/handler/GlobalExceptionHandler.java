package ink.whi.gateway.handler;

import com.alibaba.nacos.common.model.RestResult;
import com.google.common.collect.Maps;
import ink.whi.gateway.util.MonoUtils;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.handler.ResponseStatusExceptionHandler;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.HashMap;

@Slf4j
@Order(-1)
public class GlobalExceptionHandler implements ErrorWebExceptionHandler {

	@Override
	public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
		ServerHttpResponse response = exchange.getResponse();
		ServerHttpRequest request = exchange.getRequest();

		if (response.isCommitted()) {
			return Mono.error(ex);
		}

		// 设置返回信息和HTTP状态码
		HttpStatus httpStatus;
		if (ex instanceof ResponseStatusException) {
			// HTTP状态码异常
			log.warn("Http Status Warn : {}, url: {}", ex.getMessage(), request.getURI());
			httpStatus = ((ResponseStatusException) ex).getStatus();
		} else {
			// 系统异常
			log.error("Error Gateway, {}: {}, url: {}", ex.getClass(), ex.getMessage(), request.getURI());
			httpStatus = HttpStatus.BAD_GATEWAY;
		}
		HashMap<String, Object> result = Maps.newHashMap();
		result.put("message", httpStatus.getReasonPhrase());
		result.put("code", httpStatus.value());
		response.setStatusCode(httpStatus);;

		return MonoUtils.buildMonoWrap(response, result);
	}

}
