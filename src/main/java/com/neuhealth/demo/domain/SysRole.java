package com.neuhealth.demo.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("sys_role")
public class SysRole {
    private long roleId;
    private String roleName;
    private String roleKey;
    private int roleSort;
    private String status;
    private String delFlag;
}
