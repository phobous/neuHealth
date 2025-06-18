package com.neuhealth.demo.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("client_bed_mapping")
public class ClientBedMapping {
    private int id;
    private int clientId;
    private int bedId;
    private Date assignedAt;
    private boolean isDeleted;
    private Date createdAt;
    private Date updatedAt;
}
