package com.neuhealth.demo.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("sys_role_menu")
public class SysRoleMenu {
    private long roleId;
    private long menuId;
}
