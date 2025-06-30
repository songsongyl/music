package com.music.demo.login.service;

import com.music.demo.domain.entity.User;

import java.util.List;

public interface IRegistryService {
    //    void addUser(User user);
    List<User> findAll();
    //    User findSingleUser();
    void registry(User user,String EmailCode);
    boolean findEmail(String email);
}
