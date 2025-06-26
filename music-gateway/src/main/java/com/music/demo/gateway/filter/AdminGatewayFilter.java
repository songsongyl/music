package com.music.demo.gateway.filter;

import cn.hutool.http.Header;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.music.demo.common.result.HttpResult;
import com.music.demo.domain.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.NettyWriteResponseFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class AdminGatewayFilter implements GlobalFilter, Ordered {

    @SneakyThrows
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        String path = request.getURI().getPath();
        //如果不是knife4j接口文档或者不是管理员路径，放行
        if (path.contains("v3/api-docs") || !path.contains("api/admin")) {
            return chain.filter(exchange);
        }

        HttpHeaders headers = request.getHeaders();
        List<String> hs = headers.get("role");
        log.debug("{}",hs);

        ObjectMapper mapper = new ObjectMapper();
        DataBuffer dataBuffer = null;

        if (hs == null || hs.isEmpty() ) {
            response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
            HttpResult<String> result = HttpResult.failed("role不存在，管理员访问异常");
            dataBuffer = response.bufferFactory().wrap(
                    mapper.writeValueAsString(result).getBytes(StandardCharsets.UTF_8)
            );

            return response.writeWith(Mono.just(dataBuffer));
        }

        if (!hs.get(0).equals(User.ADMIN)) {
            response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
            HttpResult<String> result = HttpResult.failed("非管理员，访问异常");
            dataBuffer = response.bufferFactory().wrap(
                    mapper.writeValueAsString(result).getBytes(StandardCharsets.UTF_8)
            );

            return response.writeWith(Mono.just(dataBuffer));
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return NettyWriteResponseFilter.WRITE_RESPONSE_FILTER_ORDER - 1;
    }
}
