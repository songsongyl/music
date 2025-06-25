package com.music.demo.user.service.impl;

import cn.hutool.crypto.SmUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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

    @Override
    public void changePassword(String username, String password, String newpassword) {
        if (Objects.isNull(newpassword) || newpassword.isEmpty()) {
            throw new UserSettingException("新的密码不能为空");
        }

        User user =  mapper.selectOne(
                new LambdaQueryWrapper<User>()
                        .eq(User::getUsername,username)
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
    public void uploadAvator(MultipartFile file) {


        String fileType = file.getContentType();
        if (!fileType.contains("image")) throw new UserSettingException("文件格式异常，请选择图片");
//        file.transferTo(new File(path + file.getOriginalFilename()));
        file.transferTo(new File(path + file.getOriginalFilename()));
    }
}
