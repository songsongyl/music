package com.music.demo.admin.service.impl;

import cn.hutool.crypto.SmUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.music.demo.admin.mapper.AdminMapper;
import com.music.demo.admin.service.AdminService;
import com.music.demo.domain.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminMapper adminMapper;

    @Override
    public User findById(String id) {
      User user = adminMapper.selectById(id);
        return user;
    }
    @Override
    public void removeUser(String userId) {
        adminMapper.deleteById(userId);
    }

    //    @Override
//    public void upadateUser(User user) {
//        mapper.updateById(user);
//    }
    @Override
    public void resetPassword(String userId) {
        User user = findById(userId);
        String newPassword = SmUtil.sm3(user.getUsername());
        user.setPassword(newPassword);
        adminMapper.updateById(user);
    }

    @Override
    public List<User> findAll() {
        LambdaQueryWrapper<User> queryWrapper =
                new LambdaQueryWrapper<>();
        return adminMapper.selectList(queryWrapper);
        //mapper.selectList(null)
//        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.select(User.class, info -> !"user_password".equals(info.getColumn()));
//        return mapper.selectList(queryWrapper);
    }


    @Override
    public List<User> searchUsers(String role, String name) {
        // 1. 构建查询条件
        LambdaQueryWrapper<User> queryWrapper = Wrappers.lambdaQuery();
        if (role != null) {
            queryWrapper.eq(User::getRole, role);
        }
        if (name != null) {
            queryWrapper.eq(User::getName, name);
        }
        List<User> users = adminMapper.selectList(queryWrapper);
        return users;
    }
    @Override
    public List<User> findByCondition(String keyword) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(User::getUsername, keyword)
                .or()
                .like(User::getName, keyword)
                .or()
                .like(User::getEmail, keyword);

        List<User> users = adminMapper.selectList(queryWrapper);
        return users;
    }
}
