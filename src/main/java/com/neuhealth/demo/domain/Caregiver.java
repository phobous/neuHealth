package com.neuhealth.demo.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("caregivers")
public class Caregiver {
    private int id;
    private int userId;
    private String name;
    private String gender;
    private String phone;
    private String email;
    private String introduction;
    private String status;
    private Date createdAt;
}
