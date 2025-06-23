package com.neuhealth.demo.controller;

import com.neuhealth.demo.domain.User;
import com.neuhealth.demo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController  // ✅ 必须加上它
@RequestMapping("/user")  // 可选：建议加上统一路径前缀
public class LoginController {

    @Autowired
    private IUserService userService;

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> loginData) {
        String username = loginData.get("username");
        String password = loginData.get("password");

        Map<String, Object> result = new HashMap<>();

        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
            result.put("isOk", false);
            result.put("msg", "账号或密码不能为空");
            return result;
        }

        User user = userService.login(username, password);

        if (user != null) {
            result.put("isOk", true);
            result.put("msg", "登录成功");
            result.put("user", user);
            result.put("token", "mock-token");
        } else {
            result.put("isOk", false);
            result.put("msg", "账号或密码错误");
        }

        return result;
    }
}
