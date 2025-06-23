package com.neuhealth.demo.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("care_records")
public class CareRecord {
    private Integer id;
    private Integer clientId;
    private Integer itemId;
    private Integer caregiverId;
    private Date careTime;
    private Integer careQuantity;
    private boolean isDeleted;
}
