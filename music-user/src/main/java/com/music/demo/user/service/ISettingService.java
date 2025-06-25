package com.music.demo.user.service;

import org.springframework.web.multipart.MultipartFile;

public interface ISettingService {
    void changePassword(String username, String password, String newpassword);
//    void changePassword2(String username, String password, String newpassword);
    void uploadAvator(MultipartFile file);
}
