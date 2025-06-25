package com.music.demo.login.service.impl;

import cn.hutool.crypto.SmUtil;
import com.music.demo.common.exception.user.UserCredentialsException;
import com.music.demo.common.exception.user.UsernameEmptyException;
import com.music.demo.common.util.ULID;
import com.music.demo.domain.entity.User;
import com.music.demo.login.mapper.UserMapper;
import com.music.demo.login.service.IRegistryService;
import com.music.demo.login.util.BloomFilterUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class IRegistryServiceImpl implements IRegistryService {

    private final UserMapper mapper;
    private final ULID ulid;

    @Override
    public List<User> findAll() {
        return mapper.selectList(null);
    }

    @Override
    public void registry(User user) {

        String username = user.getUsername();
        boolean isExist = BloomFilterUtil.getInstance().contains(username);
        if (isExist) throw new UserCredentialsException("注册失败，用户名已存在");
        if (Objects.isNull(user.getPassword()) || user.getPassword().isEmpty()) {
            throw new UsernameEmptyException("注册失败，密码不能为空");
        }

        String newP = SmUtil.sm3(user.getPassword());
        user.setPassword(newP);

        user.setId(ulid.nextULID());
        user.setUpdateby("admin");
        user.setRole(User.USER);
        user.setUpdateTime(new Date());

        mapper.insert(user);
        BloomFilterUtil.getInstance().add(user.getUsername());
    }


}
