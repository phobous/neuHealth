package com.neuhealth.demo.service.impl;

import com.neuhealth.demo.domain.User;
import com.neuhealth.demo.domain.UserDTO;
import com.neuhealth.demo.domain.UserInfoDTO;
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
    public UserDTO login(String username, String password) {
        return userMapper.login(username, password);
    }
    @Override
    public List<UserInfoDTO> queryUsers(String keyword) {
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
        // 1. 根据 roleName 查询 role_id
        Long roleId = user.getRoleId();
        if (roleId == null) {
            throw new IllegalArgumentException("角色名称无效，找不到对应的角色ID");
        }
        // 2. 更新 user 表的信息
        user.setRoleId(roleId); // 设置查询到的 role_id
        userMapper.updateById(user); // 更新用户信息

        // 如果你有更多的联动表更新逻辑，可以在这里继续扩展
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
