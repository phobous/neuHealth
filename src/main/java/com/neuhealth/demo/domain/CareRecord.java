package com.neuhealth.demo.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("care_records")
public class CareRecord {
    private int id;
    private int clientId;
    private int itemId;
    private int caregiverId;
    private Date careTime;
    private int careQuantity;
    private boolean isDeleted;
}
