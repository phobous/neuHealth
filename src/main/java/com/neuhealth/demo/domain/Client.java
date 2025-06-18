package com.neuhealth.demo.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("clients")
public class Client {
    private int id;
    private String name;
    private String gender;
    private String nation;
    private Date birthDate;
    private String idCard;
    private String bloodType;
    private int mealId;
    private Date checkInDate;
    private Date contractEndDate;
    private String type;
    private boolean isDeleted;
    private Date createdAt;
    private int bedId;
}
