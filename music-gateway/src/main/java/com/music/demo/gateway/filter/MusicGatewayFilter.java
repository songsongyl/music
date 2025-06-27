package com.music.demo.gateway.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.music.demo.common.result.HttpResult;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.NettyWriteResponseFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 网关的全局过滤器
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class MusicGatewayFilter implements GlobalFilter, Ordered {

    private final StringRedisTemplate stringRedisTemplate;
    @Value("${my.secretkey}")
    private  String SALT;

    @SneakyThrows
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.debug("来到网关过滤器中...");

        ServerHttpRequest request = exchange.getRequest(); //请求
        ServerHttpResponse response = exchange.getResponse(); //响应

        System.err.println(request.getURI());

        String path = request.getURI().getPath();
        if (path.contains("/api/login") || path.contains("/api/register")|| path.contains("v3/api-docs")) {
//            放行
            return chain.filter(exchange);
        }

        //2. 获取token
        HttpHeaders headers = request.getHeaders();
        List<String> hs = headers.get("token");
//        log.debug(hs.toString());
        ObjectMapper mapper = new ObjectMapper();
        DataBuffer dataBuffer = null;

//        ObjectMapper mapper = null;
        if (hs == null || hs.isEmpty()) {
//            异常抛出
            response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
            HttpResult<String> result = HttpResult.failed("token不存在，用户登录异常");
            dataBuffer = response.bufferFactory().wrap(
                    mapper.writeValueAsString(
                                    result)
                            .getBytes(StandardCharsets.UTF_8));
            return response.writeWith(Mono.just(dataBuffer));

        }

        String token = hs.get(0);
        if (!stringRedisTemplate.hasKey(token)) {
//            异常抛出
            response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
            HttpResult<String> result = HttpResult.failed("非法登录，访问异常");
            dataBuffer = response.bufferFactory().wrap(
                    mapper.writeValueAsString(
                                    result)
                            .getBytes(StandardCharsets.UTF_8));
            return response.writeWith(Mono.just(dataBuffer));
        }
                DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(SALT)).build().verify(token);
        String uid = decodedJWT.getClaim("uid").asString();
        String role = decodedJWT.getClaim("role").asString();
        log.debug(role);
        log.debug(uid);
        ServerHttpRequest modifiedRequest = request.mutate()
                .header("uid", uid)
                .header("role", role)
                .build();
        return chain.filter(exchange.mutate().request(modifiedRequest).build());
//        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return NettyWriteResponseFilter.WRITE_RESPONSE_FILTER_ORDER - 2;
    }
}
