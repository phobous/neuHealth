package com.neuhealth.demo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("care_items")
public class CareItem {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String code;
    private String name;
    private double price;
    private String cycle;
    private Integer times;
    private String description;
    private String status;
    private boolean isDeleted;
}
