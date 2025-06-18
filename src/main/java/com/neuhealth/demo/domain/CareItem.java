package com.neuhealth.demo.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("care_items")
public class CareItem {
    private int id;
    private String code;
    private String name;
    private double price;
    private String cycle;
    private int times;
    private String description;
    private String status;
    private boolean isDeleted;
}
