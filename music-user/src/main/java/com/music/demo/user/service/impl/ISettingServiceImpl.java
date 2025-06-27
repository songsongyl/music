package com.music.demo.user.service.impl;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.SmUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.music.demo.admin.service.AdminService;
import com.music.demo.common.exception.user.UserSettingException;
import com.music.demo.domain.entity.User;
import com.music.demo.user.mapper.UserMapper;
import com.music.demo.user.service.ISettingService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ISettingServiceImpl implements ISettingService {

    private final UserMapper mapper;
    @Value("${mypath}")
    private String path;

    private final AdminService adminService;
    @Override
    public void changePassword(String uid, String password, String newpassword) {
        if (Objects.isNull(newpassword) || newpassword.isEmpty()) {
            throw new UserSettingException("新的密码不能为空");
        }

        User user =  mapper.selectOne(
                new LambdaQueryWrapper<User>()
                        .eq(User::getId,uid)
        );

        String oldPasswordN = SmUtil.sm3(password);
        if (!user.getPassword().equals(oldPasswordN)) {
            throw new UserSettingException("输入的密码不正确，请重新输入");
        }

        String newpasswordN = SmUtil.sm3(newpassword);
        user.setPassword(newpasswordN);
        mapper.updateById(user);

    }

//    @Override
//    public void changePassword2(String userId, String password, String newpassword) {
//        if (Objects.isNull(newpassword) || newpassword.isEmpty()) {
//            throw new UserSettingException("新的密码不能为空");
//        }
//
//        User user =  mapper.selectById(userId);
//
//        String oldPasswordN = SmUtil.sm3(password);
//        if (!user.getPassword().equals(oldPasswordN)) {
//            throw new UserSettingException("输入的密码不正确，请重新输入");
//        }
//
//        String newpasswordN = SmUtil.sm3(newpassword);
//        user.setPassword(newpasswordN);
//        mapper.updateById(user);
//
//    }
    @SneakyThrows
    @Override
    public void uploadAvator(MultipartFile file,String uid) {


        String fileType = file.getContentType();
        if (!fileType.contains("image")) throw new UserSettingException("文件格式异常，请选择图片");
//        file.transferTo(new File(path + file.getOriginalFilename()));
        String encode = Base64.encode(file.getInputStream());
        String ncode = SmUtil.sm3(encode);
        User user = adminService.findById(uid);
        user.setAvatar(ncode);
        mapper.updateById(user);
        file.transferTo(new File(path + file.getOriginalFilename()));
    }

    @Override
    public void changeUserSettings(String uid,User user) {
        if (Objects.isNull(user)) {
            throw new UserSettingException("新的用户信息不能为空");
        }
        User user1 =  mapper.selectOne(
                new LambdaQueryWrapper<User>()
                        .eq(User::getId,uid)
        );
        user1.setEmail(user.getEmail());
        user1.setPhone(user.getPhone());
        user1.setName(user.getName());
        user1.setUsername(user.getUsername());
        mapper.updateById(user1);
    }
}
