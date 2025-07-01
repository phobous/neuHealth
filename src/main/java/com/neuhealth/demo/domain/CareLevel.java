package com.neuhealth.demo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("care_levels")
public class CareLevel {
    @TableId(type = IdType.AUTO) // ⭐ 自动自增主键
    private Integer id;
    private String name;
    private String status;
}
