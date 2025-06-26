package com.music.demo.admin.controller;

import com.music.demo.admin.service.AdminService;
import com.music.demo.common.result.HttpResult;
import com.music.demo.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/findById")
    public HttpResult<User> findById(String userId) {
        return HttpResult.success(adminService.findById(userId));
    }
}
