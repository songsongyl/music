package com.music.demo.login.service.impl;

import cn.hutool.crypto.SmUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.music.demo.common.exception.user.UserCredentialsException;
import com.music.demo.common.exception.user.UsernameEmptyException;
import com.music.demo.common.exception.user.UsernameNotFoundException;
import com.music.demo.domain.entity.User;
import com.music.demo.login.mapper.UserMapper;
import com.music.demo.login.service.ILoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ILoginServiceImpl implements ILoginService {

    private final UserMapper mapper;

    @Override
    public User login(String username, String password) {
        if (Objects.isNull(username) || username.isEmpty()) {
            throw new UsernameEmptyException("用户名为空，登陆失败");
        }

        User user = mapper.selectOne(
                new LambdaQueryWrapper<User>()
                        .eq(User::getUsername, username)
        );

        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException("用户不存在，登陆失败");
        }

        String cpass = SmUtil.sm3(password);
        if (!cpass.equals(user.getPassword())) {
            throw new UserCredentialsException("用户名密码错误,登录失败");
        }


        return user;
    }
}
