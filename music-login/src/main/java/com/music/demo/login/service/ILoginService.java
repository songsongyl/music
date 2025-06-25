package com.music.demo.login.service;

import com.music.demo.domain.entity.User;

public interface ILoginService {
    User login(String username, String password);
}
