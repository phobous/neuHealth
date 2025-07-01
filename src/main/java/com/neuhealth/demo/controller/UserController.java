package com.neuhealth.demo.controller;

import com.neuhealth.demo.domain.User;
import com.neuhealth.demo.domain.UserInfoDTO;
import com.neuhealth.demo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    // 查询系统用户列表
    @GetMapping("/queryUsers")
    public Map<String, Object> queryUsers(@RequestParam(required = false) String keyword) {
        List<UserInfoDTO> users = userService.queryUsers(keyword);
        Map<String, Object> result = new HashMap<>();
        result.put("isOk", true);
        result.put("msg", "查询用户成功");
        result.put("data", users);
        result.put("count", users.size());
        return result;
    }

    // 查询角色列表（假设角色数据存储在数据库中）
    @GetMapping("/queryRoles")
    public Map<String, Object> queryRoles() {
        List<Map<String, Object>> roles = new ArrayList<>();

        roles.add(Map.of("id", 1, "roleName", "管理员"));
        roles.add(Map.of("id", 2, "roleName", "普通用户"));
        roles.add(Map.of("id", 3, "roleName", "医生"));
        roles.add(Map.of("id", 4, "roleName", "健康管家"));

        Map<String, Object> result = new HashMap<>();
        result.put("isOk", true);
        result.put("msg", "查询角色成功");
        result.put("data", roles);
        result.put("count", roles.size());

        return result;
    }


    // 添加用户
    @PostMapping("/addUser")
    public Map<String, Object> addUser(@RequestBody User user) {
        if (user.getPhone() != null && user.getPhone().length() >= 6) {
            String defaultPassword = user.getPhone().substring(user.getPhone().length() - 6);
            user.setPassword(defaultPassword);
        }

        userService.addUser(user);
        Map<String, Object> result = new HashMap<>();
        result.put("isOk", true);
        result.put("msg", "添加用户成功");
        return result;
    }

    // 更新用户
    @PutMapping("/updateUser")
    public Map<String, Object> updateUser(@RequestBody User user) {
        userService.updateUser(user);
        Map<String, Object> result = new HashMap<>();
        result.put("isOk", true);
        result.put("msg", "更新用户成功");
        return result;
    }

    // 删除用户
    @DeleteMapping("/deleteUser/{userId}")
    public Map<String, Object> deleteUser(@PathVariable int userId) {
        userService.deleteUser(userId);
        Map<String, Object> result = new HashMap<>();
        result.put("isOk", true);
        result.put("msg", "删除用户成功");
        return result;
    }

    // 配置用户角色
    @PostMapping("/assignRole")
    public Map<String, Object> assignRole(@RequestParam int userId, @RequestParam long roleId) {
        userService.assignRole(userId, roleId);
        Map<String, Object> result = new HashMap<>();
        result.put("isOk", true);
        result.put("msg", "角色分配成功");
        return result;
    }
}
