package com.music.demo.user.controller;

import com.music.demo.common.result.HttpResult;
import com.music.demo.domain.entity.User;
import com.music.demo.user.service.ISettingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "测试用户信息设置接口")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/setting")
public class SettingController {

    private final ISettingService iSettingService;

    @Operation(summary = "用户修改密码")
    @GetMapping("/changePassword")
    public HttpResult<String> changePassword(String password, String newPassword,@RequestHeader("uid") String uid) {
        iSettingService.changePassword(uid,password,newPassword);
        return HttpResult.success("修改成功");
    }

    @Operation(summary = "用户修改个人信息")
    @PostMapping("/changeUserSettings")
    public HttpResult<String> changeUserName(@RequestHeader("uid") String uid,@RequestBody User user) {
        iSettingService.changeUserSettings(uid,user);
        return HttpResult.success("修改成功");
    }
//    @Operation(summary = "用户修改密码2")
//    @GetMapping("/user/changePassword")
//    public HttpResult<String> changePassword(@RequestAttribute("uid") String userId, String password, String newPassword) {
////        iSettingService.changePassword(username,password,newPassword);
//        iSettingService.changePassword2(userId,password,newPassword);
//        return HttpResult.success("修改成功");
//    }

    @Operation(summary = "上传头像图片")
    @PostMapping("/uploadAvatar")
    public HttpResult<String> uploadAvator(@RequestBody MultipartFile file,@RequestHeader("uid") String uid) {
        iSettingService.uploadAvator(file,uid);
        return HttpResult.success("上传成功");
    }

}
