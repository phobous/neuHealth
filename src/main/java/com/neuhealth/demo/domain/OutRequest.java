package com.neuhealth.demo.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("out_requests")
public class OutRequest {
    private int id;
    private int clientId;
    private String reason;
    private Date outTime;
    private Date expectedReturnTime;
    private Date actualReturnTime;
    private String status;
    private int reviewerId;
    private Date reviewTime;
}
