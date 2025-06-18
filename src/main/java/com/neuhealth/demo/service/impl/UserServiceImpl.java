package com.neuhealth.demo.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.neuhealth.demo.domain.User;
import com.neuhealth.demo.mapper.UserMapper;
import com.neuhealth.demo.service.IUserService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements IUserService {

    private final UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }
    @Override
    public User login(String username, String password) {
        return userMapper.login(username, password);
    }
    @Override
    public List<User> queryUsers(String keyword) {
        return userMapper.queryUsers(keyword);
    }

    @Override
    public void addUser(User user) {
        // 密码默认设为手机号后六位
        String phone = user.getPhone();
        if (phone != null && phone.length() >= 6) {
            user.setPassword(phone.substring(phone.length() - 6));
        } else {
            user.setPassword("123456"); // 备用默认
        }
        user.setCreatedAt(new Date());
        userMapper.insert(user);
    }

    @Override
    public void updateUser(User user) {
        userMapper.updateById(user);
    }

    @Override
    public void deleteUser(int userId) {
        userMapper.logicalDelete(userId);
    }

    @Override
    public void assignRole(int userId, long roleId) {
        User user = userMapper.selectById(userId);
        if (user != null) {
            user.setRoleId(roleId);
            userMapper.updateById(user);
        }
    }
}
