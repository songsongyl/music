package com.music.demo.admin.service;

import com.music.demo.domain.entity.User;

import java.util.List;

public interface AdminService {
  User findById(String id);
  void removeUser(String userId);
  void resetPassword(String userId);
  //void upadateUser(User user);
  List<User> findAll();
  List<User> searchUsers(String role, String name);
  //    void registry(User user);
  List<User> findByCondition(String keyword);
}
