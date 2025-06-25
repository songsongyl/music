package com.music.demo.login.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.music.demo.common.result.HttpResult;
import com.music.demo.domain.entity.User;
import com.music.demo.login.service.ILoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Tag(name = "测试用户接口")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LoginController {
    @Value("${my.secretkey}")
    private String SALT;

    private final ILoginService iLoginService;
    private final StringRedisTemplate stringRedisTemplate;

    @Operation(summary = "用户登录")
    @GetMapping("/login")
    public HttpResult<String> login(HttpServletRequest request, HttpServletResponse response, String username, String password) throws JsonProcessingException {
        User user = iLoginService.login(username, password);

        String token = JWT.create()
                .withClaim("username", username)
                .withExpiresAt(
                        new Date(System.currentTimeMillis() + 1000*60*15)
                )
                .sign(Algorithm.HMAC256(SALT));

        ObjectMapper mapper = new ObjectMapper();
        String userStr = mapper.writeValueAsString(user);

        stringRedisTemplate.opsForValue().set(token, userStr, 30, TimeUnit.MINUTES);

        return HttpResult.success(token);
    }

}
