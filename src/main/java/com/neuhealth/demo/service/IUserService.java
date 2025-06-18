package com.neuhealth.demo.service;

import com.neuhealth.demo.domain.User;

import java.util.List;

public interface IUserService {
    List<User> queryUsers(String keyword);
    void addUser(User user);
    void updateUser(User user);
    void deleteUser(int userId);
    void assignRole(int userId, long roleId);
    User login(String username, String password);
}
