package com.neuhealth.demo.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("check_out_requests")
public class CheckOutRequest {
    private Integer id;
    private Integer clientId;
    private String type;
    private String reason;
    private Date requestedAt;
    private String status;
    private Integer reviewerId;
    private Date reviewTime;
    private String detail;
}
