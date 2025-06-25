package com.music.demo.common.result;

public enum RespCode {

    SUCCESS(200,"success"),
    FAILED(500,"failed");
    int code;
    String message;
    RespCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
