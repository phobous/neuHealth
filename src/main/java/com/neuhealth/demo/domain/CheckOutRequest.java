package com.neuhealth.demo.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("check_out_requests")
public class CheckOutRequest {
    private int id;
    private int clientId;
    private String type;
    private String reason;
    private Date requestedAt;
    private String status;
    private int reviewerId;
    private Date reviewTime;
}
