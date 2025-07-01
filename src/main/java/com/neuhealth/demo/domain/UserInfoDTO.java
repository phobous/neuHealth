package com.neuhealth.demo.domain;

import lombok.Data;

@Data
public class UserInfoDTO {
    private Integer id;           // 序号
    private String realName;      // 姓名
    private String phone;         // 电话
    private String gender;        // 性别
    private String username;      // 昵称
    private String email;         // 邮箱
    private String roleName;      // 员工类型（角色名称）
}
