package com.neuhealth.demo.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("menu")
public class Menu {
    private long menuId;
    private String menuName;
    private long parentId;
    private int orderNum;
    private String path;
    private String icon;
    private String status;
    private String delFlag;
}
