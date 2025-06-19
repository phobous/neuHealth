package com.neuhealth.demo.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("users")  // 指定与数据库表名“users”映射
// 1. 用户实体类
public class User {
    private int id;
    private String username;
    private String password;
    private String realName;
    private String phone;
    private long roleId;
    private boolean status;
    private Date createdAt;
    private boolean isDeleted;


}