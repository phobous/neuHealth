package com.neuhealth.demo.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("client_care_config")
public class ClientCareConfig {
    private int id;
    private int clientId;
    private int careLevelId;
    private int itemId;
    private Date startDate;
    private int quantity;
    private Date endDate;
    //护理项目状态
    @TableField(exist = false)
    private String status;  // 动态字段：normal / expired / arrear
}
