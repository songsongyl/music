package com.music.demo.user.service;

import com.music.demo.domain.entity.User;
import org.springframework.web.multipart.MultipartFile;

public interface ISettingService {
    void changePassword(String uid, String password, String newpassword);
//    void changePassword2(String username, String password, String newpassword);
    void uploadAvator(MultipartFile file,String uid);
    void changeUserSettings(String uid, User user);
}
