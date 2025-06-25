package com.music.demo.common.handler;

import com.music.demo.common.exception.music.MusicException;
import com.music.demo.common.result.HttpResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class MusicExceptionHandler {

    @ExceptionHandler(MusicException.class)
    public HttpResult<String> handleException(MusicException e) {
        log.debug("出现异常：{}", e.getMessage());
        return HttpResult.failed(e.getMessage());
    }
}
