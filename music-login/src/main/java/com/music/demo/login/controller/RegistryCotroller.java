package com.music.demo.login.controller;

import com.music.demo.common.result.HttpResult;
import com.music.demo.domain.entity.User;
import com.music.demo.login.mapper.UserMapper;
import com.music.demo.login.service.IRegistryService;
import com.music.demo.login.util.BloomFilterUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "测试用户注册接口")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RegistryCotroller {

    private final IRegistryService iRegistryService;


    @Operation(summary = "测试用户注册")
    @PostMapping("/register")
    public HttpResult<String> registry(@RequestBody User user,String emailCode) {
        iRegistryService.registry(user,emailCode);
        return HttpResult.success("注册成功");
    }
}
