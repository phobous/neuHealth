package com.neuhealth.demo.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("care_level_items")
public class CareLevelItem {
    private int id;
    private int levelId;
    private int itemId;
}
