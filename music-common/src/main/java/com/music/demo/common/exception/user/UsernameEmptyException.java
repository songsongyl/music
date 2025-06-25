package com.music.demo.common.exception.user;

public class UsernameEmptyException extends RuntimeException {
    public UsernameEmptyException(String message) {
        super(message);
    }
}
