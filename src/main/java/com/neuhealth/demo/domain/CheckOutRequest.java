package com.neuhealth.demo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("check_out_requests")
public class CheckOutRequest {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private int clientId;
    private String type;
    private String reason;
    private Date requestedAt;
    private String status;
    private int reviewerId;
    private Date reviewTime;
    private String detail;

}
