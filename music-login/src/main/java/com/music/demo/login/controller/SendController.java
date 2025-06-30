package com.music.demo.login.controller;

import com.music.demo.common.result.HttpResult;
import com.music.demo.login.service.ISendMailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@Tag(name = "邮箱接口")
@RestController
@RequestMapping("/api/email")
@RequiredArgsConstructor
public class SendController {

    public final ISendMailService iSendMailService;

    @Operation(summary = "发送邮箱验证码")
    @GetMapping("/sendcode")
    public HttpResult<String> sendCode(
            @RequestParam String email,
            @RequestHeader("uid") String uid) {
        // 1. 生成验证码并存储（有效期5分钟）
        String code = iSendMailService.generateAndStoreCode(uid, email);

        // 2. 发送邮件
        iSendMailService.sendVerificationMail(email, "邮箱验证", code);

        return HttpResult.success("发送成功");
    }

    @Operation(summary = "验证邮箱验证码")
    @PostMapping("/verifycode")
    public HttpResult<String> verifyCode(
            @RequestParam String email,
            @RequestParam String code,
            @RequestHeader("uid") String uid) {
        // 1. 验证验证码
        boolean isValid = iSendMailService.verifyCode(uid, email, code);
        if (!isValid) {
            return HttpResult.failed("验证码错误或已过期");
        }

        // 2. 激活邮箱
        iSendMailService.activateEmail(uid);

        return HttpResult.success("激活成功");
    }

}
