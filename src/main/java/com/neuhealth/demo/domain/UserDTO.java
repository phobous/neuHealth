package com.neuhealth.demo.domain;

import lombok.Data;

import java.util.Date;
@Data
public class UserDTO {
    private Integer id;
    private String username;
    private String password;
    private String realName;
    private String gender;
    private String phone;
    private String email;
    private long roleId;
    private boolean status;
    private Date createdAt;
    private boolean isDeleted;

    // 健康管家ID（仅当 roleId == 4 时存在）
    private Integer caregiverId;

}
