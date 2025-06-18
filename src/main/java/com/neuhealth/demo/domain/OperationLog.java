package com.neuhealth.demo.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("operation_logs")
public class OperationLog {
    private int id;
    private int operatorId;
    private String operationType;
    private String targetType;
    private int targetId;
    private String description;
    private String reason;
    private Date createdAt;
}
