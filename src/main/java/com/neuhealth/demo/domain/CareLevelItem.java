package com.neuhealth.demo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("care_level_items")
public class CareLevelItem {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer levelId;
    private Integer itemId;
}
