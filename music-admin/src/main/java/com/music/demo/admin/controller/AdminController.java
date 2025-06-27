package com.music.demo.admin.controller;

import com.music.demo.admin.service.AdminService;
import com.music.demo.common.result.HttpResult;
import com.music.demo.domain.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @Operation(summary = "查找某一用户")
    @GetMapping("/findById")
    public HttpResult<User> findById(String userId) {
        return HttpResult.success(adminService.findById(userId));
    }

    @Operation(summary = "重置密码")
    @PutMapping("/resetPassword/{userId}")
    public HttpResult<String> resetPassword(@PathVariable String userId) {
        adminService.resetPassword(userId);
        return HttpResult.success("重置密码");
    }

    @Operation(summary = "删除用户")
    @DeleteMapping("/removeUser/{userId}")
    public HttpResult<String> removeUser(@PathVariable String userId){
        adminService.removeUser(userId);
        return HttpResult.success("删除成功");
    }

    @Operation(summary = "根据角色/姓名查询用户")
    @GetMapping("/searchUserByRoleAndName/{role}/{name}")
    public HttpResult<List<User>> searchUsers(
            @PathVariable(required = false) String role,
            @PathVariable(required = false) String name) {
        List<User> users = adminService.searchUsers(role, name);
        return HttpResult.success(users);
    }
    @Operation(summary = "根据关键字查询用户")
    @GetMapping("/searchUserByCondition/{keyword}")
    public HttpResult<List<User>> searchUsers(@PathVariable String keyword) {
        List<User> users = adminService.findByCondition(keyword);
        return HttpResult.success(users);
    }

    @Operation(summary = "查询所有用户")
    @GetMapping("/findAll")
    public HttpResult<List<User>> findAll(){
        List<User> users = adminService.findAll();
        return HttpResult.success(users);
    }
}
