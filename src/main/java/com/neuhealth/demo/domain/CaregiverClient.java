package com.neuhealth.demo.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("caregiver_client")
public class CaregiverClient {
    private int id;
    private int caregiverId;
    private int clientId;
}
