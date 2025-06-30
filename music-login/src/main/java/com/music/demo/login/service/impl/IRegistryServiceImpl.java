package com.music.demo.login.service.impl;

import cn.hutool.crypto.SmUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.music.demo.common.exception.user.UserCredentialsException;
import com.music.demo.common.exception.user.UserSettingException;
import com.music.demo.common.exception.user.UsernameEmptyException;
import com.music.demo.common.util.ULID;
import com.music.demo.domain.entity.User;
import com.music.demo.login.mapper.UserMapper;
import com.music.demo.login.service.IRegistryService;
import com.music.demo.login.service.ISendMailService;
import com.music.demo.login.util.BloomFilterUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class IRegistryServiceImpl implements IRegistryService {

    private final UserMapper mapper;
    private final ULID ulid;
    private final ISendMailService sendMailService;

    @Override
    public List<User> findAll() {
        return mapper.selectList(null);
    }

    @Override
    public void registry(User user,String emailCode) {

        String username = user.getUsername();
        boolean isExist = BloomFilterUtil.getInstance().contains(username);
        if (isExist) throw new UserCredentialsException("注册失败，用户名已存在");
        if (Objects.isNull(user.getPassword()) || user.getPassword().isEmpty()) {
            throw new UsernameEmptyException("注册失败，密码不能为空");
        }
        if (Objects.isNull(user.getUsername()) || user.getUsername().isEmpty()) {
            throw new UsernameEmptyException("注册失败，用户名不能为空");
        }

        String newP = SmUtil.sm3(user.getPassword());
        user.setPassword(newP);
        user.setId(ulid.nextULID());
        user.setUpdateby("admin");
        if(user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole(User.USER);
        }
        user.setUpdateTime(new Date());
        // 邮箱校验
        String email = user.getEmail();
        if (Objects.nonNull(email) && !email.isEmpty()) {
            if (!Pattern.matches("^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$", email)) {
                throw new UserSettingException("注册失败，邮箱格式不正确");
            }
        }

        if(findEmail(email)) throw new UserSettingException("邮箱已存在");

        // 手机号校验
        String phone = user.getPhone();
        if (Objects.nonNull(phone) && !phone.isEmpty()) {
            if (!Pattern.matches("^1[3-9]\\d{9}$", phone)) {
                throw new UserSettingException("注册失败，手机号格式不正确");
            }
        }
        

        mapper.insert(user);
        BloomFilterUtil.getInstance().add(user.getUsername());


    }

    @Override
    public boolean findEmail(String email) {
    if(mapper.selectCount(
                new LambdaQueryWrapper<User>()
                        .eq(User::getEmail, email)
        ) >0) return true;

       return false;
    }
}
