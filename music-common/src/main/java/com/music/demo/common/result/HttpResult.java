package com.music.demo.common.result;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class HttpResult<T> {
    @Schema(description = "返回代码，200-成功，500-失败")
    private int code;
    @Schema(description = "返回信息")
    private String msg;
    @Schema(description = "返回结果")
    private T results;
    @Schema(description = "返回时间")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss",timezone = "GMT+8")
    private Date time;
    /*
    * 调用返回成功结果
    * */
    public static <T> HttpResult<T> success(T results) {
        return new HttpResult<>(RespCode.SUCCESS.code, RespCode.SUCCESS.message,results,new Date());
    }

    public static <T> HttpResult<T> failed(T results) {
        return new HttpResult<>(RespCode.FAILED.code, RespCode.FAILED.message, results,new Date());
    }


    public static <T> HttpResult<T> failed() {
        return failed(null);
    }
}
