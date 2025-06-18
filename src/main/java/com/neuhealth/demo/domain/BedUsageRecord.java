package com.neuhealth.demo.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("bed_usage_records")
public class BedUsageRecord {
    private int id;
    private int clientId;
    private int bedId;
    private Date checkInDate;
    private Date checkOutDate;
    private String status;
    private boolean isDeleted;
    private Date createdAt;
    private Date updatedAt;
}
