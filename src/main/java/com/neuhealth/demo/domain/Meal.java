package com.neuhealth.demo.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("meals")
public class Meal {
    private int id;
    private int clientId;
    private String mealName;
    private String mealType;
    private String period;
    private boolean isDeleted;
}
