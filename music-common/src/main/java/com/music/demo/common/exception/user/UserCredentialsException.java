package com.music.demo.common.exception.user;

//用户凭证异常(用户名或者密码不正确)
public class UserCredentialsException extends RuntimeException {
    public UserCredentialsException(String message) {
        super(message);
    }
}
