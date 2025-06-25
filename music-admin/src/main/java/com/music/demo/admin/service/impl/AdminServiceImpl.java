package com.music.demo.admin.service.impl;

import com.music.demo.admin.mapper.AdminMapper;
import com.music.demo.admin.service.AdminService;
import com.music.demo.domain.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminMapper adminMapper;

    @Override
    public User findById(String id) {
      User user = adminMapper.selectById(id);
        return user;
    }
}
