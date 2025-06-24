package com.neuhealth.demo.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
@TableName("client_care_config")
public class ClientCareConfig {
    private int id;
    private int clientId;
    private int careLevelId;
    private int itemId;
    private LocalDate startDate;
    private int quantity;
    private LocalDate endDate;
    //护理项目状态
    @TableField(exist = false)
    private String status;  // 动态字段：normal / expired / arrear

    // 添加 itemName 字段
    private String itemName;

    // 其他属性的 getter 和 setter 方法

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

}
