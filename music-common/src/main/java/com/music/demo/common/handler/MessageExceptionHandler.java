package com.music.demo.common.handler;

import com.music.demo.common.exception.message.MessageException;
import com.music.demo.common.exception.music.MusicException;
import com.music.demo.common.result.HttpResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class MessageExceptionHandler {
    @ExceptionHandler(MessageException.class)
    public HttpResult<String> handleException(MessageException e) {
        log.debug("出现异常：{}", e.getMessage());
        return HttpResult.failed(e.getMessage());
    }
}
