package com.music.demo.login.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.music.demo.common.exception.user.UserTokenException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;

@Component
public class JWTComponent {
    @Value("${my.secretkey}")
    private String secretKey;
    private static Algorithm algorithm;

    @PostConstruct
    private void init() {
        algorithm = Algorithm.HMAC256(secretKey);
    }

    public String encode(Map<String, Object> map) {
//        过期时间为当前时间 + 1天
        LocalDateTime time = LocalDateTime.now().plusDays(1);
        return JWT.create()
                .withPayload(map)
                .withIssuedAt(new Date())
                .withExpiresAt(Date.from(time.atZone(ZoneId.systemDefault()).toInstant()))
                .sign(algorithm);
    }
    public DecodedJWT decode(String token) {
        try {
            return JWT.require(algorithm).build().verify(token);
        }catch (TokenExpiredException | SignatureVerificationException e) {
            if (e instanceof SignatureVerificationException) {
                throw new UserTokenException("无权限");
            }
            throw new UserTokenException("token已过期，请重新登录");
        }

        //这里抛出的XException异常，会转到ExceptionConroller中统一处理
    }
}
