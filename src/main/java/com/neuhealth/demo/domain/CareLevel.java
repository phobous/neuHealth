package com.neuhealth.demo.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("care_levels")
public class CareLevel {
    private int id;
    private String name;
    private String status;
}
